package sharemer.business.manager.master.interceptor;

import org.springframework.web.method.HandlerMethod;
import sharemer.business.manager.master.anno.NeedLogin;

/**
 * Created by 18073 on 2017/5/21.
 */
public class InterceptorUtils {

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

}
