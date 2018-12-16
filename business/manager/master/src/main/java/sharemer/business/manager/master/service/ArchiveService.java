package sharemer.business.manager.master.service;

import sharemer.business.manager.master.dao.Page;
import sharemer.business.manager.master.vo.ArchiveVo;

import java.util.List;

/**
 * Create by 18073 on 2018/12/15.
 */
public interface ArchiveService {

    void passArchive(Integer archiveId, Integer status);

    List<ArchiveVo> getAllArchive(String key, Integer type, Page<ArchiveVo> page);

}
