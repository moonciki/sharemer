package sharemer.business.manager.master.po;

/**
 * Created by 18073 on 2017/10/16.
 */
public class FavMedia {

    private Integer id;

    private Integer oid;

    private Integer otype;

    private Integer list_id;

    public FavMedia(){}

    public FavMedia(Integer oid, Integer otype, Integer list_id) {
        this.oid = oid;
        this.otype = otype;
        this.list_id = list_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOid() {
        return oid;
    }

    public void setOid(Integer oid) {
        this.oid = oid;
    }

    public Integer getOtype() {
        return otype;
    }

    public void setOtype(Integer otype) {
        this.otype = otype;
    }

    public Integer getList_id() {
        return list_id;
    }

    public void setList_id(Integer list_id) {
        this.list_id = list_id;
    }
}
