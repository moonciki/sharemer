package sharemer.business.api.master.rao.reply;

import sharemer.business.api.master.utils.Page;
import sharemer.business.api.master.vo.ReplyVo;

import java.util.List;

/**
 * Created by 18073 on 2017/9/2.
 */
public interface ReplyRao {

    Long getRealId();

    List<Long> getReplyIdsByOId(Integer oid, Integer otype, Integer sort, Page<ReplyVo> page);

    void addOReplyId(Integer oid, Integer otype, Long replyId);

    void addUReplyId(Integer uid, Integer otype, Long replyId);

    void addCReplyId(Integer oid, Integer otype, Long replyId, Long cReplyId);

    List<Long> getChildReplyIdsByOId(Long replyId, Integer oid, Integer otype, Page<ReplyVo> page);

    boolean isLike(Integer userId, Long replyId);

    boolean isDisLike(Integer userId, Long replyId);

    void like(Integer userId, Long replyId);

    void dislike(Integer userId, Long replyId);

    void cancel_like(Integer userId, Long replyId);

    void cancel_dislike(Integer userId, Long replyId);

}
