package sharemer.business.api.master.rao.reply.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import sharemer.business.api.master.rao.reply.ReplyRao;
import sharemer.business.api.master.utils.*;
import sharemer.business.api.master.vo.ReplyVo;
import sharemer.component.redis.client.SharemerRedisClient;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by 18073 on 2017/9/2.
 */
@Repository("replyRao")
public class ReplyRaoImpl implements ReplyRao {

    private Logger logger = LoggerFactory.getLogger(ReplyRaoImpl.class);

    @Resource
    private ReplySource replySource;

    @Resource
    private SharemerRedisClient sharemerRedisClient;

    @Resource
    private ConstantProperties constantProperties;

    @Override
    public Long getRealId() {
        return sharemerRedisClient.incr(RedisKeys.Reply.getRealId());
    }

    @Override
    public List<Long> getReplyIdsByOId(Integer oid, Integer otype, Integer sort, Page<ReplyVo> page) {
        try {
            String key = RedisKeys.Reply.getReplyIdsByOId(oid, otype);
            Set<String> ids;

            if (!constantProperties.getRedisSourceValue().equals(sharemerRedisClient.get(RedisKeys.getSourceKey(key)))) {//如果回源key不存在，或者存在但不等于当前回源键，则触发redis异步回源
                PriorityExecutor.execute(() -> replySource.sourceReplyByOid(oid, otype), 1);
            }

            if (sort.equals(Constant.Reply.Z_SORT)) {
                ids = sharemerRedisClient.zrange(key, (page.getPageNo() - 1) * page.getPageSize(),
                        page.getPageNo() * page.getPageSize() - 1);
            } else {
                ids = sharemerRedisClient.zrevrange(key, (page.getPageNo() - 1) * page.getPageSize(),
                        page.getPageNo() * page.getPageSize() - 1);
            }

            /** 将数据总量放入page对象*/
            page.setTotalCount(sharemerRedisClient.zcard(key).intValue());
            if (ids != null && ids.size() > 0) {
                return ids.stream().map(Long::parseLong).collect(Collectors.toList());
            }
        } catch (Exception e) {
            logger.error("get redis reply ids error! oid={}, otype={}", oid, otype);
        }
        return null;
    }

    @Override
    public void addOReplyId(Integer oid, Integer otype, Long replyId) {
        try {
            String key = RedisKeys.Reply.getReplyIdsByOId(oid, otype);
            sharemerRedisClient.zadd(key, new Date().getTime(), replyId + "");
        } catch (Exception e) {
            logger.error("add redis o reply ids error! oid={}, otype={}, reply_id={}", oid, otype, replyId);
        }
    }

    @Override
    public void addUReplyId(Integer uid, Integer otype, Long replyId) {
        try {
            String key = RedisKeys.Reply.getReplyIdsByUid(uid, otype);
            sharemerRedisClient.zadd(key, new Date().getTime(), replyId + "");
        } catch (Exception e) {
            logger.error("add redis u reply ids error! uid={}, otype={}, reply_id={}", uid, otype, replyId);
        }
    }

    @Override
    public void addCReplyId(Integer oid, Integer otype, Long replyId, Long cReplyId) {
        try {
            String key = RedisKeys.Reply.getChildRepliesByOId(oid, replyId, otype);
            sharemerRedisClient.zadd(key, new Date().getTime(), cReplyId + "");
        } catch (Exception e) {
            logger.error("add redis c reply ids error! oid={}, otype={}, reply_id={}, c_reply_id={}", oid, otype, replyId, cReplyId);
        }
    }

    @Override
    public List<Long> getChildReplyIdsByOId(Long replyId, Integer oid, Integer otype, Page<ReplyVo> page) {
        try {
            String key = RedisKeys.Reply.getChildRepliesByOId(oid, replyId, otype);

            if (!sharemerRedisClient.exists(key)) {//如果key不存在，则触发redis异步回源
                PriorityExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        replySource.sourceChildReplyByOid(replyId, oid, otype);
                    }
                }, 1);
            }

            Set<String> ids = sharemerRedisClient.zrange(key, (page.getPageNo() - 1) * page.getPageSize(),
                    page.getPageNo() * page.getPageSize() - 1);
            /** 将数据总量放入page对象*/
            page.setTotalCount(sharemerRedisClient.zcard(key).intValue());
            if (ids != null && ids.size() > 0) {
                return ids.stream().map(Long::parseLong).collect(Collectors.toList());
            }
        } catch (Exception e) {
            logger.error("get redis child reply ids error! oid={}, reply_id={}, otype={}", oid, replyId, otype);
        }
        return null;
    }

    @Override
    public boolean isLike(Integer userId, Long replyId) {
        try {
            if (userId == null || replyId == null) {
                return false;
            }
            return sharemerRedisClient.sismember(RedisKeys.Reply.getLikeReplyIds(userId), replyId + "");
        } catch (Exception e) {
            logger.error("get redis is like error! user_id={}, reply_id={}", userId, replyId);
        }
        return false;
    }

    @Override
    public boolean isDisLike(Integer userId, Long replyId) {
        try {
            if (userId == null || replyId == null) {
                return false;
            }
            return sharemerRedisClient.sismember(RedisKeys.Reply.getDisLikeReplyIds(userId), replyId + "");
        } catch (Exception e) {
            logger.error("get redis is dislike error! user_id={}, reply_id={}", userId, replyId);
        }
        return false;
    }

    @Override
    public void like(Integer userId, Long replyId) {
        try {
            sharemerRedisClient.sadd(RedisKeys.Reply.getLikeReplyIds(userId), replyId + "");
        } catch (Exception e) {
            logger.error("redis like error! user_id={}, reply_id={}", userId, replyId);
        }
    }

    @Override
    public void dislike(Integer userId, Long replyId) {
        try {
            sharemerRedisClient.sadd(RedisKeys.Reply.getDisLikeReplyIds(userId), replyId + "");
        } catch (Exception e) {
            logger.error("redis dislike error! user_id={}, reply_id={}", userId, replyId);
        }
    }

    @Override
    public void cancel_like(Integer userId, Long replyId) {
        try {
            sharemerRedisClient.srem(RedisKeys.Reply.getLikeReplyIds(userId), replyId + "");
        } catch (Exception e) {
            logger.error("redis like error! user_id={}, reply_id={}", userId, replyId);
        }
    }

    @Override
    public void cancel_dislike(Integer userId, Long replyId) {
        try {
            sharemerRedisClient.srem(RedisKeys.Reply.getDisLikeReplyIds(userId), replyId + "");
        } catch (Exception e) {
            logger.error("redis dislike error! user_id={}, reply_id={}", userId, replyId);
        }
    }

}
