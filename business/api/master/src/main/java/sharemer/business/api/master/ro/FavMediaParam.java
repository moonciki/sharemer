package sharemer.business.api.master.ro;

import java.util.List;

/**
 * Created by 18073 on 2017/10/14.
 */
public class FavMediaParam {

    private Integer o_id;

    private Integer o_type;

    private List<Integer> fav_ids;

    private Integer user_id;

    private String csrf_token;

    public Integer getO_id() {
        return o_id;
    }

    public void setO_id(Integer o_id) {
        this.o_id = o_id;
    }

    public Integer getO_type() {
        return o_type;
    }

    public void setO_type(Integer o_type) {
        this.o_type = o_type;
    }

    public List<Integer> getFav_ids() {
        return fav_ids;
    }

    public void setFav_ids(List<Integer> fav_ids) {
        this.fav_ids = fav_ids;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getCsrf_token() {
        return csrf_token;
    }

    public void setCsrf_token(String csrf_token) {
        this.csrf_token = csrf_token;
    }
}
