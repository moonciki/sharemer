package sharemer.business.manager.master.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import sharemer.component.memcache.client.ShareMerMemcacheClient;
import sharemer.component.memcache.client.properties.MemcahePropertis;

import java.io.IOException;

/**
 * Create by 18073 on 2018/7/4.
 */
@Configuration
public class MemcacheConfig {

    @Primary
    @ConfigurationProperties(prefix = "sharemer.memcache.dogvane")
    @Bean(name = "memcacheProperties")
    public MemcahePropertis dataSource() {
        return new MemcahePropertis();
    }

    @Bean(destroyMethod = "close")
    ShareMerMemcacheClient shareMerMemcacheClient(MemcahePropertis memcacheProperties) throws IOException {
        return new ShareMerMemcacheClient(memcacheProperties.getServers());
    }

}
