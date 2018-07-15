package sharemer.business.api.master.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import sharemer.business.api.master.po.Music;
import sharemer.business.api.master.utils.Page;
import sharemer.business.api.master.vo.MusicVo;

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

    List<MusicVo> getAll(@Param("key") String key, @Param("type") Integer type,
                         Page<MusicVo> page);

    List<MusicVo> getBaseMusicVo(@Param("ids") List<Integer> ids);

    List<Integer> getMusicsByUid(@Param("uid") Integer uid, @Param("sort") Integer sort,
                                 @Param("page") Integer page);

    List<Integer> getMusicsByKey(@Param("key") String key, @Param("sort") Integer sort,
                                 @Param("page") Integer page);

}
