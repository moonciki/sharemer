package sharemer.business.api.master.service.music.impl;

import org.springframework.stereotype.Service;
import sharemer.business.api.master.dao.MusicMapper;
import sharemer.business.api.master.dto.MusicIndex;
import sharemer.business.api.master.mao.music.MusicMao;
import sharemer.business.api.master.mao.tag.TagMao;
import sharemer.business.api.master.mao.video.VideoMao;
import sharemer.business.api.master.po.Tag;
import sharemer.business.api.master.po.User;
import sharemer.business.api.master.service.tag.TagService;
import sharemer.business.api.master.service.favlist.FavListService;
import sharemer.business.api.master.service.music.MusicService;
import sharemer.business.api.master.utils.Constant;
import sharemer.business.api.master.utils.Page;
import sharemer.business.api.master.vo.MusicVo;
import sharemer.business.api.master.vo.VideoVo;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by 18073 on 2017/8/1.
 */
@Service("musicService")
public class MusicServiceImpl implements MusicService {

    @Resource
    private TagMao tagMao;

    @Resource
    private MusicMao musicMao;

    @Resource
    private MusicMapper musicMapper;

    @Resource
    private VideoMao videoMao;

    @Resource
    private TagService tagService;

    @Resource
    private FavListService favListService;

    @Override
    public Page<MusicIndex> getMusicsByTag(Integer tagId, Integer page, Integer pageSize) {

        Page result = new Page(page, pageSize);
        /** 首先获取该tag下所有的musicId*/
        List<Integer> musicIds = this.tagMao.getMusicIdsByTagId(tagId);
        if(musicIds != null && musicIds.size() > 0){
            result.setTotalCount(musicIds.size());

            int from = 0, to = 0;
            if (page > 0 && pageSize > 0) {
                from = (page - 1) * pageSize;
                to = page * pageSize;
            }

            if(to > 0 && from < musicIds.size()){
                /** 截取当前页的musicId*/
                List<Integer> ids = musicIds.subList(from, to > musicIds.size() ? musicIds.size() : to);
                List<MusicVo> currentMusics = this.getMusicsByCache(ids);
                /** 排序、封装*/
                List<MusicIndex> finalResult = currentMusics.stream()
                        .map(MusicIndex::getMusicIndex).sorted((m1, m2)->{
                            if(m1 != null && m2 != null && m1.getPublish_time() != null && m2.getPublish_time() != null){
                                return m1.getPublish_time().isAfter(m2.getPublish_time()) ? -1 : 1;
                            }else{return 0;}
                        }).collect(Collectors.toList());

                result.setRecords(finalResult);
            }
        }

        return result;
    }

    @Override
    public MusicVo getMusicInfoById(Integer id, User user) {

        /** 获取基本信息*/
        MusicVo musicVo = this.musicMao.getBaseOne(id);

        /** 获取tag信息*/
        if(musicVo != null){
            List<Tag> tags = this.tagService.getTagsByMediaId(
                    id, Constant.TagMedia.MUSIC_TYPE);

            musicVo.setTags(tags);

            if(user != null){
                musicVo.setIs_faved(favListService.isFaved(Constant.TagMedia.MUSIC_TYPE, id, user.getId()) ? 1 : 0);
            }
        }

        return musicVo;
    }

    @Override
    public List<VideoVo> getRelateVideos(Integer musicId) {

        /** 获取相关的videoIds*/
        List<Integer> vids = this.musicMao.getRelateVideoIds(musicId);

        if(vids.size() > 0){
            List<VideoVo> videos = new ArrayList<>();//最终返回结果
            List<Integer> needDb = new ArrayList<>();//需要回源的id
            vids.forEach(vid->{
                VideoVo videoVo = this.videoMao.getBaseOneWithoutDb(vid);
                if(videoVo != null){
                    videos.add(videoVo);
                }else{
                    needDb.add(vid);
                }
            });

            /** size大于0说明存在需要回源的元缓存数据*/
            if(needDb.size() > 0){
                List<VideoVo> dataWithDb = this.videoMao.setBaseVideos(needDb);
                if(dataWithDb != null && dataWithDb.size() > 0){
                    videos.addAll(dataWithDb);
                }
            }

            return videos;
        }

        return null;
    }

    @Override
    public List<MusicVo> getMusicsByUid(Integer uid, Integer sort, Integer page) {
        List<Integer> ids = this.musicMao.getMusicIdsByUid(uid, sort, page);
        if(ids != null && ids.size() > 0){
            List<MusicVo> result = this.getMusicsByCache(ids);
            result = this.sortList(sort, result);
            return result;
        }
        return null;
    }

    @Override
    public List<MusicVo> getMusicsByKey(String key, Integer sort, Integer page) {
        List<Integer> ids = this.musicMapper.getMusicsByKey(key, sort, page);
        if(ids != null && ids.size() > 0){
            List<MusicVo> result = this.getMusicsByCache(ids);
            result = this.sortList(sort, result);
            return result;
        }
        return null;
    }

    private List<MusicVo> sortList(int sort, List<MusicVo> result){
        final int sorted = sort;
        return result.stream()
                .sorted((m1, m2)->{
                    if(m1 != null && m2 != null && m1.getCtime() != null && m2.getCtime() != null){
                        if(sorted == 1){
                            return m1.getCtime().isAfter(m2.getCtime()) ? -1 : 1;
                        }else{
                            return m1.getCtime().isAfter(m2.getCtime()) ? 1 : -1;
                        }
                    }else{return 0;}
                }).collect(Collectors.toList());
    }

    private List<MusicVo> getMusicsByCache(List<Integer> ids){
        /** 需要回源的musicIds*/
        List<Integer> needDb = new ArrayList<>();
        List<MusicVo> currentMusics = new ArrayList<>();
        ids.forEach(id->{
            MusicVo musicVo = this.musicMao.getBaseOneWithoutDb(id);
            if(musicVo != null){
                currentMusics.add(musicVo);
            }else{
                needDb.add(id);
            }
        });

        if(needDb.size() > 0){
            List<MusicVo> fill = this.musicMao.setBaseMusics(needDb);
            if(fill != null && fill.size() > 0){
                /** 回源成功，将回源后的结果并入结果集*/
                currentMusics.addAll(fill);
            }
        }

        return currentMusics;
    }
}
