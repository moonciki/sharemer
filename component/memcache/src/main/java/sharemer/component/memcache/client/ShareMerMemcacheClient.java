package sharemer.component.memcache.client;

import com.google.common.collect.Sets;
import net.rubyeye.xmemcached.GetsResponse;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.command.BinaryCommandFactory;
import net.rubyeye.xmemcached.impl.KetamaMemcachedSessionLocator;
import net.rubyeye.xmemcached.transcoders.Transcoder;
import net.rubyeye.xmemcached.utils.AddrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sharemer.component.memcache.client.transcoder.KryoTranscoder;

import java.io.Closeable;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Create by 18073 on 2018/7/2.
 */
public class ShareMerMemcacheClient implements Closeable {

    private static final Logger logger = LoggerFactory.getLogger(ShareMerMemcacheClient.class);

    private MemcachedClient memcachedClient;

    private int DEFAULT_CACHE_SECONDS = 300;

    private String servers;

    private Transcoder transcoder = new KryoTranscoder();

    private long opTimeout = 300;

    private long connectTimeout = 3000;

    private int connectPoolSize = 5;

    public ShareMerMemcacheClient(String servers) throws IOException {
        XMemcachedClientBuilder builder = new XMemcachedClientBuilder(AddrUtil.getAddresses(servers));
        builder.setOpTimeout(opTimeout);
        builder.setConnectTimeout(connectTimeout);
        builder.setTranscoder(transcoder);
        builder.setConnectionPoolSize(connectPoolSize);
        builder.setSessionLocator(new KetamaMemcachedSessionLocator());
        builder.setCommandFactory(new BinaryCommandFactory());
        this.memcachedClient = builder.build();
    }

    public <T> T get(String key) {
        return get(key, 0, null);
    }

    public <T> T get(String key, Callable<T> callable) {
        return get(key, DEFAULT_CACHE_SECONDS, callable);
    }

    public <T> T get(String key, int expire, Callable<T> callable) {
        if (key == null || "".equals(key)) {
            return null;
        }

        T result = null;

        try {
            result = memcachedClient.get(key);
        } catch (Exception e) {
            logger.error("get exception key:{}", key, e);
            return null;
        }
        if (result != null) {
            return result;
        }

        if (callable == null) {
            return result;
        }

        try {
            final T r = callable.call();
            set(key, expire, r);
            return r;
        } catch (Exception e) {
            logger.error("set exception key:{}", key, e);
            return null;
        }

    }

    public <T> void set(String key, T value) {
        set(key, 0, value);
    }

    public <T> void set(String key, int expire, final T value) {
        if (key != null && value != null) {
            try {
                int expireNew = expire;
                if (expire <= 0) {
                    expireNew = DEFAULT_CACHE_SECONDS;
                }
                logger.debug("mc set key:{} , expire:{}", key, expireNew);
                memcachedClient.set(key, expireNew, value);
            } catch (Exception e) {
                logger.error("set exception key:{}", key, e);
            }
        }
    }

    public <T> T getAndTouch(String key, int expire) {
        try {
            return memcachedClient.getAndTouch(key, expire);
        } catch (Exception e) {
            logger.error("touch exception key:{}", key, e);
        }
        return null;
    }

    public boolean touch(String key, int expire) {
        boolean b;
        try {
            b = memcachedClient.touch(key, expire);
        } catch (Exception e) {
            logger.error("touch exception key:{}", key, e);
            return false;
        }
        return b;
    }

    public long increment(String key, long delta, long initValue, int exp) {
        try {
            return memcachedClient.incr(key, delta, initValue, opTimeout, exp);
        } catch (Exception e) {
            logger.error("increment exception key:{} , delta:{}", key, delta, e);
            return 0L;
        }
    }

    public long decrement(String key, long delta, long initValue, int exp) {
        try {
            return memcachedClient.decr(key, delta, initValue, opTimeout, exp);
        } catch (Exception e) {
            logger.error("decrement exception key:{} , num:{}", key, delta, e);
            return 0L;
        }
    }

    public <K, V> Map<K, V> getsMap(Collection<K> ids, Function<K, String> idKeyProvider) {
        return getsMap(ids, idKeyProvider, null, DEFAULT_CACHE_SECONDS);
    }

    public <K, V> Map<K, V> getsMap(Collection<K> ids, Function<K, String> idKeyProvider, Function<Collection<K>, Map<K, V>> batchBackSource) {
        return getsMap(ids, idKeyProvider, batchBackSource, DEFAULT_CACHE_SECONDS);
    }

    public <K, V> Map<K, V> getsMap(Collection<K> ids, Function<K, String> idKeyProvider, Function<Collection<K>, Map<K, V>> batchBackSource, int expire) {
        try {
            if (ids == null || ids.size() == 0) {
                return Collections.emptyMap();
            }
            Set<String> keys = ids.stream().map(idKeyProvider::apply).collect(Collectors.toSet());

            Map<K, V> results = new HashMap();

            Map<String, GetsResponse<V>> mresponse = memcachedClient.gets(keys);

            Set<K> batchBackIds = Sets.newHashSet(ids);//需要回源的id集合

            if (mresponse != null && mresponse.size() > 0) {
                ids.forEach(id -> {
                    String key = idKeyProvider.apply(id);
                    if (mresponse.get(key) == null) {
                        return;
                    }
                    results.put(id, mresponse.get(key).getValue());
                    batchBackIds.remove(id);
                });
            }

            if (batchBackIds.size() > 0) {
                if (batchBackSource == null) {
                    return results;
                }

                Map<K, V> backSources = batchBackSource.apply(batchBackIds);
                if (backSources == null || backSources.size() == 0) {
                    return results;
                }

                backSources.forEach((id, value) -> {
                    results.put(id, value);
                });

                for (K id : batchBackIds) {
                    if (results.get(id) == null) {
                        continue;
                    }
                    String key = idKeyProvider.apply(id);
                    try {
                        memcachedClient.set(key, expire, results.get(id));
                    } catch (Exception e) {
                        logger.error("getsMap set batchBack error ! ids:{}, expire:{}, opTimeout:{}", batchBackIds, expire, opTimeout, e);
                    }
                }
            }
            return results;
        } catch (Exception e) {
            logger.error("getsMap error! ids:{}", ids, e);
        }
        return Collections.emptyMap();
    }

    public void delete(String key) {
        try {
            memcachedClient.delete(key);
        } catch (Exception e) {
            logger.error("delete key error ! key = {} ", key, e);
        }
    }


    @Override
    public void close() throws IOException {
        if (memcachedClient != null && !memcachedClient.isShutdown()) {
            memcachedClient.shutdown();
        }
    }
}
