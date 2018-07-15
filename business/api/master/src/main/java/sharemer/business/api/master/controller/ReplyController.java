package sharemer.business.api.master.controller;

import sharemer.business.api.master.anno.NeedUser;
import sharemer.business.api.master.anno.TriggerLimit;
import sharemer.business.api.master.mao.reply.ReplyMao;
import sharemer.business.api.master.mao.user.UserMao;
import sharemer.business.api.master.po.Reply;
import sharemer.business.api.master.po.User;
import sharemer.business.api.master.rao.user.UserRao;
import sharemer.business.api.master.ro.ReplyParam;
import sharemer.business.api.master.service.music.ReplyService;
import sharemer.business.api.master.utils.Constant;
import sharemer.business.api.master.utils.Page;
import sharemer.business.api.master.vo.ReplyVo;
import org.springframework.web.bind.annotation.*;
import sharemer.component.global.resp.WrappedResult;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by 18073 on 2017/9/2.
 */
@RestController
@RequestMapping(value = "/pc_api")
public class ReplyController {

    @Resource
    private ReplyService replyService;

    @Resource
    private UserRao userRao;

    @Resource
    private ReplyMao replyMao;

    @Resource
    private UserMao userMao;

    @RequestMapping(value = "get_replies", method = RequestMethod.GET)
    public WrappedResult list(@RequestParam(value = "oid") Integer oid,
                              @RequestParam(value = "otype") Integer otype,
                              @RequestParam(value = "sort") Integer sort,
                              @RequestParam(value = "c_id", required = false) Integer c_id,
                              @RequestParam(value = "pageNo", required = false, defaultValue = "1") int pageNo,
                              @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) throws Exception{
        Page<ReplyVo> result = this.replyService.getRepliesByOid(c_id, oid, otype, sort, pageNo, pageSize);
        return WrappedResult.success(result);
    }

    @RequestMapping(value = "get_child_replies", method = RequestMethod.GET)
    public WrappedResult childList(@RequestParam(value = "oid") Integer oid,
                              @RequestParam(value = "otype") Integer otype,
                              @RequestParam(value = "reply_id") Long reply_id,
                                   @RequestParam(value = "c_id") Integer c_id,
                              @RequestParam(value = "pageNo", required = false, defaultValue = "1") int pageNo,
                              @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) throws Exception{
        Page<ReplyVo> result = new Page<ReplyVo>(pageNo, pageSize);
        this.replyService.getChildReplies(c_id, reply_id, oid, otype, result);
        return WrappedResult.success(result);
    }

    /** 保存评论*/
    @RequestMapping(value = "w/reply/save", method = RequestMethod.POST)
    @NeedUser
    public WrappedResult save(@RequestBody ReplyParam replyParam,
                              HttpServletRequest request){
        User user = (User) request.getAttribute(Constant.LOGIN_USER);
        if(user != null){
            /** 基本验证*/
            if(replyParam.getUser_id() == null ||
                    !user.getId().equals(replyParam.getUser_id())){
                return WrappedResult.fail("登录用户不一致！");
            }
            if(replyParam.getCsrf_token() == null ||
                    !replyParam.getCsrf_token().equals(this.userRao.getCsrfToken())){
                return WrappedResult.fail("写入令牌失效！");
            }
            if(replyParam.getContent() == null || replyParam.getContent().length() > 1000){
                return WrappedResult.fail("评论内容过长！");
            }
            Reply reply = new Reply();
            reply.setContent(replyParam.getContent());
            reply.setOid(replyParam.getOid());
            reply.setOtype(replyParam.getOtype());
            reply.setUser_id(replyParam.getUser_id());
            reply.setReply_id(replyParam.getReply_id());
            Long replyId = this.replyService.saveReply(replyParam.getOid(),
                    replyParam.getUser_id(), replyParam.getOtype(), reply);

            ReplyVo replyVo = this.replyMao.getBaseOne(replyParam.getOid(), replyId);
            replyVo.setUser(replyVo.getClearUser(userMao.getBaseOne(replyVo.getUser_id())));
            return WrappedResult.success(replyVo);
        }else{
            return WrappedResult.fail("账号未登录！");
        }
    }

    /** 顶或踩*/
    @RequestMapping(value = "r/reply/action", method = RequestMethod.POST)
    @NeedUser
    @TriggerLimit(t_k = 1)
    public WrappedResult action(@RequestBody ReplyParam replyParam,
                              HttpServletRequest request){
        User user = (User) request.getAttribute(Constant.LOGIN_USER);
        if(user != null){
            if(replyParam.getCsrf_token() == null ||
                    !replyParam.getCsrf_token().equals(this.userRao.getCsrfToken())){
                return WrappedResult.fail("写入令牌失效！");
            }

            replyService.like(replyParam.getL_type(),
                    replyParam.getOid(), replyParam.getUser_id(), replyParam.getReply_id());

            return WrappedResult.success();
        }else{
            return WrappedResult.fail("账号未登录！");
        }
    }
}
