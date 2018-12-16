package sharemer.business.manager.master.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import sharemer.business.manager.master.po.Archive;
import sharemer.business.manager.master.vo.ArchiveVo;

import java.util.List;

/**
 * Create by 18073 on 2018/12/15.
 */
@Mapper
public interface ArchiveMapper {

    void insert(@Param("pojo") Archive archive);

    void update(@Param("pojo") Archive archive);

    Integer getAllCount(@Param("key") String key, @Param("type") Integer type);

    List<ArchiveVo> getAll(@Param("key") String key, @Param("type") Integer type, @Param("start") Integer start, @Param("size") Integer size);

}
