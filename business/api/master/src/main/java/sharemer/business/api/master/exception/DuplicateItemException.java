package sharemer.business.api.master.exception;

public class DuplicateItemException extends RuntimeException{

	public DuplicateItemException() {
		super();
	}

	public DuplicateItemException(String message) {
		super(message);
	}
	
}
