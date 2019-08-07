package split.impl.mybatis.dao.base;

import java.lang.reflect.ParameterizedType;

import org.apache.ibatis.session.SqlSession;

import split.annotation.SplitPolicy;
import split.annotation.SplitPolicy.OperationType;
import split.engine.EngineStatus;
import split.impl.mybatis.action.MybatisCallBack;
import split.impl.mybatis.dao.IBaseDAO;
import split.impl.mybatis.engine.MybatisEngineContext;
import split.impl.mybatis.engine.MybatisEngineStatus;
import split.impl.mybatis.engine.MybatisEngineSupport;
import split.transaction.annotation.Transactional;

/**
 * implementation of IBaseDAO interface
 * @author zhuxinze
 *
 * @param <T> class Type
 * @param <K> key class type
 */
public class BaseDAOImpl<T,K> implements IBaseDAO<T, K> {
	
	/**
	 * class type of the given T
	 */
	private Class<T> clazz;
	
	@SuppressWarnings("unchecked")
	public BaseDAOImpl(){
		this.clazz = ((Class<T>) ((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
	}
	
	/**
	 * get session directly, in this circumstance, end user need to manipulate the session manually,e.g: commit, rollback, close 
	 * @param dbId
	 * @return
	 */
	public SqlSession getSesssion(String dbId){
		SqlSession session = MybatisEngineSupport.singleTon().getSession(dbId);
		return session;
	}
	
	/**
	 * executor
	 * @throws Exception 
	 */
	public Object doAction(MybatisCallBack callback) throws Exception{
		//get the SqlSession object according to the context
		MybatisEngineStatus session = MybatisEngineContext.singleTon().pop();
		if(session==null){
			throw new Exception("there is no SqlSession in the context, should push a SqlSession to the context first");
		}
		Object objRes = callback.doAction(session);
		//check the session state , whether end user has commit or close the session
		session.setState(EngineStatus.ENGINE_RUNNING);
		//TODO later need to implements the function of multiple thread to get data from all data sources
		return objRes;
	}
	
	@SplitPolicy(type=OperationType.INSERT)
	@Transactional()
	@SuppressWarnings("unchecked")
	public K insert(T object) throws Exception{
		final T obj = object;
		return (K) this.doAction(new MybatisCallBack(){
			public Object doAction(SqlSession session) {
				int insert = session.insert(clazz.getName()+".insert", obj);
				return insert;
			}
		});
	}

	@SplitPolicy(type=OperationType.UPDATE)
	@Transactional()
	public void update(T object) throws Exception{
		final T obj = object;
		this.doAction(new MybatisCallBack() {
			public Object doAction(SqlSession session) {
				session.update(clazz.getName()+".update", obj);
				return null;
			}
		});
	}

	@SplitPolicy(type=OperationType.DELETE)
	@Transactional()
	public void delete(T object) throws Exception{
		final T obj = object;
		this.doAction(new MybatisCallBack() {
			public Object doAction(SqlSession session) {
				session.delete(clazz.getName()+".delete", obj);
				return null;
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	@SplitPolicy(type=OperationType.QUERY)
	public T selectById(T object) throws Exception {
		final T obj = object;
		return (T) this.doAction(new MybatisCallBack() {
			public Object doAction(SqlSession session) {
				T objRes = (T) session.selectOne(clazz.getName()+".selectById", obj);
				return objRes;
			}
		});
	}	

	public Class<T> getClazz() {
		return clazz;
	}


	public void setClazz(Class<T> clazz) {
		this.clazz = clazz;
	}
}
