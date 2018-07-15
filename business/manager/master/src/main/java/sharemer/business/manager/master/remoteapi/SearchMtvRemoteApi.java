package sharemer.business.manager.master.remoteapi;

import retrofit2.http.GET;
import retrofit2.http.Query;
import sharemer.business.manager.master.vo.remote.mtv.MtvReqData;
import sharemer.component.http.client.ReqCall;

/**
 * Create by 18073 on 2018/7/8.
 */
public interface SearchMtvRemoteApi {

    @GET("/search/video-search")
    ReqCall<MtvReqData> getMtvVideoByTitle(@Query("keyword") String keyword,
                                           @Query("pageIndex") Integer pageIndex,
                                           @Query("pageSize") Integer pageSize,
                                           @Query("offset") Integer offset,
                                           @Query("orderType") String orderType);//根据title获取MTV音悦台资源

}
