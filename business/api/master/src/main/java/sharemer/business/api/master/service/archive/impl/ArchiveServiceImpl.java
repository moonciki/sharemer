package sharemer.business.api.master.service.archive.impl;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import sharemer.business.api.master.dao.ArchiveMapper;
import sharemer.business.api.master.dao.TagMapper;
import sharemer.business.api.master.dao.TagMediaMapper;
import sharemer.business.api.master.mao.archive.ArchiveMao;
import sharemer.business.api.master.mao.tag.TagMao;
import sharemer.business.api.master.mao.user.UserMao;
import sharemer.business.api.master.po.Archive;
import sharemer.business.api.master.po.Tag;
import sharemer.business.api.master.po.TagMedia;
import sharemer.business.api.master.po.User;
import sharemer.business.api.master.ro.ArchiveParam;
import sharemer.business.api.master.service.archive.ArchiveService;
import sharemer.business.api.master.service.favlist.FavListService;
import sharemer.business.api.master.service.tag.TagService;
import sharemer.business.api.master.utils.Constant;
import sharemer.business.api.master.utils.Page;
import sharemer.business.api.master.vo.ArchiveVo;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Create by 18073 on 2018/12/15.
 */
@Service
public class ArchiveServiceImpl implements ArchiveService {

    private Logger logger = LoggerFactory.getLogger(ArchiveServiceImpl.class);

    @Resource
    private ArchiveMapper archiveMapper;

    @Resource
    private TagMapper tagMapper;

    @Resource
    private TagMediaMapper tagMediaMapper;

    @Resource
    private ArchiveMao archiveMao;

    @Resource
    private TagMao tagMao;

    @Resource
    private TagService tagService;

    @Resource
    private FavListService favListService;

    @Resource
    private UserMao userMao;

    @Override
    public Page<ArchiveVo> getArchivesByTag(Integer tagId, Integer page, Integer pageSize) {
        Page<ArchiveVo> result = new Page(page, pageSize);
        /** 首先获取该tag下所有的musicId*/
        List<Integer> archiveIds = this.tagMao.getArchiveIdsByTagId(tagId);
        if (archiveIds != null && archiveIds.size() > 0) {
            result.setTotalCount(archiveIds.size());

            int from = 0, to = 0;
            if (page > 0 && pageSize > 0) {
                from = (page - 1) * pageSize;
                to = page * pageSize;
            }

            if (to > 0 && from < archiveIds.size()) {
                /** 截取当前页的musicId*/
                List<Integer> ids = archiveIds.subList(from, to > archiveIds.size() ? archiveIds.size() : to);
                /** 需要回源的musicIds*/
                List<Integer> needDb = Lists.newArrayList();
                List<ArchiveVo> currentArchives = Lists.newArrayList();
                ids.forEach(id -> {
                    ArchiveVo videoVo = this.archiveMao.getBaseOneWithoutDb(id);
                    if (videoVo != null) {
                        currentArchives.add(videoVo);
                    } else {
                        needDb.add(id);
                    }
                });

                if (needDb.size() > 0) {
                    List<ArchiveVo> fill = this.archiveMao.setBaseArchives(needDb);
                    if (fill != null && fill.size() > 0) {
                        /** 回源成功，将回源后的结果并入结果集*/
                        currentArchives.addAll(fill);
                    }
                }

                /** 排序、封装*/
                List<ArchiveVo> finalResult = currentArchives.stream().sorted((m1, m2) -> {
                    if (m1 != null && m2 != null && m1.getMtime() != null && m2.getMtime() != null) {
                        return m1.getMtime().isAfter(m2.getMtime()) ? -1 : 1;
                    } else {
                        return 0;
                    }
                }).collect(Collectors.toList());

                result.setRecords(finalResult);
            }
        }

        return result;
    }

    @Override
    public void addArchive(ArchiveParam archiveParam) {

        Archive archive = archiveParam.getArchive();
        archiveMapper.insert(archive);

        List<String> tagNames = archiveParam.getStyle_tag();
        if (tagNames == null) {
            tagNames = Lists.newArrayList();
        }
        if (!Strings.isNullOrEmpty(archiveParam.getTags())) {
            String[] tgs = archiveParam.getTags().split(",");
            tagNames.addAll(Arrays.asList(tgs));
        }

        if (tagNames.size() > 0) {
            Map<String, Tag> tags = tagMapper.getTagsByNames(tagNames);
            if (tags == null) {
                tags = Maps.newHashMap();
            }
            List<TagMedia> relateTags = Lists.newArrayList();
            for (String t : tagNames) {
                TagMedia tagMedia = new TagMedia();
                tagMedia.setMedia_id(archive.getId());
                tagMedia.setType(Constant.TagMedia.GAOJIAN_TYPE);
                Tag originTag = tags.get(t);
                if (originTag == null) {
                    originTag = new Tag();
                    originTag.setTag_name(t);
                    tagMapper.insert(originTag);
                    tagMedia.setTag_id(originTag.getId());
                } else {
                    tagMedia.setTag_id(originTag.getId());
                }
                relateTags.add(tagMedia);
            }
            if (relateTags.size() > 0) {
                tagMediaMapper.insertsMediaTag(archive.getId() % Constant.TagMedia.TABLE_TOTAL, relateTags);
                relateTags.forEach(rt -> tagMediaMapper.insertTagMedia(rt.getTag_id() % Constant.TagMedia.TABLE_TOTAL, rt));
            }
        }
    }

    @Override
    public List<ArchiveVo> getArchivesByUid(Integer uid, Integer sort, Integer c_p) {
        List<Integer> ids = this.archiveMao.getArchiveIdsByUid(uid, sort, c_p);
        if (ids != null && ids.size() > 0) {
            List<ArchiveVo> result = this.getArchivesByCache(ids);
            result = this.sortList(sort, result);
            return result;
        }
        return null;
    }

    @Override
    public ArchiveVo getArchiveInfo(Integer archiveId, User user) {
        ArchiveVo archiveVo = archiveMao.getBaseOne(archiveId);
        if(archiveVo != null){
            ArchiveVo playInfo = archiveMao.getDetailOne(archiveId);
            if(playInfo != null){
                archiveVo.setDesc(playInfo.getDesc());
                archiveVo.setFile(playInfo.getFile());
            }
            archiveVo.setUser(this.userMao.getBaseOne(archiveVo.getUser_id()));
            List<Tag> tags = this.tagService.getTagsByMediaId(
                    archiveId, Constant.TagMedia.GAOJIAN_TYPE);
            archiveVo.setTags(tags);
            if(user != null){
                archiveVo.setIs_faved(favListService.isFaved(Constant.TagMedia.PV_TYPE, archiveId, user.getId()) ? 1 : 0);
            }
            return archiveVo;
        }
        return null;
    }

    private List<ArchiveVo> sortList(int sort, List<ArchiveVo> result) {
        final int sorted = sort;
        return result.stream()
                .sorted((m1, m2) -> {
                    if (m1 != null && m2 != null && m1.getCtime() != null && m2.getCtime() != null) {
                        if (sorted == 1) {
                            return m1.getCtime().isAfter(m2.getCtime()) ? -1 : 1;
                        } else {
                            return m1.getCtime().isAfter(m2.getCtime()) ? 1 : -1;
                        }
                    } else {
                        return 0;
                    }
                }).collect(Collectors.toList());
    }

    private List<ArchiveVo> getArchivesByCache(List<Integer> ids) {
        /** 需要回源的musicIds*/
        List<Integer> needDb = Lists.newArrayList();
        List<ArchiveVo> currentArchives = Lists.newArrayList();
        ids.forEach(id -> {
            ArchiveVo videoVo = this.archiveMao.getBaseOneWithoutDb(id);
            if (videoVo != null) {
                currentArchives.add(videoVo);
            } else {
                needDb.add(id);
            }
        });

        if (needDb.size() > 0) {
            List<ArchiveVo> fill = this.archiveMao.setBaseArchives(needDb);
            if (fill != null && fill.size() > 0) {
                /** 回源成功，将回源后的结果并入结果集*/
                currentArchives.addAll(fill);
            }
        }

        return currentArchives;
    }
}
