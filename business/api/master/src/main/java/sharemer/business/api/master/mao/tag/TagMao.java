package sharemer.business.api.master.mao.tag;

import sharemer.business.api.master.po.Tag;

import java.util.List;

/**
 * Created by 18073 on 2017/8/1.
 */
public interface TagMao {

    List<Integer> getMusicIdsByTagId(Integer tagId);

    List<Integer> getVideoIdsByTagId(Integer tagId);

    List<Integer> getFavIdsByTagId(Integer tagId);

    List<Integer> getTagsByMediaId(Integer mediaId, Integer type);

    List<Tag> setBaseTags(List<Integer> tagIds);

    Tag getBaseOne(Integer tagId);

    Tag getBaseOneWithoutDb(Integer tagId);
}
