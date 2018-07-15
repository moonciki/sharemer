package sharemer.business.api.master.mao.video;

import sharemer.business.api.master.vo.VideoVo;

import java.util.List;

/**
 * Created by 18073 on 2017/8/8.
 */
public interface VideoMao {

    VideoVo getBaseOneWithoutDb(Integer videoId);

    VideoVo getBaseOne(Integer videoId);

    List<VideoVo> setBaseVideos(List<Integer> ids);

    List<Integer> getVideoIdsByUid(Integer uid, Integer sort, Integer p);

}
