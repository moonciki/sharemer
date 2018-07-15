package sharemer.component.http.client;

import retrofit2.http.GET;

/**
 * Create by 18073 on 2018/6/30.
 */
public interface ApiServiceTest {

    @GET("/")
    ReqCall<String> baidu();

}
