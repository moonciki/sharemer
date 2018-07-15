package sharemer.component.mysql.datasource;

import com.alibaba.druid.util.StringUtils;

import java.net.URI;

/**
 * Create by 18073 on 2018/7/1.
 */
class SharemerDataSourceUtils {

    static String transferJdbcUrl(String url){
        try{
            if(StringUtils.isEmpty(url)){
                return null;
            }
            URI uri = new URI(url.replace(":", ""));
            String[] segments = uri.getPath().split("/");
            return segments[segments.length-1];
        }catch (Exception e){
            return null;
        }
    }
}
