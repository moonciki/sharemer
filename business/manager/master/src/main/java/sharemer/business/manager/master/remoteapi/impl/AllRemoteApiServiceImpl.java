package sharemer.business.manager.master.remoteapi.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import retrofit2.Response;
import sharemer.business.manager.master.exception.BusinessException;
import sharemer.business.manager.master.remoteapi.*;
import sharemer.business.manager.master.vo.BiliSearch;
import sharemer.business.manager.master.vo.remote.m.MissEvanSound;
import sharemer.business.manager.master.vo.WyObj;
import sharemer.business.manager.master.vo.remote.mtv.MtvReqData;
import sharemer.component.http.client.HttpClient;
import sharemer.component.http.client.ReqCall;
import sharemer.component.http.response.BiliResponse;

/**
 * Create by 18073 on 2018/7/5.
 */
@Service("allRemoteApiService")
public class AllRemoteApiServiceImpl implements AllRemoteApiService {

    private Logger logger = LoggerFactory.getLogger(AllRemoteApiServiceImpl.class);

    private AcFunRemoteApi acFunRemoteApi;

    private BilibiliRemoteApi bilibiliRemoteApi;

    private SearchBilibiliRemoteApi searchBilibiliRemoteApi;

    private TuCaoRemoteApi tuCaoRemoteApi;

    private MissEvanRemoteApi missEvanRemoteApi;

    private NetEaseRemoteApi netEaseRemoteApi;

    private SearchMtvRemoteApi searchMtvRemoteApi;

    public AllRemoteApiServiceImpl(){

        acFunRemoteApi = HttpClient.builder()
                .connectionPool(new HttpClient.ConnectionPool(10))
                .timeout(new HttpClient.TimeOut(500, 500))
                .target(AcFunRemoteApi.class, "http://www.acfun.cn");

        bilibiliRemoteApi = HttpClient.builder()
                .connectionPool(new HttpClient.ConnectionPool(10))
                .timeout(new HttpClient.TimeOut(500, 500))
                .target(BilibiliRemoteApi.class, "http://www.bilibili.com");

        searchBilibiliRemoteApi = HttpClient.builder()
                .connectionPool(new HttpClient.ConnectionPool(10))
                .timeout(new HttpClient.TimeOut(500, 500))
                .target(SearchBilibiliRemoteApi.class, "http://api.bilibili.com");

        tuCaoRemoteApi = HttpClient.builder()
                .connectionPool(new HttpClient.ConnectionPool(10))
                .timeout(new HttpClient.TimeOut(500, 500))
                .target(TuCaoRemoteApi.class, "http://www.tucao.tv");

        missEvanRemoteApi = HttpClient.builder()
                .connectionPool(new HttpClient.ConnectionPool(10))
                .timeout(new HttpClient.TimeOut(500, 500))
                .target(MissEvanRemoteApi.class, "http://www.missevan.com");

        netEaseRemoteApi = HttpClient.builder()
                .connectionPool(new HttpClient.ConnectionPool(10))
                .timeout(new HttpClient.TimeOut(500, 500))
                .target(NetEaseRemoteApi.class, "http://music.163.com");

        searchMtvRemoteApi = HttpClient.builder()
                .connectionPool(new HttpClient.ConnectionPool(10))
                .timeout(new HttpClient.TimeOut(500, 500))
                .target(SearchMtvRemoteApi.class, "http://soapi.yinyuetai.com");
    }


    @Override
    public WyObj getMusicsByListId(int id) {
        ReqCall<WyObj> call = netEaseRemoteApi.getMusicsByListId(id);
        try {
            Response<WyObj> response = call.execute();
            if (response.isSuccessful() && response.body() != null && response.body().getCode() == 200) {
                return response.body();
            }
        } catch (Exception e) {
            logger.error("remote-163-error----->getMusicsByListId", e);
        }
        return null;
    }

    @Override
    public String getMvUrlByMvId(int mv_id) {
        ReqCall<String> call = netEaseRemoteApi.getMvUrlByMvId(mv_id);
        try {
            Response<String> response = call.execute();
            if (response.isSuccessful() && response.body() != null) {
                return response.body();
            }
        } catch (Exception e) {
            logger.error("remote-163-error----->getMvUrlByMvId", e);
        }
        return null;
    }

    @Override
    public String getPlayListByType(String type, Integer offset) {
        ReqCall<String> call = netEaseRemoteApi.getPlayListById("hot", type, 35, offset);
        try {
            Response<String> response = call.execute();
            if (response.isSuccessful() && response.body() != null) {
                return response.body();
            }
        } catch (Exception e) {
            logger.error("remote-163-error----->getPlayListById", e);
        }
        return null;
    }

    @Override
    public BiliSearch getBliVideoByTitle(String title) throws BusinessException {
        ReqCall<BiliResponse<BiliSearch>> call = searchBilibiliRemoteApi.getBliVideoByTitle("video", title,
                "totalrank", 3, 1);//tids代表分区，3代表音乐区
        try {
            Response<BiliResponse<BiliSearch>> response = call.execute();
            if (response.isSuccessful() && response.body() != null && response.body().getCode() == 0) {
                return response.body().getData();
            }
        } catch (Exception e) {
            logger.error("remote-bilibili-error----->getBliVideoByTitle", e);
        }
        return null;
    }

    @Override
    public String getBvideoByAvId(int av_id) throws BusinessException {
        ReqCall<String> call = bilibiliRemoteApi.getBvideoByAvId(av_id);
        try {
            Response<String> response = call.execute();
            if (response.isSuccessful() && response.body() != null) {
                return response.body();
            }
        } catch (Exception e) {
            logger.error("remote-bilibili-error----->getBvideoByAvId", e);
        }
        return null;
    }

    @Override
    public String getAvideoByAcId(int ac_id) throws BusinessException {
        ReqCall<String> call = acFunRemoteApi.getAvideoByAcId(ac_id);
        try {
            Response<String> response = call.execute();
            if (response.isSuccessful() && response.body() != null) {
                return response.body();
            }
        } catch (Exception e) {
            logger.error("remote-acfun-error----->getAvideoByAcId", e);
        }
        return null;
    }

    @Override
    public MissEvanSound getMvideoByMId(int m_id) throws BusinessException {
        ReqCall<MissEvanSound> call = missEvanRemoteApi.getMvideoByMId(m_id);
        try {
            Response<MissEvanSound> response = call.execute();
            if (response.isSuccessful() && response.body() != null) {
                return response.body();
            }
        } catch (Exception e) {
            logger.error("remote-missevan-error----->getMvideoByMId", e);
        }
        return null;
    }

    @Override
    public MtvReqData getMtvVideoByTitle(String title) throws BusinessException {
        ReqCall<MtvReqData> call = searchMtvRemoteApi.getMtvVideoByTitle(title, 1,
                24, 0, "REGDATE");
        try {
            Response<MtvReqData> response = call.execute();
            if (response.isSuccessful() && response.body() != null && !response.body().getError()) {
                return response.body();
            }
        } catch (Exception e) {
            logger.error("remote-mtv-error----->getMtvVideoByTitle", e);
        }
        return null;
    }

    @Override
    public String getCvideoByMId(int c_id) throws BusinessException {
        ReqCall<String> call = tuCaoRemoteApi.getCvideoByMId(c_id);
        try {
            Response<String> response = call.execute();
            if (response.isSuccessful() && response.body() != null) {
                return response.body();
            }
        } catch (Exception e) {
            logger.error("remote-tucao-error----->getCvideoByMId", e);
        }
        return null;
    }
}
