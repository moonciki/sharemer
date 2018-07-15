package sharemer.business.api.master.config;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import sharemer.component.mysql.datasource.SharemerDataSource;
import sharemer.component.mysql.vfs.SpringBootVFS;

import javax.sql.DataSource;

/**
 * Created by crop on 2018/7/1.
 */
@Configuration
@MapperScan(basePackages = "sharemer.business.api.master", sqlSessionFactoryRef = "sqlSession", annotationClass = Mapper.class)
public class DataSourceConfig {

    @Primary
    @ConfigurationProperties(prefix = "sharemer.mysql.dogvane")
    @Bean(name = "dataSource", destroyMethod = "close")
    public SharemerDataSource dataSource() {
        return new SharemerDataSource();
    }


    @Bean(name = "sqlSession")
    public SqlSessionFactory sqlSession(@Qualifier("dataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setVfs(SpringBootVFS.class);
        return bean.getObject();
    }

}