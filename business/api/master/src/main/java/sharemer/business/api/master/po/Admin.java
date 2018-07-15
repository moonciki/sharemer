package sharemer.business.api.master.po;

/**
 * Created by 18073 on 2017/5/20.
 */
public class Admin implements java.io.Serializable {

    private static final long serialVersionUID = 4872141450564478864L;

    private Integer id;

    private String name;

    private String password;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
