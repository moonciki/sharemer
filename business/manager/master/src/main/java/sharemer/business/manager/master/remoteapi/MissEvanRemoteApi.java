package sharemer.business.manager.master.remoteapi;

import retrofit2.http.GET;
import retrofit2.http.Query;
import sharemer.business.manager.master.vo.remote.m.MissEvanSound;
import sharemer.component.http.client.ReqCall;

/**
 * Create by 18073 on 2018/7/5.
 */
public interface MissEvanRemoteApi {

    @GET("/sound/getsound")
    ReqCall<MissEvanSound> getMvideoByMId(@Query("soundid") int id);//根据mId获取M站资源

}
