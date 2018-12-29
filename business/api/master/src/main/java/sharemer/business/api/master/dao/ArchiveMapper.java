package sharemer.business.api.master.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import sharemer.business.api.master.po.Archive;
import sharemer.business.api.master.vo.ArchiveVo;

import java.util.List;

/**
 * Create by 18073 on 2018/12/15.
 */
@Mapper
public interface ArchiveMapper {

    void insert(@Param("pojo") Archive archive);

    void update(@Param("pojo") Archive archive);

    List<Integer> getArchivesByUid(@Param("uid") Integer uid, @Param("sort") Integer sort,
                                 @Param("page") Integer page);

    List<ArchiveVo> getBaseArchiveVo(@Param("ids") List<Integer> ids);

    ArchiveVo getArchivePlayInfo(@Param("archive_id") Integer archive_id);

    List<Integer> getArchivesByKey(@Param("key") String key, @Param("sort") Integer sort,
                                 @Param("page") Integer page);

}
