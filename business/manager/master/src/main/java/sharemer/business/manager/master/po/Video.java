package sharemer.business.manager.master.po;

import org.jsoup.Jsoup;

import java.util.List;

/**
 * Created by 18073 on 2017/6/8.
 */
public class Video implements java.io.Serializable {

    private static final long serialVersionUID = 3955480063024052131L;

    private Integer id;

    private String title;

    private Integer v_id;

    private Integer page;

    private String msg;

    private String cover;

    private Integer embed_type;

    private Integer type;

    private String video_url;

    private Integer r_id;

    private String r_name;

    private String source_url;

    private List<String> final_tags;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = Jsoup.parse(title).text();
    }

    public Integer getV_id() {
        return v_id;
    }

    public void setV_id(Integer v_id) {
        this.v_id = v_id;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public Integer getEmbed_type() {
        return embed_type;
    }

    public void setEmbed_type(Integer embed_type) {
        this.embed_type = embed_type;
    }

    public Integer getR_id() {
        return r_id;
    }

    public void setR_id(Integer r_id) {
        this.r_id = r_id;
    }

    public String getR_name() {
        return r_name;
    }

    public void setR_name(String r_name) {
        this.r_name = r_name;
    }

    public String getSource_url() {
        return source_url;
    }

    public void setSource_url(String source_url) {
        this.source_url = source_url;
    }

    public List<String> getFinal_tags() {
        return final_tags;
    }

    public void setFinal_tags(List<String> final_tags) {
        this.final_tags = final_tags;
    }
}
