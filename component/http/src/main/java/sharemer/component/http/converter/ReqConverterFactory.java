package sharemer.component.http.converter;

import com.alibaba.fastjson.JSON;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.Buffer;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.http.Streaming;

import javax.annotation.Nullable;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * Create by 18073 on 2018/6/30.
 */
public class ReqConverterFactory extends Converter.Factory {


    /** 请求反回信息类型的转换处理*/
    @Nullable
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        if (type == String.class) {
            return ToStringResponseBodyConverter.INSTANCE;
        }
        if (type == ResponseBody.class) {
            if (isAnnotationPresent(annotations, Streaming.class)) {
                return StreamingResponseBodyConverter.INSTANCE;
            }
            return BufferingResponseBodyConverter.INSTANCE;
        }

        if (type == Void.class) {
            return VoidResponseBodyConverter.INSTANCE;
        }
        return new FastjsonResponseBodyConverter<>(type);
    }

    @Nullable
    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        return super.requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit);
    }

    static final class FastjsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
        private Type responseType;

        public FastjsonResponseBodyConverter(Type type) {
            this.responseType = type;
        }

        @Override
        public T convert(ResponseBody value) throws IOException {
            final String input = value.string();
            return JSON.parseObject(input, responseType);
        }
    }


    static final class VoidResponseBodyConverter implements Converter<ResponseBody, Void> {
        static final VoidResponseBodyConverter INSTANCE = new VoidResponseBodyConverter();

        @Override
        public Void convert(ResponseBody value) {
            value.close();
            return null;
        }
    }

    static final class StreamingResponseBodyConverter
            implements Converter<ResponseBody, ResponseBody> {

        static final StreamingResponseBodyConverter INSTANCE = new StreamingResponseBodyConverter();

        @Override
        public ResponseBody convert(ResponseBody value) {
            return value;
        }
    }

    static final class BufferingResponseBodyConverter
            implements Converter<ResponseBody, ResponseBody> {

        static final BufferingResponseBodyConverter INSTANCE = new BufferingResponseBodyConverter();

        @Override
        public ResponseBody convert(ResponseBody value) throws IOException {
            try {
                Buffer buffer = new Buffer();
                value.source().readAll(buffer);
                return ResponseBody.create(value.contentType(), value.contentLength(), buffer);
            } finally {
                value.close();
            }
        }
    }

    static final class ToStringResponseBodyConverter implements Converter<ResponseBody, String> {

        static final ToStringResponseBodyConverter INSTANCE = new ToStringResponseBodyConverter();

        @Override
        public String convert(ResponseBody value) throws IOException {
            return value.string();
        }
    }


    /**
     * Returns true if {@code annotations} contains an instance of {@code cls}.
     */
    static boolean isAnnotationPresent(Annotation[] annotations,
                                       Class<? extends Annotation> cls) {
        for (Annotation annotation : annotations) {
            if (cls.isInstance(annotation)) {
                return true;
            }
        }
        return false;
    }


}