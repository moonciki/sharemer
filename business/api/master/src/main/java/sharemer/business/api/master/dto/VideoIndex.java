package sharemer.business.api.master.dto;

import sharemer.business.api.master.vo.VideoVo;

import java.time.LocalDateTime;

/**
 * Created by 18073 on 2017/8/13.
 */
public class VideoIndex {

    private Integer id;

    private String title;

    private String r_name;

    private Integer r_id;

    private String cover;

    private String net_name;

    private String net_color;

    private String logo_url;

    private String video_url;

    private LocalDateTime ctime;

    private LocalDateTime mtime;

    public static VideoIndex getVideoIndex(VideoVo videoVo){
        VideoIndex videoIndex = new VideoIndex();
        videoIndex.setR_name(videoVo.getR_name());
        videoIndex.setR_id(videoVo.getR_id());
        videoIndex.setTitle(videoVo.getTitle());
        videoIndex.setCover(videoVo.getCover());
        videoIndex.setId(videoVo.getId());
        videoIndex.setNet_name(videoVo.getNet_name());
        videoIndex.setNet_color(videoVo.getNet_color());
        videoIndex.setLogo_url(videoVo.getLogo_url());
        videoIndex.setMtime(videoVo.getMtime());
        videoIndex.setCtime(videoVo.getCtime());
        videoIndex.setVideo_url(videoVo.getVideo_url());
        return videoIndex;
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

    public String getR_name() {
        return r_name;
    }

    public void setR_name(String r_name) {
        this.r_name = r_name;
    }

    public Integer getR_id() {
        return r_id;
    }

    public void setR_id(Integer r_id) {
        this.r_id = r_id;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
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

    public LocalDateTime getCtime() {
        return ctime;
    }

    public void setCtime(LocalDateTime ctime) {
        this.ctime = ctime;
    }

    public LocalDateTime getMtime() {
        return mtime;
    }

    public void setMtime(LocalDateTime mtime) {
        this.mtime = mtime;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public String getNet_color() {
        return net_color;
    }

    public void setNet_color(String net_color) {
        this.net_color = net_color;
    }
}
