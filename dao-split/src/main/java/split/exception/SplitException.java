package split.exception;

/**
 * exception threw from the split operation
 * @author zhuxinze
 *
 */
public class SplitException extends Exception {

	/**
	 * default serial id
	 */
	private static final long serialVersionUID = -4006665997619304027L;
	
	public SplitException() {
		super();
	}

	public SplitException(String message) {
		super(message);
	}

	public SplitException(String message, Throwable cause) {
		super(message, cause);
	}

	public SplitException(Throwable cause) {
		super(cause);
	}
}
