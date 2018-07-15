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

    void insertMediaTag(@Param("num") Integer num, @Param("pojo") TagMedia tagMedia);

    void insertTagMedia(@Param("num") Integer num, @Param("pojo") TagMedia tagMedia);

    TagMedia getOneFromMediaTagByMediaIdAndTagId(@Param("num") Integer num,
                                                 @Param("media_id") Integer media_id,
                                                 @Param("tag_id") Integer tag_id,
                                                 @Param("type") Integer type);

    List<Integer> getMusicIdsByTagId(@Param("num") Integer num, @Param("tag_id") Integer tag_id);

    List<Integer> getVideoIdsByTagId(@Param("num") Integer num, @Param("tag_id") Integer tag_id);

    List<Integer> getFavIdsByTagId(@Param("num") Integer num, @Param("tag_id") Integer tag_id);
}
