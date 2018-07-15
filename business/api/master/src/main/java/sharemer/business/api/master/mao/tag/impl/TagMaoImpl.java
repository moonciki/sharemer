package sharemer.business.api.master.mao.tag.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import sharemer.business.api.master.dao.TagMapper;
import sharemer.business.api.master.dao.TagMediaMapper;
import sharemer.business.api.master.mao.tag.TagMao;
import sharemer.business.api.master.po.Tag;
import sharemer.business.api.master.utils.MemcachedKeys;
import sharemer.component.memcache.client.ShareMerMemcacheClient;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by 18073 on 2017/8/1.
 */
@Repository
public class TagMaoImpl implements TagMao {

    private Logger logger = LoggerFactory.getLogger(TagMaoImpl.class);

    @Resource
    private TagMediaMapper tagMediaMapper;

    @Resource
    private TagMapper tagMapper;

    @Resource
    private ShareMerMemcacheClient shareMerMemcacheClient;

    @Override
    public List<Integer> getMusicIdsByTagId(Integer tagId) {
        try{
            return shareMerMemcacheClient.get(MemcachedKeys.getMusicIdsByTagId(tagId), ()->{
                List<Integer> ids = this.tagMediaMapper.getMusicIdsByTagId(tagId%10, tagId);
                if(ids != null && ids.size() > 0){
                    return ids;
                }else{
                    return Collections.EMPTY_LIST;
                }
            });
        }catch (Exception e){
            logger.error("tag get_video_ids_by_tag_id error! tag_id={}", tagId);
        }
        return null;
    }

    @Override
    public List<Integer> getVideoIdsByTagId(Integer tagId){
        try{
            return shareMerMemcacheClient.get(MemcachedKeys.getVideoIdsByTagId(tagId), ()->{
                List<Integer> ids = this.tagMediaMapper.getVideoIdsByTagId(tagId%10, tagId);
                if(ids != null && ids.size() > 0){
                    return ids;
                }else{
                    return Collections.EMPTY_LIST;
                }
            });
        }catch (Exception e){
            logger.error("tag get_video_ids_by_tag_id error! tag_id={}", tagId);
        }
        return null;
    }

    @Override
    public List<Integer> getFavIdsByTagId(Integer tagId) {
        try{
            return shareMerMemcacheClient.get(MemcachedKeys.getFavIdsByTagId(tagId), ()->{
                List<Integer> ids = this.tagMediaMapper.getFavIdsByTagId(tagId%10, tagId);
                if(ids != null && ids.size() > 0){
                    return ids;
                }else{
                    return Collections.EMPTY_LIST;
                }
            });
        }catch (Exception e){
            logger.error("tag get_fav_ids_by_tag_id error! tag_id={}", tagId);
        }
        return null;
    }

    @Override
    public List<Integer> getTagsByMediaId(Integer mediaId, Integer type) {
        try{
            return shareMerMemcacheClient.get(MemcachedKeys.Tag.getTagIdsByMediaId(mediaId, type), ()->{
                List<Integer> tagIds = this.tagMapper.getTagIdsByMediaIdAndType(
                        mediaId%10, mediaId, type);
                if(tagIds == null || tagIds.size() == 0){
                    return Collections.EMPTY_LIST;
                }
                return  tagIds;
            });
        }catch (Exception e){
            logger.error("tag get_tags_by_media_id error! tag_id = {}, type = {}", mediaId, type);
        }
        return Collections.EMPTY_LIST;
    }

    /** 批量回源tag元数据*/
    @Override
    public List<Tag> setBaseTags(List<Integer> tagIds) {
        if(tagIds != null && tagIds.size() > 0){
            List<Tag> result = this.tagMapper.getBaseTagVo(tagIds);
            if(result != null){
                result.forEach(m->{
                    shareMerMemcacheClient.set(MemcachedKeys.Tag.getTagInfo(m.getId()), m);
                });
            }
            return result;
        }else{
            return null;
        }
    }

    /** 获取music元缓存数据*/
    @Override
    public Tag getBaseOne(Integer tagId) {
        try{
            return shareMerMemcacheClient.get(MemcachedKeys.Tag.getTagInfo(tagId), ()->{
                List<Integer> ids = new ArrayList<>();
                ids.add(tagId);
                List<Tag> result = this.tagMapper.getBaseTagVo(ids);
                if(result != null && result.size() > 0){
                    return result.get(0);
                }
                return null;
            });
        }catch (Exception e){
            logger.error("tag get_base_one error! tag_id={}", tagId);
        }
        return null;
    }


    /** 无回源调用*/
    @Override
    public Tag getBaseOneWithoutDb(Integer tagId) {
        return shareMerMemcacheClient.get(MemcachedKeys.Tag.getTagInfo(tagId));
    }


}
