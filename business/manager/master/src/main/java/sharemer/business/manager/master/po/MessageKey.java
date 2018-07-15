package sharemer.business.manager.master.po;

import com.alibaba.fastjson.JSON;

/**
 * Created by 18073 on 2017/7/4.
 */
public class MessageKey {

    private Integer id;
    private String taskId;
    private Integer type;
    private String title;
    private Integer sourceType;
    private String cover;

    @Override
    public String toString(){
        return JSON.toJSONString(this);
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getSourceType() {
        return sourceType;
    }

    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
