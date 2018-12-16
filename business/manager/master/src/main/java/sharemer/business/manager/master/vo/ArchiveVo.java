package sharemer.business.manager.master.vo;

import sharemer.business.manager.master.po.Archive;
import sharemer.business.manager.master.po.Tag;
import sharemer.business.manager.master.po.User;

import java.util.List;

/**
 * Create by 18073 on 2018/12/15.
 */
public class ArchiveVo extends Archive {

    private List<Tag> tags;

    private User user;

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
