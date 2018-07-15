package sharemer.business.api.master.utils;

/**
 * 存放所有的redis key
 */
public class RedisKeys {

    /** redis回源标记*/
    public static String getSourceKey(String key){
        return String.format("source_%s", key);
    }

    /** music相关redis结构key*/
    public static class Music{
        public static String getStrKey(){
            return "r:s";
        }
    }

    /** 用户相关redis结构key*/
    public static class User{
        /** csrf令牌*/
        public static String getCsrfTokenKey(){
            return "csrf_token";
        }
        /** 登录令牌*/
        public static String getLoginTokenKey(String token) {
            return String.format("login_tokens:%s", token);
        }
    }

    /** 评论相关redis结构*/
    public static class Reply{
        /** 生成reply的real_id*/
        public static String getRealId(){
            return "reply_real_id";
        }
        /** 数据源下面的评论序列*/
        public static String getReplyIdsByOId(Integer oid, Integer otype){
            return String.format("r_ids:o_%d:t_%d", oid, otype);
        }
        /** 用户评论序列*/
        public static String getReplyIdsByUid(Integer uid, Integer otype){
            return String.format("r_ids:u_%d:t_%d", uid, otype);
        }
        /** 评论下面的子评论*/
        public static String getChildRepliesByOId(Integer oid, Long reply_id, Integer otype){
            return String.format("r_c_ids:o_%d:c_%d:t_%d", oid, reply_id, otype);
        }
        /** 用户点赞序列*/
        public static String getLikeReplyIds(Integer userId){
            return String.format("r_l:%d", userId);
        }
        /** 用户踩序列*/
        public static String getDisLikeReplyIds(Integer userId){
            return String.format("r_dl:%d", userId);
        }
    }

    /** 收藏夹相关redis结构*/
    public static class FavList{
        /** 用户下的收藏夹序列*/
        public static String getFavListByUserId(Integer userId){
            return String.format("u_f:fid:%d", userId);
        }


        /** 收藏夹下音乐资源序列*/
        public static String getMusicListByFavId() {
            return "u_f_m:mid_v2:%d";
        }

        /** 收藏夹下视频资源序列*/
        public static String getVideoListByFavId() {
            return "u_f_v:vid_v2:%d";
        }

        /** 收藏夹下稿件资源序列*/
        public static String getSubListByFavId() {
            return "u_f_s:sid_v2:%d";
        }
    }

    /** 写入动作触发次数限制*/
    public static class Trigger{
        /** 赞/踩动作计数*/
        public static String getActionTrigger(Integer user_id){
            return String.format("trg_dc:%d", user_id);
        }
    }

}
