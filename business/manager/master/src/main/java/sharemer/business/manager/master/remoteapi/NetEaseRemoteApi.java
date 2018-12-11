package sharemer.business.manager.master.remoteapi;

import retrofit2.http.GET;
import retrofit2.http.Query;
import sharemer.business.manager.master.vo.WyObj;
import sharemer.component.http.client.ReqCall;
import sharemer.component.http.response.NetEaseResponse;

/**
 * Create by 18073 on 2018/7/5.
 */
public interface NetEaseRemoteApi {

    @GET("/api/playlist/detail")
    ReqCall<WyObj> getMusicsByListId(@Query("id") long id);//根据歌单获取歌单内所有的音乐信息

    @GET("/mv")
    ReqCall<String> getMvUrlByMvId(@Query("id")int id);//根据mvId获取mp4外链

    @GET("/discover/playlist")
    ReqCall<String> getPlayListById(@Query("order")String order,
                                    @Query("cat") String cat,
                                    @Query("limit") Integer limit,
                                    @Query("offset") Integer offset);//根据mvId获取mp4外链

}
