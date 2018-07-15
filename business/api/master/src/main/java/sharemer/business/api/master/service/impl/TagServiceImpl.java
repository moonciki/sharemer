package sharemer.business.api.master.service.impl;

import sharemer.business.api.master.dao.TagMapper;
import sharemer.business.api.master.mao.tag.TagMao;
import sharemer.business.api.master.po.Tag;
import sharemer.business.api.master.service.TagService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by 18073 on 2017/6/16.
 */
@Service("tagService")
public class TagServiceImpl implements TagService {

    private static final String DEL_TAG_1 = "弹幕";
    private static final String DEL_TAG_2 = "Bilibili";
    private static final String DEL_TAG_3 = "哔哩哔哩";
    private static final String DEL_TAG_4 = "B站";
    private static final String DEL_TAG_5 = "bilibili";

    @Resource
    private TagMapper tagMapper;

    @Resource
    private TagMao tagMao;

    @Override
    public List<Integer> tagAdd(List<String> tags) {
        List<Integer> result = new ArrayList<>();
        Tag newTag = new Tag();
        tags.forEach(tag ->{
            if(!StringUtils.isEmpty(tag)){
                String finalTagName = tag.trim();
                /** 把不需要的标签去掉*/
                if(!DEL_TAG_1.equals(finalTagName) &&
                        !DEL_TAG_2.equals(finalTagName) &&
                        !DEL_TAG_3.equals(finalTagName) &&
                        !DEL_TAG_4.equals(finalTagName) &&
                        !DEL_TAG_5.equals(finalTagName)){
                    Tag origin = tagMapper.getOneByTagName(finalTagName);
                    if(origin == null){
                        newTag.setTag_name(finalTagName);
                        newTag.setUser_id(0);
                        tagMapper.insert(newTag);
                        result.add(newTag.getId());
                        newTag.setId(null);
                    }else{
                        result.add(origin.getId());
                    }
                }
            }
        });
        return result;
    }

    @Override
    public List<Tag> getTagsByMediaId(Integer mediaId, Integer type) {
        /** 首先获取该media下所有的tagId*/
        List<Integer> tagIds = this.tagMao.getTagsByMediaId(mediaId, type);
        if(tagIds != null && tagIds.size() > 0){
            List<Tag> tags = new ArrayList<>();//最终返回结果
            List<Integer> needDb = new ArrayList<>();//需要回源的tag
            tagIds.forEach(id->{
                Tag tag = this.tagMao.getBaseOneWithoutDb(id);//直接获取缓存结果
                if(tag != null){
                    tags.add(tag);
                }else{
                    needDb.add(id);
                }
            });

            /** size大于0说明存在需要回源的元缓存数据*/
            if(needDb.size() > 0){
                List<Tag> dataWithDb = this.tagMao.setBaseTags(needDb);
                if(dataWithDb != null && dataWithDb.size() > 0){
                    tags.addAll(dataWithDb);
                }
            }

            /** 排序*/
            if(tags.size() > 0){
                tags.sort((t1, t2)->{return t2.getId()-t1.getId();});
            }
            return tags;
        }else{
            return Collections.EMPTY_LIST;
        }
    }

    @Override
    public Tag getBaseOne(Integer tagId) {
        return this.tagMao.getBaseOne(tagId);
    }
}
