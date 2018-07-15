package sharemer.business.api.master.vo;

import java.util.List;

/**
 * Created by 18073 on 2017/5/30.
 */
public class WyMusic implements java.io.Serializable{

    private static final long serialVersionUID = 4872141450564478892L;

    private List<Track> tracks;

    private List<String> tags;

    private List<String> alias;

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<String> getAlias() {
        return alias;
    }

    public void setAlias(List<String> alias) {
        this.alias = alias;
    }
}
