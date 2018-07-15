package sharemer.business.api.master.utils;

/**
 * Create by 18073 on 2018/7/15.
 */
public class ConstantProperties {

    //回源键值，该值用于redis崩溃后恢复数据回源标记用，当需要全体redis回源时只要调整该值即可
    private String redisSourceValue = "0";

    //redis批量操作削峰速率（只针对异步的回源方法生效）
    private Integer redisSourceRate;

    public String getRedisSourceValue() {
        return redisSourceValue;
    }

    public void setRedisSourceValue(String redisSourceValue) {
        this.redisSourceValue = redisSourceValue;
    }

    public Integer getRedisSourceRate() {
        return redisSourceRate;
    }

    public void setRedisSourceRate(Integer redisSourceRate) {
        this.redisSourceRate = redisSourceRate;
    }
}
