package sharemer.business.manager.master.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import sharemer.business.manager.master.po.MusicList;

import java.util.List;

/**
 * Created by 18073 on 2017/5/28.
 */
@Mapper
public interface MusicListMapper {

    MusicList one(@Param("id") Integer id);

    MusicList getOneByWyId(@Param("wy_id") Integer wy_id);

    void insert(@Param("pojo") MusicList musicList);

    void update(@Param("pojo") MusicList musicList);

    void updateByWyId(@Param("pojo") MusicList musicList);

    Integer getAllCount();

    List<MusicList> getAll(@Param("start") Integer start, @Param("size") Integer size);

}
