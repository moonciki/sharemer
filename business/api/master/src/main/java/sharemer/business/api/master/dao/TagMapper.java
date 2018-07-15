package sharemer.business.api.master.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import sharemer.business.api.master.po.Tag;
import sharemer.business.api.master.utils.Page;

import java.util.List;

/**
 * Created by 18073 on 2017/5/31.
 */
@Mapper
public interface TagMapper {
    Tag one(@Param("id") Integer id);

    Tag getOneByTagName(@Param("tag_name") String tag_name);

    void insert(@Param("pojo") Tag tag);

    List<Tag> getAll(Page<Tag> page);

    List<Tag> getTagByMediaId(@Param("num") Integer num,
                              @Param("media_id") Integer media_id,
                              @Param("type") Integer type);

    List<Integer> getTagIdsByMediaIdAndType(@Param("num") Integer num,
                                            @Param("media_id") Integer media_id,
                                            @Param("type") Integer type);

    List<Tag> getBaseTagVo(@Param("ids") List<Integer> ids);
}
