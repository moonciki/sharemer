package sharemer.business.api.master.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import sharemer.business.api.master.po.Tag;
import sharemer.business.api.master.po.User;
import sharemer.business.api.master.po.Video;

import java.util.List;

/**
 * Created by 18073 on 2017/6/17.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VideoVo extends Video implements java.io.Serializable {

    private static final long serialVersionUID = -3596107123247091173L;

    private List<Tag> tags;

    private User user;

    private String net_name;

    private String logo_url;

    private Integer is_faved = 0;

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public String getNet_name() {
        return net_name;
    }

    public void setNet_name(String net_name) {
        this.net_name = net_name;
    }

    public String getLogo_url() {
        return logo_url;
    }

    public void setLogo_url(String logo_url) {
        this.logo_url = logo_url;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getIs_faved() {
        return is_faved;
    }

    public void setIs_faved(Integer is_faved) {
        this.is_faved = is_faved;
    }
}
