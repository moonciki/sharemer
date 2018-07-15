package sharemer.business.manager.master.service.impl;

import org.springframework.stereotype.Service;
import sharemer.business.manager.master.dao.Page;
import sharemer.business.manager.master.dao.UserMapper;
import sharemer.business.manager.master.mao.UserMao;
import sharemer.business.manager.master.po.User;
import sharemer.business.manager.master.service.UserService;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 18073 on 2017/5/6.
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserMao userMao;

    @Override
    public User getOne(Integer id) throws Exception{
        return this.userMao.getOne(id);
    }

    @Override
    public User updAndAdd(Integer id) {

        User user = new User();
        user.setId(1);
        user.setName("孙二苟233");
        user.setSex(233);
        this.userMapper.update(user);

        user.setId(3);
        user.setName("蛤蛤233");
        user.setSex(91);
        this.userMapper.insert(user);

        try{
            throw new RuntimeException();
        }catch (Exception e){
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public List<User> getAllUser(Page<User> page) {
        page.setRecords(this.userMapper.getAllUser((page.getPageNo() -1) * page.getPageSize(), page.getPageSize()));
        page.setTotalCount(this.userMapper.getAllUserCount());
        return page.getRecords();
    }
}
