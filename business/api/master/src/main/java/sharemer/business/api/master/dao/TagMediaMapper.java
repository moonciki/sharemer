package sharemer.business.api.master.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import sharemer.business.api.master.po.TagMedia;

import java.util.List;

/**
 * Created by 18073 on 2017/5/31.
 */
@Mapper
public interface TagMediaMapper {

    void insertsMediaTag(@Param("num") Integer num, @Param("media_tags") List<TagMedia> media_tags);

    void insertsTagMedia(@Param("num") Integer num, @Param("media_tags") List<TagMedia> media_tags);

    void insertTagMedia(@Param("num") Integer num, @Param("media_tag") TagMedia media_tag);

    TagMedia getOneFromMediaTagByMediaIdAndTagId(@Param("num") Integer num,
                                                 @Param("media_id") Integer media_id,
                                                 @Param("tag_id") Integer tag_id,
                                                 @Param("type") Integer type);

    List<Integer> getMusicIdsByTagId(@Param("num") Integer num, @Param("tag_id") Integer tag_id);

    List<Integer> getVideoIdsByTagId(@Param("num") Integer num, @Param("tag_id") Integer tag_id);

    List<Integer> getArchiveIdsByTagId(@Param("num") Integer num, @Param("tag_id") Integer tag_id);

    List<Integer> getFavIdsByTagId(@Param("num") Integer num, @Param("tag_id") Integer tag_id);
}
