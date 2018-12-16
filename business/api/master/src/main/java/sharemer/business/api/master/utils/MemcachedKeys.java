package sharemer.business.api.master.utils;

/**
 * Created by 18073 on 2017/5/8.
 */
public class MemcachedKeys {

    public static String getAppSeason(int userId) {
        return String.format("user_info:%d", userId);
    }

    /**
     * 音乐区二级页tag下musicIds
     */
    public static String getMusicIdsByTagId(Integer tagId) {
        return String.format("m:i:t:%d", tagId);
    }

    /**
     * 个人捐赠音乐序列id，type传0按时间正序，传1按时间倒序
     */
    public static String getMusicIdsByUid(Integer uid, Integer sort, Integer p) {
        return String.format("m:u:u_id:%d:sort:%d:c_p:%d", uid, sort, p);
    }

    /**
     * 个人捐赠视频序列id，type传0按时间正序，传1按时间倒序
     */
    public static String getVideoIdsByUid(Integer uid, Integer sort, Integer p) {
        return String.format("v:u:u_id:%d:sort:%d:c_p:%d", uid, sort, p);
    }

    /**
     * 个人投稿音频序列id，type传0按时间正序，传1按时间倒序
     */
    public static String getArchiveIdsByUid(Integer uid, Integer sort, Integer p) {
        return String.format("a:u:u_id:%d:sort:%d:c_p:%d", uid, sort, p);
    }

    /**
     * 视频区二级页tag下videoIds
     */
    public static String getVideoIdsByTagId(Integer tagId) {
        return String.format("v:i:t:%d", tagId);
    }

    /**
     * 稿件区二级页tag下videoIds
     */
    public static String getArchiveIdsByTagId(Integer tagId) {
        return String.format("a:i:t:%d", tagId);
    }

    /**
     * 收藏单区二级页tag下favIds
     */
    public static String getFavIdsByTagId(Integer tagId) {
        return String.format("f:i:t:%d", tagId);
    }

    /**
     * 管理员登陆状态令牌-value存放管理员id、name
     */
    public static String getAdmin(String token) {
        return String.format("admin_token:%s", token);
    }

    /**
     * music相关元缓存数据
     */
    public static class Music {
        public static String getBaseMusic(Integer musicId) {
            return String.format("b:m:%d", musicId);
        }

        public static String getRelateVideoIds(Integer musicId) {
            return String.format("b:m:%d:v", musicId);
        }
    }

    /**
     * favlist相关元缓存数据
     */
    public static class FavList {
        public static String getBaseFav(Integer favId) {
            return String.format("b:f:%d", favId);
        }
    }

    /**
     * video相关元缓存数据
     */
    public static class Video {
        public static String getBaseVideo(Integer videoId) {
            return String.format("b:v:%d", videoId);
        }
    }

    /**
     * archive相关元缓存数据
     */
    public static class Archive {
        public static String getBaseArchive(Integer archiveId) {
            return String.format("b:a:%d", archiveId);
        }
    }

    /**
     * user相关元缓存数据
     */
    public static class User {
        public static String getBaseUser(Integer userId) {
            return String.format("u:%d", userId);
        }
    }

    /**
     * 根据媒体id和类型获取其对应的tag id list，tag详细信息需要取tag元缓存数据取
     */
    public static class Tag {
        public static String getTagIdsByMediaId(Integer mediaId, Integer type) {
            return String.format("t:%d:m:%d", type, mediaId);
        }

        public static String getTagInfo(Integer tagId) {
            return String.format("tag:%d", tagId);
        }
    }

    /**
     * reply相关元缓存数据
     */
    public static class Reply {
        public static String getBaseReply(Long replyId) {
            return String.format("b:r:%d", replyId);
        }
    }
}
