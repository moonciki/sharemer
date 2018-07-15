package sharemer.business.manager.master.remoteapi;

import retrofit2.http.GET;
import retrofit2.http.Path;
import sharemer.component.http.client.ReqCall;

/**
 * Create by 18073 on 2018/7/5.
 */
public interface BilibiliRemoteApi {

    @GET("/video/av{av}")
    ReqCall<String> getBvideoByAvId(@Path ("av") int av);//根据avId获取B站资源

}
