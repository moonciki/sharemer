package sharemer.business.manager.master.remoteapi;

import retrofit2.http.GET;
import retrofit2.http.Query;
import sharemer.business.manager.master.vo.BiliSearch;
import sharemer.component.http.client.ReqCall;
import sharemer.component.http.response.BiliResponse;

/**
 * Create by 18073 on 2018/7/5.
 */
public interface SearchBilibiliRemoteApi {

    @GET("/x/web-interface/search/type")
    ReqCall<BiliResponse<BiliSearch>> getBliVideoByTitle(@Query("search_type") String search_type,
                                                        @Query("keyword") String keyword,
                                                        @Query("order") String order,
                                                        @Query("tids") Integer tids,
                                                        @Query("page") int page);//根据songTitle获取B站视频资源

}
