package sharemer.business.manager.master.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import sharemer.business.manager.master.po.Admin;

/**
 * Created by 18073 on 2017/5/21.
 */
@Mapper
public interface AdminMapper {

    Admin getOneByNameAndPassword(@Param("name") String name, @Param("password") String password);

}

