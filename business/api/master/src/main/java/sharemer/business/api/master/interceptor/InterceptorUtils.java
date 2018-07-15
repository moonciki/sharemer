package sharemer.business.api.master.interceptor;

import org.springframework.web.method.HandlerMethod;
import sharemer.business.api.master.anno.NeedLogin;
import sharemer.business.api.master.anno.NeedUser;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by 18073 on 2017/5/21.
 */
public class InterceptorUtils {

    public static boolean needUser(Object handler){
        if (handler instanceof HandlerMethod) {
            NeedUser needUser = ((HandlerMethod) handler)
                    .getMethodAnnotation(NeedUser.class);

            if (needUser == null) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    /**
     * 判断调用是否需要登录鉴权
     */
    public static boolean needAuth(Object handler) {
        if (handler instanceof HandlerMethod) {
            NeedLogin needLogin = ((HandlerMethod) handler)
                    .getMethodAnnotation(NeedLogin.class);

            if (needLogin == null) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }


    /** 根据cookie名称获取指定value*/
    public static String getCookieValue(String cookieName, HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if(cookies == null){
            return null;
        }
        if(cookies.length == 0){
            return null;
        }
        for(Cookie cookie : cookies){
            if(cookie.getName() != null && cookie.getName().equals(cookieName)){
                return cookie.getValue();
            }
        }
        return null;
    }

}
