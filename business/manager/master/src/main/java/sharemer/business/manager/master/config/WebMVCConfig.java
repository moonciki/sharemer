package sharemer.business.manager.master.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import sharemer.business.manager.master.interceptor.AuthInterceptor;

@Configuration
public class WebMVCConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        AuthInterceptor auth = new AuthInterceptor();
        registry.addInterceptor(auth)
                .addPathPatterns("/**");
    }
}
