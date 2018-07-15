package sharemer.component.http.client;

import org.junit.Before;
import org.junit.Test;
import retrofit2.Response;

/**
 * Create by 18073 on 2018/6/30.
 */
public class MainTest {

    private ApiServiceTest apiServiceTest;

    @Before
    public void doBefore(){
        apiServiceTest = HttpClient.builder()
                .connectionPool(new HttpClient.ConnectionPool(10))
                .timeout(new HttpClient.TimeOut(500, 500))
                .target(ApiServiceTest.class, "http://www.baidu.com");
    }


    @Test
    public void test(){
        ReqCall<String> call = apiServiceTest.baidu();
        Response<String> response = call.execute();
        if (response.isSuccessful() && response.body() != null) {
            System.out.print(response.body());
        }
    }

}
