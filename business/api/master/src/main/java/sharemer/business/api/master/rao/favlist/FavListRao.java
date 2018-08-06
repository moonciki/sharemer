package sharemer.business.api.master.rao.favlist;

import java.util.List;
import java.util.Set;

/**
 * Created by 18073 on 2017/9/30.
 */
public interface FavListRao {

    void addFavsByUserId(Integer fid, Integer uid);

    List<Integer> getFavsByUserId(Integer uid);

    void addMediasByFavId(String key, Integer mediaId);

    boolean isMemeberByUserId(String key, Integer oid);

    Integer getMusicCount(Integer fid);

    Integer getVideoCount(Integer fid);

    Integer getSubCount(Integer fid);

    Set<String> getMusicIds(Integer fid);

    Set<String> getVideoIds(Integer fid);

}
