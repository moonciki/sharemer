package sharemer.business.manager.master.vo;

/**
 * Created by 18073 on 2017/5/30.
 */
public class WyObj implements java.io.Serializable {

    private static final long serialVersionUID = 4872141450564478893L;

    private Integer code;

    private WyMusic result;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public WyMusic getResult() {
        return result;
    }

    public void setResult(WyMusic result) {
        this.result = result;
    }
}
