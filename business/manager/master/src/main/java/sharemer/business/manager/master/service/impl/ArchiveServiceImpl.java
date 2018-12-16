package sharemer.business.manager.master.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import sharemer.business.manager.master.dao.ArchiveMapper;
import sharemer.business.manager.master.dao.Page;
import sharemer.business.manager.master.dao.TagMapper;
import sharemer.business.manager.master.po.Archive;
import sharemer.business.manager.master.po.TagMedia;
import sharemer.business.manager.master.service.ArchiveService;
import sharemer.business.manager.master.utils.Constant;
import sharemer.business.manager.master.vo.ArchiveVo;

import javax.annotation.Resource;
import java.util.List;

/**
 * Create by 18073 on 2018/12/15.
 */
@Service
public class ArchiveServiceImpl implements ArchiveService {

    @Resource
    private ArchiveMapper archiveMapper;

    @Resource
    private TagMapper tagMapper;

    @Override
    public void passArchive(Integer archiveId, Integer status) {
        Archive archive = new Archive();
        archive.setId(archiveId);
        archive.setStatus(status);
        archiveMapper.update(archive);
    }

    @Override
    public List<ArchiveVo> getAllArchive(String key, Integer type, Page<ArchiveVo> page) {
        key = StringUtils.isEmpty(key) ? null : key;
        page.setRecords(this.archiveMapper.getAll(key, type,
                (page.getPageNo() - 1) * page.getPageSize(), page.getPageSize()));
        page.setTotalCount(this.archiveMapper.getAllCount(key, type));
        page.getRecords().forEach(videoVo -> {
            /** 填充tag*/
            videoVo.setTags(this.tagMapper.getTagByMediaId(videoVo.getId() % 10,
                    videoVo.getId(), TagMedia.GAOJIAN_TYPE));
        });
        return page.getRecords();
    }
}
