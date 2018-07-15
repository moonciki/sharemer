package sharemer.business.manager.master.utils;

/**
 * Created by 18073 on 2018/1/6.
 */
public class SourceProxy {

    public String NETEASESOURCE = "http://music.163.com/#/song?id=%d";

    private String A = "http://www.acfun.cn/v/ac%d_%d";

    private String B = "https://www.bilibili.com/video/av%d/#page=%d";

    private String C = "http://www.tucao.tv/play/h%d";

    private String M = "http://www.missevan.com/sound/player?id=%d";

    private String MTV = "http://v.yinyuetai.com/video/%d";

    public String getNETEASESOURCE(Integer songId) {
        return String.format(NETEASESOURCE, songId);
    }

    public String getVideoSource(Integer videoType, Integer vId, Integer p){
        switch (videoType){
            case Constant.Video.TYPE_ACFUN:
                return String.format(A, vId, p);
            case Constant.Video.TYPE_BILIBILI:
                return String.format(B, vId, p);
            case Constant.Video.TYPE_TUCAO:
                return String.format(C, vId);
            case Constant.Video.TYPE_M:
                return String.format(M, vId);
            case Constant.Video.TYPE_MTV:
                return String.format(MTV, vId);
        }
        return null;
    }

}
