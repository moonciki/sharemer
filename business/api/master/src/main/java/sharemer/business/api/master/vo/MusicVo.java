package sharemer.business.api.master.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import sharemer.business.api.master.po.Music;
import sharemer.business.api.master.po.Tag;

import java.util.List;

/**
 * Created by 18073 on 2017/6/2.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MusicVo extends Music implements java.io.Serializable {

    private static final long serialVersionUID = 3632984800698790610L;

    private List<Tag> tags;

    private Integer is_faved = 0;

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public Integer getIs_faved() {
        return is_faved;
    }

    public void setIs_faved(Integer is_faved) {
        this.is_faved = is_faved;
    }
}
