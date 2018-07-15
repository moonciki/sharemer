package sharemer.business.api.master.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by 18073 on 2017/9/12.
 * 需要做次数限制的接口，需要做该标记，在这之前必须要引入当前登录用户
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface TriggerLimit {

    int t_k();

}
