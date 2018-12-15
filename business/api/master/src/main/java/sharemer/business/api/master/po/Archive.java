package sharemer.business.api.master.po;

/**
 * Create by 18073 on 2018/12/15.
 */
public class Archive {

    private Integer id;

    private String title;

    private String cover;

    private Integer publish_type;

    private String origin_title;

    private String origin_author;

    private String file;

    private Integer status;

    private Integer user_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Integer getPublish_type() {
        return publish_type;
    }

    public void setPublish_type(Integer publish_type) {
        this.publish_type = publish_type;
    }

    public String getOrigin_title() {
        return origin_title;
    }

    public void setOrigin_title(String origin_title) {
        this.origin_title = origin_title;
    }

    public String getOrigin_author() {
        return origin_author;
    }

    public void setOrigin_author(String origin_author) {
        this.origin_author = origin_author;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }
}
