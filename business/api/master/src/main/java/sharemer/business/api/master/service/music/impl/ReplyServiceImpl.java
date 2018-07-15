package sharemer.business.api.master.service.music.impl;

import sharemer.business.api.master.dao.ReplyMapper;
import sharemer.business.api.master.mao.reply.ReplyMao;
import sharemer.business.api.master.mao.user.UserMao;
import sharemer.business.api.master.po.Reply;
import sharemer.business.api.master.rao.reply.ReplyRao;
import sharemer.business.api.master.service.music.ReplyService;
import sharemer.business.api.master.utils.Constant;
import sharemer.business.api.master.utils.Page;
import sharemer.business.api.master.vo.ReplyVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by 18073 on 2017/9/2.
 */
@Service("replyService")
public class ReplyServiceImpl implements ReplyService {

    @Resource
    private ReplyRao replyRao;

    @Resource
    private ReplyMao replyMao;

    @Resource
    private UserMao userMao;

    @Resource
    private ReplyMapper replyMapper;

    @Override
    public Page<ReplyVo> getRepliesByOid(Integer userId, Integer oid, Integer otype, Integer sort, Integer page, Integer pageSize) {

        Page<ReplyVo> result = new Page<ReplyVo>(page, pageSize);
        /** 根据参数获取来源所对应的评论序列*/
        List<Long> replyIds = this.replyRao.getReplyIdsByOId(oid, otype, sort, result);

        List<ReplyVo> replyVos = this.getReplies(userId, oid, otype, sort, replyIds);
        if(replyVos != null && replyVos.size() > 0){
            /** 添加子评论*/
            replyVos.forEach(replyVo -> {
                Page<ReplyVo> resultC = new Page<ReplyVo>(1, pageSize);//子评论默认都是第一页开始取
                this.getChildReplies(userId, replyVo.getReal_id(), oid, otype, resultC);
                replyVo.setChild_replies(resultC);
            });
        }
        result.setRecords(replyVos);
        return result;
    }

    @Override
    public void getChildReplies(Integer userId, Long replyId, Integer oid, Integer otype, Page<ReplyVo> resultC) {
        List<Long> childIds = this.replyRao.getChildReplyIdsByOId(replyId, oid, otype, resultC);
        resultC.setRecords(this.getReplies(userId, oid, otype, Constant.Reply.Z_SORT, childIds));
    }

    @Override
    public Long saveReply(Integer oid, Integer userId, Integer otype, Reply reply) {
        reply.setReal_id(replyRao.getRealId());//自增id
        this.replyMapper.insert(oid % Constant.Reply.TABLE_TOTAL, reply);
        reply.setId(null);
        this.replyMapper.insertU(userId % Constant.Reply.TABLE_TOTAL, reply);

        Long rId = reply.getReal_id();
        /** redis存入*/
        if(reply.getReply_id() != null){
            replyRao.addCReplyId(oid, otype, reply.getReply_id(), rId);
        }else{
            replyRao.addOReplyId(oid, otype, rId);
        }
        replyRao.addUReplyId(userId, otype, reply.getReal_id());

        return rId;
    }

    @Override
    public void like(Integer ltype, Integer oid, Integer userId, Long replyId) {
        switch (ltype){
            case 1:
                if(!replyRao.isLike(userId, replyId)){
                    this.replyMapper.like(oid % Constant.Reply.TABLE_TOTAL, replyId);
                    replyRao.like(userId, replyId);
                }
                break;
            case 0:
                if(replyRao.isLike(userId, replyId)){
                    this.replyMapper.cancel_like(oid % Constant.Reply.TABLE_TOTAL, replyId);
                    replyRao.cancel_like(userId, replyId);
                }
                break;
            case -1:
                if(!replyRao.isDisLike(userId, replyId)){
                    this.replyMapper.dislike(oid % Constant.Reply.TABLE_TOTAL, replyId);
                    replyRao.dislike(userId, replyId);
                }
                break;
            case -2:
                if(replyRao.isDisLike(userId, replyId)){
                    this.replyMapper.cancel_dislike(oid % Constant.Reply.TABLE_TOTAL, replyId);
                    replyRao.cancel_dislike(userId, replyId);
                }
                break;
        }
        replyMao.delBaseOne(oid, replyId);
    }

    private List<ReplyVo> getReplies(Integer userId, Integer oid, Integer otype, Integer sort, List<Long> replyIds){
        if(replyIds != null && replyIds.size() > 0){
            /** 需要回源的replyIds*/
            List<Long> needDb = new ArrayList<>();
            List<ReplyVo> currentReplies = new ArrayList<>();
            replyIds.forEach(id->{
                ReplyVo replyVo = this.replyMao.getBaseOneWithoutDb(oid, otype, id);
                if(replyVo != null){
                    currentReplies.add(replyVo);
                }else{
                    needDb.add(id);
                }
            });

            if(needDb.size() > 0){
                List<ReplyVo> fill = this.replyMao.setBaseReplies(oid, otype, needDb);
                if(fill != null && fill.size() > 0){
                    /** 回源成功，将回源后的结果并入结果集*/
                    currentReplies.addAll(fill);
                }
            }

            /** 排序、封装*/
            return currentReplies.stream()
                    .peek(c->{
                        c.setIs_like(
                                replyRao.isLike(userId, c.getReal_id()) ? 1:0);
                        c.setIs_dislike(
                                replyRao.isDisLike(userId, c.getReal_id()) ? 1:0);
                        c.setUser(c.getClearUser(this.userMao.getBaseOne(c.getUser_id())));
                    }).sorted((m1, m2)->{
                        if(m1 != null && m2 != null && m1.getCtime() != null && m2.getCtime() != null){
                            return m1.getCtime().isAfter(m2.getCtime())
                                    && Objects.equals(sort, Constant.Reply.D_SORT)
                                    ? -1 : 1;
                        }else{return 0;}
                    }).collect(Collectors.toList());
        }
        return null;
    }
}
