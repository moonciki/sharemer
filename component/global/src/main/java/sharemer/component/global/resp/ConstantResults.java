package sharemer.component.global.resp;

/**
 * Created by 18073 on 2017/5/21.
 */
public enum ConstantResults {

	ILLEGAL_PARAM(1, "参数错误"),
	SUCCESS(0, "success"),
	APPNOTEXIST(-1,"应用程序不存在或已被封禁"),
	KEYERROR(-2,"管理员 token 出错,请重新登录"),
	APIERROR(-3,"权限不足"),
	PLATFORMERROR(-4,"Platform错误"),
	LACKOFPARAM(-5,"缺少通用实体字段"),
	NODATA(10,"结果为空或者已被删除"),
	NOTFOUND(-404,"结果为空或者已被删除"),
	ERROR(-500,"失败"),
	PERMISSIONDENIED(-403,"权限不足,请联系管理员"),
	NOTLOGIN(-101,"帐号未登陆");

	private int code;
	private String message;

	ConstantResults(int code, String message) {
		this.setCode(code);
		this.setMessage(message);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

}