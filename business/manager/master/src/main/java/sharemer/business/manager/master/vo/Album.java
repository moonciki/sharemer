package sharemer.business.manager.master.vo;

/**
 * Created by 18073 on 2017/5/30.
 */
public class Album implements java.io.Serializable {

    private static final long serialVersionUID = 4872141450564478891L;

    private String name;

    private String picUrl;

    private Long publishTime;

    private String type;

    private String company;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public Long getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Long publishTime) {
        this.publishTime = publishTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
