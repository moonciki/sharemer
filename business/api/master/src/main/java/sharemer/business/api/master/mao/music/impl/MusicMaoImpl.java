package sharemer.business.api.master.mao.music.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import sharemer.business.api.master.dao.MusicMapper;
import sharemer.business.api.master.dao.MusicVideoMapper;
import sharemer.business.api.master.mao.music.MusicMao;
import sharemer.business.api.master.utils.MemcachedKeys;
import sharemer.business.api.master.vo.MusicVo;
import sharemer.component.memcache.client.ShareMerMemcacheClient;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by 18073 on 2017/7/31.
 */
@Repository
public class MusicMaoImpl implements MusicMao {

    private Logger logger = LoggerFactory.getLogger(MusicMaoImpl.class);

    @Resource
    private MusicMapper musicMapper;

    @Resource
    private MusicVideoMapper musicVideoMapper;

    @Resource
    private ShareMerMemcacheClient shareMerMemcacheClient;

    /** 无回源调用*/
    @Override
    public MusicVo getBaseOneWithoutDb(Integer musicId) {
        return shareMerMemcacheClient.get(MemcachedKeys.Music.getBaseMusic(musicId));
    }

    /** 获取music元缓存数据*/
    @Override
    public MusicVo getBaseOne(Integer musicId) {
        try{
            return shareMerMemcacheClient.get(MemcachedKeys.Music.getBaseMusic(musicId), ()->{
                List<Integer> ids = new ArrayList<>();
                ids.add(musicId);
                List<MusicVo> result = this.musicMapper.getBaseMusicVo(ids);
                if(result != null && result.size() > 0){
                    return result.get(0);
                }
                return null;
            });
        }catch (Exception e){
            logger.error("music get_base_one error! music_id={}", musicId);
        }
        return null;
    }

    /** 批量回源*/
    @Override
    public List<MusicVo> setBaseMusics(List<Integer> ids) {
        try{
            if(ids != null && ids.size() > 0){
                List<MusicVo> result = this.musicMapper.getBaseMusicVo(ids);
                if(result != null){
                    result.forEach(m->{
                        shareMerMemcacheClient.set(MemcachedKeys.Music.getBaseMusic(m.getId()), m);
                    });
                }
                return result;
            }
        }catch (Exception e){
            logger.error("set base musics error ! ids={}", ids.toString());
        }
        return null;
    }

    @Override
    public List<Integer> getRelateVideoIds(Integer musicId) {
        try{
            return shareMerMemcacheClient.get(MemcachedKeys.Music.getRelateVideoIds(musicId), ()->{
                List<Integer> ids = this.musicVideoMapper.getVideoIdsByMusicId(musicId%10, musicId);
                if (ids != null && ids.size() > 0){
                    return ids;
                }else{
                    return Collections.EMPTY_LIST;
                }
            });
        }catch (Exception e){
            logger.error("music get_relate_video_ids error! music_id = {}", musicId);
            return Collections.EMPTY_LIST;
        }
    }

    @Override
    public List<Integer> getMusicIdsByUid(Integer uid, Integer sort, Integer p) {
        try{
            return shareMerMemcacheClient.get(MemcachedKeys.getMusicIdsByUid(uid, sort, p), ()->{
                List<Integer> ids = this.musicMapper.getMusicsByUid(uid, sort, p);
                if(ids != null && ids.size() > 0){
                    return ids;
                }else{
                    return null;
                }
            });
        }catch (Exception e){
            logger.error("tag get_music_ids_by_user_id error! uid={}, sort={}, p={}", uid, sort, p);
        }
        return null;
    }
}
