package sharemer.business.api.master.rao.favlist.impl;

import org.springframework.stereotype.Repository;
import sharemer.business.api.master.dao.FavListMapper;
import sharemer.business.api.master.utils.Constant;
import sharemer.business.api.master.utils.ConstantProperties;
import sharemer.business.api.master.utils.RedisKeys;
import sharemer.component.redis.client.SharemerRedisClient;

import javax.annotation.Resource;
import java.util.List;

/**
 * Create by 18073 on 2018/8/6.
 */
@Repository
public class VideoSource {

    @Resource
    private FavListMapper favListMapper;

    @Resource
    private SharemerRedisClient sharemerRedisClient;

    @Resource
    private ConstantProperties constantProperties;

    public void sourceVideoIdsByFavId(Integer fid){
        List<String> videoIds = favListMapper.getVideoIdsByFavId(fid, fid % Constant.FavList.TABLE_TOTAL);
        String key = String.format(RedisKeys.FavList.getVideoListByFavId(), fid);
        sharemerRedisClient.set(RedisKeys.getSourceKey(key), constantProperties.getRedisSourceValue());//回源标记

        if(videoIds != null && videoIds.size() > 0){
            String[] s = videoIds.toArray(new String[0]);
            sharemerRedisClient.sadd(key, s);
        }
    }

}
