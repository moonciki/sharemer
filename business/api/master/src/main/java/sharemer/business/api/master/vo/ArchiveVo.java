package sharemer.business.api.master.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import sharemer.business.api.master.po.Archive;
import sharemer.business.api.master.po.Tag;
import sharemer.business.api.master.po.User;

import java.util.List;

/**
 * Create by 18073 on 2018/12/15.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArchiveVo extends Archive implements java.io.Serializable {

    private static final long serialVersionUID = -6596107123247091265L;

    private List<Tag> tags;

    private User user;

    private Integer is_faved = 0;

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

    public Integer getIs_faved() {
        return is_faved;
    }

    public void setIs_faved(Integer is_faved) {
        this.is_faved = is_faved;
    }
}
