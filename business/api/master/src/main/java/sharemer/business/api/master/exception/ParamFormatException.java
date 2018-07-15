package sharemer.business.api.master.exception;

public class ParamFormatException extends BaseException {

	private static final long serialVersionUID = -3102328003108159211L;

	public ParamFormatException() {
		super();
	}

	public ParamFormatException(String message, Throwable cause,
                                boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ParamFormatException(String message, Throwable cause) {
		super(message, cause);
	}

	public ParamFormatException(String message) {
		super(message);
	}

	public ParamFormatException(Throwable cause) {
		super(cause);
	}

}
