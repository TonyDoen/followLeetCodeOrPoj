package split.support.exception;

/**
 * exception threw by Service or DAO loader
 * @author zhuxinze
 *
 */
public class LoaderException extends RuntimeException {
	
	/**
	 * defautl serial id
	 */
	private static final long serialVersionUID = 1L;
	
	public LoaderException() {
		super();
	}

	public LoaderException(String message) {
		super(message);
	}

	public LoaderException(String message, Throwable cause) {
		super(message, cause);
	}

	public LoaderException(Throwable cause) {
		super(cause);
	}
}
