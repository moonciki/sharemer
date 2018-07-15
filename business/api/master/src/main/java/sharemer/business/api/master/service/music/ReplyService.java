package sharemer.business.api.master.service.music;

import sharemer.business.api.master.po.Reply;
import sharemer.business.api.master.utils.Page;
import sharemer.business.api.master.vo.ReplyVo;

/**
 * Created by 18073 on 2017/9/2.
 */
public interface ReplyService {

    Page<ReplyVo> getRepliesByOid(Integer userId, Integer oid, Integer otype, Integer sort, Integer page, Integer pageSize);

    void getChildReplies(Integer userId, Long replyId, Integer oid, Integer otype, Page<ReplyVo> resultC);

    Long saveReply(Integer oid, Integer userId, Integer otype, Reply reply);

    void like(Integer ltype, Integer oid, Integer userId, Long replyId);
}
