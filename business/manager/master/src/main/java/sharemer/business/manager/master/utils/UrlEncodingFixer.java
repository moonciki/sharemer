package sharemer.business.manager.master.utils;

/**
 * 由于各个远程接口对 URL 中特殊字符（"", "+", "&"）的要求不一致，接口调用 URL 需要针对 Java 原生 encode
 * 之后做一系列（post-processing）
 * 
 * @author goku
 *
 */
public class UrlEncodingFixer {

	/** 只处理 空格 */
	@Deprecated
	public static String fixSpace(String source) {
		return source.replace("+", "%20").replace("%7E", "~").replace("*", "%2A");
	}

	/** 处理 Get URL 中的特殊字符，为 Android 设备提供 URL*/
	public static String fixAll(String source) {
		source = source.replace("+", "%20");
		source = source.replace("*", "%2A");
		source = source.replace("%7E", "~");
		return source;
	}

}
