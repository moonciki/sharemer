package sharemer.component.mysql.datasource;

import com.alibaba.druid.pool.DruidDataSourceStatLogger;
import com.alibaba.druid.pool.DruidDataSourceStatLoggerAdapter;
import com.alibaba.druid.pool.DruidDataSourceStatValue;
import io.prometheus.client.Gauge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by 18073 on 2018/7/1.
 * 此类用来给prometheus监控定时推送连接池数据，可通过参数控制是否启用该服务
 */
class StatLogger extends DruidDataSourceStatLoggerAdapter implements DruidDataSourceStatLogger {

    private static final Logger logger = LoggerFactory.getLogger(StatLogger.class);

    private Gauge gauge = Gauge.build().name("lib_client_gauge")
            .help("db_druid_pool server gauge")
            .labelNames("method", "quota").register();


    private String dbName;

    public StatLogger(String dbName) {
        this.dbName = dbName;
    }

    @Override
    public void log(DruidDataSourceStatValue statValue) {
        try {
            gauge.labels(dbName, "druid:count").set(statValue.getPoolingCount());//连接数
            gauge.labels(dbName, "druid:active").set(statValue.getActiveCount());//活跃连接数数量
            gauge.labels(dbName, "druid:wait").set(statValue.getNotEmptyWaitCount());//发生等待的次数
        } catch (Exception e) {
            logger.error("StatLogger error!", e);
        }
    }

}
