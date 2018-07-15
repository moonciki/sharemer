package sharemer.business.api.master.mao.video.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import sharemer.business.api.master.dao.VideoMapper;
import sharemer.business.api.master.mao.video.VideoMao;
import sharemer.business.api.master.utils.Constant;
import sharemer.business.api.master.utils.MemcachedKeys;
import sharemer.business.api.master.vo.VideoVo;
import sharemer.component.memcache.client.ShareMerMemcacheClient;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 18073 on 2017/8/8.
 */
@Repository
public class VideoMaoImpl implements VideoMao {

    private Logger logger = LoggerFactory.getLogger(VideoMaoImpl.class);

    @Resource
    private VideoMapper videoMapper;

    @Resource
    private ShareMerMemcacheClient shareMerMemcacheClient;

    @Override
    public VideoVo getBaseOneWithoutDb(Integer videoId) {
        return shareMerMemcacheClient.get(MemcachedKeys.Video.getBaseVideo(videoId));
    }

    /** 获取video元缓存数据*/
    @Override
    public VideoVo getBaseOne(Integer videoId) {
        try{
            return shareMerMemcacheClient.get(MemcachedKeys.Video.getBaseVideo(videoId), ()->{
                List<Integer> ids = new ArrayList<>();
                ids.add(videoId);
                List<VideoVo> videos = this.videoMapper.getBaseVideoVo(ids);
                if(videos != null && videos.size() > 0){
                    VideoVo v = videos.get(0);
                    v.setLogo_url(Constant.videoLogos.get(v.getType()));
                    v.setNet_name(Constant.videoNetName.get(v.getType()));
                    return v;
                }
                return null;
            });
        }catch (Exception e){
            logger.error("video get_base_one video_id = {}", videoId);
        }
        return null;
    }

    @Override
    public List<VideoVo> setBaseVideos(List<Integer> ids) {
        if(ids != null && ids.size() > 0){
            List<VideoVo> result = this.videoMapper.getBaseVideoVo(ids);
            if(result != null){
                result.forEach(v->{
                    v.setLogo_url(Constant.videoLogos.get(v.getType()));
                    v.setNet_name(Constant.videoNetName.get(v.getType()));
                    shareMerMemcacheClient.set(MemcachedKeys.Video.getBaseVideo(v.getId()), v);
                });
            }
            return result;
        }
        return null;
    }

    @Override
    public List<Integer> getVideoIdsByUid(Integer uid, Integer sort, Integer p) {
        try{
            return shareMerMemcacheClient.get(MemcachedKeys.getVideoIdsByUid(uid, sort, p), ()->{
                List<Integer> ids = this.videoMapper.getVideosByUid(uid, sort, p);
                if(ids != null && ids.size() > 0){
                    return ids;
                }else{
                    return null;
                }
            });
        }catch (Exception e){
            logger.error("tag get_video_ids_by_user_id error! uid={}, sort={}, p={}", uid, sort, p);
        }
        return null;
    }
}
