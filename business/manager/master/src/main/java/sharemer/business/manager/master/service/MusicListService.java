package sharemer.business.manager.master.service;

import sharemer.business.manager.master.dao.Page;
import sharemer.business.manager.master.po.MusicList;

import java.util.List;

/**
 * Created by 18073 on 2017/5/28.
 */
public interface MusicListService {

    List<MusicList> getAllMusicLists(Page<MusicList> page);

    void update(MusicList musicList);

    void add(MusicList musicList);

    MusicList one(Integer id);

    MusicList getOneByWyId(Long wy_id);

}
