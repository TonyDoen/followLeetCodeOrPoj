package split.exception;

/**
 * exception threw from the executing engine
 * @author zhuxinze
 *
 */
public class EngineException extends RuntimeException {

	/**
	 * default serialization id
	 */
	private static final long serialVersionUID = 1L;

	public EngineException() {
		super();
	}

	public EngineException(String message) {
		super(message);
	}

	public EngineException(String message, Throwable cause) {
		super(message, cause);
	}

	public EngineException(Throwable cause) {
		super(cause);
	}

}