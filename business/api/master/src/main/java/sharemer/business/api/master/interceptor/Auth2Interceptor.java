package sharemer.business.api.master.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import sharemer.business.api.master.rao.user.UserRao;
import sharemer.business.api.master.utils.Constant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 认证授权拦截器
 */
@Component("auth2Interceptor")
public class Auth2Interceptor extends HandlerInterceptorAdapter {

	private UserRao userRao;

	public Auth2Interceptor(UserRao userRao){
		this.userRao = userRao;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {

		if (!InterceptorUtils.needAuth(handler)) {
			return true;
		}

		/**
		 * 检验本次请求是否携带可信任的token
		 */
		String token = InterceptorUtils.getCookieValue(Constant.LOGIN_TOKEN, request);

		if(StringUtils.isEmpty(token)){
			response.sendRedirect(Constant.LOGIN_URL);//重定向到登录页
			return false;
		}

		Integer uid = userRao.getLoginToken(token);
		if(uid == null){
			response.sendRedirect(Constant.LOGIN_URL);//重定向到登录页
			return false;
		}
		return true;
	}

}
