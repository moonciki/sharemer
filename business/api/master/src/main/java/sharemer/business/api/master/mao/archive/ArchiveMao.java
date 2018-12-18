package sharemer.business.api.master.mao.archive;

import sharemer.business.api.master.vo.ArchiveVo;

import java.util.List;

/**
 * Create by 18073 on 2018/12/15.
 */
public interface ArchiveMao {

    List<Integer> getArchiveIdsByUid(Integer uid, Integer sort, Integer p);

    ArchiveVo getBaseOneWithoutDb(Integer archiveId);

    ArchiveVo getBaseOne(Integer archiveId);

    ArchiveVo getDetailOne(Integer archiveId);

    List<ArchiveVo> setBaseArchives(List<Integer> ids);
}
