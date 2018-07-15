package sharemer.business.api.master.ro;

/**
 * Created by 18073 on 2017/9/3.
 */
public class ReplyParam {

    private Integer oid;

    private Integer otype;

    private Integer user_id;

    private Long reply_id;

    private String content;

    private String csrf_token;

    private Integer l_type;

    public Integer getOid() {
        return oid;
    }

    public void setOid(Integer oid) {
        this.oid = oid;
    }

    public Integer getOtype() {
        return otype;
    }

    public void setOtype(Integer otype) {
        this.otype = otype;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Long getReply_id() {
        return reply_id;
    }

    public void setReply_id(Long reply_id) {
        this.reply_id = reply_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCsrf_token() {
        return csrf_token;
    }

    public void setCsrf_token(String csrf_token) {
        this.csrf_token = csrf_token;
    }

    public Integer getL_type() {
        return l_type;
    }

    public void setL_type(Integer l_type) {
        this.l_type = l_type;
    }
}
