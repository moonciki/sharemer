package sharemer.business.api.master.utils;

/**
 * Create by 18073 on 2018/7/15.
 */
public class ConstantProperties {

    private String qiniuBucketName;

    private String qiniuAk;

    private String qiniuSk;

    private String qiniuAudioBucket;

    private String qiniuImageDomain;

    private String qiniuAudioDomain;

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

    public String getQiniuBucketName() {
        return qiniuBucketName;
    }

    public void setQiniuBucketName(String qiniuBucketName) {
        this.qiniuBucketName = qiniuBucketName;
    }

    public String getQiniuAk() {
        return qiniuAk;
    }

    public void setQiniuAk(String qiniuAk) {
        this.qiniuAk = qiniuAk;
    }

    public String getQiniuSk() {
        return qiniuSk;
    }

    public void setQiniuSk(String qiniuSk) {
        this.qiniuSk = qiniuSk;
    }

    public String getQiniuAudioBucket() {
        return qiniuAudioBucket;
    }

    public void setQiniuAudioBucket(String qiniuAudioBucket) {
        this.qiniuAudioBucket = qiniuAudioBucket;
    }

    public String getQiniuImageDomain() {
        return qiniuImageDomain;
    }

    public void setQiniuImageDomain(String qiniuImageDomain) {
        this.qiniuImageDomain = qiniuImageDomain;
    }

    public String getQiniuAudioDomain() {
        return qiniuAudioDomain;
    }

    public void setQiniuAudioDomain(String qiniuAudioDomain) {
        this.qiniuAudioDomain = qiniuAudioDomain;
    }
}
