package split.exception;

public class HolisticException extends RuntimeException {

	/**
	 * default serial id
	 */
	private static final long serialVersionUID = 8356993131585975330L;

	public HolisticException() {
		super();
	}

	public HolisticException(String message) {
		super(message);
	}

	public HolisticException(String message, Throwable cause) {
		super(message, cause);
	}

	public HolisticException(Throwable cause) {
		super(cause);
	}

}
