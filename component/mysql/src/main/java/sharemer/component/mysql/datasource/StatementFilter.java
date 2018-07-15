package sharemer.component.mysql.datasource;

import com.alibaba.druid.filter.FilterEventAdapter;
import com.alibaba.druid.proxy.jdbc.StatementProxy;
import io.prometheus.client.Collector;
import io.prometheus.client.Summary;
import org.springframework.util.StringUtils;

/**
 * Created by 18073 on 2018/7/1.
 * 此类用来给prometheus监控推送sql执行的qps、耗时等信息，可通过参数控制是否启用该服务
 */
class StatementFilter extends FilterEventAdapter {

    private static Summary summary = Summary.build().name("lib_client")
            .help("lib_client server summary").labelNames("method").register();

    @Override
    protected void statementExecuteBatchBefore(StatementProxy statement) {
        statement.setLastExecuteStartNano();//执行sql之前计数初始化
    }

    @Override
    protected void statementExecuteAfter(StatementProxy statement, String sql,
                                         boolean result) {
        /** 获取操作名称*/
        String action = this.getAction(sql);
        if (action != null) {
            statement.setLastExecuteTimeNano();//执行sql之后计算执行时间
            long time = statement.getLastExecuteTimeNano();//执行时间
            summary.labels(action).observe(time / Collector.NANOSECONDS_PER_SECOND);
        }
    }

    private String getAction(String sql) {
        if (!StringUtils.isEmpty(sql)) {
            sql = sql.toLowerCase().trim();
            if (sql.startsWith("select")) {
                return "mysql:select";
            }
            if (sql.startsWith("update")) {
                return "mysql:update";
            }
            if (sql.startsWith("insert")) {
                return "mysql:insert";
            }
            if (sql.startsWith("delete")) {
                return "mysql:delete";
            }
            if (sql.startsWith("replace")) {
                return "mysql:replace";
            }
        }
        return null;
    }

}
