package sharemer.business.manager.master.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 18073 on 2017/5/21.
 */
public class Constant {

    /** 视频源logo和网站名称*/
    public static Map<Integer, String> videoLogos = new HashMap<>();
    public static Map<Integer, String> videoNetName = new HashMap<>();

    public static int TOTAL_TABLE_NUM = 10;

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

        videoNetName.put(0, "BiLiBiLi");
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
    }

    public static String LOGIN_TOKEN = "haha-token";

    public static String LOGIN_URL = "/login";

    public static String FILTER_VIDEO_TAG = "音乐";

    public static class TagMedia{
        /** 音乐*/
        public static final int MUSIC_TYPE = 0;
        /** 视频*/
        public static final int PV_TYPE = 1;
        /** 稿件*/
        public static final int GAOJIAN_TYPE = 2;
        /** 收藏单*/
        public static final int FAV_LIST_TYPE = 3;
        /** 网易音单*/
        public static final int MUSIC_LIST_TYPE = 4;
    }

    public static class Video{

        public static final int TYPE_BILIBILI = 0;
        public static final int TYPE_ACFUN = 1;
        public static final int TYPE_TUCAO = 2;
        public static final Integer TYPE_YOUKU = 3;
        public static final Integer TYPE_IQY = 4;
        public static final Integer TYPE_TUDOU = 5;
        public static final Integer TYPE_56 = 6;
        public static final Integer TYPE_LETV = 7;
        public static final Integer TYPE_163 = 8;
        public static final int TYPE_M = 9;
        public static final Integer TYPE_QQ = 10;
        public static final Integer TYPE_HUASHU = 11;
        public static final int TYPE_MTV = 12;
        public static final Integer TYPE_SOHU = 13;
        public static final Integer TYPE_SINA = 14;
        public static final Integer TYPE_PPTV = 15;

        public static final Integer EMBED_FLASH = 0;
        public static final Integer EMBED_EMBED = 1;
        public static final Integer EMBED_IFRAME = 2;

        public static String getBUrl(Integer avId){
            return String.format("http://player.bilibili.com/player.html?aid=%d&page=1", avId);
        }

        public static String getAUrl(Integer acId, String vid){
            return String.format("http://cdn.aixifan.com/player/ACFlashPlayer.out.swf?vid=%s&ref=http://www.acfun.cn/v/ac%d", vid, acId);
        }

        public static String getMUrl(Integer mId){
            return String.format("http://www.missevan.com/soundiframe/%d", mId);
        }

        public static String getMtv(Integer id){
            return String.format("http://player.yinyuetai.com/video/player/%d/v_0.swf", id);
        }

        public static String getCUrl(Integer cId){
            return String.format("http://www.tucao.tv/mini/%d.swf", cId);
        }
    }
}
