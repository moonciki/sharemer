package sharemer.business.api.master.service.music;

import sharemer.business.api.master.dto.MusicIndex;
import sharemer.business.api.master.po.User;
import sharemer.business.api.master.utils.Page;
import sharemer.business.api.master.vo.MusicVo;
import sharemer.business.api.master.vo.VideoVo;

import java.util.List;

/**
 * Created by 18073 on 2017/8/1.
 */
public interface MusicService {

    Page<MusicIndex> getMusicsByTag(Integer tagId, Integer page, Integer pageSize);

    MusicVo getMusicInfoById(Integer id, User user);

    List<VideoVo> getRelateVideos(Integer musicId);

    List<MusicVo> getMusicsByUid(Integer uid, Integer sort, Integer page);

    List<MusicVo> getMusicsByKey(String key, Integer sort, Integer page);

}
