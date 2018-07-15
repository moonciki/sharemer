package sharemer.business.api.master.utils;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class HttpUtil {

	public static final String X_BACKEND_BILI_REAL_IP = "X-BACKEND-BILI-REAL-IP";
	public static final String X_REAL_IP = "X-Real-IP";
	private static final String CLIENT_IP =  "Client-IP";
	private static final String REFER =  "Referer";
	private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);

	private static final Splitter splitter = Splitter.on(",").trimResults().omitEmptyStrings();

	/**判断请求是否是 ajax 请求*/
	public static boolean isAjax(HttpServletRequest request) {
		return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
	}

	/**将对象 JSON 序列化之后，向 response 中输出*/
	public static void writeJson(HttpServletResponse response, Object object) {
		response.setContentType("application/json;charset=UTF-8");
		try {
			PrintWriter out = response.getWriter();
			out.write(JsonUtil.toJson(object, true));
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**向 response 中输出字符串*/
	public static void write(HttpServletResponse response, String json) {
		response.setContentType("application/json;charset=UTF-8");
		try {
			PrintWriter out = response.getWriter();
			out.write(json);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**向 response 中输出字符串*/
	public static void writePlainText(HttpServletResponse response, String text) {
		response.setContentType("text/plain");
		try {
			PrintWriter out = response.getWriter();
			out.write(text);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**获取发起 request 的 client 的 IP 地址*/
	public static String getClientIpAddress(HttpServletRequest request) {
		if (request == null) {
			return null;
		}
		String ip = request.getHeader(X_BACKEND_BILI_REAL_IP);
		if (Strings.isNullOrEmpty(ip))
			ip = request.getHeader(X_REAL_IP) != null ? request.getHeader(X_REAL_IP) : request
					.getRemoteAddr();
		return ip;
	}
}
