package sharemer.business.manager.master.controller;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sharemer.business.manager.master.anno.NeedLogin;
import sharemer.business.manager.master.dao.Page;
import sharemer.business.manager.master.pipline.DownLoadPipLine;
import sharemer.business.manager.master.po.MusicList;
import sharemer.business.manager.master.remoteapi.AllRemoteApiService;
import sharemer.business.manager.master.service.MusicListService;
import sharemer.business.manager.master.utils.Constant;
import sharemer.business.manager.master.vo.DownLoadVo;
import sharemer.component.global.resp.WrappedResult;
import sun.plugin.dom.core.CoreConstants;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 18073 on 2017/5/28.
 */
@RestController
@RequestMapping(value = "/music_list")
public class MusicListController {

    @Resource
    private MusicListService musicListService;

    @Resource
    private AllRemoteApiService allRemoteApiService;

    @Resource
    private DownLoadPipLine downLoadPipLine;

    @RequestMapping(value = "page", method = RequestMethod.GET)
    @NeedLogin
    public WrappedResult list(@RequestParam(value = "pageNo", required = false, defaultValue = "1") int pageNo,
                              @RequestParam(value = "pageSize", required = false, defaultValue = "20") int pageSize) throws Exception {
        Page<MusicList> page = new Page(pageNo, pageSize);
        musicListService.getAllMusicLists(page);
        return WrappedResult.success(page);
    }

    @RequestMapping(value = "spider", method = RequestMethod.GET)
    @NeedLogin
    public WrappedResult list(@RequestParam(value = "type") String type,
                              @RequestParam(value = "offset") Integer offset) throws Exception {
        String html = allRemoteApiService.getPlayListByType(type, offset);
        Document doc = Jsoup.parse(html);
        Elements divs = doc.getElementsByClass("u-cover");
        for (Element div : divs) {
            String wyIdStr = div.getElementsByClass("icon-play").attr("data-res-id");
            Long wyId = Long.parseLong(wyIdStr);
            String title = div.getElementsByClass("msk").attr("title");
            String cover = div.getElementsByClass("j-flag").attr("src");
            cover = String.format("%s%s", cover.substring(0, cover.indexOf("?")), "?param=200y200");
            MusicList musicList = this.musicListService.getOneByWyId(wyId);
            if (musicList == null) {
                // 说明之前没有添加过这个歌单
                musicList = new MusicList();
                musicList.setTitle(title);
                musicList.setWy_id(wyId);
                musicList.setCover(cover);
                musicList.setWy_type(type);
                this.musicListService.add(musicList);
                // 下载封面
                downLoadPipLine.push(new DownLoadVo(musicList.getId(), cover, Constant.TagMedia.MUSIC_LIST_TYPE));
            } else {
                // 若之前添加过这个歌单，那么获取到tag后加上这次的tag
                String[] types = musicList.getWy_type().split(",");
                boolean isAddType = true;
                if (types.length > 0) {
                    for (String t : types) {
                        if (t.equals(type)) {
                            isAddType = false;
                            break;
                        }
                    }
                }
                if (isAddType) {
                    musicList.setWy_type(musicList.getWy_type() + "," + type);
                    this.musicListService.update(musicList);
                }
            }
        }
        return WrappedResult.success();
    }

}
