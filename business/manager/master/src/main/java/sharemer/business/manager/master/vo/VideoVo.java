package sharemer.business.manager.master.vo;

import sharemer.business.manager.master.po.Tag;
import sharemer.business.manager.master.po.Video;

import java.util.List;

/**
 * Created by 18073 on 2017/6/17.
 */
public class VideoVo extends Video {

    private List<Tag> tags;

    private String net_name;

    private String logo_url;

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
}
