package sharemer.business.manager.master.remoteapi;

import retrofit2.http.GET;
import retrofit2.http.Path;
import sharemer.component.http.client.ReqCall;

/**
 * Create by 18073 on 2018/7/5.
 */
public interface AcFunRemoteApi {

    @GET("/v/ac{ac}")
    ReqCall<String> getAvideoByAcId(@Path("ac") int ac);//根据acId获取A站资源

}
