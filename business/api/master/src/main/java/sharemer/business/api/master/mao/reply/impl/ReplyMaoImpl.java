package sharemer.business.api.master.mao.reply.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import sharemer.business.api.master.dao.ReplyMapper;
import sharemer.business.api.master.mao.reply.ReplyMao;
import sharemer.business.api.master.utils.Constant;
import sharemer.business.api.master.utils.MemcachedKeys;
import sharemer.business.api.master.vo.ReplyVo;
import sharemer.component.memcache.client.ShareMerMemcacheClient;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 18073 on 2017/9/2.
 */
@Repository
public class ReplyMaoImpl implements ReplyMao {

    private Logger logger = LoggerFactory.getLogger(ReplyMaoImpl.class);

    @Resource
    private ReplyMapper replyMapper;

    @Resource
    private ShareMerMemcacheClient shareMerMemcacheClient;

    @Override
    public ReplyVo getBaseOneWithoutDb(Integer oid, Integer otype, Long replyId) {
        return shareMerMemcacheClient.get(MemcachedKeys.Reply.getBaseReply(replyId));
    }

    @Override
    public ReplyVo getBaseOne(Integer oid, Long replyId) {
        try{
            return shareMerMemcacheClient.get(MemcachedKeys.Reply.getBaseReply(replyId), ()->{
                List<Long> ids = new ArrayList<>();
                ids.add(replyId);
                return this.replyMapper.getBaseOne(
                       oid % Constant.Reply.TABLE_TOTAL,
                        replyId);
            });
        }catch (Exception e){
            logger.error("reply get_base_one error! oid={}, reply_id={}", oid, replyId);
        }
        return null;
    }

    @Override
    public void delBaseOne(Integer oid, Long replyId) {
        try{
            shareMerMemcacheClient.delete(MemcachedKeys.Reply.getBaseReply(replyId));
        }catch (Exception e){
            logger.error("reply del_base_one error! oid={}, reply_id={}", oid, replyId);
        }
    }

    @Override
    public List<ReplyVo> setBaseReplies(Integer oid, Integer otype, List<Long> ids) {
        try{
            if(ids != null && ids.size() > 0){
                List<ReplyVo> result = this.replyMapper.getBaseList(
                        oid % Constant.Reply.TABLE_TOTAL,
                        ids);
                if(result != null){
                    result.forEach(m->{
                        shareMerMemcacheClient.set(MemcachedKeys.Reply.getBaseReply(m.getReal_id()), m);
                    });
                }
                return result;
            }
        }catch (Exception e){
            logger.error("set base replies error ! oid={}, otype={}, ids={}", oid, otype, ids.toString());
        }
        return null;
    }

}
