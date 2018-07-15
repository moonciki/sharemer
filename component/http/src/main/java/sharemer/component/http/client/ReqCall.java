package sharemer.component.http.client;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Converter;
import retrofit2.Response;
import sharemer.component.http.exception.ReqApiException;
import sharemer.component.http.exception.ReqHttpClientException;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

/**
 * Create by 18073 on 2018/6/30.
 * 请求返回实体
 */
public class ReqCall<T> implements Cloneable {

    private volatile boolean canceled;
    private boolean executed;
    private okhttp3.Call rawCall;
    private OkHttpClient okHttpClient;
    private final Converter<ResponseBody, T> responseBodyConverter;
    private final okhttp3.Request rawRequest;
    private final Annotation[] annotations;


    public ReqCall(Request rawRequest, Annotation[] annotations, OkHttpClient okClient, Converter<ResponseBody, T> responseBodyConverter) {
        this.rawRequest = rawRequest;
        this.annotations = annotations;
        this.okHttpClient = okClient;
        this.responseBodyConverter = responseBodyConverter;
    }

    public Response<T> execute() throws ReqHttpClientException {
        return execute(null);
    }

    public Response<T> execute(HttpClient.TimeOut timeOut) throws ReqHttpClientException {
        Request request = rawRequest;
        okhttp3.Call call;
        synchronized (this) {
            if (executed) {
                throw new IllegalStateException("Already executed.");
            }
            executed = true;
            if (timeOut != null) {
                okHttpClient = okHttpClient
                        .newBuilder()
                        .connectTimeout(timeOut.getConnectTimeoutMillis(), TimeUnit.MILLISECONDS)
                        .readTimeout(timeOut.getReadTimeoutMillis(), TimeUnit.MILLISECONDS)
                        .build();
            }
            call = rawCall = okHttpClient.newCall(request);
        }
        if (canceled) {
            call.cancel();
        }

        try {
            Response<T> response = parseResponse(call.execute());
            if (!response.isSuccessful()) {
                throw new ReqHttpClientException(response.message(), response.code());
            }
            return response;
        } catch (Exception e) {
            throw throwsException(e);
        }
    }

    Response<T> parseResponse(okhttp3.Response rawResponse) throws IOException {

        ResponseBody rawBody = rawResponse.body();

        rawResponse = rawResponse.newBuilder()
                .body(new NoContentResponseBody(rawBody.contentType(), rawBody.contentLength()))
                .build();

        int code = rawResponse.code();
        if (code < 200 || code >= 300) {
            try {
                Buffer buffer = new Buffer();
                rawBody.source().readAll(buffer);
                ResponseBody bufferedBody = ResponseBody.create(rawBody.contentType(), rawBody.contentLength(), buffer);
                return Response.error(bufferedBody, rawResponse);
            } finally {
                rawBody.close();
            }
        }
        if (code == 204 || code == 205) {
            return Response.success(null, rawResponse);
        }
        T body = responseBodyConverter.convert(rawBody);
        return Response.success(body, rawResponse);
    }

    /** 异常统一处理*/
    ReqHttpClientException throwsException(Exception e) {
        if (e instanceof ReqApiException) {
            return (ReqApiException) e;
        } else if (e instanceof ReqHttpClientException) {
            return (ReqHttpClientException) e;
        } else if (e instanceof IOException) {
            Throwable cause = e.getCause();
            if (cause instanceof ConnectException) {
                return new ReqApiException(-502, 502, cause.getMessage());
            } else if (cause instanceof SocketTimeoutException) {
                return new ReqApiException(-504, 504, cause.getMessage());
            } else if (e instanceof SocketTimeoutException) {
                return new ReqApiException(-504, 504, e.getMessage());
            } else {
                return new ReqApiException(-502, 502, e.getMessage());
            }
        } else {
            return new ReqHttpClientException("unexpected exception", e);
        }
    }

    public Request request() {
        return rawRequest;
    }

    public void cancel() {
        canceled = true;
        okhttp3.Call call;
        synchronized (this) {
            call = rawCall;
        }
        if (call != null) {
            call.cancel();
        }
    }

    public boolean isCanceled() {
        return canceled;
    }

    public boolean isExecuted() {
        return executed;
    }

    @Override
    public ReqCall<T> clone() {
        return new ReqCall<>(rawRequest, annotations, okHttpClient, responseBodyConverter);
    }

    private static final class NoContentResponseBody extends ResponseBody {
        private final MediaType contentType;
        private final long contentLength;

        NoContentResponseBody(MediaType contentType, long contentLength) {
            this.contentType = contentType;
            this.contentLength = contentLength;
        }

        @Override
        public MediaType contentType() {
            return contentType;
        }

        @Override
        public long contentLength() {
            return contentLength;
        }

        @Override
        public BufferedSource source() {
            throw new IllegalStateException("Cannot read raw response body of a converted body.");
        }
    }

}
