package sharemer.component.redis.client;

import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.*;
import redis.clients.jedis.params.sortedset.ZAddParams;
import redis.clients.jedis.params.sortedset.ZIncrByParams;
import sharemer.component.redis.properties.SharemerRedisProperties;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Create by 18073 on 2018/7/7.
 */
public class SharemerRedisClient {

    private Logger logger = LoggerFactory.getLogger(SharemerRedisClient.class);

    private JedisCluster jedisCluster;

    public SharemerRedisClient(SharemerRedisProperties properties) {

        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMaxWaitMillis(properties.getMaxWaitMillis());
        config.setMaxTotal(properties.getMaxTotal());
        config.setMaxIdle(properties.getMaxIdle());
        config.setMinIdle(properties.getMinIdle());


        String[] servers = properties.getServers().split(",");
        Set<HostAndPort> hosts = Sets.newHashSet();
        for (String node : servers) {
            String[] hp = node.split(":");
            hosts.add(new HostAndPort(hp[0], Integer.parseInt(hp[1])));
        }

        jedisCluster = new JedisCluster(hosts, properties.getTimeout(),
                properties.getRetry(), config);

    }

    public String set(String key, String value) {
        return jedisCluster.set(key, value);
    }

    public String set(String key, String value, String nxxx, String expx, long time) {
        return jedisCluster.set(key, value, nxxx, expx, time);
    }

    
    public String get(String key) {
        return jedisCluster.get(key);
    }

    
    public Boolean exists(String key) {
        return jedisCluster.exists(key);
    }

    public Long expire(String key, int seconds) {
        return jedisCluster.expire(key, seconds);
    }

    public Long pexpire(String key, long milliseconds) {
        return jedisCluster.pexpire(key, milliseconds);
    }

    public Long expireAt(String key, long unixTime) {
        return jedisCluster.expireAt(key, unixTime);
    }
    
    public Long pexpireAt(String key, long millisecondsTimestamp) {
        return jedisCluster.pexpireAt(key, millisecondsTimestamp);
    }

    public String getSet(String key, String value) {
        return jedisCluster.getSet(key, value);
    }
    
    public Long setnx(String key, String value) {
        return jedisCluster.setnx(key, value);
    }

    public String setex(String key, int seconds, String value) {
        return jedisCluster.setex(key, seconds, value);
    }

    public String psetex(String key, long milliseconds, String value) {
        return jedisCluster.psetex(key, milliseconds, value);
    }

    public Long decrBy(String key, long integer) {
        return jedisCluster.decrBy(key, integer);
    }
    
    public Long decr(String key) {
        return jedisCluster.decr(key);
    }

    public Long incr(String key) {
        return jedisCluster.incr(key);
    }

    public Long incrBy(String key, long integer) {
        return jedisCluster.incrBy(key, integer);
    }
    
    public Long hset(String key, String field, String value) {
        return jedisCluster.hset(key, field, value);
    }
    
    public String hget(String key, String field) {
        return jedisCluster.hget(key, field);
    }

    public Long hsetnx(String key, String field, String value) {
        return jedisCluster.hsetnx(key, field, value);
    }
    
    public String hmset(String key, Map<String, String> hash) {
        return jedisCluster.hmset(key, hash);
    }

    public List<String> hmget(String key, String... fields) {
        return jedisCluster.hmget(key, fields);
    }

    public Long rpush(String key, String... string) {
        return jedisCluster.rpush(key, string);
    }
    
    public Long lpush(String key, String... string) {
        return jedisCluster.lpush(key, string);
    }

    public Long llen(String key) {
        return jedisCluster.llen(key);
    }

    
    public List<String> lrange(String key, long start, long end) {
        return jedisCluster.lrange(key, start, end);
    }

    
    public String ltrim(String key, long start, long end) {
        return jedisCluster.ltrim(key, start, end);
    }

    
    public String lindex(String key, long index) {
        return jedisCluster.lindex(key, index);
    }

    
    public String lset(String key, long index, String value) {
        return jedisCluster.lset(key, index, value);
    }

    
    public Long lrem(String key, long count, String value) {
        return jedisCluster.lrem(key, count, value);
    }

    
    public String lpop(String key) {
        return jedisCluster.lpop(key);
    }

    
    public String rpop(String key) {
        return jedisCluster.rpop(key);
    }

    
    public Long sadd(String key, String... member) {
        return jedisCluster.sadd(key, member);
    }

    
    public Set<String> smembers(String key) {
        return jedisCluster.smembers(key);
    }

    
    public Long srem(String key, String... member) {
        return jedisCluster.srem(key, member);
    }

    
    public String spop(String key) {
        return jedisCluster.spop(key);
    }

    
    public Set<String> spop(String key, long count) {
        return jedisCluster.spop(key, count);
    }

    
    public Long scard(String key) {
        return jedisCluster.scard(key);
    }

    
    public Boolean sismember(String key, String member) {
        return jedisCluster.sismember(key, member);
    }

    
    public String srandmember(String key) {
        return jedisCluster.srandmember(key);
    }

    
    public List<String> srandmember(String key, int count) {
        return jedisCluster.srandmember(key, count);
    }

    
    public Long zadd(String key, double score, String member) {
        return jedisCluster.zadd(key, score, member);
    }

    
    public Long zadd(String key, double score, String member, ZAddParams params) {
        return jedisCluster.zadd(key, score, member, params);
    }

    
    public Long zadd(String key, Map<String, Double> scoreMembers) {
        return jedisCluster.zadd(key, scoreMembers);
    }

    
    public Long zadd(String key, Map<String, Double> scoreMembers, ZAddParams params) {
        return jedisCluster.zadd(key, scoreMembers, params);
    }

    
    public Set<String> zrange(String key, long start, long end) {
        return jedisCluster.zrange(key, start, end);
    }

    
    public Long zrem(String key, String... member) {
        return jedisCluster.zrem(key, member);
    }

    
    public Double zincrby(String key, double score, String member) {
        return jedisCluster.zincrby(key, score, member);
    }

    
    public Double zincrby(String key, double score, String member, ZIncrByParams params) {
        return jedisCluster.zincrby(key, score, member, params);
    }

    
    public Long zrank(String key, String member) {
        return jedisCluster.zrank(key, member);
    }

    
    public Long zrevrank(String key, String member) {
        return jedisCluster.zrevrank(key, member);
    }

    
    public Set<String> zrevrange(String key, long start, long end) {
        return jedisCluster.zrevrange(key, start, end);
    }

    
    public Set<Tuple> zrangeWithScores(String key, long start, long end) {
        return jedisCluster.zrangeWithScores(key, start, end);
    }

    
    public Set<Tuple> zrevrangeWithScores(String key, long start, long end) {
        return jedisCluster.zrevrangeWithScores(key, start, end);
    }

    
    public Long zcard(String key) {
        return jedisCluster.zcard(key);
    }

    
    public Double zscore(String key, String member) {
        return jedisCluster.zscore(key, member);
    }

    
    public List<String> sort(String key) {
        return jedisCluster.sort(key);
    }

    
    public List<String> sort(String key, SortingParams sortingParameters) {
        return jedisCluster.sort(key, sortingParameters);
    }

    
    public Long zcount(String key, double min, double max) {
        return jedisCluster.zcount(key, min, max);
    }

    
    public Long zcount(String key, String min, String max) {
        return jedisCluster.zcount(key, min, max);
    }

    
    public Set<String> zrangeByScore(String key, double min, double max) {
        return jedisCluster.zrangeByScore(key, min, max);
    }

    
    public Set<String> zrangeByScore(String key, String min, String max) {
        return jedisCluster.zrangeByScore(key, min, max);
    }

    
    public Set<String> zrevrangeByScore(String key, double max, double min) {
        return jedisCluster.zrevrangeByScore(key, max, min);
    }

    
    public Set<String> zrangeByScore(String key, double min, double max, int offset, int count) {
        return jedisCluster.zrangeByScore(key, min, max, offset, count);
    }

    
    public Set<String> zrevrangeByScore(String key, String max, String min) {
        return jedisCluster.zrevrangeByScore(key, max, min);
    }

    
    public Set<String> zrangeByScore(String key, String min, String max, int offset, int count) {
        return jedisCluster.zrangeByScore(key, min, max, offset, count);
    }

    
    public Set<String> zrevrangeByScore(String key, double max, double min, int offset, int count) {
        return jedisCluster.zrevrangeByScore(key, max, min, offset, count);
    }

    
    public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max) {
        return jedisCluster.zrangeByScoreWithScores(key, min, max);
    }

    
    public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min) {
        return jedisCluster.zrevrangeByScoreWithScores(key, max, min);
    }

    
    public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max, int offset, int count) {
        return jedisCluster.zrangeByScoreWithScores(key, min, max, offset, count);
    }

    
    public Set<String> zrevrangeByScore(String key, String max, String min, int offset, int count) {
        return jedisCluster.zrevrangeByScore(key, max, min, offset, count);
    }

    
    public Set<Tuple> zrangeByScoreWithScores(String key, String min, String max) {
        return jedisCluster.zrangeByScoreWithScores(key, min, max);
    }

    
    public Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min) {
        return jedisCluster.zrevrangeByScoreWithScores(key, max, min);
    }

    
    public Set<Tuple> zrangeByScoreWithScores(String key, String min, String max, int offset, int count) {
        return jedisCluster.zrangeByScoreWithScores(key, min, max, offset, count);
    }

    
    public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min, int offset, int count) {
        return jedisCluster.zrevrangeByScoreWithScores(key, max, min, offset, count);
    }

    
    public Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min, int offset, int count) {
        return jedisCluster.zrevrangeByScoreWithScores(key, max, min, offset, count);
    }

    
    public Long zremrangeByRank(String key, long start, long end) {
        return jedisCluster.zremrangeByRank(key, start, end);
    }

    
    public Long zremrangeByScore(String key, double start, double end) {
        return jedisCluster.zremrangeByScore(key, start, end);
    }

    
    public Long zremrangeByScore(String key, String start, String end) {
        return jedisCluster.zremrangeByScore(key, start, end);
    }

    
    public Long linsert(String key, BinaryClient.LIST_POSITION where, String pivot, String value) {
        return jedisCluster.linsert(key, where, pivot, value);
    }

    
    public Long lpushx(String key, String... string) {
        return jedisCluster.lpushx(key, string);
    }

    
    public Long rpushx(String key, String... string) {
        return jedisCluster.rpushx(key, string);
    }

    
    public List<String> blpop(int timeout, String key) {
        return jedisCluster.blpop(timeout, key);
    }

    
    public List<String> brpop(int timeout, String key) {
        return jedisCluster.brpop(timeout, key);
    }

    
    public Long del(String key) {
        return jedisCluster.del(key);
    }

    
    public String echo(String string) {
        return jedisCluster.echo(string);
    }

    
    public Long bitcount(String key) {
        return jedisCluster.bitcount(key);
    }

    
    public Long bitcount(String key, long start, long end) {
        return jedisCluster.bitcount(key, start, end);
    }

    
    public ScanResult<Map.Entry<String, String>> hscan(String key, String cursor) {
        return jedisCluster.hscan(key, cursor);
    }

    
    public ScanResult<Map.Entry<String, String>> hscan(String key, String cursor, ScanParams params) {
        return jedisCluster.hscan(key, cursor, params);
    }

    
    public ScanResult<String> sscan(String key, String cursor) {
        return jedisCluster.sscan(key, cursor);
    }

    
    public ScanResult<String> sscan(String key, String cursor, ScanParams params) {
        return jedisCluster.sscan(key, cursor, params);
    }

    public ScanResult<Tuple> zscan(String key, String cursor) {
        return jedisCluster.zscan(key, cursor);
    }

    
    public ScanResult<Tuple> zscan(String key, String cursor, ScanParams params) {
        return jedisCluster.zscan(key, cursor, params);
    }

    
    public List<Long> bitfield(String key, String... arguments) {
        return jedisCluster.bitfield(key, arguments);
    }

    public void close(){
        try{
            jedisCluster.close();
        }catch (IOException e){
            logger.error("sharemer redis close error！", e);
        }

    }
}
