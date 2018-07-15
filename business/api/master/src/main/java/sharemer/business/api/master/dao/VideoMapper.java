package sharemer.business.api.master.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import sharemer.business.api.master.po.Video;
import sharemer.business.api.master.utils.Page;
import sharemer.business.api.master.vo.VideoVo;

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

    List<VideoVo> getAll(@Param("key") String key, @Param("type") Integer type,
                         Page<VideoVo> page);

    List<VideoVo> getBaseVideoVo(@Param("ids") List<Integer> ids);

    List<Integer> getVideosByUid(@Param("uid") Integer uid, @Param("sort") Integer sort,
                                 @Param("page") Integer page);

    List<Integer> getVideosByKey(@Param("key") String key, @Param("sort") Integer sort,
                                 @Param("page") Integer page);

}
