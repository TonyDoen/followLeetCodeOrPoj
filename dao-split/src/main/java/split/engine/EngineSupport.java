package split.engine;

import java.util.Map;

/**
 * 
 * engine manipulating support class
 * @author zhuxinze
 *
 */
public interface EngineSupport<T extends EngineStatus> {
	
	/**
	 * get executor engine
	 * @return
	 */
	public Map<String, T> getEngine(String... params);
	
	/**
	 * get the default engine index
	 * @return
	 */
	public String getDefaultEngineIndex();
	
	/**
	 * get the default engine
	 * @return
	 */	
	public Map<String,T> getDefaultEngine();
	
	/**
	 * get all engines
	 * @return
	 */
	public Map<String,T> getAllEngines();
	
	/**
	 * get all engines's index
	 * @return
	 */
	public String[] getAllEnginesIndexs();
	
}

