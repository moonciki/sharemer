package sharemer.component.http.response;

/**
 * Create by 18073 on 2018/6/30.
 */
public class NetEaseResponse<T> {

    private int code;

    private T result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
