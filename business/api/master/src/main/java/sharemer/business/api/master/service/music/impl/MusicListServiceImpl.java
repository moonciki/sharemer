package sharemer.business.api.master.service.music.impl;

import sharemer.business.api.master.dao.MusicListMapper;
import sharemer.business.api.master.po.MusicList;
import sharemer.business.api.master.service.music.MusicListService;
import org.springframework.stereotype.Service;
import sharemer.business.api.master.utils.Page;

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
        return musicListMapper.getAll(page);
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
