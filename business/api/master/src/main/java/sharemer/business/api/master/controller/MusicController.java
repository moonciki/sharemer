package sharemer.business.api.master.controller;

import sharemer.business.api.master.anno.NeedUser;
import sharemer.business.api.master.dto.MusicIndex;
import sharemer.business.api.master.po.Tag;
import sharemer.business.api.master.po.User;
import sharemer.business.api.master.rao.music.MusicRao;
import sharemer.business.api.master.service.tag.TagService;
import sharemer.business.api.master.service.music.MusicService;
import sharemer.business.api.master.utils.Constant;
import sharemer.business.api.master.utils.Page;
import sharemer.business.api.master.vo.MusicVo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sharemer.component.global.resp.WrappedResult;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by 18073 on 2017/5/30.
 */
@RestController
@RequestMapping(value = "/pc_api")
public class MusicController {

    @Resource
    private MusicService musicService;

    @Resource
    private TagService tagService;

    @Resource
    private MusicRao musicRao;

    @RequestMapping(value = "get_music_by_tag", method = RequestMethod.GET)
    public WrappedResult list(@RequestParam(value = "tag_id",required = true) Integer tag_id,
                              @RequestParam(value = "pageNo", required = false, defaultValue = "1") int pageNo,
                              @RequestParam(value = "pageSize", required = false, defaultValue = "20") int pageSize) throws Exception{
        Page<MusicIndex> result = this.musicService.getMusicsByTag(tag_id, pageNo, pageSize);
        return WrappedResult.success(result);
    }

    @RequestMapping(value = "get_music_info", method = RequestMethod.GET)
    @NeedUser
    public WrappedResult info(@RequestParam(value = "music_id", required = true) Integer music_id,
                              HttpServletRequest request){
        User user = (User) request.getAttribute(Constant.LOGIN_USER);
        return WrappedResult.success(this.musicService.getMusicInfoById(music_id, user));
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
        c_p = (c_p-1)*10;
        List<MusicVo> result = this.musicService.getMusicsByUid(uid, sort, c_p);
        return WrappedResult.success(result);
    }

    @RequestMapping(value = "get_current_tag", method = RequestMethod.GET)
    public WrappedResult getCurrentTag(@RequestParam(value = "tag_id", required = true) Integer tag_id){
        Tag tag = this.tagService.getBaseOne(tag_id);
        return WrappedResult.success(tag);
    }

    @RequestMapping(value = "get_relate_video", method = RequestMethod.GET)
    public WrappedResult getRelateVideo(@RequestParam(value = "music_id",  required=true) Integer music_id){
        return WrappedResult.success(this.musicService.getRelateVideos(music_id));
    }

    /** 测试redis以及redis持久化的可用性*/
    @RequestMapping(value = "set_str", method = RequestMethod.GET)
    public WrappedResult setStr(@RequestParam(value = "str") String str){
        musicRao.setStr(str);
        return WrappedResult.success();
    }

    @RequestMapping(value = "get_str", method = RequestMethod.GET)
    public WrappedResult getStr(@RequestParam(value = "str") String str){
        return WrappedResult.success(musicRao.getStr());
    }

}
