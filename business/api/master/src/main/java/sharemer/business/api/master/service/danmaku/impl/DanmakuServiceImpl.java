package sharemer.business.api.master.service.danmaku.impl;

import org.springframework.stereotype.Service;
import sharemer.business.api.master.dao.DanmakuMapper;
import sharemer.business.api.master.po.Danmaku;
import sharemer.business.api.master.service.danmaku.DanmakuService;
import sharemer.business.api.master.utils.Constant;

import javax.annotation.Resource;
import java.util.List;

/**
 * Create by 18073 on 2018/12/17.
 */
@Service
public class DanmakuServiceImpl implements DanmakuService {

    @Resource
    private DanmakuMapper danmakuMapper;

    @Override
    public void addDanmaku(Danmaku danmaku) {
        danmakuMapper.insert(danmaku.getArchive_id() % Constant.Danmaku.TABLE_TOTAL, danmaku);
    }

    @Override
    public List<Danmaku> getDanmakusByAid(Integer aid) {
        return danmakuMapper.getDanmakusByAid(aid % Constant.Danmaku.TABLE_TOTAL, aid);
    }
}
