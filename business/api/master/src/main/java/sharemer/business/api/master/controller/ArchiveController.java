package sharemer.business.api.master.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sharemer.business.api.master.anno.NeedUser;
import sharemer.business.api.master.anno.TriggerLimit;
import sharemer.business.api.master.po.User;
import sharemer.business.api.master.rao.user.UserRao;
import sharemer.business.api.master.ro.ArchiveParam;
import sharemer.business.api.master.service.archive.ArchiveService;
import sharemer.business.api.master.utils.Constant;
import sharemer.component.global.resp.WrappedResult;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Create by 18073 on 2018/12/15.
 */
@RestController
@RequestMapping(value = "/pc_api")
public class ArchiveController {

    @Resource
    private UserRao userRao;

    @Resource
    private ArchiveService archiveService;

    @RequestMapping(value = "/save/archive", method = RequestMethod.POST)
    @NeedUser
    @TriggerLimit(t_k = 1)
    public WrappedResult saveArchive(@RequestBody ArchiveParam archiveParam,
                                     HttpServletRequest request) {

        User user = (User) request.getAttribute(Constant.LOGIN_USER);
        if (user != null) {
            if (archiveParam.getCsrf() == null || !archiveParam.getCsrf().equals(this.userRao.getCsrfToken())) {
                return WrappedResult.fail("写入令牌失效！");
            }
            archiveParam.setUser_id(user.getId());
            archiveService.addArchive(archiveParam);
        } else {
            return WrappedResult.fail("账号未登录！");
        }

        return WrappedResult.success();
    }

}
