package sharemer.business.api.master.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import sharemer.business.api.master.mao.user.UserMao;
import sharemer.business.api.master.po.User;
import sharemer.business.api.master.rao.user.UserRao;
import sharemer.business.api.master.utils.Constant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by 18073 on 2017/8/27.
 */
@Component("auth3Interceptor")
public class Auth3Interceptor extends HandlerInterceptorAdapter {

    private UserRao userRao;

    private UserMao userMao;

    public Auth3Interceptor(UserRao userRao, UserMao userMao){
        this.userRao = userRao;
        this.userMao = userMao;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {

        if (!InterceptorUtils.needUser(handler)) {
            return true;
        }

        /**
         * 检验本次请求是否携带可信任的token
         */
        String token = InterceptorUtils.getCookieValue(Constant.LOGIN_TOKEN, request);

        if(!StringUtils.isEmpty(token)){
            Integer uid = userRao.getLoginToken(token);
            if(uid != null){
                User currentUser = this.userMao.getBaseOne(uid);
                if(currentUser != null){
                    /** 将当前登录用户信息绑定本次请求，在包含NeedUser注解的controller方法下用*/
                    request.setAttribute(Constant.LOGIN_USER, currentUser);
                }
            }
        }
        return true;
    }

}
