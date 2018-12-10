package sharemer.business.manager.master.po;

/**
 * Create by 18073 on 2018/12/10.
 */
public class Images {

    private Integer id;

    private String origin_url;

    private String qiniu_url;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrigin_url() {
        return origin_url;
    }

    public void setOrigin_url(String origin_url) {
        this.origin_url = origin_url;
    }

    public String getQiniu_url() {
        return qiniu_url;
    }

    public void setQiniu_url(String qiniu_url) {
        this.qiniu_url = qiniu_url;
    }
}
