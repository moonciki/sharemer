package sharemer.business.api.master.po;

/**
 * Created by 18073 on 2017/5/31.
 */
public class TagMedia {

    private Integer id;

    private Integer tag_id;

    private Integer media_id;

    private Integer type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTag_id() {
        return tag_id;
    }

    public void setTag_id(Integer tag_id) {
        this.tag_id = tag_id;
    }

    public Integer getMedia_id() {
        return media_id;
    }

    public void setMedia_id(Integer media_id) {
        this.media_id = media_id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getMediaTagTableNum(){
        return this.media_id % 10;
    }

    public Integer getTagMediaTableNum(){
        return this.tag_id % 10;
    }
}
