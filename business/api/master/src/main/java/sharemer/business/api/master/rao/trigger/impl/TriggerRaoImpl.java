package sharemer.business.api.master.rao.trigger.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import sharemer.business.api.master.rao.trigger.TriggerRao;
import sharemer.component.redis.client.SharemerRedisClient;

import javax.annotation.Resource;

/**
 * Created by 18073 on 2017/9/13.
 */
@Repository("triggerRao")
public class TriggerRaoImpl  implements TriggerRao {

    @Resource
    private SharemerRedisClient sharemerRedisClient;

    private Logger logger = LoggerFactory.getLogger(TriggerRaoImpl.class);

    @Override
    public boolean isAllowed(String key) {
        if(sharemerRedisClient.exists(key)){
            try{
                Integer num = Integer.parseInt(sharemerRedisClient.get(key));
                if(num >= 10){//一分钟不能连续操作十次
                    return false;
                }else{
                    sharemerRedisClient.incr(key);
                    return true;
                }
            }catch (Exception ignored){
                logger.error("is allowed error ! key={}", key);
            }
        }
        sharemerRedisClient.set(key, "1");
        sharemerRedisClient.expire(key, 60);
        return true;
    }

}
