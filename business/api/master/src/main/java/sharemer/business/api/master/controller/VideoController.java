package sharemer.business.api.master.controller;

import sharemer.business.api.master.anno.NeedUser;
import sharemer.business.api.master.dto.VideoIndex;
import sharemer.business.api.master.po.User;
import sharemer.business.api.master.service.video.VideoService;
import sharemer.business.api.master.service.favlist.FavListService;
import sharemer.business.api.master.utils.Constant;
import sharemer.business.api.master.utils.Page;
import sharemer.business.api.master.vo.VideoVo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sharemer.component.global.resp.WrappedResult;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by 18073 on 2017/6/10.
 */
@RestController
@RequestMapping(value = "/pc_api")
public class VideoController {

    @Resource
    private VideoService videoService;

    @Resource
    private FavListService favListService;

    @RequestMapping(value = "get_video_by_tag", method = RequestMethod.GET)
    public WrappedResult list(@RequestParam(value = "tag_id",required = true) Integer tag_id,
                              @RequestParam(value = "pageNo", required = false, defaultValue = "1") int pageNo,
                              @RequestParam(value = "pageSize", required = false, defaultValue = "20") int pageSize) throws Exception{
        Page<VideoIndex> result = this.videoService.getVideosByTag(tag_id, pageNo, pageSize);
        return WrappedResult.success(result);
    }

    @RequestMapping(value = "get_video_by_uid", method = RequestMethod.GET)
    public WrappedResult getVideoByUid(@RequestParam(value = "uid") Integer uid,
                                       @RequestParam(value = "sort") Integer sort,
                                       @RequestParam(value = "c_p") Integer c_p){
        if(c_p <= 0){
            c_p = 1;
        }
        if(sort != 0 && sort != 1){
            sort = 1;
        }
        c_p = (c_p-1)*20;
        List<VideoVo> result = this.videoService.getVideosByUid(uid, sort, c_p);
        return WrappedResult.success(result);
    }

    @RequestMapping(value = "get_video_info", method = RequestMethod.GET)
    @NeedUser
    public WrappedResult info(@RequestParam(value = "video_id", required = true) Integer video_id, HttpServletRequest request){
        User user = (User) request.getAttribute(Constant.LOGIN_USER);
        VideoVo result = this.videoService.getVideoInfoById(video_id);
        if(result != null && user != null){
            result.setIs_faved(favListService.isFaved(Constant.TagMedia.PV_TYPE, video_id, user.getId()) ? 1 : 0);
        }
        return WrappedResult.success(result);
    }

}
