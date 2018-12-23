package sharemer.business.api.master.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import sharemer.business.api.master.po.FavList;

import java.util.List;

/**
 * Created by 18073 on 2017/9/29.
 * 收藏单
 */
@Mapper
public interface FavListMapper {

    void insert(@Param("pojo") FavList favList);

    void update(@Param("pojo") FavList favList);

    FavList getBaseOne(@Param("id") Integer id);

    List<FavList> getBaseFavs(@Param("ids") List<Integer> ids);

    List<Integer> getFavsByKey(@Param("key") String key, @Param("sort") Integer sort,
                               @Param("page") Integer page);

    List<FavList> getFavsByUserId(@Param("userId") Integer userId);

    List<String> getMusicIdsByFavId(@Param("favId") Integer favId, @Param("num") Integer num);

    List<String> getVideoIdsByFavId(@Param("favId") Integer favId, @Param("num") Integer num);

    List<String> getArchiveIdsByFavId(@Param("favId") Integer favId, @Param("num") Integer num);
}
