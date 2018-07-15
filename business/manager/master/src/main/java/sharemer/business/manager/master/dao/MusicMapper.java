package sharemer.business.manager.master.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import sharemer.business.manager.master.po.Music;
import sharemer.business.manager.master.vo.MusicVo;

import java.util.List;

/**
 * Created by 18073 on 2017/5/30.
 */
@Mapper
public interface MusicMapper {

    Music one(@Param("id") Integer id);

    Music getOneBySongId(@Param("song_id") Integer song_id);

    void insert(@Param("pojo") Music music);

    void update(@Param("pojo") Music music);

    Integer getAllCount(@Param("key") String key, @Param("type") Integer type);

    List<MusicVo> getAll(@Param("key") String key, @Param("type") Integer type,
                         @Param("start") Integer start, @Param("size") Integer size);

}
