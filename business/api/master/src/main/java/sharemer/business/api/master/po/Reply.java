package sharemer.business.api.master.po;

import java.time.LocalDateTime;

/**
 * Created by 18073 on 2017/9/2.
 */
public class Reply implements java.io.Serializable {

    private static final long serialVersionUID = -8620400890718113336L;

    private Integer id;

    private Long real_id;

    private Integer user_id;

    private Integer oid;

    private Integer otype;

    private Long reply_id;

    private String content;

    private Integer like;

    private Integer dislike;

    private Integer status;

    private LocalDateTime ctime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

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

    public Integer getLike() {
        return like;
    }

    public void setLike(Integer like) {
        this.like = like;
    }

    public Integer getDislike() {
        return dislike;
    }

    public void setDislike(Integer dislike) {
        this.dislike = dislike;
    }

    public LocalDateTime getCtime() {
        return ctime;
    }

    public void setCtime(LocalDateTime ctime) {
        this.ctime = ctime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getReal_id() {
        return real_id;
    }

    public void setReal_id(Long real_id) {
        this.real_id = real_id;
    }
}
