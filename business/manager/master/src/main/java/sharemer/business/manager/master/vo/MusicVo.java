package sharemer.business.manager.master.vo;

import sharemer.business.manager.master.po.Music;
import sharemer.business.manager.master.po.Tag;

import java.util.List;

/**
 * Created by 18073 on 2017/6/2.
 */
public class MusicVo extends Music {

    private List<Tag> tags;

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
