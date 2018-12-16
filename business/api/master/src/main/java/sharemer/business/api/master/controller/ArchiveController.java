package sharemer.business.api.master.controller;

import org.springframework.web.bind.annotation.*;
import sharemer.business.api.master.anno.NeedUser;
import sharemer.business.api.master.anno.TriggerLimit;
import sharemer.business.api.master.dto.VideoIndex;
import sharemer.business.api.master.po.User;
import sharemer.business.api.master.rao.user.UserRao;
import sharemer.business.api.master.ro.ArchiveParam;
import sharemer.business.api.master.service.archive.ArchiveService;
import sharemer.business.api.master.utils.Constant;
import sharemer.business.api.master.utils.Page;
import sharemer.business.api.master.vo.ArchiveVo;
import sharemer.component.global.resp.WrappedResult;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

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

    @RequestMapping(value = "get_archive_by_tag", method = RequestMethod.GET)
    public WrappedResult list(@RequestParam(value = "tag_id") Integer tag_id,
                              @RequestParam(value = "pageNo", required = false, defaultValue = "1") int pageNo,
                              @RequestParam(value = "pageSize", required = false, defaultValue = "20") int pageSize) throws Exception {
        Page<ArchiveVo> result = this.archiveService.getArchivesByTag(tag_id, pageNo, pageSize);
        return WrappedResult.success(result);
    }

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

    @RequestMapping(value = "get_archive_by_uid", method = RequestMethod.GET)
    public WrappedResult getMusicByUid(@RequestParam(value = "uid") Integer uid,
                                       @RequestParam(value = "sort") Integer sort,
                                       @RequestParam(value = "c_p") Integer c_p) {
        if (c_p <= 0) {
            c_p = 1;
        }
        if (sort != 0 && sort != 1) {
            sort = 1;
        }
        c_p = (c_p - 1) * 20;
        List<ArchiveVo> result = this.archiveService.getArchivesByUid(uid, sort, c_p);
        return WrappedResult.success(result);
    }

}
