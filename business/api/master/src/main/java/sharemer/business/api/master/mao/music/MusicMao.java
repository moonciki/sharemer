package sharemer.business.api.master.mao.music;

import sharemer.business.api.master.vo.MusicVo;

import java.util.List;

/**
 * Created by 18073 on 2017/7/31.
 */
public interface MusicMao {

    MusicVo getBaseOneWithoutDb(Integer musicId);

    MusicVo getBaseOne(Integer musicId);

    List<MusicVo> setBaseMusics(List<Integer> ids);

    List<Integer> getRelateVideoIds(Integer musicId);

    List<Integer> getMusicIdsByUid(Integer uid, Integer sort, Integer p);

}
