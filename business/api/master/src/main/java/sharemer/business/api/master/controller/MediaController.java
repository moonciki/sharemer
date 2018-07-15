package sharemer.business.api.master.controller;

import sharemer.business.api.master.service.video.VideoService;
import sharemer.business.api.master.service.favlist.FavListService;
import sharemer.business.api.master.service.music.MusicService;
import sharemer.business.api.master.utils.Constant;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sharemer.component.global.resp.WrappedResult;

import javax.annotation.Resource;

/**
 * Created by 18073 on 2017/7/22.
 */
@RestController
@RequestMapping(value = "/media")
public class MediaController {

    @Resource
    private MusicService musicService;

    @Resource
    private VideoService videoService;

    @Resource
    private FavListService favListService;

    @RequestMapping(value = "query", method = RequestMethod.GET)
    public WrappedResult getMusicByUid(@RequestParam(value = "key") String key,
                                       @RequestParam(value = "sort") Integer sort,
                                       @RequestParam(value = "c_p") Integer c_p,
                                       @RequestParam(value = "type") Integer type){

        if(StringUtils.isEmpty(key) || !Constant.Media.rightType(type)){
            return WrappedResult.fail("检测到无意义字段");
        }

        if(c_p <= 0) { c_p = 1; }
        if(sort != 0 && sort != 1) { sort = 1; }
        c_p = (c_p-1)*10;

        switch (type){
            case Constant.Media.M_TYPE:
                return WrappedResult.success(this.musicService.getMusicsByKey(key, sort, c_p));
            case Constant.Media.V_TYPE:
                return WrappedResult.success(this.videoService.getVideosByKey(key, sort, c_p));
            case Constant.Media.G_TYPE:
                return null;
            case Constant.Media.F_TYPE:
                return WrappedResult.success(this.favListService.getFavsByKey(key, sort, c_p));

        }
        return WrappedResult.fail("不符合常态的type");
    }
}
