package sharemer.business.api.master.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 18073 on 2017/5/21.
 */
public class Constant {

    /** 爬虫相关常量
    视频源logo和网站名称*/
    public static Map<Integer, String> videoLogos = new HashMap<>();
    public static Map<Integer, String> videoNetName = new HashMap<>();
    public static Map<Integer, String> videoColor = new HashMap<>();

    static {
        videoLogos.put(0, "http://static.hdslb.com/images/favicon.ico");
        videoLogos.put(1, "http://cdn.aixifan.com/ico/favicon.ico");
        videoLogos.put(2, "http://www.tucao.tv/favicon.ico");
        videoLogos.put(3, "http://static.youku.com/v1.0.166/index/img/favicon.ico");
        videoLogos.put(4, "http://www.qiyipic.com/common/images/logo.ico");
        videoLogos.put(5, "http://www.tudou.com/favicon.ico");
        videoLogos.put(6, "https://ss3.bdstatic.com/lPoZeXSm1A5BphGlnYG/icon/95603.png");
        videoLogos.put(7, "http://www.le.com/favicon.ico");
        videoLogos.put(8, "https://ss3.bdstatic.com/iPoZeXSm1A5BphGlnYG/icon/7183.png");
        videoLogos.put(9, "http://www.missevan.com/images/index/favicon.ico");
        videoLogos.put(10, "https://v.qq.com/favicon.ico");
        videoLogos.put(11, "http://www.wasu.cn/favicon.ico");
        videoLogos.put(12, "https://ss3.bdstatic.com/lPoZeXSm1A5BphGlnYG/icon/95515.png");
        videoLogos.put(13, "https://ss2.bdstatic.com/lfoZeXSm1A5BphGlnYG/icon/95574.png");
        videoLogos.put(14, "https://ss2.bdstatic.com/kfoZeXSm1A5BphGlnYG/icon/9898.png");
        videoLogos.put(15, "https://ss1.bdstatic.com/kvoZeXSm1A5BphGlnYG/icon/9609.png");

        videoNetName.put(0, "哔哩哔哩");
        videoNetName.put(1, "AcFun");
        videoNetName.put(2, "TuCao");
        videoNetName.put(3, "优酷");
        videoNetName.put(4, "爱奇艺");
        videoNetName.put(5, "土豆网");
        videoNetName.put(6, "56网");
        videoNetName.put(7, "乐视TV");
        videoNetName.put(8, "网易视频");
        videoNetName.put(9, "MissEvan");
        videoNetName.put(10, "腾讯视频");
        videoNetName.put(11, "华数TV");
        videoNetName.put(12, "音悦台");
        videoNetName.put(13, "搜狐视频");
        videoNetName.put(14, "新浪视频");
        videoNetName.put(15, "PPTV");

        videoColor.put(0, "#ff8eb3");
        videoColor.put(1, "#fd4c5d");
        videoColor.put(2, "#FF1493");
        videoColor.put(3, "#4876FF");
        videoColor.put(4, "#66CD00");
        videoColor.put(5, "#FFA500");
        videoColor.put(6, "#FF4040");
        videoColor.put(7, "#EE3A8C");
        videoColor.put(8, "#EE0000");
        videoColor.put(9, "#404040");
        videoColor.put(10, "#EE9A00");
        videoColor.put(11, "#EEB422");
        videoColor.put(12, "#9ACD32");
        videoColor.put(13, "#EE4000");
        videoColor.put(14, "#63B8FF");
        videoColor.put(15, "#4876FF");
    }

    public static String LOGIN_TOKEN = "user-token";

    public static String LOGIN_USER = "user_info";

    public static Integer LOGIN_TIME = 30*24*60*60;

    public static String LOGIN_URL = "/login";

    public static String FILTER_VIDEO_TAG = "音乐";

    public static String getBUrl(Integer avId){
        return String.format("http://player.bilibili.com/player.html?aid=%d&page=1", avId);
    }

    public static String getAUrl(Integer acId, String vid){
        return String.format("http://cdn.aixifan.com/player/ACFlashPlayer.out.swf?vid=%s&ref=http://www.acfun.cn/v/ac%d", vid, acId);
    }

    public static String getMUrl(Integer mId){
        return String.format("http://www.missevan.com/soundiframe/%d", mId);
    }

    public static String getCUrl(Integer cId){
        return String.format("http://www.tucao.tv/mini/%d.swf", cId);
    }


    /** ES相关常量*/
    public final static Integer ES_FAIL_CODE = 1;
    public final static Integer ES_SUCCESS_CODE = 0;

    public static String getEsMediaId(Integer type, Integer id){
        String esid;
        switch (type){
            case 0:
                esid = String.format("m:%d", id);
                break;
            case 1:
                esid = String.format("v:%d", id);
                break;
            case 2:
                esid = String.format("g:%d", id);
                break;
            default:
                esid = String.format("d:%d", id);
                break;
        }
        return esid;
    }

    public static class Danmaku{
        /** 分表个数*/
        public static final Integer TABLE_TOTAL = 10;
    }

    public static class TagMedia{
        /** 音乐*/
        public static final Integer MUSIC_TYPE = 0;
        /** 视频*/
        public static final Integer PV_TYPE = 1;
        /** 稿件*/
        public static final Integer GAOJIAN_TYPE = 2;
        /** 收藏单*/
        public static final Integer FAV_LIST_TYPE = 3;
        /** 分表个数*/
        public static final Integer TABLE_TOTAL = 10;
    }

    public static class User{

        /** 是否是自己的资源*/
        public static final Integer SELF = 1;
        public static final Integer NOT_SELF = 0;

        public static String getLevelColor(Integer level){
            String color;
            switch (level){
                case 1:
                    color = "#00EEEE";
                    break;
                case 2:
                    color = "#76EEC6";
                    break;
                case 3:
                    color = "#9ACD32";
                    break;
                case 4:
                    color = "#CDCD00";
                    break;
                case 5:
                    color = "#EEC900";
                    break;
                case 6:
                    color = "#EEAD0E";
                    break;
                case 7:
                    color = "#EE7942";
                    break;
                case 8:
                    color = "#CD6839";
                    break;
                case 9:
                    color = "#FF1493";
                    break;
                case 10:
                    color = "#D02090";
                    break;
                case 11:
                    color = "#B03060";
                    break;
                case 12:
                    color = "#8B2252";
                    break;
                default:
                    color = "#8B0000";
                    break;
            }
            return color;
        }
    }

    public static class Video{
        /** 播放器嵌入方式，0：flash嵌入；1：embed嵌入；2：iframe嵌入*/
        public static final Integer EMBED_FLASH = 0;
        public static final Integer EMBED_EMBED = 1;
        public static final Integer EMBED_IFRAME = 2;
    }

    public static class FavList{
        /** 公有收藏夹*/
        public static final Integer COMMON = 0;
        /** 私有收藏夹*/
        public static final Integer PRIVATE = 1;
        /** 关系表分表个数*/
        public static final Integer TABLE_TOTAL = 10;
    }

    public static class Reply{
        /** 按照origin_id查询，路由表类型*/
        public static final Integer O_TYPE = 0;
        /** 按照user_id查询，路由表类型*/
        public static final Integer U_TYPE = 1;

        /** 分表数量*/
        public static final Integer TABLE_TOTAL = 10;
        /** 倒序排序*/
        public static final Integer D_SORT = 0;
        /** 正序排序*/
        public static final Integer Z_SORT = 1;

        /** 顶*/
        public static final Integer LIKE = 1;
        public static final Integer CANCEL_LIKE = 0;
        /** 踩*/
        public static final Integer DISLIKE = -1;
        public static final Integer CANCEL_DISLIKE = -2;
    }

    public static class Media{
        public static final int M_TYPE = 0;
        public static final int V_TYPE = 1;
        public static final int G_TYPE = 2;
        public static final int F_TYPE = 3;

        public static boolean rightType(Integer type){
            return type != null &&
                    (type.equals(M_TYPE)
                            || type.equals(V_TYPE)
                            || type.equals(G_TYPE)
                            || type.equals(F_TYPE));
        }
    }
}
