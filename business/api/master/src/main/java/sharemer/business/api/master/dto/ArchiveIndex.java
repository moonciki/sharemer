package sharemer.business.api.master.dto;

import sharemer.business.api.master.vo.ArchiveVo;

import java.time.LocalDateTime;

/**
 * Create by 18073 on 2018/12/23.
 */
public class ArchiveIndex {

    private Integer id;

    private String title;

    private Integer source_type;

    private Integer user_id;

    private String user_name;

    private String cover;

    private Integer publish_type;

    private LocalDateTime ctime;

    private LocalDateTime mtime;

    public static ArchiveIndex getArchiveIndex(ArchiveVo archiveVo){
        ArchiveIndex archiveIndex = new ArchiveIndex();
        archiveIndex.setId(archiveVo.getId());
        archiveIndex.setCover(archiveVo.getCover());
        archiveIndex.setPublish_type(archiveVo.getPublish_type());
        archiveIndex.setSource_type(archiveVo.getSource_type());
        archiveIndex.setTitle(archiveVo.getTitle());
        archiveIndex.setCtime(archiveVo.getCtime());
        archiveIndex.setMtime(archiveVo.getMtime());
        archiveIndex.setUser_id(archiveVo.getUser_id());
        return archiveIndex;
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

    public Integer getSource_type() {
        return source_type;
    }

    public void setSource_type(Integer source_type) {
        this.source_type = source_type;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Integer getPublish_type() {
        return publish_type;
    }

    public void setPublish_type(Integer publish_type) {
        this.publish_type = publish_type;
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
}
