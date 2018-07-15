package sharemer.business.api.master.rao.reply.impl;

import com.google.common.util.concurrent.RateLimiter;
import org.springframework.stereotype.Repository;
import sharemer.business.api.master.dao.ReplyMapper;
import sharemer.business.api.master.utils.Constant;
import sharemer.business.api.master.utils.ConstantProperties;
import sharemer.business.api.master.utils.RedisKeys;
import sharemer.business.api.master.vo.ReplyVo;
import sharemer.component.redis.client.SharemerRedisClient;

import javax.annotation.Resource;
import java.time.ZoneOffset;
import java.util.List;

/**
 * Created by 18073 on 2018/2/21.
 */
@Repository("replySource")
public class ReplySource {

    @Resource
    private ReplyMapper replyMapper;
    
    @Resource
    private SharemerRedisClient sharemerRedisClient;

    @Resource
    private ConstantProperties constantProperties;

    @Resource
    private RateLimiter redisSourceRateLimiter;

    public void sourceReplyByOid(Integer oid, Integer otype){
        List<ReplyVo> replyRealIds = this.replyMapper.getReplysByOid(
                oid % Constant.Reply.TABLE_TOTAL, oid, otype);
        String key = RedisKeys.Reply.getReplyIdsByOId(oid, otype);
        sharemerRedisClient.set(RedisKeys.getSourceKey(key), constantProperties.getRedisSourceValue());//回源标记
        if(replyRealIds != null && replyRealIds.size() > 0){
            replyRealIds.forEach(replyVo -> {
                redisSourceRateLimiter.acquire();//削峰
                sharemerRedisClient.zadd(key,
                        replyVo.getCtime().toEpochSecond(ZoneOffset.of("+8")) * 1000,
                        replyVo.getReal_id()+"");
            });

        }
    }

    public void sourceChildReplyByOid(Long replyId, Integer oid, Integer otype){
        List<ReplyVo> replyRealIds = this.replyMapper.getChildReplysByOid(
                oid % Constant.Reply.TABLE_TOTAL, replyId.intValue(), otype);
        String key = RedisKeys.Reply.getChildRepliesByOId(oid, replyId, otype);
        sharemerRedisClient.set("source_"+key, "1");//回源标记
        if(replyRealIds != null && replyRealIds.size() > 0){
            replyRealIds.forEach(replyVo -> {
                redisSourceRateLimiter.acquire();//削峰
                sharemerRedisClient.zadd(key,
                        replyVo.getCtime().toEpochSecond(ZoneOffset.of("+8")) * 1000,
                        replyVo.getReal_id()+"");
            });

        }
    }

}
