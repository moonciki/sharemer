package sharemer.business.api.master.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import sharemer.business.api.master.anno.TriggerLimit;
import sharemer.business.api.master.exception.BusinessException;
import sharemer.business.api.master.po.User;
import sharemer.business.api.master.rao.trigger.TriggerRao;
import sharemer.business.api.master.utils.Constant;
import sharemer.business.api.master.utils.HttpUtil;
import sharemer.business.api.master.utils.RedisKeys;
import sharemer.component.global.resp.WrappedResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by 18073 on 2017/9/13.
 */
@Component("triggerLimitInterceptor")
public class TriggerLimitInterceptor extends HandlerInterceptorAdapter {

    private TriggerRao triggerRao;

    public TriggerLimitInterceptor(TriggerRao triggerRao){
        this.triggerRao = triggerRao;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {

        User user = (User) request.getAttribute(Constant.LOGIN_USER);
        if(user == null){
            throw new BusinessException("未登录");
        }
        if(handler instanceof HandlerMethod){
            TriggerLimit triggerLimit = ((HandlerMethod) handler)
                    .getMethodAnnotation(TriggerLimit.class);
            int t_k = triggerLimit.t_k();
            String key;
            switch (t_k){
                case 1:
                    key = RedisKeys.Trigger.getActionTrigger(user.getId());
                    break;
                default:
                    key= "";
                    break;
            }
            boolean allowed = triggerRao.isAllowed(key);
            if(allowed){
                return true;
            }else{
                if (HttpUtil.isAjax(request)) {
                    HttpUtil.writeJson(response, WrappedResult.fail(-1, "您操作太频繁啦，请先稍微休息下~"));
                }
                return false;
            }
        }else{
            if (HttpUtil.isAjax(request)) {
                HttpUtil.writeJson(response, WrappedResult.fail(-1, "内部错误"));
            }
            return false;
        }
    }

}
