package sharemer.business.api.master.mao.favlist;

import sharemer.business.api.master.po.FavList;

import java.util.List;

/**
 * Created by 18073 on 2017/9/29.
 */
public interface FavListMao {

    FavList getBaseOneWithoutDb(Integer favId);

    FavList getBaseOne(Integer favId);

    List<FavList> setBaseFavLists(List<Integer> ids);

}
