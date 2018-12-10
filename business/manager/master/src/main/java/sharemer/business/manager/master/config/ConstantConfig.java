package sharemer.business.manager.master.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import sharemer.business.manager.master.config.properties.ConstantProperties;

/**
 * Create by 18073 on 2018/7/15.
 */
@Configuration
public class ConstantConfig {

    @Primary
    @ConfigurationProperties(prefix = "sharemer.constant.manager")
    @Bean(name = "constantProperties")
    public ConstantProperties constantProperties() {
        return new ConstantProperties();
    }

}
