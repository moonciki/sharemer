package sharemer.business.api.master.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import sharemer.business.api.master.po.Archive;

/**
 * Create by 18073 on 2018/12/15.
 */
@Mapper
public interface ArchiveMapper {

    void insert(@Param("pojo") Archive archive);

    void update(@Param("pojo") Archive archive);

}
