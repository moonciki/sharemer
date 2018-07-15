package sharemer.business.manager.master.vo.remote.mtv;

import java.util.List;

/**
 * Create by 18073 on 2018/7/8.
 */
public class VideoData {

    private Integer id;

    private String title;

    private String hltitle;

    private String headImg;

    private List<Tag> artists;

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
        this.title = title;
    }

    public String getHltitle() {
        return hltitle;
    }

    public void setHltitle(String hltitle) {
        this.hltitle = hltitle;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public List<Tag> getArtists() {
        return artists;
    }

    public void setArtists(List<Tag> artists) {
        this.artists = artists;
    }
}
