package sharemer.business.manager.master.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import sharemer.business.manager.master.po.User;

import java.util.List;

/**
 * Created by 18073 on 2017/5/6.
 */
@Mapper
public interface UserMapper {

    User one(@Param("id") Integer id);

    void insert(@Param("pojo") User user);

    void update(@Param("pojo") User user);

    Integer getAllUserCount();

    List<User> getAllUser(@Param("start") Integer start, @Param("size") Integer size);

    List<User> getRobots();

}
