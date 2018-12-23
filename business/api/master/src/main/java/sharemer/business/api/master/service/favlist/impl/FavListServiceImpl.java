package sharemer.business.api.master.service.favlist.impl;

import com.google.common.collect.Lists;
import sharemer.business.api.master.dao.FavListMapper;
import sharemer.business.api.master.dao.FavMediaMapper;
import sharemer.business.api.master.dto.ArchiveIndex;
import sharemer.business.api.master.dto.MusicIndex;
import sharemer.business.api.master.dto.VideoIndex;
import sharemer.business.api.master.exception.BusinessRollBackException;
import sharemer.business.api.master.mao.archive.ArchiveMao;
import sharemer.business.api.master.mao.favlist.FavListMao;
import sharemer.business.api.master.mao.music.MusicMao;
import sharemer.business.api.master.mao.tag.TagMao;
import sharemer.business.api.master.mao.user.UserMao;
import sharemer.business.api.master.mao.video.VideoMao;
import sharemer.business.api.master.po.FavList;
import sharemer.business.api.master.po.FavMedia;
import sharemer.business.api.master.po.Tag;
import sharemer.business.api.master.po.User;
import sharemer.business.api.master.rao.favlist.FavListRao;
import sharemer.business.api.master.service.tag.TagService;
import sharemer.business.api.master.service.favlist.FavListService;
import sharemer.business.api.master.utils.Constant;
import sharemer.business.api.master.utils.Page;
import sharemer.business.api.master.utils.RedisKeys;
import sharemer.business.api.master.vo.ArchiveVo;
import sharemer.business.api.master.vo.FavListInfoVo;
import sharemer.business.api.master.vo.MusicVo;
import org.springframework.stereotype.Service;
import sharemer.business.api.master.vo.VideoVo;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by 18073 on 2017/9/30.
 */
@Service("favListService")
public class FavListServiceImpl implements FavListService {

    @Resource
    private FavListMapper favListMapper;

    @Resource
    private FavMediaMapper favMediaMapper;

    @Resource
    private FavListRao favListRao;

    @Resource
    private FavListMao favListMao;

    @Resource
    private TagMao tagMao;

    @Resource
    private TagService tagService;

    @Resource
    private MusicMao musicMao;

    @Resource
    private UserMao userMao;

    @Resource
    private VideoMao videoMao;

    @Resource
    private ArchiveMao archiveMao;

    @Override
    public void addFavList(FavList favList) {
        favListMapper.insert(favList);
        favListRao.addFavsByUserId(favList.getId(), favList.getUser_id());
    }

    @Override
    public FavList getFavListById(Integer fid) {
        return favListMao.getBaseOne(fid);
    }

    @Override
    public List<FavList> getFavsByUserId(Integer userId, boolean isSelf) {
        List<FavList> currentFavs = new ArrayList<>();
        /** 首先获取该用户创建的所有的favId*/
        List<Integer> favIds = this.favListRao.getFavsByUserId(userId);
        if (favIds != null && favIds.size() > 0) {
            /** 需要回源的favIds*/
            List<Integer> needDb = new ArrayList<>();
            favIds.forEach(id -> {
                FavList favList = this.favListMao.getBaseOneWithoutDb(id);
                if (favList != null) {
                    currentFavs.add(favList);
                } else {
                    needDb.add(id);
                }
            });

            if (needDb.size() > 0) {
                List<FavList> fill = this.favListMao.setBaseFavLists(needDb);
                if (fill != null && fill.size() > 0) {
                    /** 回源成功，将回源后的结果并入结果集*/
                    currentFavs.addAll(fill);
                }
            }

            if (!isSelf) {
                /** 如果当前登录用户和需要获取的用户不一致，那么需要过滤掉私有的收藏夹*/
                return currentFavs.stream().filter(favList ->
                        Constant.FavList.COMMON.equals(favList.getIs_hide())).collect(
                        Collectors.toList());
            }
        }

        return currentFavs;
    }

    @Override
    public void addAllFavListToMedia(Integer oid, Integer otype, List<Integer> favIds) {
        List<FavMedia> favMedias = new ArrayList<>();
        favIds.forEach(favId -> {
            FavMedia favMedia = new FavMedia(oid, otype, favId);
            favMedias.add(favMedia);
            favMediaMapper.insertFavMedia(favId % 10, favMedia);
        });

        favMediaMapper.insertsMediaFav(oid % 10, favMedias);

        /** 为redis添加对应关系*/
        String rkey = null;
        if (Constant.TagMedia.MUSIC_TYPE.equals(otype)) {
            rkey = RedisKeys.FavList.getMusicListByFavId();
        }
        if (Constant.TagMedia.PV_TYPE.equals(otype)) {
            rkey = RedisKeys.FavList.getVideoListByFavId();
        }
        if (Constant.TagMedia.GAOJIAN_TYPE.equals(otype)) {
            rkey = RedisKeys.FavList.getSubListByFavId();
        }
        if (rkey == null) {
            throw new BusinessRollBackException();
        }
        String finalRkey = rkey;
        favIds.forEach(favId -> {
            favListRao.addMediasByFavId(String.format(finalRkey, favId), oid);
        });
    }

    public boolean isFaved(Integer otype, Integer oid, Integer userId) {
        /** 首先找到该user所有的收藏夹*/
        List<Integer> favIds = favListRao.getFavsByUserId(userId);
        if (favIds != null && favIds.size() > 0) {
            String rkey = null;
            if (Constant.TagMedia.MUSIC_TYPE.equals(otype)) {
                rkey = RedisKeys.FavList.getMusicListByFavId();
            }
            if (Constant.TagMedia.PV_TYPE.equals(otype)) {
                rkey = RedisKeys.FavList.getVideoListByFavId();
            }
            if (Constant.TagMedia.GAOJIAN_TYPE.equals(otype)) {
                rkey = RedisKeys.FavList.getSubListByFavId();
            }
            if (rkey == null) {
                throw new BusinessRollBackException();
            }
            final String finalRkey = rkey;
            for (Integer favId : favIds) {
                if (favListRao.isMemeberByUserId(String.format(finalRkey, favId), oid)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Page<FavList> getFavsByTag(Integer tagId, Integer page, Integer pageSize) {
        Page<FavList> result = new Page<FavList>(page, pageSize);
        /** 首先获取该tag下所有的favId*/
        List<Integer> favIds = this.tagMao.getFavIdsByTagId(tagId);
        if (favIds != null && favIds.size() > 0) {
            result.setTotalCount(favIds.size());

            int from = 0, to = 0;
            if (page > 0 && pageSize > 0) {
                from = (page - 1) * pageSize;
                to = page * pageSize;
            }

            if (to > 0 && from < favIds.size()) {
                List<Integer> ids = favIds.subList(from, to > favIds.size() ? favIds.size() : to);
                List<FavList> currentFavs = this.getFavsByCache(ids);
                /** 排序、封装*/
                List<FavList> finalResult = currentFavs.stream().sorted((m1, m2) -> {
                    if (m1 != null && m2 != null && m1.getCtime() != null && m2.getCtime() != null) {
                        return m1.getCtime().isAfter(m2.getCtime()) ? -1 : 1;
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
    public FavListInfoVo getFavListInfo(Integer favId) {
        FavList fav = favListMao.getBaseOne(favId);
        FavListInfoVo vo = new FavListInfoVo();
        vo.setFav(fav);
        List<Tag> tags = this.tagService.getTagsByMediaId(favId, Constant.TagMedia.FAV_LIST_TYPE);
        vo.setTags(tags);
        vo.setM_count(favListRao.getMusicCount(favId));
        vo.setV_count(favListRao.getVideoCount(favId));
        vo.setG_count(favListRao.getSubCount(favId));
        vo.setUser(userMao.getBaseOne(fav.getUser_id()));
        return vo;
    }

    @Override
    public Page<MusicIndex> getMusicsByFavId(Integer favId, Integer pageNo, Integer pageSize) {
        /** 首先获取该fav_list下所有的musicId*/
        Page<MusicIndex> result = new Page<>(pageNo, pageSize);
        Set<String> musicIds = this.favListRao.getMusicIds(favId);

        if (musicIds != null && musicIds.size() > 0) {

            result.setTotalCount(musicIds.size());

            List<String> musicIdArr = Lists.newArrayList(musicIds);
            int from = 0, to = 0;
            if (pageNo > 0 && pageSize > 0) {
                from = (pageNo - 1) * pageSize;
                to = pageNo * pageSize;
            }

            if (to > 0 && from < musicIds.size()) {
                /** 截取当前页的musicId*/
                List<String> ids = musicIdArr.subList(from, to > musicIds.size() ? musicIds.size() : to);
                List<MusicVo> currentMusics = this.getMusicsByCache(ids);
                /** 排序、封装*/
                List<MusicIndex> finalResult = currentMusics.stream()
                        .map(MusicIndex::getMusicIndex).sorted((m1, m2) -> {
                            if (m1 != null && m2 != null && m1.getPublish_time() != null && m2.getPublish_time() != null) {
                                return m1.getPublish_time().isAfter(m2.getPublish_time()) ? -1 : 1;
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
    public Page getVideosByFavId(Integer favId, Integer pageNo, Integer pageSize) {
        /** 首先获取该fav_list下所有的musicId*/
        Page<VideoIndex> result = new Page<VideoIndex>(pageNo, pageSize);
        Set<String> videoIds = this.favListRao.getVideoIds(favId);

        if (videoIds != null && videoIds.size() > 0) {

            result.setTotalCount(videoIds.size());

            List<String> videoIdArr = Lists.newArrayList(videoIds);
            int from = 0, to = 0;
            if (pageNo > 0 && pageSize > 0) {
                from = (pageNo - 1) * pageSize;
                to = pageNo * pageSize;
            }

            if (to > 0 && from < videoIds.size()) {
                /** 截取当前页的musicId*/
                List<String> ids = videoIdArr.subList(from, to > videoIds.size() ? videoIds.size() : to);
                List<VideoVo> currentVideos = this.getVideosByCache(ids);
                /** 排序、封装*/
                List<VideoIndex> finalResult = currentVideos.stream()
                        .map(VideoIndex::getVideoIndex).sorted((m1, m2) -> {
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
    public Page getArchivesByFavId(Integer favId, Integer pageNo, Integer pageSize) {
        /** 首先获取该fav_list下所有的archiveId*/
        Page<ArchiveIndex> result = new Page<>(pageNo, pageSize);
        Set<String> archiveIds = this.favListRao.getArchiveIds(favId);

        if (archiveIds != null && archiveIds.size() > 0) {

            result.setTotalCount(archiveIds.size());

            List<String> archiveIdArr = Lists.newArrayList(archiveIds);
            int from = 0, to = 0;
            if (pageNo > 0 && pageSize > 0) {
                from = (pageNo - 1) * pageSize;
                to = pageNo * pageSize;
            }

            if (to > 0 && from < archiveIds.size()) {
                /** 截取当前页的videoId*/
                List<String> ids = archiveIdArr.subList(from, to > archiveIds.size() ? archiveIds.size() : to);
                List<ArchiveVo> currentArchives = this.getArchivesByCache(ids);
                /** 排序、封装*/
                List<ArchiveIndex> finalResult = currentArchives.stream()
                        .map(ArchiveIndex::getArchiveIndex).peek(a -> {
                            User user = userMao.getBaseOne(a.getUser_id());
                            if (user != null) {
                                a.setUser_name(user.getName());
                            }
                        }).sorted((m1, m2) -> {
                            if (m2 != null && m1.getMtime() != null && m2.getMtime() != null) {
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
    public List<FavList> getFavsByKey(String key, Integer sort, Integer cp) {
        List<Integer> ids = this.favListMapper.getFavsByKey(key, sort, cp);
        if (ids != null && ids.size() > 0) {
            List<FavList> result = this.getFavsByCache(ids);
            result = this.sortList(sort, result);
            return result;
        }
        return null;
    }


    private List<FavList> getFavsByCache(List<Integer> ids) {
        /** 需要回源的musicIds*/
        List<Integer> needDb = new ArrayList<>();
        List<FavList> currentFavs = new ArrayList<>();
        ids.forEach(id -> {
            FavList favList = this.favListMao.getBaseOneWithoutDb(id);
            if (favList != null) {
                currentFavs.add(favList);
            } else {
                needDb.add(id);
            }
        });
        if (needDb.size() > 0) {
            List<FavList> fill = this.favListMao.setBaseFavLists(needDb);
            if (fill != null && fill.size() > 0) {
                /** 回源成功，将回源后的结果并入结果集*/
                currentFavs.addAll(fill);
            }
        }
        return currentFavs;
    }

    private List<FavList> sortList(int sort, List<FavList> result) {
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

    private List<MusicVo> getMusicsByCache(List<String> ids) {
        /** 需要回源的musicIds*/
        List<Integer> needDb = new ArrayList<>();
        List<MusicVo> currentMusics = new ArrayList<>();
        ids.forEach(id -> {
            Integer idInt = Integer.parseInt(id);
            MusicVo musicVo = this.musicMao.getBaseOneWithoutDb(idInt);
            if (musicVo != null) {
                currentMusics.add(musicVo);
            } else {
                needDb.add(idInt);
            }
        });

        if (needDb.size() > 0) {
            List<MusicVo> fill = this.musicMao.setBaseMusics(needDb);
            if (fill != null && fill.size() > 0) {
                /** 回源成功，将回源后的结果并入结果集*/
                currentMusics.addAll(fill);
            }
        }

        return currentMusics;
    }

    private List<VideoVo> getVideosByCache(List<String> ids) {
        /** 需要回源的musicIds*/
        List<Integer> needDb = new ArrayList<>();
        List<VideoVo> currentVideos = new ArrayList<>();
        ids.forEach(id -> {
            Integer idInt = Integer.parseInt(id);
            VideoVo videoVo = this.videoMao.getBaseOneWithoutDb(idInt);
            if (videoVo != null) {
                currentVideos.add(videoVo);
            } else {
                needDb.add(idInt);
            }
        });

        if (needDb.size() > 0) {
            List<VideoVo> fill = this.videoMao.setBaseVideos(needDb);
            if (fill != null && fill.size() > 0) {
                /** 回源成功，将回源后的结果并入结果集*/
                currentVideos.addAll(fill);
            }
        }

        return currentVideos;
    }

    private List<ArchiveVo> getArchivesByCache(List<String> ids) {
        /** 需要回源的archiveIds*/
        List<Integer> needDb = new ArrayList<>();
        List<ArchiveVo> currentArchives = new ArrayList<>();
        ids.forEach(id -> {
            Integer idInt = Integer.parseInt(id);
            ArchiveVo archiveVo = this.archiveMao.getBaseOneWithoutDb(idInt);
            if (archiveVo != null) {
                currentArchives.add(archiveVo);
            } else {
                needDb.add(idInt);
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
