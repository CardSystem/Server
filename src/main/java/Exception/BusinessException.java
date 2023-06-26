package Exception;

public class BusinessException extends Exception {

	private ErrorCode errorCode;
	private String message;

	public BusinessException(ErrorCode errorCode, String message) {

		super(message);
		this.errorCode = errorCode;

	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}
}