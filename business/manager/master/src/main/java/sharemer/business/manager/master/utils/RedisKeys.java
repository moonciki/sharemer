package sharemer.business.manager.master.utils;

/**
 * Created by 18073 on 2017/10/29.
 */
public class RedisKeys {

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

}