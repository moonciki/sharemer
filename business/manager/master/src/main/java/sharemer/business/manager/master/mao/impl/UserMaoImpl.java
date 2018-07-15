package sharemer.business.manager.master.mao.impl;

import org.springframework.stereotype.Repository;
import sharemer.business.manager.master.dao.UserMapper;
import sharemer.business.manager.master.mao.UserMao;
import sharemer.business.manager.master.po.User;
import sharemer.business.manager.master.utils.MemcachedKeys;
import sharemer.component.memcache.client.ShareMerMemcacheClient;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 18073 on 2017/5/8.
 */
@Repository
public class UserMaoImpl implements UserMao {

    @Resource
    private UserMapper userMapper;

    @Resource
    private ShareMerMemcacheClient shareMerMemcacheClient;

    @Override
    public User getOne(Integer id) {
        return shareMerMemcacheClient.get(MemcachedKeys.getUserInfo(id), ()->{
            return this.userMapper.one(id);
        });
    }

    @Override
    public List<User> getRobots() {
        return shareMerMemcacheClient.get(MemcachedKeys.getRobots(), ()->{
            return this.userMapper.getRobots();
        });
    }
}
