package sharemer.business.api.master.mao.archive.impl;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import sharemer.business.api.master.dao.ArchiveMapper;
import sharemer.business.api.master.mao.archive.ArchiveMao;
import sharemer.business.api.master.utils.MemcachedKeys;
import sharemer.business.api.master.vo.ArchiveVo;
import sharemer.component.memcache.client.ShareMerMemcacheClient;

import javax.annotation.Resource;
import java.util.List;

/**
 * Create by 18073 on 2018/12/15.
 */
@Repository
public class ArchiveMaoImpl implements ArchiveMao {

    private Logger logger = LoggerFactory.getLogger(ArchiveMaoImpl.class);

    @Resource
    private ShareMerMemcacheClient shareMerMemcacheClient;

    @Resource
    private ArchiveMapper archiveMapper;

    @Override
    public List<Integer> getArchiveIdsByUid(Integer uid, Integer sort, Integer p) {
        try {
            return shareMerMemcacheClient.get(MemcachedKeys.getArchiveIdsByUid(uid, sort, p), () -> {
                List<Integer> ids = this.archiveMapper.getArchivesByUid(uid, sort, p);
                if (ids != null && ids.size() > 0) {
                    return ids;
                } else {
                    return null;
                }
            });
        } catch (Exception e) {
            logger.error("tag get_archive_ids_by_user_id error! uid={}, sort={}, p={}", uid, sort, p);
        }
        return null;
    }

    @Override
    public ArchiveVo getBaseOneWithoutDb(Integer archiveId) {
        return shareMerMemcacheClient.get(MemcachedKeys.Archive.getBaseArchive(archiveId));
    }

    /**
     * 获取video元缓存数据
     */
    @Override
    public ArchiveVo getBaseOne(Integer archiveId) {
        try {
            return shareMerMemcacheClient.get(MemcachedKeys.Archive.getBaseArchive(archiveId), () -> {
                List<Integer> ids = Lists.newArrayList();
                ids.add(archiveId);
                List<ArchiveVo> archiveVos = this.archiveMapper.getBaseArchiveVo(ids);
                if (archiveVos != null && archiveVos.size() > 0) {
                    return archiveVos.get(0);
                }
                return null;
            });
        } catch (Exception e) {
            logger.error("archive get_base_one archive_id = {}", archiveId);
        }
        return null;
    }

    @Override
    public ArchiveVo getDetailOne(Integer archiveId) {
        try {
            return shareMerMemcacheClient.get(MemcachedKeys.Archive.getDetailArchive(archiveId),
                    () -> this.archiveMapper.getArchivePlayInfo(archiveId));
        } catch (Exception e) {
            logger.error("archive get_detail_one archive_id = {}", archiveId);
        }
        return null;
    }

    @Override
    public List<ArchiveVo> setBaseArchives(List<Integer> ids) {
        if (ids != null && ids.size() > 0) {
            List<ArchiveVo> result = this.archiveMapper.getBaseArchiveVo(ids);
            if (result != null) {
                result.forEach(a -> shareMerMemcacheClient.set(MemcachedKeys.Video.getBaseVideo(a.getId()), a));
            }
            return result;
        }
        return null;
    }

}
