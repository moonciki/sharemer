package sharemer.business.manager.master.service.impl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import sharemer.business.manager.master.dao.Page;
import sharemer.business.manager.master.dao.TagMapper;
import sharemer.business.manager.master.dao.TagMediaMapper;
import sharemer.business.manager.master.dao.VideoMapper;
import sharemer.business.manager.master.mao.UserMao;
import sharemer.business.manager.master.pipline.DownLoadPipLine;
import sharemer.business.manager.master.po.TagMedia;
import sharemer.business.manager.master.po.User;
import sharemer.business.manager.master.po.Video;
import sharemer.business.manager.master.remoteapi.AllRemoteApiService;
import sharemer.business.manager.master.service.TagService;
import sharemer.business.manager.master.service.VideoService;
import sharemer.business.manager.master.utils.Constant;
import sharemer.business.manager.master.utils.PriorityExecutor;
import sharemer.business.manager.master.utils.SourceProxy;
import sharemer.business.manager.master.vo.BiliSearch;
import sharemer.business.manager.master.vo.DownLoadVo;
import sharemer.business.manager.master.vo.remote.m.MissEvanSound;
import sharemer.business.manager.master.vo.SearchResult;
import sharemer.business.manager.master.vo.VideoVo;
import sharemer.business.manager.master.vo.remote.m.Tag;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Created by 18073 on 2017/6/15.
 */
@Service("videoService")
public class VideoServiceImpl implements VideoService {

    @Resource
    private VideoMapper videoMapper;

    @Resource
    private AllRemoteApiService allRemoteApiService;

    @Resource
    private TagService tagService;

    @Resource
    private TagMediaMapper tagMediaMapper;

    @Resource
    private TagMapper tagMapper;

    @Resource
    private UserMao userMao;

    @Resource
    private SourceProxy sourceProxy;

    @Resource
    private DownLoadPipLine downLoadPipLine;

    @Override
    public List<VideoVo> getAllVideo(String key, Integer type, Page<VideoVo> page) {
        key = StringUtils.isEmpty(key) ? null : key;
        page.setRecords(this.videoMapper.getAll(key, type,
                (page.getPageNo() - 1) * page.getPageSize(), page.getPageSize()));
        page.setTotalCount(this.videoMapper.getAllCount(key, type));
        page.getRecords().forEach(videoVo -> {
            /** 填充tag*/
            videoVo.setTags(this.tagMapper.getTagByMediaId(videoVo.getId() % 10,
                    videoVo.getId(), TagMedia.PV_TYPE));
            videoVo.setNet_name(Constant.videoNetName.get(videoVo.getType()));
            videoVo.setLogo_url(Constant.videoLogos.get(videoVo.getType()));
        });
        return page.getRecords();
    }

    @Override
    public void deepBVideo(Integer start, Integer end) {
        for(int offset = start; offset <= end; offset++){
            final int avId = offset;
            /** 异步线程执行*/
            PriorityExecutor.execute(() -> deepBVideo(avId), 1);
        }
    }


    @Override
    public void deepAVideo(Integer start, Integer end) {
        for(int offset = start; offset <= end; offset++){
            final int acId = offset;
            /** 异步线程执行*/
            PriorityExecutor.execute(() -> deepAVideo(acId), 1);
        }
    }

    @Override
    public void deepMVideo(Integer start, Integer end) {
        for(int offset = start; offset <= end; offset++){
            final int mId = offset;
            /** 异步线程执行*/
            PriorityExecutor.execute(() -> deepMVideo(mId), 1);
        }
    }

    @Override
    public void deepCVideo(Integer start, Integer end) {
        for(int offset = start; offset <= end; offset++){
            final int cId = offset;
            /** 异步线程执行*/
            PriorityExecutor.execute(() -> deepCVideo(cId), 1);
        }
    }

    /** B站视频信息落地*/
    private void deepBVideo(Integer avId){
        try{
            BiliSearch biliSearch = this.allRemoteApiService.getBliVideoByTitle(avId+"");
            if (biliSearch != null && biliSearch.getResult() != null && biliSearch.getResult().size() > 0) {
                SearchResult searchResult = biliSearch.getResult().get(0);
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
                    video.setVideo_url(Constant.Video.getBUrl(avId));
                    deepVideoDB(video, finalTags, searchResult.getAid(), Constant.Video.TYPE_BILIBILI);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /** A站视频信息落地*/
    private void deepAVideo(Integer acId){
        try{
            String html = this.allRemoteApiService.getAvideoByAcId(acId);
            if(!StringUtils.isEmpty(html)){
                Document document = Jsoup.parse(html);
                //先判断是否是音乐区
                Elements sp = document.select("a.sp3");
                if(sp.size() > 0){
                    String type = sp.get(0).text();
                    if("音乐".equals(type)){
                        Elements base = document.select("div#pageInfo");
                        if(base.size() > 0){
                            String title = base.get(0).attr("data-title");
                            String des = base.get(0).attr("data-desc");
                            String cover = base.get(0).attr("data-pic");
                            String vid = base.get(0).attr("data-vid");
                            Video video = new Video();
                            video.setTitle(title);
                            video.setCover(cover);
                            video.setMsg(des);
                            video.setType(Constant.Video.TYPE_ACFUN);
                            video.setEmbed_type(Constant.Video.EMBED_IFRAME);
                            video.setV_id(acId);
                            video.setPage(1);
                            video.setVideo_url(Constant.Video.getAUrl(acId, vid));

                            Elements tagE = document.select("span.tag-single");
                            List<String> tags = new ArrayList<>();
                            if(tagE.size() > 0){
                                for(Element element : tagE){
                                    String tag = element.child(0).text();
                                    if(!StringUtils.isEmpty(tag) && !"[name]".equals(tag)){
                                        tags.add(element.child(0).text());
                                    }
                                }
                            }
                            deepVideoDB(video, tags, acId, Constant.Video.TYPE_ACFUN);
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /** M站信息落地*/
    private void deepMVideo(Integer mId){
        try {
            MissEvanSound missEvanSound = this.allRemoteApiService.getMvideoByMId(mId);
            if (missEvanSound != null
                    && missEvanSound.getInfo() != null
                    && missEvanSound.getInfo().getSound() != null
                    && missEvanSound.getInfo().getTags() != null
                    && missEvanSound.getInfo().getTags().size() > 0) {

                List<String> finalTags = missEvanSound.getInfo().getTags().stream()
                        .map(Tag::getName).collect(Collectors.toList());


                /** 获取本次需要关联的标签id*/
                Video video = new Video();
                video.setV_id(mId);
                video.setCover(missEvanSound.getInfo().getSound().getFront_cover());
                video.setTitle(missEvanSound.getInfo().getSound().getSoundstr());
                video.setMsg(missEvanSound.getInfo().getSound().getIntro());
                video.setPage(1);
                video.setType(Constant.Video.TYPE_M);
                video.setVideo_url(Constant.Video.getMUrl(mId));
                video.setEmbed_type(Constant.Video.EMBED_IFRAME);

                deepVideoDB(video, finalTags, mId, Constant.Video.TYPE_M);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** C站信息落地*/
    private void deepCVideo(Integer cId){
        try{
            String html = this.allRemoteApiService.getCvideoByMId(cId);
            if(!StringUtils.isEmpty(html)) {
                Document document = Jsoup.parse(html);
                Elements showpos = document.select("div.showpos");
                if (showpos.size() > 0) {
                    Element aTag = showpos.get(0).child(0);
                    if (aTag != null && Constant.FILTER_VIDEO_TAG.equals(aTag.text())) {
                        Elements metas = document.select("meta");
                        String[] tags = {};
                        Video video = new Video();
                        Elements titleTag = document.select("title");
                        if (titleTag.size() > 0) {
                            video.setTitle(titleTag.get(0).text().replace("- 吐槽弹幕网 - tucao.tv", ""));
                        }
                        for (Element e : metas) {
                            if ("keywords".equals(e.attr("name"))) {
                                String tagStr = e.attr("content");
                                if (!StringUtils.isEmpty(tagStr)) {
                                    tags = tagStr.split(",");
                                }
                            }
                            if ("description".equals(e.attr("name"))) {
                                String msg = e.attr("content");
                                if (!StringUtils.isEmpty(msg)) {
                                    if (msg.length() > 1500) {
                                        video.setMsg(msg.substring(0, 1497) + "...");
                                    } else {
                                        video.setMsg(msg);
                                    }
                                }
                            }
                        }
                        if (tags.length > 0) {
                            List<String> finalTags = Arrays.asList(tags);
                            if (finalTags.size() > 0) {
                                /** 获取本次需要关联的标签id*/
                                video.setV_id(cId);
                                video.setPage(1);
                                video.setType(Constant.Video.TYPE_TUCAO);
                                video.setVideo_url(Constant.Video.getCUrl(cId));
                                video.setEmbed_type(Constant.Video.EMBED_FLASH);
                                deepVideoDB(video, finalTags, cId, Constant.Video.TYPE_TUCAO);
                            }
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public void deepVideoDB(Video video, List<String> tags, Integer vid, Integer type){
        List<Integer> tagIds = this.tagService.tagAdd(tags);
        Video exist = this.videoMapper.getOneByVIdAndType(vid, type);
        if(exist == null){
            try{
                video.setSource_url(sourceProxy.getVideoSource(type, vid, 1));
                List<User> robots = userMao.getRobots();
                if(robots != null && robots.size() > 0){
                    int num = new Random().nextInt(robots.size());//0~(size-1)之间的随机数
                    User robot = robots.get(num);
                    if(robot != null){
                       video.setR_id(robot.getId());
                       video.setR_name(robot.getName());
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            videoMapper.insert(video);
            // 下载封面
            downLoadPipLine.push(new DownLoadVo(video.getId(), video.getCover(), Constant.TagMedia.PV_TYPE));
        }else{
            video.setId(exist.getId());
            video = exist;
        }

        /** 开始关联对应的tag*/
        //落地Tag关联信息
        List<TagMedia> tagMedias = new ArrayList<>();
        for(Integer tagId : tagIds){
            TagMedia tagMedia = new TagMedia();
            tagMedia.setMedia_id(video.getId());
            tagMedia.setType(TagMedia.PV_TYPE);
            tagMedia.setTag_id(tagId);
            tagMedias.add(tagMedia);
        }

        if(tagMedias.size() > 0){
            //tag-media和media-tag各加一遍
            tagMedias.forEach(tagMedia -> {
                if(tagMedia.getMedia_id() != null && tagMedia.getTag_id() != null){
                    TagMedia origin = tagMediaMapper.getOneFromMediaTagByMediaIdAndTagId(
                            tagMedia.getMedia_id() % 10,
                            tagMedia.getMedia_id(), tagMedia.getTag_id(), TagMedia.PV_TYPE);
                    if(origin == null){
                        tagMediaMapper.insertMediaTag(tagMedia.getMedia_id() % 10, tagMedia);
                        tagMediaMapper.insertTagMedia(tagMedia.getTag_id() % 10, tagMedia);
                    }
                }
            });
        }
    }
}
