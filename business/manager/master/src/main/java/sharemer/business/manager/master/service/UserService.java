package sharemer.business.manager.master.service;

import sharemer.business.manager.master.dao.Page;
import sharemer.business.manager.master.po.User;

import java.util.List;

/**
 * Created by 18073 on 2017/5/6.
 */
public interface UserService {

    User getOne(Integer id) throws Exception;

    User updAndAdd(Integer id);

    List<User> getAllUser(Page<User> page);

}
