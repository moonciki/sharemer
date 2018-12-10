package sharemer.business.manager.master.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import sharemer.business.manager.master.po.FavList;

/**
 * Created by 18073 on 2017/9/29.
 * 收藏单
 */
@Mapper
public interface FavListMapper {

    void insert(@Param("pojo") FavList favList);

    void update(@Param("pojo") FavList favList);

    FavList getBaseOne(@Param("id") Integer id);
}
