package sharemer.business.manager.master.vo.remote.mtv;

/**
 * Create by 18073 on 2018/7/8.
 */
public class MtvReqData {

    private String message;

    private Boolean error;

    private Videos videos;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public Videos getVideos() {
        return videos;
    }

    public void setVideos(Videos videos) {
        this.videos = videos;
    }
}
