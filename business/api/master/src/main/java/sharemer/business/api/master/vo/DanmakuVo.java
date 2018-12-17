package sharemer.business.api.master.vo;

import sharemer.business.api.master.po.Danmaku;

/**
 * Created by Administrator on 2017/1/20 0020.
 */
public class DanmakuVo extends Danmaku implements java.io.Serializable {

    private String csrf;

    public String getCsrf() {
        return csrf;
    }

    public void setCsrf(String csrf) {
        this.csrf = csrf;
    }
}
