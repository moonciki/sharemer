package sharemer.business.api.master.service;

import sharemer.business.api.master.dto.VideoIndex;
import sharemer.business.api.master.utils.Page;
import sharemer.business.api.master.vo.VideoVo;

import java.util.List;

/**
 * Created by 18073 on 2017/6/15.
 */
public interface VideoService {

    Page<VideoIndex> getVideosByTag(Integer tagId, Integer page, Integer pageSize);

    VideoVo getVideoInfoById(Integer id);

    List<VideoVo> getVideosByUid(Integer uid, Integer sort, Integer c_p);

    List<VideoVo> getVideosByKey(String key, Integer sort, Integer c_p);

}
