package sharemer.business.manager.master.config;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import sharemer.business.manager.master.utils.SourceProxy;

/**
 * Create by 18073 on 2018/6/30.
 */
@Configuration
@ComponentScan(basePackages = {
        "sharemer.business.manager.master"
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

    @Primary
    @ConfigurationProperties(prefix = "sharemer.source.dogvane")
    @Bean(name = "sourceProxy")
    public SourceProxy sourceProxy() {
        return new SourceProxy();
    }
}
