package sharemer.component.http.exception;

/**
 * Create by 18073 on 2018/6/30.
 * 请求时异常
 */
public class ReqApiException extends ReqHttpClientException {

    private final int code;

    public ReqApiException(int code, int status, String message) {
        super(message, status);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}