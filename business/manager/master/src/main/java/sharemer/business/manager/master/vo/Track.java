package sharemer.business.manager.master.vo;

import java.util.List;

/**
 * Created by 18073 on 2017/5/30.
 */
public class Track implements java.io.Serializable {

    private static final long serialVersionUID = 4872141450564478890L;

    private Integer id;

    private String name;

    private Integer score;//评分

    private Integer duration;//时长

    private Integer mvid;//mv对应id]

    private List<Artist> artists;

    private Album album;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getMvid() {
        return mvid;
    }

    public void setMvid(Integer mvid) {
        this.mvid = mvid;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }
}
