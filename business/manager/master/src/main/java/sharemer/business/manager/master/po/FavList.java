package sharemer.business.manager.master.po;

/**
 * Created by 18073 on 2017/9/29.
 */
public class FavList implements java.io.Serializable {

    private static final long serialVersionUID = 6742143653087880957L;

    public static FavList EMPTY = new FavList();

    private Integer id;

    private String title;

    private String cover;

    private Integer user_id;

    private Integer status;

    private Integer is_hide;

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

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIs_hide() {
        return is_hide;
    }

    public void setIs_hide(Integer is_hide) {
        this.is_hide = is_hide;
    }
}
