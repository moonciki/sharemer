package sharemer.business.api.master.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import sharemer.business.api.master.po.User;

/**
 * Created by 18073 on 2017/5/6.
 */
@Mapper
public interface UserMapper {

    User one(@Param("id") Integer id);

    User getBaseOne(@Param("id") Integer id);

    void insert(@Param("pojo") User user);

    void update(@Param("pojo") User user);

    Integer getUserIdByNameAndPwd(@Param("email") String email, @Param("pwd") String pwd);

}
