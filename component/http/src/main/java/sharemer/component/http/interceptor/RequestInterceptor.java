package sharemer.component.http.interceptor;

import okhttp3.Request;

/**
 * Create by 18073 on 2018/6/30.
 * 请求前的拦截器定义，提供定义实现
 */
public interface RequestInterceptor {
    Request intercept(Request origin);
}