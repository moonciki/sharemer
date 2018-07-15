package sharemer.business.api.master.rao.music.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import sharemer.business.api.master.rao.music.MusicRao;
import sharemer.business.api.master.utils.RedisKeys;
import sharemer.component.redis.client.SharemerRedisClient;

import javax.annotation.Resource;

/**
 * Created by 18073 on 2017/8/20.
 */
@Repository("musicRao")
public class MusicRaoImpl implements MusicRao {

    @Resource
    private SharemerRedisClient sharemerRedisClient;
    
    private Logger logger = LoggerFactory.getLogger(MusicRaoImpl.class);

    @Override
    public void setStr(String str) {
        sharemerRedisClient.set(RedisKeys.Music.getStrKey(), str);
    }

    @Override
    public String getStr() {
        try{
            return sharemerRedisClient.get(RedisKeys.Music.getStrKey());
        }catch (Exception e){
           logger.error("get redis music str error!");
        }
        return null;
    }
}
