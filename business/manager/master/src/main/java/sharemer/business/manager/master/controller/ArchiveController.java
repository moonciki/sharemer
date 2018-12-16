package sharemer.business.manager.master.controller;

import org.springframework.web.bind.annotation.*;
import sharemer.business.manager.master.anno.NeedLogin;
import sharemer.business.manager.master.dao.Page;
import sharemer.business.manager.master.service.ArchiveService;
import sharemer.business.manager.master.vo.ArchiveVo;
import sharemer.component.global.resp.WrappedResult;

import javax.annotation.Resource;

/**
 * Create by 18073 on 2018/12/15.
 */
@RestController
@RequestMapping(value = "/archive")
public class ArchiveController {

    @Resource
    private ArchiveService archiveService;

    @RequestMapping(value = "page", method = RequestMethod.GET)
    @NeedLogin
    public WrappedResult list(@RequestParam(value = "key") String key,
                              @RequestParam(value = "type") Integer type,
                              @RequestParam(value = "pageNo", required = false, defaultValue = "1") int pageNo,
                              @RequestParam(value = "pageSize", required = false, defaultValue = "20") int pageSize) throws Exception {
        Page<ArchiveVo> page = new Page<>(pageNo, pageSize);
        archiveService.getAllArchive(key, type, page);
        return WrappedResult.success(page);
    }

    @RequestMapping(value = "pass", method = RequestMethod.GET)
    @NeedLogin
    public WrappedResult pass(@RequestParam(value = "archive_id") Integer archive_id,
                              @RequestParam(value = "status") Integer status) {
        archiveService.passArchive(archive_id, status);
        return WrappedResult.success();
    }

}
