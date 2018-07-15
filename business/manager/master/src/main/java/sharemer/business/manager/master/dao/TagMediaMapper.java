package sharemer.business.manager.master.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import sharemer.business.manager.master.po.TagMedia;

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

}
