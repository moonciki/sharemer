package sharemer.business.api.master.controller;

import org.springframework.web.bind.annotation.*;
import sharemer.business.api.master.anno.NeedUser;
import sharemer.business.api.master.anno.TriggerLimit;
import sharemer.business.api.master.po.User;
import sharemer.business.api.master.rao.user.UserRao;
import sharemer.business.api.master.service.danmaku.DanmakuService;
import sharemer.business.api.master.utils.Constant;
import sharemer.business.api.master.vo.DanmakuVo;
import sharemer.component.global.resp.WrappedResult;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Create by 18073 on 2018/12/17.
 */
@RestController
@RequestMapping(value = "/pc_api")
public class DanmakuController {

    @Resource
    private UserRao userRao;

    @Resource
    private DanmakuService danmakuService;

    @RequestMapping(value = "r/danmaku/save", method = RequestMethod.POST)
    @NeedUser
    @TriggerLimit(t_k = 1)
    public WrappedResult save(DanmakuVo danmakuVo,
                              HttpServletRequest request) {
        User user = (User) request.getAttribute(Constant.LOGIN_USER);
        if (user != null) {
            /** 基本验证*/
            if (danmakuVo.getCsrf() == null ||
                    !danmakuVo.getCsrf().equals(this.userRao.getCsrfToken())) {
                return WrappedResult.fail("写入令牌失效！");
            }
            if (danmakuVo.getText() == null || danmakuVo.getText().length() > 80) {
                return WrappedResult.fail("弹幕内容过长！");
            }
            danmakuVo.setUser_id(user.getId());
            danmakuService.addDanmaku(danmakuVo);
            return WrappedResult.success();
        } else {
            return WrappedResult.fail("账号未登录！");
        }
    }

}
