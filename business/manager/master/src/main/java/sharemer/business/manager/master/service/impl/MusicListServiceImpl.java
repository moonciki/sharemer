package sharemer.business.manager.master.service.impl;

import org.springframework.stereotype.Service;
import sharemer.business.manager.master.dao.MusicListMapper;
import sharemer.business.manager.master.dao.Page;
import sharemer.business.manager.master.po.MusicList;
import sharemer.business.manager.master.service.MusicListService;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 18073 on 2017/5/28.
 */
@Service("musicListService")
public class MusicListServiceImpl implements MusicListService {

    @Resource
    private MusicListMapper musicListMapper;

    @Override
    public List<MusicList> getAllMusicLists(Page<MusicList> page) {
        List<MusicList> result = musicListMapper.getAll(
                (page.getPageNo()-1) * page.getPageSize(),
                page.getPageSize());
        Integer total = musicListMapper.getAllCount();
        page.setRecords(result);
        page.setTotalCount(total);
        return result;
    }

    @Override
    public void update(MusicList musicList) {
        this.musicListMapper.update(musicList);
    }

    @Override
    public void add(MusicList musicList) {
        this.musicListMapper.insert(musicList);
    }

    @Override
    public MusicList one(Integer id) {
        return this.musicListMapper.one(id);
    }

    @Override
    public MusicList getOneByWyId(Integer wy_id) {
        return this.musicListMapper.getOneByWyId(wy_id);
    }

}
