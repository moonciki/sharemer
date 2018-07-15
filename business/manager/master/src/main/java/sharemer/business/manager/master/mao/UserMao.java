package sharemer.business.manager.master.mao;

import sharemer.business.manager.master.po.User;

import java.util.List;

/**
 * Created by 18073 on 2017/5/8.
 */
public interface UserMao {

    User getOne(Integer id);

    List<User> getRobots();

}
