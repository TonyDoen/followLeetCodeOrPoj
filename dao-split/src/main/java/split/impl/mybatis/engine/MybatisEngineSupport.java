package split.impl.mybatis.engine;

import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import split.engine.EngineSupport;

/**
 * tools for manage SQL session and configuration
 * @author zhuxinze
 *
 */
public final class MybatisEngineSupport implements EngineSupport<MybatisEngineStatus> {
	
	private Logger logger = LoggerFactory.getLogger(MybatisEngineSupport.class);
	
	private static MybatisEngineSupport engineSupport;
	
	private MybatisEngineSupport(){
		try {
			init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * default engine index
	 */
	private String defaultIndex;
	
	/**
	 * default engine
	 */
	private SqlSessionFactory defautlSqlSessionFactory;
	
	/**
	 * container for SqlSessionFactory
	 */
	private Map<String,SqlSessionFactory> factorys;
	
	/**
	 * configure the SqlsessionFactory
	 * @throws Exception 
	 */
	private void init() throws Exception{
		Reader reader = Resources.getResourceAsReader("mybatis/mybatis-config.xml");
		factorys = new MultipleSqlSessionFactoryBuilder().buildMulti(reader);
		//get the default sqlSessionFactory
		for(String strIndex : this.factorys.keySet()){
			int intIndex = strIndex.indexOf("_default");
			if(intIndex>=0){
				this.defaultIndex = strIndex.substring(0, intIndex);
				this.defautlSqlSessionFactory = this.factorys.get(strIndex);
				this.factorys.remove(strIndex);
				this.factorys.put(defaultIndex, defautlSqlSessionFactory);
				break;
			}
		}
	}
	
	public static MybatisEngineSupport singleTon(){
		if(engineSupport==null){
			engineSupport = new MybatisEngineSupport();
		}
		return engineSupport;
	}
	
	public String getDefaultEngineIndex() {
		return this.defaultIndex;
	}

	public String[] getAllEnginesIndexs() {
		return (String[]) this.factorys.keySet().toArray();
	}

	@Override
	public Map<String, MybatisEngineStatus> getEngine(String... params) {
		if(ArrayUtils.isEmpty(params)){
			return null;
		}
		Map<String, MybatisEngineStatus> mapRes = new HashMap<String, MybatisEngineStatus>();
		for(String strIndex : params){
			SqlSessionFactory sqlSessionFactory = this.factorys.get(strIndex);
			if(sqlSessionFactory!=null){
				SqlSession openSession = sqlSessionFactory.openSession();
				MybatisEngineStatus engineStatus = new MybatisEngineStatus(openSession);
				engineStatus.setIndex(strIndex);
				mapRes.put(strIndex, engineStatus);
				logger.debug(">>>> create engine instance::"+strIndex+"::"+engineStatus);
			}else{
				mapRes.put(strIndex, null);
			}
		}
		return mapRes;
	}
	
	@Override
	public Map<String, MybatisEngineStatus> getDefaultEngine() {
		Map<String, MybatisEngineStatus> mapRes = new HashMap<String, MybatisEngineStatus>();
		SqlSession openSession = defautlSqlSessionFactory.openSession();
		MybatisEngineStatus engineStatus = new MybatisEngineStatus(openSession);
		engineStatus.setIndex(this.defaultIndex);
		mapRes.put(defaultIndex, engineStatus);
		logger.debug(">>>> create default engine instance::"+this.defaultIndex+"::"+engineStatus);
		return mapRes;
	}
	
	@Override
	public Map<String, MybatisEngineStatus> getAllEngines() {
		Map<String, MybatisEngineStatus> mapRes = new HashMap<String, MybatisEngineStatus>();
		for(String strIndex : this.factorys.keySet()){
			SqlSessionFactory sqlSessionFactory = this.factorys.get(strIndex);
			SqlSession openSession = sqlSessionFactory.openSession();
			MybatisEngineStatus engineStatus = new MybatisEngineStatus(openSession);
			engineStatus.setIndex(strIndex);
			mapRes.put(strIndex, engineStatus);
			logger.debug(">>>> create engine instance::"+strIndex+"::"+engineStatus);			
		}
		return mapRes;
	}
	
	/** 
	 * return default actual engine, the given key's SqlSession
	 * @return
	 */
	public SqlSession getDefaultSqlSession(){
		return this.factorys.get(defaultIndex).openSession();
	}
	
	/**
	 * get the given index's appropriate SqlSession
	 * @param index
	 * @return
	 */
	public SqlSession getSession(String index){
		SqlSessionFactory sessionFactory = this.factorys.get(index);
		if(sessionFactory!=null){
			return sessionFactory.openSession();
		}else{
			return null;
		}
	}
	
	/**
	 * get all sessions
	 * @return
	 */
	public Map<String, SqlSession> getAllSessions(){
		Map<String, SqlSession> mapRes = new HashMap<String, SqlSession>();
		for(String strIndex : this.factorys.keySet()){
			SqlSessionFactory sqlSessionFactory = this.factorys.get(strIndex);
			SqlSession openSession = sqlSessionFactory.openSession();
			mapRes.put(strIndex, openSession);
		}
		return mapRes;
	}
}
