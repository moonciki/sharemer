package sharemer.business.api.master.service.tag;

import sharemer.business.api.master.po.Tag;

import java.util.List;

/**
 * Created by 18073 on 2017/6/16.
 */
public interface TagService {

    List<Integer> tagAdd(List<String> tags);

    List<Tag> getTagsByMediaId(Integer mediaId, Integer type);

    Tag getBaseOne(Integer tagId);

}
