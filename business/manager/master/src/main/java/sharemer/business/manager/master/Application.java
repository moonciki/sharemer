package sharemer.business.manager.master;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import sharemer.business.manager.master.config.AppConfig;

/**
 * Create by 18073 on 2018/6/30.
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
public class Application {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(AppConfig.class);
        app.setWebApplicationType(WebApplicationType.SERVLET);
        app.run(args);
    }
}
