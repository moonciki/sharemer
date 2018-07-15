package sharemer.business.manager.master.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sharemer.business.manager.master.anno.NeedLogin;
import sharemer.business.manager.master.dao.MusicVideoMapper;
import sharemer.business.manager.master.dao.Page;
import sharemer.business.manager.master.po.Video;
import sharemer.business.manager.master.service.VideoService;
import sharemer.business.manager.master.vo.VideoVo;
import sharemer.component.global.resp.WrappedResult;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 18073 on 2017/6/10.
 */
@RestController
@RequestMapping(value = "/video")
public class VideoController {

    @Resource
    private MusicVideoMapper musicVideoMapper;

    @Resource
    private VideoService videoService;

    @RequestMapping(value = "getVideoByMusicId", method = RequestMethod.GET)
    @NeedLogin
    public WrappedResult one(@RequestParam(value = "music_id", required = true)Integer music_id) throws Exception{
        List<Video> result = musicVideoMapper.getVideosByMusicId(music_id%10, music_id);
        return WrappedResult.success(result);
    }

    /** 落地B站资源*/
    @RequestMapping(value = "deepBVideo", method = RequestMethod.GET)
    @NeedLogin
    public WrappedResult deepBVideo(@RequestParam(value = "start", required = true)Integer start,
                                    @RequestParam(value = "end", required = true)Integer end) throws Exception{
        videoService.deepBVideo(start, end);
        return WrappedResult.success();
    }

    /** 落地A站资源*/
    @RequestMapping(value = "deepAVideo", method = RequestMethod.GET)
    @NeedLogin
    public WrappedResult deepAVideo(@RequestParam(value = "start", required = true)Integer start,
                                    @RequestParam(value = "end", required = true)Integer end) throws Exception{
        videoService.deepAVideo(start, end);
        return WrappedResult.success();
    }

    /** 落地M站资源*/
    @RequestMapping(value = "deepMVideo", method = RequestMethod.GET)
    @NeedLogin
    public WrappedResult deepMVideo(@RequestParam(value = "start", required = true)Integer start,
                                    @RequestParam(value = "end", required = true)Integer end) throws Exception{
        videoService.deepMVideo(start, end);
        return WrappedResult.success();
    }

    /** 落地C站资源*/
    @RequestMapping(value = "deepCVideo", method = RequestMethod.GET)
    @NeedLogin
    public WrappedResult deepCVideo(@RequestParam(value = "start", required = true)Integer start,
                                    @RequestParam(value = "end", required = true)Integer end) throws Exception{
        videoService.deepCVideo(start, end);
        return WrappedResult.success();
    }

    @RequestMapping(value = "page", method = RequestMethod.GET)
    @NeedLogin
    public WrappedResult list(@RequestParam(value = "key") String key,
                              @RequestParam(value = "type")Integer type,
                              @RequestParam(value = "pageNo", required = false, defaultValue = "1") int pageNo,
                              @RequestParam(value = "pageSize", required = false, defaultValue = "20") int pageSize) throws Exception{
        Page<VideoVo> page = new Page<>(pageNo, pageSize);
        videoService.getAllVideo(key, type, page);
        return WrappedResult.success(page);
    }

}
