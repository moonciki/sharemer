package sharemer.business.manager.master.service;

import sharemer.business.manager.master.dao.Page;
import sharemer.business.manager.master.exception.BusinessException;
import sharemer.business.manager.master.po.Music;
import sharemer.business.manager.master.vo.MusicVo;

import java.util.List;

/**
 * Created by 18073 on 2017/5/30.
 */
public interface MusicService {

    List<MusicVo> getAllMusic(String key, Integer type, Page<MusicVo> page);

    void update(Music music);

    void add(Music music);

    Music one(Integer id);

    Music getOneBySongId(Integer song_id);

    void videoAdd(Music music) throws BusinessException;

    void deepMusic(Integer list_id) throws BusinessException;

    String getMvUrl(Integer mv_id) throws BusinessException;

}
