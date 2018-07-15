package sharemer.business.manager.master.po;

import java.time.LocalDateTime;

/**
 * Created by 18073 on 2017/5/30.
 */
public class Music implements java.io.Serializable {

    private static final long serialVersionUID = 2272141450564477863L;

    private Integer id;

    private String title;

    private String album_title;

    private String other_msg;

    private Integer song_id;

    private Integer score_num;

    private Integer score;

    private Integer duration;

    private Integer mv_id;

    private String mv_playurl;

    private String cover;

    private LocalDateTime publish_time;

    private String songer;

    private Integer r_id;

    private String r_name;

    private LocalDateTime ctime;

    private String source_url;

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

    public String getAlbum_title() {
        return album_title;
    }

    public void setAlbum_title(String album_title) {
        this.album_title = album_title;
    }

    public String getOther_msg() {
        return other_msg;
    }

    public void setOther_msg(String other_msg) {
        this.other_msg = other_msg;
    }

    public Integer getSong_id() {
        return song_id;
    }

    public void setSong_id(Integer song_id) {
        this.song_id = song_id;
    }

    public Integer getScore_num() {
        return score_num;
    }

    public void setScore_num(Integer score_num) {
        this.score_num = score_num;
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

    public Integer getMv_id() {
        return mv_id;
    }

    public void setMv_id(Integer mv_id) {
        this.mv_id = mv_id;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public LocalDateTime getPublish_time() {
        return publish_time;
    }

    public void setPublish_time(LocalDateTime publish_time) {
        this.publish_time = publish_time;
    }

    public String getSonger() {
        return songer;
    }

    public void setSonger(String songer) {
        this.songer = songer;
    }

    public LocalDateTime getCtime() {
        return ctime;
    }

    public void setCtime(LocalDateTime ctime) {
        this.ctime = ctime;
    }

    public String getMv_playurl() {
        return mv_playurl;
    }

    public void setMv_playurl(String mv_playurl) {
        this.mv_playurl = mv_playurl;
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
}
