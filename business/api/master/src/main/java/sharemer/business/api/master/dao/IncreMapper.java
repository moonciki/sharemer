package sharemer.business.api.master.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Create by 18073 on 2018/12/14.
 */
@Mapper
public interface IncreMapper {

    void update(@Param("incre")Long incre);

    Long getIncre();
}
