package sharemer.business.manager.master.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import sharemer.business.manager.master.anno.NeedLogin;
import sharemer.business.manager.master.dao.Page;
import sharemer.business.manager.master.exception.BusinessException;
import sharemer.business.manager.master.po.Music;
import sharemer.business.manager.master.service.MusicService;
import sharemer.business.manager.master.vo.MusicVo;
import sharemer.component.global.resp.WrappedResult;

import javax.annotation.Resource;

/**
 * Created by 18073 on 2017/5/30.
 */
@RestController
@RequestMapping(value = "/music")
public class MusicController {

    @Resource
    private MusicService musicService;

    @RequestMapping(value = "spider", method = RequestMethod.GET)
    @NeedLogin
    public WrappedResult addAllMusic(@RequestParam(value = "list_id") Long list_id) throws Exception{
        try{
            long start = System.currentTimeMillis();
            musicService.deepMusic(list_id);
            long end = System.currentTimeMillis();
            System.out.println("操作用时："+(end - start));
            return WrappedResult.success();
        }catch (Exception e){
            e.printStackTrace();
            return WrappedResult.fail("500-内部错误");
        }

    }

    @RequestMapping(value = "getMv")
    @NeedLogin
    public ModelAndView getMv(@RequestParam(value = "mv_id", required = true) Integer mv_id) throws Exception{
        try{
            String mp4 = musicService.getMvUrl(mv_id);
            ModelAndView view = new ModelAndView("redirect:"+mp4);
            return view;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    @RequestMapping(value = "page", method = RequestMethod.GET)
    @NeedLogin
    public WrappedResult list(@RequestParam(value = "key") String key,
                              @RequestParam(value = "type")Integer type,
                              @RequestParam(value = "pageNo", required = false, defaultValue = "1") int pageNo,
                              @RequestParam(value = "pageSize", required = false, defaultValue = "20") int pageSize) throws Exception{
        Page<MusicVo> page = new Page<>(pageNo, pageSize);
        musicService.getAllMusic(key, type, page);
        return WrappedResult.success(page);
    }

    @RequestMapping(value = "relation", method = RequestMethod.GET)
    @NeedLogin
    public WrappedResult list(@RequestParam(value = "id") Integer id) throws BusinessException {
        Music music = musicService.one(id);
        musicService.videoAdd(music);
        return WrappedResult.success();
    }
}
