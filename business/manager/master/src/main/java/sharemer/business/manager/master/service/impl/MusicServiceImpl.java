package sharemer.business.manager.master.service.impl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import sharemer.business.manager.master.dao.*;
import sharemer.business.manager.master.exception.BusinessException;
import sharemer.business.manager.master.mao.UserMao;
import sharemer.business.manager.master.pipline.DownLoadPipLine;
import sharemer.business.manager.master.po.*;
import sharemer.business.manager.master.remoteapi.AllRemoteApiService;
import sharemer.business.manager.master.service.MusicService;
import sharemer.business.manager.master.service.TagService;
import sharemer.business.manager.master.service.VideoService;
import sharemer.business.manager.master.utils.Constant;
import sharemer.business.manager.master.utils.RedisKeys;
import sharemer.business.manager.master.utils.SourceProxy;
import sharemer.business.manager.master.vo.*;
import sharemer.business.manager.master.vo.remote.mtv.MtvReqData;
import sharemer.business.manager.master.vo.remote.mtv.Tag;
import sharemer.business.manager.master.vo.remote.mtv.VideoData;
import sharemer.component.redis.client.SharemerRedisClient;
import sun.plugin.dom.core.CoreConstants;

import javax.annotation.Resource;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by 18073 on 2017/5/30.
 */
@Service("musicService")
public class MusicServiceImpl implements MusicService {

    @Resource
    private MusicMapper musicMapper;

    @Resource
    private MusicListMapper musicListMapper;

    @Resource
    private TagMapper tagMapper;

    @Resource
    private TagMediaMapper tagMediaMapper;

    @Resource
    private AllRemoteApiService allRemoteApiService;

    @Resource
    private MusicVideoMapper musicVideoMapper;

    @Resource
    private TagService tagService;

    @Resource
    private VideoService videoService;

    @Resource
    private UserMao userMao;

    @Resource
    private SharemerRedisClient sharemerRedisClient;

    @Resource
    private FavListMapper favListMapper;

    @Resource
    private FavMediaMapper favMediaMapper;

    @Resource
    private SourceProxy sourceProxy;

    @Resource
    private DownLoadPipLine downLoadPipLine;

    @Override
    public List<MusicVo> getAllMusic(String key, Integer type, Page<MusicVo> page) {
        key = StringUtils.isEmpty(key) ? null : key;
        List<MusicVo> result = this.musicMapper.getAll(key, type,
                (page.getPageNo() - 1) * page.getPageSize(),
                page.getPageSize());
        page.setRecords(result);
        page.setTotalCount(this.musicMapper.getAllCount(key, type));
        page.getRecords().forEach(musicVo -> {
            /** 填充tag*/
            musicVo.setTags(this.tagMapper.getTagByMediaId(musicVo.getId() % 10,
                    musicVo.getId(), TagMedia.MUSIC_TYPE));
        });
        return result;
    }

    @Override
    public void update(Music music) {
        this.musicMapper.update(music);
    }

    @Override
    public void add(Music music) {
        this.musicMapper.insert(music);
    }

    @Override
    public Music one(Integer id) {
        return this.musicMapper.one(id);
    }

    @Override
    public Music getOneBySongId(Integer song_id) {
        return this.musicMapper.getOneBySongId(song_id);
    }

    /**
     * 落地网易云歌单内所有的歌曲信息
     */
    @Override
    public void deepMusic(Long list_id) throws BusinessException {

        /** 首先落地收藏夹*/
        MusicList ml = this.musicListMapper.getOneByWyId(list_id);
        FavList favList = new FavList();
        favList.setTitle(ml.getTitle());
        favList.setCover(ml.getCover());
        User user = this.getRandomRobot();
        favList.setUser_id(user.getId());
        this.favListMapper.insert(favList);
        String key = RedisKeys.FavList.getFavListByUserId(user.getId());
        /** 添加redis关联*/
        sharemerRedisClient.zadd(key, new Date().getTime(), favList.getId() + "");

        WyObj wyObj = allRemoteApiService.getMusicsByListId(list_id);

        if (wyObj != null && wyObj.getCode() == 200 && wyObj.getResult() != null) {
            WyMusic wyMusic = wyObj.getResult();
            List<Integer> tagIds = this.tagService.tagAdd(wyMusic.getTags());//补录tag
            Music music = new Music();
            if (wyMusic.getTracks() != null && wyMusic.getTracks().size() > 0) {
                wyMusic.getTracks().forEach(track -> {

                    //落地music
                    this.musicAdd(track, music, favList.getId());

                    //落地Tag关联信息
                    List<TagMedia> tagMedias = new ArrayList<>();
                    tagIds.forEach(tagId -> {
                        TagMedia tagMedia = new TagMedia();
                        tagMedia.setMedia_id(music.getId());
                        tagMedia.setType(TagMedia.MUSIC_TYPE);
                        tagMedia.setTag_id(tagId);
                        tagMedias.add(tagMedia);
                        /** 收藏单Tag关联*/
                        TagMedia tagMedia2 = new TagMedia();
                        tagMedia2.setMedia_id(favList.getId());
                        tagMedia2.setType(TagMedia.FAV_TYPE);
                        tagMedia2.setTag_id(tagId);
                        tagMedias.add(tagMedia2);
                    });

                    if (tagMedias.size() > 0) {
                        //tag-media和media-tag各加一遍
                        tagMedias.forEach(tagMedia -> {
                            if (tagMedia.getMedia_id() != null && tagMedia.getTag_id() != null) {
                                TagMedia origin = tagMediaMapper.getOneFromMediaTagByMediaIdAndTagId(
                                        tagMedia.getMedia_id() % 10,
                                        tagMedia.getMedia_id(), tagMedia.getTag_id(), tagMedia.getType());
                                if (origin == null) {
                                    tagMediaMapper.insertMediaTag(tagMedia.getMedia_id() % 10, tagMedia);
                                    tagMediaMapper.insertTagMedia(tagMedia.getTag_id() % 10, tagMedia);
                                }
                            }
                        });
                    }
                    music.setId(null);
                });
            }
        }
        //将歌单设置为已完成状态
        MusicList musicList = new MusicList();
        musicList.setWy_id(list_id);
        musicList.setIs_done(1);
        musicListMapper.updateByWyId(musicList);
    }

    @Override
    public String getMvUrl(Integer mv_id) throws BusinessException {
        String html = this.allRemoteApiService.getMvUrlByMvId(mv_id);
        if (!StringUtils.isEmpty(html)) {
            Document document = Jsoup.parse(html);
            Elements e = document.select("embed[flashvars]");
            if (e.size() > 0 && e.get(0) != null) {
                Element element = e.get(0);
                String mp4 = element.attr("flashvars");
                mp4 = mp4.substring(mp4.indexOf("http://"), mp4.indexOf(".mp4") + 4);
                return mp4;
            }
        }
        return null;
    }

    private void musicAdd(Track track, Music music, Integer favId) {
        Music origin = this.musicMapper.getOneBySongId(track.getId());
        if (origin == null) {
            String otherMsg = "";
            //添加音乐
            music.setTitle(track.getName());
            if (track.getAlbum() != null) {
                music.setAlbum_title(track.getAlbum().getName());
                music.setCover(track.getAlbum().getPicUrl());
                if (track.getAlbum().getPublishTime() != null) {
                    Instant instant = Instant.ofEpochMilli(track.getAlbum().getPublishTime());
                    music.setPublish_time(LocalDateTime.ofInstant(instant, ZoneId.systemDefault()));
                }
                otherMsg += "所属专辑《" + track.getAlbum().getName() + "》，专辑类型为「" +
                        (StringUtils.isEmpty(track.getAlbum().getType()) ? "未知" : track.getAlbum().getType())
                        + "」";
            }
            music.setDuration(track.getDuration());
            music.setMv_id(track.getMvid());
            /*if(track.getMvid() > 0){
             *//** 如果mvid大于0，则说明音乐存在mv，则进行爬取mv的视频外链*//*
                try{
                    music.setMv_playurl(this.getMvUrl(track.getMvid()));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }*/
            music.setScore(track.getScore());
            music.setScore_num(1);
            StringBuilder sb = new StringBuilder();

            track.getArtists().forEach(artist -> {
                sb.append(artist.getName() + "   ");
            });
            music.setSonger(sb.toString());
            otherMsg += "，发行公司为「" +
                    (StringUtils.isEmpty(track.getAlbum().getCompany()) ? "未知" : track.getAlbum().getCompany())
                    + "」";
            music.setOther_msg(otherMsg);
            music.setSong_id(track.getId());

            /** 批量导入音乐时需要指定系统内机器人*/
            User robot = this.getRandomRobot();
            music.setR_id(robot.getId());
            music.setR_name(robot.getName());
            music.setSource_url(sourceProxy.getNETEASESOURCE(track.getId()));
            //落地音乐信息
            this.musicMapper.insert(music);
            // 下载封面
            downLoadPipLine.push(new DownLoadVo(music.getId(), music.getCover(), Constant.TagMedia.MUSIC_TYPE));
            /** 落地收藏单redis关系*/
            String key = RedisKeys.FavList.getMusicListByFavId();
            sharemerRedisClient.sadd(String.format(key, favId), music.getId() + "");
            FavMedia favMedia = new FavMedia(music.getId(), Constant.TagMedia.MUSIC_TYPE, favId);
            favMediaMapper.insertFavMedia(favId % Constant.TOTAL_TABLE_NUM, favMedia);
            favMediaMapper.insertMediaFav(music.getId() % Constant.TOTAL_TABLE_NUM, favMedia);

            /** 获取相关视频资源*/
            try {
                this.videoAdd(favId, music);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            music.setId(origin.getId());
        }
    }

    @Override
    public void videoAdd(Integer favId, Music music) throws BusinessException {
        /** 落地音乐后再次利用title获取A站B站相关视频资源*/
        if (!StringUtils.isEmpty(music.getTitle())) {
            BiliSearch biliSearch = allRemoteApiService.getBliVideoByTitle(music.getTitle());

            /** 每个网站各取3条数据，关联至音乐*/
            List<Video> finalResult = new ArrayList<>();

            if (biliSearch != null && biliSearch.getResult() != null
                    && biliSearch.getResult().size() > 0) {
                int size = biliSearch.getResult().size() > 3 ? 3 : biliSearch.getResult().size();
                for (int i = 0; i < size; i++) {
                    SearchResult searchResult = biliSearch.getResult().get(i);
                    String[] tags = searchResult.getTag().split(",");
                    List<String> finalTags = Arrays.asList(tags);
                    if (finalTags.size() > 0) {
                        Video video = new Video();
                        video.setTitle(searchResult.getTitle());
                        video.setV_id(searchResult.getAid());
                        video.setPage(1);
                        video.setCover(searchResult.getPic());
                        video.setType(Constant.Video.TYPE_BILIBILI);
                        video.setMsg(searchResult.getDescription());
                        video.setVideo_url(Constant.Video.getBUrl(searchResult.getAid()));
                        video.setFinal_tags(finalTags);
                        finalResult.add(video);
                    }
                }
            }

            MtvReqData mtvReqData = allRemoteApiService.getMtvVideoByTitle(music.getTitle());
            if (mtvReqData != null && mtvReqData.getVideos() != null
                    && mtvReqData.getVideos().getData() != null
                    && mtvReqData.getVideos().getData().size() > 0) {

                int size = mtvReqData.getVideos().getData().size() > 3 ? 3 : mtvReqData.getVideos().getData().size();

                for (int i = 0; i < size; i++) {
                    VideoData videoData = mtvReqData.getVideos().getData().get(i);
                    if (videoData.getArtists() != null && videoData.getArtists().size() > 0) {
                        List<String> finalTags = videoData.getArtists().stream().map(Tag::getName).collect(Collectors.toList());
                        Video video = new Video();
                        video.setTitle(videoData.getTitle());
                        video.setV_id(videoData.getId());
                        video.setPage(1);
                        video.setCover(videoData.getHeadImg());
                        video.setType(Constant.Video.TYPE_MTV);
                        video.setMsg(videoData.getHltitle());
                        video.setVideo_url(Constant.Video.getMtv(videoData.getId()));
                        video.setFinal_tags(finalTags);
                        finalResult.add(video);
                    }
                }
            }

            if (finalResult.size() > 0) {
                //乱序
                Collections.shuffle(finalResult);
                for (int i = 0; i < finalResult.size(); i++) {
                    Video video = finalResult.get(i);
                    videoService.deepVideoDB(video, video.getFinal_tags(), video.getV_id(), video.getType());
                    /** 是否存在关联关系*/
                    MusicVideo originMv = musicVideoMapper.getOneFromMusicVideoByMusicIdAndVideoId(
                            music.getId() % 10, music.getId(), video.getId());
                    if (originMv == null) {
                        /** 信息落地*/
                        MusicVideo musicVideo = new MusicVideo();
                        musicVideo.setMusic_id(music.getId());
                        musicVideo.setVideo_id(video.getId());
                        musicVideoMapper.insertMusicVideo(music.getId() % 10, musicVideo);
                        musicVideoMapper.insertVideoMusic(video.getId() % 10, musicVideo);

                        if (i == 0) {
                            FavMedia favMedia = new FavMedia(video.getId(), Constant.TagMedia.PV_TYPE, favId);
                            favMediaMapper.insertFavMedia(favId % Constant.TOTAL_TABLE_NUM, favMedia);
                            favMediaMapper.insertMediaFav(video.getId() % Constant.TOTAL_TABLE_NUM, favMedia);
                        }
                    }
                }
            }
        }
    }


    private User getRandomRobot() {
        try {
            List<User> robots = userMao.getRobots();
            if (robots != null && robots.size() > 0) {
                int num = new Random().nextInt(robots.size());//0~(size-1)之间的随机数
                User result = robots.get(num);
                return result == null ? new User() : result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new User();
    }
}
