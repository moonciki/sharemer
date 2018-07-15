package sharemer.business.manager.master.utils;

/**
 * Created by 18073 on 2017/5/8.
 */
public class MemcachedKeys {

    public static String getUserInfo(int userId) {
        return String.format("user_info:%d", userId);
    }

    public static String getRobots() {
        return "user_robots";
    }

    /** 管理员登陆状态令牌-value存放管理员id、name*/
    public static String getAdmin(String token){
        return String.format("admin_token:%s", token);
    }

}
