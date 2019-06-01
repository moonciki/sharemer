package sharemer.business.api.master.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import sharemer.business.api.master.interceptor.Auth2Interceptor;
import sharemer.business.api.master.interceptor.Auth3Interceptor;
import sharemer.business.api.master.interceptor.TriggerLimitInterceptor;
import sharemer.business.api.master.mao.user.UserMao;
import sharemer.business.api.master.rao.trigger.TriggerRao;
import sharemer.business.api.master.rao.user.UserRao;

import javax.annotation.Resource;

@Configuration
public class WebMVCConfig implements WebMvcConfigurer {

    @Resource
    private UserRao userRao;

    @Resource
    private UserMao userMao;

    @Resource
    private TriggerRao triggerRao;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        Auth2Interceptor auth2 = new Auth2Interceptor(userRao);
        registry.addInterceptor(auth2).addPathPatterns("/**");
        Auth3Interceptor auth3 = new Auth3Interceptor(userRao, userMao);
        registry.addInterceptor(auth3).addPathPatterns("/**");
        TriggerLimitInterceptor auth4 = new TriggerLimitInterceptor(triggerRao);
        registry.addInterceptor(auth4).addPathPatterns("/pc_api/r/**");
    }
}
