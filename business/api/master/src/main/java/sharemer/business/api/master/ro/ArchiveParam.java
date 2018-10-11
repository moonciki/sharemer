package sharemer.business.api.master.ro;

import java.util.List;

/**
 * Create by 18073 on 2018/10/12.
 */
public class ArchiveParam {

    private String title;

    private String cover;

    private Integer publish_type;

    private String file;

    private String origin_title;

    private String origin_author;

    private List<String> style_tag;

    private String tags;

    private String staff;

    private String csrf;

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

    public Integer getPublish_type() {
        return publish_type;
    }

    public void setPublish_type(Integer publish_type) {
        this.publish_type = publish_type;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getOrigin_title() {
        return origin_title;
    }

    public void setOrigin_title(String origin_title) {
        this.origin_title = origin_title;
    }

    public String getOrigin_author() {
        return origin_author;
    }

    public void setOrigin_author(String origin_author) {
        this.origin_author = origin_author;
    }

    public List<String> getStyle_tag() {
        return style_tag;
    }

    public void setStyle_tag(List<String> style_tag) {
        this.style_tag = style_tag;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getStaff() {
        return staff;
    }

    public void setStaff(String staff) {
        this.staff = staff;
    }

    public String getCsrf() {
        return csrf;
    }

    public void setCsrf(String csrf) {
        this.csrf = csrf;
    }
}
