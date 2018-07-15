package sharemer.business.api.master.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import sharemer.business.api.master.po.Admin;
import sharemer.business.api.master.utils.Constant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 认证授权拦截器
 */
@Component("authInterceptor")
public class AuthInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {

        if (!InterceptorUtils.needAuth(handler)) {
            return true;
        }

        Admin admin = (Admin)request.getSession().getAttribute("admin");
        if(admin != null){
            return true;
        }else{
            response.sendRedirect(Constant.LOGIN_URL);//重定向到登录页
            return false;
        }
    }


}
