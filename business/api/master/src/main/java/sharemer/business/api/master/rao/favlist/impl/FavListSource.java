package sharemer.business.api.master.rao.favlist.impl;

import com.google.common.util.concurrent.RateLimiter;
import org.springframework.stereotype.Repository;
import sharemer.business.api.master.dao.FavListMapper;
import sharemer.business.api.master.po.FavList;
import sharemer.business.api.master.utils.ConstantProperties;
import sharemer.business.api.master.utils.RedisKeys;
import sharemer.component.redis.client.SharemerRedisClient;

import javax.annotation.Resource;
import java.time.ZoneOffset;
import java.util.List;

/**
 * Create by 18073 on 2018/7/15.
 */
@Repository
public class FavListSource {

    @Resource
    private FavListMapper favListMapper;

    @Resource
    private SharemerRedisClient sharemerRedisClient;

    @Resource
    private ConstantProperties constantProperties;

    @Resource
    private RateLimiter redisSourceRateLimiter;

    public void sourceFavsByUserId(Integer uid){
        List<FavList> favs = favListMapper.getFavsByUserId(uid);
        String key = RedisKeys.FavList.getFavListByUserId(uid);
        sharemerRedisClient.set(RedisKeys.getSourceKey(key), constantProperties.getRedisSourceValue());//回源标记


        if(favs != null && favs.size() > 0){
            favs.forEach(fav -> {
                redisSourceRateLimiter.acquire();//削峰
                sharemerRedisClient.zadd(key,
                        fav.getCtime().toEpochSecond(ZoneOffset.of("+8")) * 1000,
                        String.valueOf(fav.getId()));
            });

        }
    }

}
