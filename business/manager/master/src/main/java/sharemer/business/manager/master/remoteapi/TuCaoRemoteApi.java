package sharemer.business.manager.master.remoteapi;

import retrofit2.http.GET;
import retrofit2.http.Path;
import sharemer.component.http.client.ReqCall;

/**
 * Create by 18073 on 2018/7/5.
 */
public interface TuCaoRemoteApi {

    @GET("/play/h{h_id}")
    ReqCall<String> getCvideoByMId(@Path("h_id") int h_id);//根据cId获取C站资源

}
