package sharemer.business.manager.master.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import sharemer.business.manager.master.dao.TagMapper;
import sharemer.business.manager.master.po.Tag;
import sharemer.business.manager.master.service.TagService;

import javax.annotation.Resource;
import java.util.ArrayList;
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

}
