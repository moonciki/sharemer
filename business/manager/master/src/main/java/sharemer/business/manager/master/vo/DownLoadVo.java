package sharemer.business.manager.master.vo;

/**
 * Create by 18073 on 2018/12/10.
 */
public class DownLoadVo {

    private Integer id;

    private String origin_url;

    private Integer mediaType;

    public DownLoadVo() {
    }

    public DownLoadVo(Integer id, String origin_url, Integer mediaType) {
        this.id = id;
        this.origin_url = origin_url;
        this.mediaType = mediaType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrigin_url() {
        return origin_url;
    }

    public void setOrigin_url(String origin_url) {
        this.origin_url = origin_url;
    }

    public Integer getMediaType() {
        return mediaType;
    }

    public void setMediaType(Integer mediaType) {
        this.mediaType = mediaType;
    }
}
