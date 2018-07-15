package sharemer.business.api.master.mao.favlist.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import sharemer.business.api.master.dao.FavListMapper;
import sharemer.business.api.master.mao.favlist.FavListMao;
import sharemer.business.api.master.po.FavList;
import sharemer.business.api.master.utils.MemcachedKeys;
import sharemer.component.memcache.client.ShareMerMemcacheClient;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * Created by 18073 on 2017/9/29.
 */
@Repository
public class FavListMaoImpl implements FavListMao {

    private Logger logger = LoggerFactory.getLogger(FavListMaoImpl.class);

    @Resource
    private FavListMapper favListMapper;

    @Resource
    private ShareMerMemcacheClient shareMerMemcacheClient;

    /** 无回源调用*/
    @Override
    public FavList getBaseOneWithoutDb(Integer favId) {
        return shareMerMemcacheClient.get(MemcachedKeys.FavList.getBaseFav(favId));
    }

    /** 获取favlist元缓存数据*/
    @Override
    public FavList getBaseOne(Integer favId) {
        try{
            return shareMerMemcacheClient.get(MemcachedKeys.FavList.getBaseFav(favId), ()->{
                FavList favList = this.favListMapper.getBaseOne(favId);
                return favList != null ? favList : FavList.EMPTY;
            });
        }catch (Exception e){
            logger.error("favlist get_base_one error! fav_id={}", favId);
        }
        return FavList.EMPTY;
    }

    /** 批量回源*/
    @Override
    public List<FavList> setBaseFavLists(List<Integer> ids) {
        try{
            if(ids != null && ids.size() > 0){
                List<FavList> result = this.favListMapper.getBaseFavs(ids);
                if(result != null){
                    result.forEach(m->{
                        shareMerMemcacheClient.set(MemcachedKeys.FavList.getBaseFav(m.getId()), m);
                    });
                }
                return result;
            }
        }catch (Exception e){
            logger.error("set base favs error ! ids={}", ids.toString());
        }
        return Collections.emptyList();
    }
}
