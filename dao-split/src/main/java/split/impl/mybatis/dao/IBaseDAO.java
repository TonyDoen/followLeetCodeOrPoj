package split.impl.mybatis.dao;

/**
 * base dao interface
 * @author zhuxinze
 *
 * @param <T> manipulated type
 * @param <K> manipulated type's id type
 */
public interface IBaseDAO<T, K> {
	
	/**
	 * insert new object
	 * @param object
	 * @return
	 */
	public K insert(T object) throws Exception;
	
	/**
	 * update object
	 * @param object
	 */
	public void update(T object)  throws Exception;
	
	/**
	 * delete the given object
	 * @param object
	 */
	public void delete(T object)  throws Exception;
	
	/**
	 * delete by id
	 * @param object
	 */
//	public void deleteById(K object)  throws Exception;
	
	/**
	 * select by id
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public T selectById(T object) throws Exception;
	
}
