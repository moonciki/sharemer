package sharemer.component.http.exception;

/**
 * Create by 18073 on 2018/6/30.
 * 请求时异常
 */
public class ReqHttpClientException extends RuntimeException {

    private int status = 500;//http code码

    public ReqHttpClientException(String message) {
        super(message);
    }

    public ReqHttpClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReqHttpClientException(String message, int status) {
        super(message);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}