package sharemer.business.api.master.dto;

import sharemer.business.api.master.vo.MusicVo;

import java.time.LocalDateTime;

/**
 * Created by 18073 on 2017/8/2.
 */
public class MusicIndex {

    private Integer id;

    private String title;

    private String uname;

    private Integer uid;

    private String cover;

    private Integer score;

    private Integer song_id;

    private String songer;

    private String desc;

    private LocalDateTime publish_time;

    public static MusicIndex getMusicIndex(MusicVo musicVo){
        MusicIndex musicIndex = new MusicIndex();
        musicIndex.setId(musicVo.getId());
        musicIndex.setTitle(musicVo.getTitle());
        musicIndex.setUname(musicVo.getR_name());
        musicIndex.setUid(musicVo.getR_id());
        musicIndex.setCover(musicVo.getCover());
        musicIndex.setScore(musicVo.getScore());
        musicIndex.setSong_id(musicVo.getSong_id());
        musicIndex.setPublish_time(musicVo.getPublish_time());
        musicIndex.setSonger(musicVo.getSonger());
        musicIndex.setDesc(musicVo.getOther_msg());
        return musicIndex;
    }

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

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getSong_id() {
        return song_id;
    }

    public void setSong_id(Integer song_id) {
        this.song_id = song_id;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
