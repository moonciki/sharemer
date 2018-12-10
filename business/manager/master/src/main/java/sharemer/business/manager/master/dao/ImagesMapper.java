package sharemer.business.manager.master.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import sharemer.business.manager.master.po.Images;

/**
 * Create by 18073 on 2018/12/10.
 */
@Mapper
public interface ImagesMapper {

    void insert(@Param("pojo") Images images);

    Images getOneByOriginUrl(@Param("origin_url") String origin_url);

}
