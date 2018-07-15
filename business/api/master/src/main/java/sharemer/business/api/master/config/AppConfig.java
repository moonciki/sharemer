package sharemer.business.api.master.config;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * Create by 18073 on 2018/6/30.
 */
@Configuration
@ComponentScan(basePackages = {
        "sharemer.business.api.master"
})
@EnableConfigurationProperties({ServerProperties.class})
public class AppConfig {

    @Bean
    public PropertySourcesPlaceholderConfigurer properties() {
        PropertySourcesPlaceholderConfigurer properties = new PropertySourcesPlaceholderConfigurer();
        YamlPropertiesFactoryBean yamlPropertiesFactoryBean = new YamlPropertiesFactoryBean();
        Resource[] resources = new Resource[]{
                new ClassPathResource("app.yml")
        };
        yamlPropertiesFactoryBean.setResources(resources);
        properties.setProperties(yamlPropertiesFactoryBean.getObject());
        return properties;
    }

}
