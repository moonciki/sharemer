package sharemer.business.api.master.po;

import com.fasterxml.jackson.annotation.JsonInclude;
import sharemer.business.api.master.utils.Constant;

import java.time.LocalDateTime;

/**
 * Created by 18073 on 2017/5/6.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User implements java.io.Serializable {

    private static final long serialVersionUID = 4872141450564478863L;

    public static final User EMPTY = new User();

    private Integer id;

    private String name;

    private String blog_title;

    private String avater;

    private Integer sex;

    private String address;

    private LocalDateTime born;

    private String email;

    private String desc;

    private Integer level;

    private Integer score;

    private String level_color;

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

    public LocalDateTime getBorn() {
        return born;
    }

    public void setBorn(LocalDateTime born) {
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

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
        this.level_color = Constant.User.getLevelColor(level);
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getLevel_color() {
        return level_color;
    }

    public void setLevel_color(String level_color) {
        this.level_color = level_color;
    }

    public String getBlog_title() {
        return blog_title;
    }

    public void setBlog_title(String blog_title) {
        this.blog_title = blog_title;
    }
}
