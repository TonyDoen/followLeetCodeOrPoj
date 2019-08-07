package split.action;

/**
 *  the call back interface for engine executing
 * @author zhuxinze
 *
 * @param <T>
 */
public interface CallBack<T> {

	public Object doAction(T engine);
}
