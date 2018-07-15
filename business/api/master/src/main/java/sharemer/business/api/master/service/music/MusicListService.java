package sharemer.business.api.master.service.music;

import sharemer.business.api.master.po.MusicList;
import sharemer.business.api.master.utils.Page;

import java.util.List;

/**
 * Created by 18073 on 2017/5/28.
 */
public interface MusicListService {

    List<MusicList> getAllMusicLists(Page<MusicList> page);

    void update(MusicList musicList);

    void add(MusicList musicList);

    MusicList one(Integer id);

    MusicList getOneByWyId(Integer wy_id);

}
