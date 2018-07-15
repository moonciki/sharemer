package sharemer.component.global.resp;

import java.util.Map;

/**
 * Created by 18073 on 2017/5/21.
 */
public class WrappedResult {

	public static WrappedResult fail(ConstantResults result) {
		return new WrappedResult(null, result.getCode(), result.getMessage());
	}

	public static WrappedResult fail(String message) {
		return new WrappedResult(null, -500, message);
	}

	public static WrappedResult fail(int code, String message) {
		return new WrappedResult(null, code, message);
	}

	public static WrappedResult success(){
			return new WrappedResult(null, ConstantResults.SUCCESS.getCode(),
					ConstantResults.SUCCESS.getMessage());
	}
	
	public static WrappedResult success(Object result) {
		return new WrappedResult(result, ConstantResults.SUCCESS.getCode(),
				ConstantResults.SUCCESS.getMessage());
	}

	public static WrappedResult success(Object result,
                                                           Map<String, Object> inspectInfo) {
		return new WrappedResult(result, ConstantResults.SUCCESS.getCode(),
				ConstantResults.SUCCESS.getMessage());
	}

	private Object result;

	private int code;
	
	private String message;

	private WrappedResult(Object result) {
		this.result = result;
	}

	private WrappedResult(Object result, int code, String message) {
		this(result);
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public Object getResult() {
		return result;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setResult(Object result) {
		this.result = result;
	}
}
