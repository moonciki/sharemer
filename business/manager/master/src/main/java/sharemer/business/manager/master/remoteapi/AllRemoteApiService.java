package sharemer.business.manager.master.remoteapi;

import sharemer.business.manager.master.exception.BusinessException;
import sharemer.business.manager.master.vo.BiliSearch;
import sharemer.business.manager.master.vo.remote.m.MissEvanSound;
import sharemer.business.manager.master.vo.WyObj;
import sharemer.business.manager.master.vo.remote.mtv.MtvReqData;

/**
 * Create by 18073 on 2018/7/5.
 */
public interface AllRemoteApiService {

    WyObj getMusicsByListId(int id) throws BusinessException;

    String getMvUrlByMvId(int mv_id) throws BusinessException;

    String getPlayListByType(String type, Integer offset) throws BusinessException;

    BiliSearch getBliVideoByTitle(String title) throws BusinessException;

    String getBvideoByAvId(int av_id) throws BusinessException;

    String getAvideoByAcId(int ac_id) throws BusinessException;

    MissEvanSound getMvideoByMId(int m_id) throws BusinessException;

    MtvReqData getMtvVideoByTitle(String title) throws BusinessException;

    String getCvideoByMId(int c_id) throws BusinessException;

}
