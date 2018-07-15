package sharemer.business.api.master.ro;

import sharemer.business.api.master.po.FavList;

/**
 * Created by 18073 on 2017/9/30.
 */
public class FavListParam {

    private Integer id;

    private String title;

    private String cover;

    private Integer user_id;

    private Integer status;

    private Integer is_hide;

    private String csrf_token;

    public FavList getFavList(){
        FavList favList = new FavList();
        favList.setId(id);
        favList.setTitle(title);
        favList.setUser_id(user_id);
        favList.setCover(cover);
        favList.setIs_hide(is_hide);
        favList.setStatus(status);
        return favList;
    }

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

    public String getCsrf_token() {
        return csrf_token;
    }

    public void setCsrf_token(String csrf_token) {
        this.csrf_token = csrf_token;
    }
}
