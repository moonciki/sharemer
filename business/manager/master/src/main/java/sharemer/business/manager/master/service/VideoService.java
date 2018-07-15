package sharemer.business.manager.master.service;

import sharemer.business.manager.master.dao.Page;
import sharemer.business.manager.master.po.Video;
import sharemer.business.manager.master.vo.VideoVo;

import java.util.List;

/**
 * Created by 18073 on 2017/6/15.
 */
public interface VideoService {

    List<VideoVo> getAllVideo(String key, Integer type, Page<VideoVo> page);

    void deepBVideo(Integer start, Integer end);

    void deepVideoDB(Video video, List<String> tags, Integer vid, Integer type);

    void deepAVideo(Integer start, Integer end);

    void deepMVideo(Integer start, Integer end);

    void deepCVideo(Integer start, Integer end);

}
