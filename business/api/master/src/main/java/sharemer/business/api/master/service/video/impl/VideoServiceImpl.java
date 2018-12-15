package sharemer.business.api.master.service.video.impl;

import sharemer.business.api.master.dao.VideoMapper;
import sharemer.business.api.master.dto.VideoIndex;
import sharemer.business.api.master.mao.tag.TagMao;
import sharemer.business.api.master.mao.user.UserMao;
import sharemer.business.api.master.mao.video.VideoMao;
import sharemer.business.api.master.po.Tag;
import sharemer.business.api.master.po.User;
import sharemer.business.api.master.service.tag.TagService;
import sharemer.business.api.master.service.video.VideoService;
import sharemer.business.api.master.utils.Constant;
import sharemer.business.api.master.utils.Page;
import sharemer.business.api.master.vo.VideoVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Created by 18073 on 2017/6/15.
 */
@Service("videoService")
public class VideoServiceImpl implements VideoService {

    @Resource
    private TagMao tagMao;

    @Resource
    private VideoMao videoMao;

    @Resource
    private UserMao userMao;

    @Resource
    private VideoMapper videoMapper;

    @Resource
    private TagService tagService;

    @Override
    public Page<VideoIndex> getVideosByTag(Integer tagId, Integer page, Integer pageSize){
        Page<VideoIndex> result = new Page(page, pageSize);
        /** 首先获取该tag下所有的musicId*/
        List<Integer> videoIds = this.tagMao.getVideoIdsByTagId(tagId);
        if(videoIds != null && videoIds.size() > 0){
            result.setTotalCount(videoIds.size());

            int from = 0, to = 0;
            if (page > 0 && pageSize > 0) {
                from = (page - 1) * pageSize;
                to = page * pageSize;
            }

            if(to > 0 && from < videoIds.size()){
                /** 截取当前页的musicId*/
                List<Integer> ids = videoIds.subList(from, to > videoIds.size() ? videoIds.size() : to);
                /** 需要回源的musicIds*/
                List<Integer> needDb = new ArrayList<>();
                List<VideoVo> currentVideos = new ArrayList<>();
                ids.forEach(id->{
                    VideoVo videoVo = this.videoMao.getBaseOneWithoutDb(id);
                    if(videoVo != null){
                        currentVideos.add(videoVo);
                    }else{
                        needDb.add(id);
                    }
                });

                if(needDb.size() > 0){
                    List<VideoVo> fill = this.videoMao.setBaseVideos(needDb);
                    if(fill != null && fill.size() > 0){
                        /** 回源成功，将回源后的结果并入结果集*/
                        currentVideos.addAll(fill);
                    }
                }

                /** 排序、封装*/
                List<VideoIndex> finalResult = currentVideos.stream()
                        .map(VideoIndex::getVideoIndex).sorted((m1, m2)->{
                            if(m1 != null && m2 != null && m1.getMtime() != null && m2.getMtime() != null){
                                return m1.getMtime().isAfter(m2.getMtime()) ? -1 : 1;
                            }else{return 0;}
                        }).collect(Collectors.toList());

                result.setRecords(finalResult);
            }
        }

        return result;
    }

    @Override
    public VideoVo getVideoInfoById(Integer id) {
        /** 获取基本信息*/
        VideoVo videoVo = this.videoMao.getBaseOne(id);

        /** 获取tag信息*/
        if(videoVo != null){
            List<Tag> tags = this.tagService.getTagsByMediaId(
                    id, Constant.TagMedia.PV_TYPE);

            videoVo.setTags(tags);

            User user = this.userMao.getBaseOne(videoVo.getR_id());
            videoVo.setUser(user);
        }

        return videoVo;
    }

    @Override
    public List<VideoVo> getVideosByUid(Integer uid, Integer sort, Integer c_p) {
        List<Integer> ids = this.videoMao.getVideoIdsByUid(uid, sort, c_p);
        if(ids != null && ids.size() > 0){
            List<VideoVo> result = this.getVideosByCache(ids);
            result = this.sortList(sort, result);
            return result;
        }
        return null;
    }

    @Override
    public List<VideoVo> getVideosByKey(String key, Integer sort, Integer c_p) {
        List<Integer> ids = this.videoMapper.getVideosByKey(key, sort, c_p);
        if(ids != null && ids.size() > 0){
            List<VideoVo> result = this.getVideosByCache(ids);
            result = this.sortList(sort, result);
            return result;
        }
        return null;
    }

    private List<VideoVo> sortList(int sort, List<VideoVo> result){
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

    private List<VideoVo> getVideosByCache(List<Integer> ids){
        /** 需要回源的musicIds*/
        List<Integer> needDb = new ArrayList<>();
        List<VideoVo> currentVideos = new ArrayList<>();
        ids.forEach(id->{
            VideoVo videoVo = this.videoMao.getBaseOneWithoutDb(id);
            if(videoVo != null){
                currentVideos.add(videoVo);
            }else{
                needDb.add(id);
            }
        });

        if(needDb.size() > 0){
            List<VideoVo> fill = this.videoMao.setBaseVideos(needDb);
            if(fill != null && fill.size() > 0){
                /** 回源成功，将回源后的结果并入结果集*/
                currentVideos.addAll(fill);
            }
        }

        return currentVideos;
    }

}
