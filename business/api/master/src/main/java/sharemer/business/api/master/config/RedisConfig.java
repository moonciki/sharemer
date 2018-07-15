package sharemer.business.api.master.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import sharemer.component.redis.client.SharemerRedisClient;
import sharemer.component.redis.properties.SharemerRedisProperties;

/**
 * Create by 18073 on 2018/7/7.
 */
@Configuration
public class RedisConfig {

    @Primary
    @ConfigurationProperties(prefix = "sharemer.redis.dogvane")
    @Bean(name = "redisProperties")
    public SharemerRedisProperties dataSource() {
        return new SharemerRedisProperties();
    }

    @Bean(destroyMethod = "close")
    public SharemerRedisClient sharemerRedisClient(SharemerRedisProperties redisProperties) {
        return new SharemerRedisClient(redisProperties);
    }
}
