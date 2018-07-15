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

    public static Map<Integer, String> favListDefaultCover = new HashMap<>();

    public static final String QINIU_CDN_DOMAIN = "http://oyjdgrp4g.bkt.clouddn.com";
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


        favListDefaultCover.put(1, QINIU_CDN_DOMAIN+"/static/images/r_f1.jpg");
        favListDefaultCover.put(2, QINIU_CDN_DOMAIN+"/static/images/r_f2.jpg");
        favListDefaultCover.put(3, QINIU_CDN_DOMAIN+"/static/images/r_f3.jpg");
        favListDefaultCover.put(4, QINIU_CDN_DOMAIN+"/static/images/r_f4.jpg");
        favListDefaultCover.put(5, QINIU_CDN_DOMAIN+"/static/images/r_f5.jpg");
        favListDefaultCover.put(6, QINIU_CDN_DOMAIN+"/static/images/r_f6.jpg");
        favListDefaultCover.put(7, QINIU_CDN_DOMAIN+"/static/images/r_f7.jpg");
        favListDefaultCover.put(8, QINIU_CDN_DOMAIN+"/static/images/r_f8.jpg");
        favListDefaultCover.put(9, QINIU_CDN_DOMAIN+"/static/images/r_f9.jpg");
        favListDefaultCover.put(10, QINIU_CDN_DOMAIN+"/static/images/r_f10.jpg");
        favListDefaultCover.put(11, QINIU_CDN_DOMAIN+"/static/images/r_f11.jpg");
        favListDefaultCover.put(12, QINIU_CDN_DOMAIN+"/static/images/r_f12.jpg");
        favListDefaultCover.put(13, QINIU_CDN_DOMAIN+"/static/images/r_f13.jpg");
        favListDefaultCover.put(14, QINIU_CDN_DOMAIN+"/static/images/r_f14.jpg");
        favListDefaultCover.put(15, QINIU_CDN_DOMAIN+"/static/images/r_f15.jpg");
        favListDefaultCover.put(16, QINIU_CDN_DOMAIN+"/static/images/r_f16.jpg");
        favListDefaultCover.put(17, QINIU_CDN_DOMAIN+"/static/images/r_f17.jpg");
        favListDefaultCover.put(18, QINIU_CDN_DOMAIN+"/static/images/r_f18.jpg");
        favListDefaultCover.put(19, QINIU_CDN_DOMAIN+"/static/images/r_f19.jpg");
        favListDefaultCover.put(20, QINIU_CDN_DOMAIN+"/static/images/r_f20.jpg");
        favListDefaultCover.put(21, QINIU_CDN_DOMAIN+"/static/images/r_f21.jpg");
        favListDefaultCover.put(22, QINIU_CDN_DOMAIN+"/static/images/r_f22.jpg");
        favListDefaultCover.put(23, QINIU_CDN_DOMAIN+"/static/images/r_f23.jpg");
        favListDefaultCover.put(24, QINIU_CDN_DOMAIN+"/static/images/r_f24.jpg");
        favListDefaultCover.put(25, QINIU_CDN_DOMAIN+"/static/images/r_f25.jpg");
        favListDefaultCover.put(26, QINIU_CDN_DOMAIN+"/static/images/r_f26.jpg");
        favListDefaultCover.put(27, QINIU_CDN_DOMAIN+"/static/images/r_f27.jpg");
        favListDefaultCover.put(28, QINIU_CDN_DOMAIN+"/static/images/r_f28.jpg");
        favListDefaultCover.put(29, QINIU_CDN_DOMAIN+"/static/images/r_f29.jpg");
        favListDefaultCover.put(30, QINIU_CDN_DOMAIN+"/static/images/r_f30.jpg");
        favListDefaultCover.put(31, QINIU_CDN_DOMAIN+"/static/images/r_f31.jpg");
        favListDefaultCover.put(32, QINIU_CDN_DOMAIN+"/static/images/r_f32.jpg");
        favListDefaultCover.put(33, QINIU_CDN_DOMAIN+"/static/images/r_f33.jpg");
        favListDefaultCover.put(34, QINIU_CDN_DOMAIN+"/static/images/r_f34.jpg");
        favListDefaultCover.put(35, QINIU_CDN_DOMAIN+"/static/images/r_f35.jpg");
        favListDefaultCover.put(36, QINIU_CDN_DOMAIN+"/static/images/r_f36.jpg");
        favListDefaultCover.put(37, QINIU_CDN_DOMAIN+"/static/images/r_f37.jpg");
        favListDefaultCover.put(38, QINIU_CDN_DOMAIN+"/static/images/r_f38.jpg");
        favListDefaultCover.put(39, QINIU_CDN_DOMAIN+"/static/images/r_f39.jpg");
        favListDefaultCover.put(40, QINIU_CDN_DOMAIN+"/static/images/r_f40.jpg");
        favListDefaultCover.put(41, QINIU_CDN_DOMAIN+"/static/images/r_f41.jpg");
        favListDefaultCover.put(42, QINIU_CDN_DOMAIN+"/static/images/r_f42.jpg");
        favListDefaultCover.put(43, QINIU_CDN_DOMAIN+"/static/images/r_f43.jpg");
        favListDefaultCover.put(44, QINIU_CDN_DOMAIN+"/static/images/r_f44.jpg");
        favListDefaultCover.put(45, QINIU_CDN_DOMAIN+"/static/images/r_f45.jpg");
        favListDefaultCover.put(46, QINIU_CDN_DOMAIN+"/static/images/r_f46.jpg");
        favListDefaultCover.put(47, QINIU_CDN_DOMAIN+"/static/images/r_f47.jpg");
        favListDefaultCover.put(48, QINIU_CDN_DOMAIN+"/static/images/r_f48.jpg");
        favListDefaultCover.put(49, QINIU_CDN_DOMAIN+"/static/images/r_f49.jpg");
        favListDefaultCover.put(50, QINIU_CDN_DOMAIN+"/static/images/r_f50.jpg");
        favListDefaultCover.put(51, QINIU_CDN_DOMAIN+"/static/images/r_f51.jpg");
        favListDefaultCover.put(52, QINIU_CDN_DOMAIN+"/static/images/r_f52.jpg");
        favListDefaultCover.put(53, QINIU_CDN_DOMAIN+"/static/images/r_f53.jpg");
        favListDefaultCover.put(54, QINIU_CDN_DOMAIN+"/static/images/r_f54.jpg");
        favListDefaultCover.put(55, QINIU_CDN_DOMAIN+"/static/images/r_f55.jpg");
        favListDefaultCover.put(56, QINIU_CDN_DOMAIN+"/static/images/r_f56.jpg");
        favListDefaultCover.put(57, QINIU_CDN_DOMAIN+"/static/images/r_f57.jpg");
        favListDefaultCover.put(58, QINIU_CDN_DOMAIN+"/static/images/r_f58.jpg");
        favListDefaultCover.put(59, QINIU_CDN_DOMAIN+"/static/images/r_f59.jpg");
        favListDefaultCover.put(60, QINIU_CDN_DOMAIN+"/static/images/r_f60.jpg");
        favListDefaultCover.put(61, QINIU_CDN_DOMAIN+"/static/images/r_f61.jpg");
        favListDefaultCover.put(62, QINIU_CDN_DOMAIN+"/static/images/r_f62.jpg");
        favListDefaultCover.put(63, QINIU_CDN_DOMAIN+"/static/images/r_f63.jpg");
        favListDefaultCover.put(64, QINIU_CDN_DOMAIN+"/static/images/r_f64.jpg");
        favListDefaultCover.put(65, QINIU_CDN_DOMAIN+"/static/images/r_f65.jpg");
        favListDefaultCover.put(66, QINIU_CDN_DOMAIN+"/static/images/r_f66.jpg");
        favListDefaultCover.put(67, QINIU_CDN_DOMAIN+"/static/images/r_f67.jpg");
        favListDefaultCover.put(68, QINIU_CDN_DOMAIN+"/static/images/r_f68.jpg");
        favListDefaultCover.put(69, QINIU_CDN_DOMAIN+"/static/images/r_f69.jpg");
        favListDefaultCover.put(70, QINIU_CDN_DOMAIN+"/static/images/r_f70.jpg");
    }

    public static String LOGIN_TOKEN = "haha-token";

    public static String LOGIN_URL = "/login";

    public static String FILTER_VIDEO_TAG = "音乐";

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
            return String.format("http://static.hdslb.com/miniloader.swf?aid=%d&page=1", avId);
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
