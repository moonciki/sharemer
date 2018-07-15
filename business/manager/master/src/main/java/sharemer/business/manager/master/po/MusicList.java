package sharemer.business.manager.master.po;

import java.time.LocalDateTime;

/**
 * Created by 18073 on 2017/5/28.
 */
public class MusicList {

    private Integer id;

    private String title;

    private Integer wy_id;

    private String wy_type;

    private Integer is_done;

    private LocalDateTime ctime;

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

    public Integer getWy_id() {
        return wy_id;
    }

    public void setWy_id(Integer wy_id) {
        this.wy_id = wy_id;
    }

    public String getWy_type() {
        return wy_type;
    }

    public void setWy_type(String wy_type) {
        this.wy_type = wy_type;
    }

    public LocalDateTime getCtime() {
        return ctime;
    }

    public void setCtime(LocalDateTime ctime) {
        this.ctime = ctime;
    }

    public Integer getIs_done() {
        return is_done;
    }

    public void setIs_done(Integer is_done) {
        this.is_done = is_done;
    }
}
