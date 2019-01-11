package sharemer.business.api.master.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sharemer.business.api.master.anno.NeedUser;
import sharemer.business.api.master.po.FavList;
import sharemer.business.api.master.po.User;
import sharemer.business.api.master.service.favlist.FavListService;
import sharemer.business.api.master.service.music.MusicService;
import sharemer.business.api.master.service.user.UserSercice;
import sharemer.business.api.master.service.video.VideoService;
import sharemer.business.api.master.utils.Constant;
import sharemer.business.api.master.vo.FavListVo;
import sharemer.business.api.master.vo.MusicVo;
import sharemer.business.api.master.vo.UserVo;
import sharemer.business.api.master.vo.VideoVo;
import sharemer.component.global.resp.ConstantResults;
import sharemer.component.global.resp.WrappedResult;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Create by 18073 on 2018/12/30.
 */
@RestController
@RequestMapping(value = "/blog")
public class BlogController {

    @Resource
    private UserSercice userSercice;

    @Resource
    private MusicService musicService;

    @Resource
    private VideoService videoService;

    @Resource
    private FavListService favListService;

    @RequestMapping(value = "get_user_info", method = RequestMethod.GET)
    public WrappedResult list(@RequestParam(value = "user_id") Integer user_id) {
        UserVo userVo = this.userSercice.getUserInfo(user_id);
        if(userVo == null){
            return WrappedResult.fail(ConstantResults.NODATA);
        }
        return WrappedResult.success(userVo);
    }

    @RequestMapping(value = "get_music_by_uid", method = RequestMethod.GET)
    public WrappedResult getMusicByUid(@RequestParam(value = "uid") Integer uid,
                                       @RequestParam(value = "sort") Integer sort,
                                       @RequestParam(value = "c_p") Integer c_p){
        if(c_p <= 0){
            c_p = 1;
        }
        if(sort != 0 && sort != 1){
            sort = 1;
        }
        c_p = (c_p-1)*20;
        List<MusicVo> result = this.musicService.getMusicsByUid(uid, sort, c_p);
        return WrappedResult.success(result);
    }

    /** 获取用户收藏夹*/
    @RequestMapping(value = "fav/fav_list", method = RequestMethod.GET)
    @NeedUser
    public WrappedResult favList(@RequestParam(value = "c_id") Integer c_id,
                                 HttpServletRequest request){
        User user = (User) request.getAttribute(Constant.LOGIN_USER);

        FavListVo favListVo = new FavListVo();
        /** 基本验证*/
        if (user != null && user.getId().equals(c_id)) {
            favListVo.setIs_self(Constant.User.SELF);
        }
        List<FavList> favs = this.favListService.getFavsByUserId(c_id,
                Constant.User.SELF.equals(favListVo.getIs_self()));
        favListVo.setCount(favs.size());
        favListVo.setFavs(favs);
        return WrappedResult.success(favListVo);

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

}
