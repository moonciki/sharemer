package sharemer.business.api.master.mao.user.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import sharemer.business.api.master.dao.UserMapper;
import sharemer.business.api.master.mao.user.UserMao;
import sharemer.business.api.master.po.User;
import sharemer.business.api.master.utils.MemcachedKeys;
import sharemer.component.memcache.client.ShareMerMemcacheClient;

import javax.annotation.Resource;

/**
 * Created by 18073 on 2017/8/16.
 */
@Repository
public class UserMaoImpl implements UserMao {

    private Logger logger = LoggerFactory.getLogger(UserMaoImpl.class);

    @Resource
    private ShareMerMemcacheClient shareMerMemcacheClient;

    @Resource
    private UserMapper userMapper;

    @Override
    public User getBaseOne(Integer id) {
        try{
            return shareMerMemcacheClient.get(MemcachedKeys.User.getBaseUser(id), ()->{
                User user = this.userMapper.getBaseOne(id);
                if(user == null){
                    user = User.EMPTY;
                }
                return user;
            });
        }catch (Exception e){
            logger.error("user get_base_one user_id = {}", id);
        }
        return User.EMPTY;
    }

}
