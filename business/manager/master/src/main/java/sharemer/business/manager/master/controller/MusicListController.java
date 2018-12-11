package sharemer.business.manager.master.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sharemer.business.manager.master.anno.NeedLogin;
import sharemer.business.manager.master.dao.Page;
import sharemer.business.manager.master.po.MusicList;
import sharemer.business.manager.master.service.MusicListService;
import sharemer.component.global.resp.WrappedResult;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 18073 on 2017/5/28.
 */
@RestController
@RequestMapping(value = "/music_list")
public class MusicListController implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(100).setTimeOut(1000);

    @Resource
    private MusicListService musicListService;

    @RequestMapping(value = "page", method = RequestMethod.GET)
    @NeedLogin
    public WrappedResult list(@RequestParam(value = "pageNo", required = false, defaultValue = "1") int pageNo,
                              @RequestParam(value = "pageSize", required = false, defaultValue = "20") int pageSize) throws Exception{
        Page<MusicList> page = new Page(pageNo, pageSize);
        musicListService.getAllMusicLists(page);
        return WrappedResult.success(page);
    }

    @RequestMapping(value = "spider", method = RequestMethod.GET)
    @NeedLogin
    public WrappedResult list(@RequestParam(value = "type", required = true) String type,
                              @RequestParam(value = "offset", required = true) Integer offset) throws Exception{
        List<String> urls = new ArrayList<>();
        String url = "http://music.163.com/discover/playlist/?order=hot&cat="+type+"&limit=35&offset="+offset;
        urls.add(url);
        Spider.create(this).startUrls(urls).thread(1).runAsync();
        return WrappedResult.success();
    }

    @Override
    public void process(us.codecraft.webmagic.Page page) {
        List<String> list = page.getHtml()
                .xpath("p[@class='dec']/html()").all();

        // 获取当前页面的主标题
        List<String> tags = page.getHtml()
                .xpath("span[@class='f-ff2 d-flag']/text()").all();

        String wy_type = tags.size() > 0 ? tags.get(0) : "";
        for(String c : list){
            String title = c.substring(c.indexOf("title=")+7, c.indexOf("\" href="));
            Integer wy_id = Integer.parseInt(
                    c.substring(c.indexOf("id=")+3, c.indexOf("\" class")));

            MusicList musicList = this.musicListService.getOneByWyId(wy_id);
            if(musicList == null){
                // 说明之前没有添加过这个歌单
                musicList = new MusicList();
                musicList.setTitle(title);
                musicList.setWy_id(wy_id);
                musicList.setWy_type(wy_type);
                this.musicListService.add(musicList);
            }else{
                // 若之前添加过这个歌单，那么获取到tag后加上这次的tag
                String[] types = musicList.getWy_type().split(",");
                boolean isAddType = true;
                if(types.length > 0){
                    for(String type : types){
                        if(type.equals(wy_type)){
                            isAddType = false;
                            break;
                        }
                    }
                }
                if(isAddType){
                    musicList.setWy_type(musicList.getWy_type()+","+wy_type);
                    this.musicListService.update(musicList);
                }
            }

        }
    }

    @Override
    public Site getSite() {
        return site;
    }

}
