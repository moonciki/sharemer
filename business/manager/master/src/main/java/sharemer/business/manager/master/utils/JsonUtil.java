package sharemer.business.manager.master.utils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * JSON util class
 * 
 * @author goku
 *
 */
public class JsonUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);

	private static final ObjectMapper MAPPER;
	private static final ObjectMapper IGNORE_NULL_MAPPER;

	static {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		
		MAPPER = new ObjectMapper().registerModule(new JavaTimeModule()).setDateFormat(df);
		IGNORE_NULL_MAPPER = new ObjectMapper().setSerializationInclusion(Include.NON_NULL)
				.registerModule(new JavaTimeModule()).setDateFormat(df);
	}

	/**
	 * 将 java 对象转换为 JSON 字符串，如果传入的是 String 类型，则返回自身
	 * 
	 * @param object
	 *            待转换的 java 对象
	 * @param ignoreNull
	 *            boolean 类型，是否忽略值为 null 的属性
	 * @return
	 */
	public static String toJson(Object object, boolean ignoreNull) {
		if (object == null) {
			return null;
		}

		if (object instanceof String) {
			return (String) object;
		}

		String result = null;
		try {
			if (ignoreNull) {
				return IGNORE_NULL_MAPPER.writeValueAsString(object);
			} else {
				return MAPPER.writeValueAsString(object);
			}
		} catch (JsonProcessingException e) {
			logger.error("序列化 java 对象时异常", e);
		}
		return result;
	}

	/**
	 * 将 JSON 字符串转换为 java 对象
	 * 
	 * @param json
	 *            待转换的 JSON 字符
	 * @param clazz
	 *            目标对象的类型
	 * @return
	 */
	public static <T> T fromJson(String json, Class<T> clazz) {
		T result = null;
		try {
			result = MAPPER.readValue(json, clazz);
		} catch (IOException e) {
			logger.error("从 String 反序列化 java 对象时异常", e);
		}

		return result;
	}

	/**
	 * 将 InpueStream 中的 JSON 内容转换为 java 对象
	 * 
	 * @param input
	 *            输入流
	 * @param clazz
	 *            目标对象的类型
	 * @return
	 */
	public static <T> T fromJson(InputStream input, Class<T> clazz) {
		T result = null;
		try {
			result = MAPPER.readValue(input, clazz);
		} catch (IOException e) {
			logger.error("从 InputStream 反序列化 java 对象时异常", e);
		}

		return result;
	}

}
