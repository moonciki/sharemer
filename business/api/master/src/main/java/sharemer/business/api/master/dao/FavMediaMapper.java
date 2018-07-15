package sharemer.business.api.master.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import sharemer.business.api.master.po.FavMedia;

import java.util.List;

/**
 * Created by 18073 on 2017/10/16.
 */
@Mapper
public interface FavMediaMapper {

    void insertsFavMedia(@Param("num") Integer num, @Param("list") List<FavMedia> list);

    void insertFavMedia(@Param("num") Integer num, @Param("favMedia") FavMedia favMedia);

    void insertsMediaFav(@Param("num") Integer num, @Param("list") List<FavMedia> list);

    void insertMediaFav(@Param("num") Integer num, @Param("favMedia") FavMedia favMedia);
}
