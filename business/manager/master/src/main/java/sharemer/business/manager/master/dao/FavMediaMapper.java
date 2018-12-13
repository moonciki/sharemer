package sharemer.business.manager.master.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import sharemer.business.manager.master.po.FavMedia;

/**
 * Created by 18073 on 2017/10/16.
 */
@Mapper
public interface FavMediaMapper {

    void insertFavMedia(@Param("num") Integer num, @Param("favMedia") FavMedia favMedia);

    void insertMediaFav(@Param("num") Integer num, @Param("favMedia") FavMedia favMedia);
}
