package sharemer.business.api.master.service.archive;

import sharemer.business.api.master.po.User;
import sharemer.business.api.master.ro.ArchiveParam;
import sharemer.business.api.master.utils.Page;
import sharemer.business.api.master.vo.ArchiveVo;

import java.util.List;

/**
 * Create by 18073 on 2018/12/15.
 */
public interface ArchiveService {

    Page<ArchiveVo> getArchivesByTag(Integer tagId, Integer page, Integer pageSize);

    void addArchive(ArchiveParam archiveParam);

    List<ArchiveVo> getArchivesByUid(Integer uid, Integer sort, Integer c_p);

    ArchiveVo getArchiveInfo(Integer archiveId, User user);

}
