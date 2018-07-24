package sharemer.business.api.master.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import sharemer.business.api.master.po.FavList;
import sharemer.business.api.master.po.Tag;
import sharemer.business.api.master.po.User;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 18073 on 2017/10/31.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FavListInfoVo implements Serializable {
    private static final long serialVersionUID = 3263819381953134498L;

    private List<Tag> tags;

    private FavList fav;

    private Integer m_count;

    private Integer v_count;

    private Integer g_count;

    private User user;

    public FavList getFav() {
        return fav;
    }

    public void setFav(FavList fav) {
        this.fav = fav;
    }

    public Integer getM_count() {
        return m_count;
    }

    public void setM_count(Integer m_count) {
        this.m_count = m_count;
    }

    public Integer getV_count() {
        return v_count;
    }

    public void setV_count(Integer v_count) {
        this.v_count = v_count;
    }

    public Integer getG_count() {
        return g_count;
    }

    public void setG_count(Integer g_count) {
        this.g_count = g_count;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
