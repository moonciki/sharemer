package sharemer.business.api.master.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sharemer.business.api.master.anno.NeedUser;
import sharemer.business.api.master.po.Danmaku;
import sharemer.business.api.master.service.archive.ArchiveService;
import sharemer.business.api.master.service.danmaku.DanmakuService;
import sharemer.component.global.resp.WrappedResult;

import javax.annotation.Resource;
import java.util.List;

/**
 * Create by 18073 on 2018/12/24.
 */
@RestController
@RequestMapping(value = "/player")
public class PlayerController {

    @Resource
    private ArchiveService archiveService;

    @Resource
    private DanmakuService danmakuService;

    @RequestMapping(value = "get_path", method = RequestMethod.GET)
    @NeedUser
    public WrappedResult getMusicByUid(@RequestParam(value = "archive_id") Integer archive_id) {
        return WrappedResult.success(this.archiveService.getArchivePath(archive_id));
    }

    @RequestMapping(value = "danmaku/gets")
    public WrappedResult getByAid(@RequestParam(value = "aid") Integer aid) {
        List<Danmaku> danmakus = danmakuService.getDanmakusByAid(aid);
        return WrappedResult.success(danmakus);
    }

}
