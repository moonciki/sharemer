package sharemer.component.http.client;

import com.google.common.base.Preconditions;
import okhttp3.*;
import okhttp3.internal.http.HttpHeaders;
import okio.Buffer;
import okio.BufferedSource;
import okio.GzipSource;
import retrofit2.Retrofit;
import sharemer.component.http.converter.ReqConverterFactory;
import sharemer.component.http.interceptor.RequestInterceptor;
import sharemer.component.http.factory.ReqAdapterFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Create by 18073 on 2018/6/30.
 * shamer外部http请求客户端
 */
public class HttpClient {

    public static Builder builder() {
        return new Builder();
    }

    public static final class TimeOut {
        private final int connectTimeoutMillis;
        private final int readTimeoutMillis;

        static final TimeOut DEFAULT_TIMEOUT = new TimeOut();

        public TimeOut(int connectTimeoutMillis, int readTimeoutMillis) {
            this.connectTimeoutMillis = connectTimeoutMillis;
            this.readTimeoutMillis = readTimeoutMillis;
        }

        public int getConnectTimeoutMillis() {
            return connectTimeoutMillis;
        }

        public int getReadTimeoutMillis() {
            return readTimeoutMillis;
        }

        public TimeOut() {
            this(200, 200);
        }
    }


    public static final class ConnectionPool {
        private int maxIdle = 500;

        public ConnectionPool(int maxIdle) {
            this.maxIdle = maxIdle;
        }

        public int getMaxIdle() {
            return maxIdle;
        }

    }

    public static final class Builder {
        /** okhttp client 连接池 初始化*/
        private static final OkHttpClient DEFAULT_CLIENT = new OkHttpClient.Builder()
                .retryOnConnectionFailure(false)
                .connectTimeout(TimeOut.DEFAULT_TIMEOUT.connectTimeoutMillis, TimeUnit.MILLISECONDS)
                .readTimeout(TimeOut.DEFAULT_TIMEOUT.readTimeoutMillis, TimeUnit.MILLISECONDS)
                .connectionPool(new okhttp3.ConnectionPool(500, 3, TimeUnit.SECONDS))
                .build();

        /** 请求发送前需要执行的外部拦截器*/
        private final List<RequestInterceptor> interceptors = new ArrayList<>();

        private final OkHttpClient.Builder okhttp;

        /** 支持自定义的okhttp client*/
        Builder(OkHttpClient.Builder okhttp) {
            this.okhttp = okhttp;
        }

        /** 否则启用默认配置*/
        public Builder() {
            this(DEFAULT_CLIENT.newBuilder());
        }

        public <T> T target(Class<T> apiType, String url) {
            /** 在这里将自定义的拦截器加进去*/
            OkHttpClient client = okhttp.addInterceptor(new InternalInterceptor(interceptors))
                    .addInterceptor(new LoggingInterceptor())
                    .build();
            return new Retrofit.Builder()
                    .addCallAdapterFactory(new ReqAdapterFactory(client))
                    .addConverterFactory(new ReqConverterFactory())
                    .client(client)
                    .baseUrl(url)
                    .build()
                    .create(apiType);
        }

        public Builder connectionPool(ConnectionPool pool) {
            okhttp.connectionPool(new okhttp3.ConnectionPool(pool.maxIdle, 3, TimeUnit.SECONDS));
            return this;
        }

        /** 自定义拦截器添加（追加责任链）*/
        public Builder addRequestInterceptor(RequestInterceptor interceptor) {
            Preconditions.checkNotNull(interceptor);
            interceptors.add(interceptor);
            return this;
        }

        /** 设置超时时间*/
        public Builder timeout(TimeOut timeOut) {
            okhttp.connectTimeout(timeOut.connectTimeoutMillis, TimeUnit.MILLISECONDS);
            okhttp.readTimeout(timeOut.readTimeoutMillis, TimeUnit.MILLISECONDS);
            return this;
        }

    }


    private static final class LoggingInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            final Request request = chain.request();
            final Response response;

            RequestBody requestBody = request.body();
            boolean hasRequestBody = requestBody != null;

            if (hasRequestBody && !bodyHasUnknownEncoding(request.headers())) {
                Buffer buffer = new Buffer();
                requestBody.writeTo(buffer);
            }
            //logger.info(">>>  {} {} {} {}", request.method(), url, connection != null ? " " + connection.protocol() : "", Strings.isNullOrEmpty(requestBodyMessage) ? "" : requestBodyMessage);
            try {
                response = chain.proceed(request);
            } catch (Exception e) { // log all exception!
                //logger.info("<<<  {} Failed on {}", url, e.getClass().getSimpleName());
                throw e;
            }
            ResponseBody responseBody = response.body();
            if (HttpHeaders.hasBody(response) && !bodyHasUnknownEncoding(response.headers())) {
                BufferedSource source = responseBody.source();
                source.request(Long.MAX_VALUE); // Buffer the entire body.
                Buffer buffer = source.buffer();
                if ("gzip".equalsIgnoreCase(response.headers().get("Content-Encoding"))) {
                    GzipSource gzippedResponseBody = null;
                    try {
                        gzippedResponseBody = new GzipSource(buffer.clone());
                        buffer = new Buffer();
                        buffer.writeAll(gzippedResponseBody);
                    } finally {
                        if (gzippedResponseBody != null) {
                            gzippedResponseBody.close();
                        }
                    }
                }
            }
            return response;
        }

        private boolean bodyHasUnknownEncoding(Headers headers) {
            String contentEncoding = headers.get("Content-Encoding");
            return contentEncoding != null
                    && !contentEncoding.equalsIgnoreCase("identity")
                    && !contentEncoding.equalsIgnoreCase("gzip");
        }

    }

    /** 添加内置的拦截器（自定义）*/
    private static final class InternalInterceptor implements Interceptor {

        private final List<RequestInterceptor> interceptors;

        InternalInterceptor(List<RequestInterceptor> interceptors) {
            this.interceptors = interceptors;
        }

        /** 重写okhttp拦截器的intercept方法，这样在请求的时候会触发该方法，
         * 会触发我们自定义拦截责任链*/
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!interceptors.isEmpty()) {
                for (RequestInterceptor interceptor : interceptors) {
                    request = interceptor.intercept(request);
                }
            }
            return chain.proceed(request);
        }
    }
}
