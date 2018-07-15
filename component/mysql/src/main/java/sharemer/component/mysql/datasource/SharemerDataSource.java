package sharemer.component.mysql.datasource;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.util.StringUtils;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Create by 18073 on 2018/7/1.
 */
public class SharemerDataSource implements DataSource {

    private DruidDataSource druidDataSource = new DruidDataSource();

    /** 是否启用普罗连接池监控*/
    private boolean enableProDataPool = true;

    /** 是否启用普罗sql语句监控*/
    private boolean enableProStatement = false;

    public SharemerDataSource() {
        //基础配置信息设置默认值
        this.setValidationQuery("SELECT 1")
                .setTimeBetweenLogStatsMillis(1000)
                .setInitialSize(5);
        this.setRemoveAbandoned(true);
        this.setTestOnBorrow(true);
        this.setTimeBetweenEvictionRunsMillis(60000);
        this.setQueryTimeout(1);
    }

    public void setDriverClassName(String driver) {
        druidDataSource.setDriverClassName(driver);
    }

    public void setUrl(String url) {
        druidDataSource.setUrl(url);

        if (!StringUtils.isEmpty(url)) {
            if(enableProDataPool){
                //添加普罗连接池属性监控
                String dbName = SharemerDataSourceUtils.transferJdbcUrl(url);
                this.druidDataSource.setStatLogger(new StatLogger(dbName));
            }

            if(enableProStatement){
                //添加普罗SQL相关信息监控
                List<Filter> filters = new ArrayList<>();
                filters.add(new StatementFilter());
                this.druidDataSource.setProxyFilters(filters);
            }
        }
    }

    public void setUsername(String userName) {
        druidDataSource.setUsername(userName);
    }

    public void setPassword(String pwd) {
        druidDataSource.setPassword(pwd);
    }

    public void setMaxActive(int maxTotal) {
        druidDataSource.setMaxActive(maxTotal);
    }

    public void setMaxWait(int maxWait) {
        druidDataSource.setMaxWait(maxWait);
    }

    public void setMaxIdle(int maxIdle) {
        druidDataSource.setMaxIdle(maxIdle);
    }

    public void setMinIdle(int minIdle) {
        druidDataSource.setMinIdle(minIdle);
    }

    public void setTestOnBorrow(boolean testOnBorrow) {
        druidDataSource.setTestOnBorrow(testOnBorrow);
    }

    public void setRemoveAbandoned(boolean removeAbandonedOn) {
        druidDataSource.setRemoveAbandoned(removeAbandonedOn);
    }

    public void setTimeBetweenEvictionRunsMillis(long timeBetweenEvictionRunsMillis) {
        druidDataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
    }

    public void setQueryTimeout(int queryTimeout) {
        druidDataSource.setQueryTimeout(queryTimeout);
    }
    //不可定制属性

    private SharemerDataSource setValidationQuery(String validationQuery) {
        druidDataSource.setValidationQuery(validationQuery);
        return this;
    }

    private SharemerDataSource setTimeBetweenLogStatsMillis(long timeBetweenLogStatsMillis) {
        druidDataSource.setTimeBetweenLogStatsMillis(timeBetweenLogStatsMillis);
        return this;
    }

    private SharemerDataSource setInitialSize(int initialSize) {
        druidDataSource.setInitialSize(initialSize);
        return this;
    }


    @Override
    public Connection getConnection() throws SQLException {
        return druidDataSource.getConnection();
    }

    @Override
    public Connection getConnection(String username, String password)
            throws SQLException {
        return druidDataSource.getConnection(username, password);
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return druidDataSource.unwrap(iface);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return druidDataSource.isWrapperFor(iface);
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return druidDataSource.getLogWriter();
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        druidDataSource.setLogWriter(out);
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        druidDataSource.setLoginTimeout(seconds);
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return druidDataSource.getLoginTimeout();
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return druidDataSource.getParentLogger();
    }

    public void close() {
        druidDataSource.close();
    }

    public void setEnableProDataPool(boolean enableProDataPool) {
        this.enableProDataPool = enableProDataPool;
    }

    public void setEnableProStatement(boolean enableProStatement) {
        this.enableProStatement = enableProStatement;
    }
}
