package sharemer.business.api.master.rao.user.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import sharemer.business.api.master.rao.user.UserRao;
import sharemer.business.api.master.utils.Constant;
import sharemer.business.api.master.utils.RedisKeys;
import sharemer.component.redis.client.SharemerRedisClient;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * Created by 18073 on 2017/8/27.
 */
@Repository("userRao")
public class UserRaoImpl implements UserRao {

    private Logger logger = LoggerFactory.getLogger(UserRaoImpl.class);

    @Resource
    private SharemerRedisClient sharemerRedisClient;

    @Override
    public void putLoginToken(String token, Integer uid) {
        try{
            String key = RedisKeys.User.getLoginTokenKey(token);
            sharemerRedisClient.set(key, String.valueOf(uid));
            sharemerRedisClient.expire(key, Constant.LOGIN_TIME);//每个登录用户登录令牌暂存一个月
        }catch (Exception e){
            logger.error("put login token error ! token={}, uid={}", token, uid);
        }
    }

    @Override
    public Integer getLoginToken(String token) {
        try{
            String uid = sharemerRedisClient.get(RedisKeys.User.getLoginTokenKey(token));
            if(!StringUtils.isEmpty(uid)){
                return Integer.parseInt(uid);
            }
        }catch (Exception e){
            logger.error("get login token error ! token={}", token);
        }
        return null;
    }

    @Override
    public String getCsrfToken() {
        try{
            String key = RedisKeys.User.getCsrfTokenKey();
            if(sharemerRedisClient.exists(key)){
                return sharemerRedisClient.get(key);
            }else{
                String token = UUID.randomUUID().toString();
                sharemerRedisClient.set(key, token);
                sharemerRedisClient.expire(key, 60*60);
                return sharemerRedisClient.get(key);
            }
        }catch (Exception e){
            logger.error("get csrf token error !");
        }
        return null;
    }
}
