package sharemer.business.api.master.service.danmaku;

import sharemer.business.api.master.po.Danmaku;

import java.util.List;

/**
 * Create by 18073 on 2018/12/17.
 */
public interface DanmakuService {

    void addDanmaku(Danmaku danmaku);

    List<Danmaku> getDanmakusByAid(Integer aid);

}
