package sharemer.business.api.master.mao.reply;

import sharemer.business.api.master.vo.ReplyVo;

import java.util.List;

/**
 * Created by 18073 on 2017/9/2.
 */
public interface ReplyMao {

    ReplyVo getBaseOneWithoutDb(Integer oid, Integer otype, Long replyId);

    ReplyVo getBaseOne(Integer oid, Long replyId);

    void delBaseOne(Integer oid, Long replyId);

    List<ReplyVo> setBaseReplies(Integer oid, Integer otype, List<Long> ids);

}
