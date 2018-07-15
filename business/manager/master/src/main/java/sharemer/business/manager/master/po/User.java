package sharemer.business.manager.master.po;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * Created by 18073 on 2017/5/6.
 */
public class User implements java.io.Serializable {

    private static final long serialVersionUID = 4872141450564478863L;

    private Integer id;

    private String name;

    private String avater;

    private Integer sex;

    private String address;

    private Date born;

    private String email;

    private String desc;

    private Integer status;

    private Integer is_robot;

    private LocalDateTime ctime;

    private LocalDateTime mtime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
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

    public String getAvater() {
        return avater;
    }

    public void setAvater(String avater) {
        this.avater = avater;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getBorn() {
        return born;
    }

    public void setBorn(Date born) {
        this.born = born;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIs_robot() {
        return is_robot;
    }

    public void setIs_robot(Integer is_robot) {
        this.is_robot = is_robot;
    }
}
