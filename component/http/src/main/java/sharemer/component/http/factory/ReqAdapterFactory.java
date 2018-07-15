package sharemer.component.http.factory;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;
import sharemer.component.http.client.ReqCall;

import javax.annotation.Nullable;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Create by 18073 on 2018/6/30.
 */
public class ReqAdapterFactory extends CallAdapter.Factory {

    private final OkHttpClient okClient;

    public ReqAdapterFactory(OkHttpClient okClient) {
        this.okClient = okClient;
    }

    @Nullable
    @Override
    public CallAdapter<?, ReqCall> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {

        if (getRawType(returnType) != ReqCall.class) {
            return null;
        }

        return new CallAdapter<Object, ReqCall>() {
            @Override
            public Type responseType() {
                return getParameterUpperBound(0, (ParameterizedType) returnType);
            }

            @Override
            public ReqCall adapt(Call<Object> call) {
                return new ReqCall(call.request(), annotations, okClient, retrofit.responseBodyConverter(responseType(), annotations));
            }
        };
    }
}