package sharemer.business.api.master.rao.favlist.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import sharemer.business.api.master.rao.favlist.FavListRao;
import sharemer.business.api.master.utils.ConstantProperties;
import sharemer.business.api.master.utils.PriorityExecutor;
import sharemer.business.api.master.utils.RedisKeys;
import sharemer.component.redis.client.SharemerRedisClient;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by 18073 on 2017/9/30.
 */
@Repository("favListRao")
public class FavListRaoImpl implements FavListRao {

    private Logger logger = LoggerFactory.getLogger(FavListRaoImpl.class);

    @Resource
    private SharemerRedisClient sharemerRedisClient;

    @Resource
    private ConstantProperties constantProperties;

    @Resource
    private FavListSource favListSource;

    @Resource
    private MusicSource musicSource;

    @Resource
    private VideoSource videoSource;

    @Resource
    private ArchiveSource archiveSource;
    
    @Override
    public void addFavsByUserId(Integer fid, Integer uid) {
        try{
            String key = RedisKeys.FavList.getFavListByUserId(uid);
            sharemerRedisClient.zadd(key, new Date().getTime(), fid+"");
        }catch (Exception e){
            logger.error("add redis fav by uid error! uid={}, fid={}", uid, fid);
        }
    }

    @Override
    public List<Integer> getFavsByUserId(Integer uid) {
        try{
            String key = RedisKeys.FavList.getFavListByUserId(uid);
            if (!constantProperties.getRedisSourceValue().equals(sharemerRedisClient.get(RedisKeys.getSourceKey(key)))) {//如果回源key不存在，或者存在但不等于当前回源键，则触发redis异步回源
                PriorityExecutor.execute(() -> favListSource.sourceFavsByUserId(uid), 1);
            }
            Set<String> ids = sharemerRedisClient.zrange(key,
                    0, -1);
            if(ids != null && ids.size() > 0){
                return ids.stream().map(Integer::parseInt).collect(Collectors.toList());
            }
        }catch (Exception e){
            logger.error("get redis favs by uid error! uid={}", uid);
        }
        return null;
    }

    @Override
    public void addMediasByFavId(String key, Integer mediaId) {
        try{
            sharemerRedisClient.sadd(key, mediaId+"");
        }catch (Exception e){
            logger.error("add redis media by fav id error! key={}, media_id={}", key, mediaId);
        }
    }

    @Override
    public boolean isMemeberByUserId(String key, Integer oid) {
        try{
            return sharemerRedisClient.sismember(key, oid+"");
        }catch (Exception e){
            logger.error("is memeber by userId redis error! key={}, oid={}", key, oid);
        }
        return false;
    }

    @Override
    public Integer getMusicCount(Integer fid) {
        try{
            return sharemerRedisClient.scard(String.format(RedisKeys.FavList.getMusicListByFavId(), fid)).intValue();
        }catch (Exception e){
            logger.error("get music count error ! fid = {}", fid);
        }
        return 0;
    }

    @Override
    public Integer getVideoCount(Integer fid) {
        try{
            return sharemerRedisClient.scard(String.format(RedisKeys.FavList.getVideoListByFavId(), fid)).intValue();
        }catch (Exception e){
            logger.error("get video count error ! fid = {}", fid);
        }
        return 0;
    }

    @Override
    public Integer getSubCount(Integer fid) {
        try{
            return sharemerRedisClient.scard(String.format(RedisKeys.FavList.getSubListByFavId(), fid)).intValue();
        }catch (Exception e){
            logger.error("get sub count error ! fid = {}", fid);
        }
        return 0;
    }

    @Override
    public Set<String> getMusicIds(Integer fid) {
        try{
            String key = String.format(RedisKeys.FavList.getMusicListByFavId(), fid);
            if (!constantProperties.getRedisSourceValue().equals(sharemerRedisClient.get(RedisKeys.getSourceKey(key)))) {//如果回源key不存在，或者存在但不等于当前回源键，则触发redis异步回源
                PriorityExecutor.execute(() -> musicSource.sourceMusicIdsByFavId(fid), 1);
            }
            return sharemerRedisClient.smembers(key);
        }catch (Exception e){
            logger.error("get music count error ! fid = {}", fid);
        }
        return null;
    }

    @Override
    public Set<String> getVideoIds(Integer fid) {
        try{
            String key = String.format(RedisKeys.FavList.getVideoListByFavId(), fid);
            if (!constantProperties.getRedisSourceValue().equals(sharemerRedisClient.get(RedisKeys.getSourceKey(key)))) {//如果回源key不存在，或者存在但不等于当前回源键，则触发redis异步回源
                PriorityExecutor.execute(() -> videoSource.sourceVideoIdsByFavId(fid), 1);
            }
            return sharemerRedisClient.smembers(key);
        }catch (Exception e){
            logger.error("get video count error ! fid = {}", fid);
        }
        return null;
    }

    @Override
    public Set<String> getArchiveIds(Integer fid) {
        try{
            String key = String.format(RedisKeys.FavList.getSubListByFavId(), fid);
            if (!constantProperties.getRedisSourceValue().equals(sharemerRedisClient.get(RedisKeys.getSourceKey(key)))) {//如果回源key不存在，或者存在但不等于当前回源键，则触发redis异步回源
                PriorityExecutor.execute(() -> archiveSource.sourceArchiveIdsByFavId(fid), 1);
            }
            return sharemerRedisClient.smembers(key);
        }catch (Exception e){
            logger.error("get archive count error ! fid = {}", fid);
        }
        return null;
    }

}
