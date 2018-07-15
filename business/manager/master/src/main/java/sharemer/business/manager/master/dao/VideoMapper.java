package sharemer.business.manager.master.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import sharemer.business.manager.master.po.Video;
import sharemer.business.manager.master.vo.VideoVo;

import java.util.List;

/**
 * Created by 18073 on 2017/6/8.
 */
@Mapper
public interface VideoMapper {

    void insert(@Param("pojo") Video video);

    void update(@Param("pojo") Video video);

    Video one(@Param("pojo") Integer id);

    Video getOneByVIdAndType(@Param("v_id") Integer v_id, @Param("type") Integer type);

    Integer getAllCount(@Param("key") String key, @Param("type") Integer type);

    List<VideoVo> getAll(@Param("key") String key, @Param("type") Integer type, @Param("start") Integer start, @Param("size") Integer size);

}
