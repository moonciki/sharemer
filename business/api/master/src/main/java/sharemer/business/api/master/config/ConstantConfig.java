package sharemer.business.api.master.config;

import com.google.common.util.concurrent.RateLimiter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import sharemer.business.api.master.utils.ConstantProperties;

/**
 * Create by 18073 on 2018/7/15.
 */
@Configuration
public class ConstantConfig {

    @Primary
    @ConfigurationProperties(prefix = "sharemer.constant.api")
    @Bean(name = "constantProperties")
    public ConstantProperties constantProperties() {
        return new ConstantProperties();
    }

    @Bean("redisSourceRateLimiter")
    RateLimiter redisSourceRateLimiter(@Qualifier("constantProperties") ConstantProperties constantProperties) {
        return RateLimiter.create(constantProperties.getRedisSourceRate());
    }
}
