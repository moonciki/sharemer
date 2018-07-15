package sharemer.business.manager.master.mao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.concurrent.Callable;

/**
 * Created by 18073 on 2017/5/8.
 */
public abstract class BaseMao {

    private Logger logger = LoggerFactory.getLogger(BaseMao.class);

    /** 默认缓存时长5min*/
    protected int DEFAULT_CACHE_SECONDS = 300;

    /*@Resource
    private MemcachedClient memcachedClient;*/

    private boolean checkInput(String key, Object o) {
        if (StringUtils.isEmpty(key) || o == null) {
            return true;
        }
        return false;
    }

    protected void set(String key, Object o) {
        if (checkInput(key, o)) {
            return;
        }
        try {
            //this.memcachedClient.set(key, DEFAULT_CACHE_SECONDS, o);
        } catch (Exception e) {
            logger.error("memcache set异常 {} {} {}", key, o, e.getStackTrace());
        }
    }

    protected void set(String key, Callable callable) {
        Object o = null;
        try {
            o = callable.call();
        } catch (Exception e) {
            logger.error("memcache call异常 {} {} {}", key, o, e.getStackTrace());
        }
        if (checkInput(key, o)) {
            return;
        }
        try {
            //this.memcachedClient.set(key, DEFAULT_CACHE_SECONDS, o);
        } catch (Exception e) {
            logger.error("memcache set异常 {} {} {}", key, o, e.getStackTrace());
        }
    }

    protected void set(String key, Callable callable, int timeout) {
        Object o = null;
        try {
            o = callable.call();
        } catch (Exception e) {
            logger.error("memcache call异常 {} {} {}", key, o, e.getStackTrace());
        }
        if (checkInput(key, o)) {
            return;
        }
        try {
            //this.memcachedClient.set(key, timeout, o);
        } catch (Exception e) {
            logger.error("memcache set异常 {} {} {}", key, o, e.getStackTrace());
        }
    }

    protected void set(String key, Object o, int timeout) {
        if (checkInput(key, o)) {
            return;
        }
        try {
            //this.memcachedClient.set(key, timeout, o);
        } catch (Exception e) {
            logger.error("memcache set异常 {} {} {}", key, o, e.getStackTrace());
        }
    }

    protected Object getIfPresent(String key) {
        if (key != null) {
            try {
                return null;
                //return memcachedClient.get(key);
            } catch (Exception e) {
                logger.error("memcached get异常 {} {}", key, e.getStackTrace());
                return null;
            }
        } else {
            return null;
        }

    }

    protected Object get(String key, Callable<?> call, int expireTime)
            throws Exception {
        if (key != null) {
            Object tmp = getIfPresent(key);
            if (tmp != null) {
                return tmp;
            } else {
                tmp = call.call();
                if (tmp != null) {
                   // memcachedClient.set(key, expireTime, tmp);
                }
                return tmp;
            }
        } else {
            return null;
        }
    }

    protected void remove(String key) {
        try {
            //this.memcachedClient.delete(key);
        } catch (Exception e) {
            logger.error("memcached 删除失败 {} {}", key, e.getStackTrace());
        }
    }
}
