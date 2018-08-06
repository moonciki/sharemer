package sharemer.business.api.master.controller;

import sharemer.business.api.master.anno.NeedUser;
import sharemer.business.api.master.po.FavList;
import sharemer.business.api.master.po.User;
import sharemer.business.api.master.rao.user.UserRao;
import sharemer.business.api.master.ro.FavListParam;
import sharemer.business.api.master.ro.FavMediaParam;
import sharemer.business.api.master.service.favlist.FavListService;
import sharemer.business.api.master.utils.Constant;
import sharemer.business.api.master.utils.Page;
import sharemer.business.api.master.vo.FavListVo;
import org.springframework.web.bind.annotation.*;
import sharemer.component.global.resp.WrappedResult;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by 18073 on 2017/9/30.
 */
@RestController
@RequestMapping(value = "/pc_api")
public class FavListController {

    @Resource
    private FavListService favListService;

    @Resource
    private UserRao userRao;

    @RequestMapping(value = "get_fav_by_tag", method = RequestMethod.GET)
    public WrappedResult list(@RequestParam(value = "tag_id") Integer tag_id,
                              @RequestParam(value = "pageNo", required = false, defaultValue = "1") int pageNo,
                              @RequestParam(value = "pageSize", required = false, defaultValue = "20") int pageSize) throws Exception{
        Page<FavList> result = this.favListService.getFavsByTag(tag_id, pageNo, pageSize);
        return WrappedResult.success(result);
    }

    /** 保存收藏夹*/
    @RequestMapping(value = "w/fav/save", method = RequestMethod.POST)
    @NeedUser
    public WrappedResult save(@RequestBody FavListParam favListParam,
                              HttpServletRequest request){
        User user = (User) request.getAttribute(Constant.LOGIN_USER);
        if(user != null){
            /** 基本验证*/
            String checkMsg = this.check(user.getId(), favListParam.getUser_id(), favListParam.getCsrf_token());
            if(checkMsg != null){
                return  WrappedResult.fail(checkMsg);
            }
            FavList favList = favListParam.getFavList();
            favList.setUser_id(user.getId());
            favListService.addFavList(favList);
            FavList result = favListService.getFavListById(favList.getId());
            return WrappedResult.success(result);
        }else{
            return WrappedResult.fail("账号未登录！");
        }
    }

    /** 获取当前用户的收藏夹*/
    @RequestMapping(value = "fav/list", method = RequestMethod.GET)
    @NeedUser
    public WrappedResult list(@RequestParam(value = "c_id") Integer c_id,
                              HttpServletRequest request){
        User user = (User) request.getAttribute(Constant.LOGIN_USER);
        if(user != null){
            /** 基本验证*/
            if(c_id == null || !user.getId().equals(c_id)){
                return WrappedResult.fail("登录用户不一致！");
            }
            List<FavList> favs = this.favListService.getFavsByUserId(c_id, true);
            return WrappedResult.success(favs);
        }else{
            return WrappedResult.fail("账号未登录！");
        }
    }

    /** 关联收藏夹和资源*/
    @RequestMapping(value = "w/fav/action", method = RequestMethod.POST)
    @NeedUser
    public WrappedResult action(@RequestBody FavMediaParam favMediaParam,
                              HttpServletRequest request){
        User user = (User) request.getAttribute(Constant.LOGIN_USER);
        if(user != null){
            /** 基本验证*/
            String checkMsg = this.check(user.getId(), favMediaParam.getUser_id(), favMediaParam.getCsrf_token());
            if(checkMsg != null){
                return  WrappedResult.fail(checkMsg);
            }
            if(favListService.isFaved(favMediaParam.getO_type(), favMediaParam.getO_id(), user.getId())){
                return  WrappedResult.fail("您已经收藏过了~");
            }
            favListService.addAllFavListToMedia(favMediaParam.getO_id(), favMediaParam.getO_type(), favMediaParam.getFav_ids());
            return WrappedResult.success();
        }else{
            return WrappedResult.fail("账号未登录！");
        }
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

    @RequestMapping(value = "get_fav_info", method = RequestMethod.GET)
    public WrappedResult info(@RequestParam(value = "fav_id") Integer fav_id){
        return WrappedResult.success(this.favListService.getFavListInfo(fav_id));
    }

    @RequestMapping(value = "get_fav_musics", method = RequestMethod.GET)
    public WrappedResult musics(@RequestParam(value = "fav_id") Integer fav_id,
                                @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
                                @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize){
        return WrappedResult.success(this.favListService.getMusicsByFavId(fav_id, pageNo, pageSize));
    }

    @RequestMapping(value = "get_fav_videos", method = RequestMethod.GET)
    public WrappedResult videos(@RequestParam(value = "fav_id") Integer fav_id,
                                @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
                                @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize){
        return WrappedResult.success(this.favListService.getVideosByFavId(fav_id, pageNo, pageSize));
    }


    private String check(Integer uid, Integer tid, String token){
        if(tid == null ||
                !uid.equals(tid)){
            return "登录用户不一致！";
        }
        if(token == null ||
                !token.equals(this.userRao.getCsrfToken())){
            return "写入令牌失效！";
        }
        return null;
    }

}
