package sharemer.business.api.master.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import sharemer.business.api.master.po.FavList;
import sharemer.business.api.master.utils.Constant;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 18073 on 2017/10/22.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FavListVo implements Serializable {

    private static final long serialVersionUID = 2812931912667688471L;

    private Integer count;

    private Integer is_self = Constant.User.NOT_SELF;

    private List<FavList> favs;

    public Integer getIs_self() {
        return is_self;
    }

    public void setIs_self(Integer is_self) {
        this.is_self = is_self;
    }

    public List<FavList> getFavs() {
        return favs;
    }

    public void setFavs(List<FavList> favs) {
        this.favs = favs;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
