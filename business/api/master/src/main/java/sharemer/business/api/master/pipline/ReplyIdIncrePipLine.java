/**
 * sharemer.com Inc.
 * Copyright (c) 2009-2018 All Rights Reserved.
 */
package sharemer.business.api.master.pipline;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import sharemer.business.api.master.dao.IncreMapper;
import sharemer.business.api.master.utils.RedisKeys;
import sharemer.component.redis.client.SharemerRedisClient;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author sunqinwen
 * @version \: PipLine.java,v 0.1 2018-12-06 14:26
 * reply id 自增修改管道
 */
@Component
public class ReplyIdIncrePipLine {

    private Logger logger = LoggerFactory.getLogger(ReplyIdIncrePipLine.class);

    @Resource
    private IncreMapper increMapper;

    @Resource
    private SharemerRedisClient sharemerRedisClient;

    private BlockingQueue<Long> queue;

    @PostConstruct
    public void init() {
        this.queue = new LinkedBlockingQueue<>();
        new Thread(this::pushTask).start();
        sharemerRedisClient.set(RedisKeys.Reply.getRealId(),
                String.valueOf(increMapper.getIncre()));
    }

    public void push(Long num) {
        queue.offer(num);
    }

    private void pushTask() {
        if (queue != null) {
            Long num;
            while (true) {
                try {
                    num = queue.take();
                    increMapper.update(num);
                } catch (Exception e) {
                    logger.error("reply id incre pipline push task error! ", e);
                }
            }
        }
    }

}
