package split.impl.mybatis.engine;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import split.engine.EngineStatus;
import split.exception.EngineException;

/**
 * Mybatis engineStatus implementation 
 * we can support distributed query here
 * @author zhuxinze
 *
 */
@SuppressWarnings("rawtypes")
public class MybatisEngineStatus extends EngineStatus implements SqlSession{
	
	private Logger logger = LoggerFactory.getLogger(MybatisEngineStatus.class);
	
	private SqlSession session;
	
	public MybatisEngineStatus(){};
	
	public MybatisEngineStatus(SqlSession session){
		this.session = session;
	}
	
	@Override
	public void statusRollback() {
		if(this.isValid()){
			this.session.rollback();
		}else{
			logger.error("session has been committed or rolled back or closed");
			throw new EngineException("session has been committed or rolled back or closed");
		}
	}

	@Override
	public void statusCommit() {
		if(this.isValid()){
			this.session.commit();
		}else{
			logger.error("session has been committed or rolled back or closed");
			throw new EngineException("session has been committed or rolled back or closed");
		}	
	}

	@Override
	public void statusClose() {
		if(this.state!=ENGINE_COMPLETED){
			if(this.needX==NEED_X){
				this.commit();
			}
			this.close();
			this.state = ENGINE_COMPLETED;
			logger.debug(">>>> sesssion closed::"+this);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public SqlSession getActualEngine() {
		return this.session;
	}

	public void rollback() {
//		if(this.needX==DONE_COMMIT_X || this.needX==DONE_ROLLBACK_X){
//			logger.error("session has been committed or rolled back");
//			return;
//		}
//		if(this.state==ENGINE_COMPLETED){
//			logger.error("session has been closed");
//			return;
//		}
//		this.session.rollback();
//		this.needX = DONE_ROLLBACK_X;
		this.rollback(false);
	}
	
	public void rollback(boolean force) {
		if(this.needX==DONE_COMMIT_X || this.needX==DONE_ROLLBACK_X){
			logger.error("session has been committed or rolled back");
			throw new EngineException("session has been committed or rolled back");
		}
		if(this.state==ENGINE_COMPLETED){
			throw new EngineException("session has been closed");
		}
		this.session.rollback(force);
		this.needX = DONE_ROLLBACK_X;
	}	

	public void commit() {
//		if(this.needX==DONE_COMMIT_X || this.needX==DONE_ROLLBACK_X){
//			logger.error("session has been committed or rolled back");
//			return;
//		}
//		if(this.state==ENGINE_COMPLETED){
//			logger.error("session has been closed");
//			return;
//		}
//		this.session.commit();
//		this.needX = DONE_COMMIT_X;
		this.commit(false);
	}
	
	@Override
	public void commit(boolean force) {
		if(this.needX==DONE_COMMIT_X || this.needX==DONE_ROLLBACK_X){
			logger.error("session has been committed or rolled back");
			throw new EngineException("session has been committed or rolled back");
		}
		if(this.state==ENGINE_COMPLETED){
			logger.error("session has been closed");
			throw new EngineException("session has been closed");
		}
		this.session.commit(force);
		this.needX = DONE_COMMIT_X;
	}

	public void close() {
		if(this.state==ENGINE_COMPLETED){
			logger.error("session has been closed");
			throw new EngineException("session has been closed");
		}
		if(this.needX==NEED_X){
			this.session.commit();
			this.needX = DONE_COMMIT_X;
		}
		this.session.close();
		this.state = ENGINE_COMPLETED;
	}

	@Override
	public boolean isValid() {
		if(this.state == EngineStatus.ENGINE_COMPLETED || 
				this.needX==EngineStatus.DONE_COMMIT_X || this.needX==EngineStatus.DONE_ROLLBACK_X){
			return false;
		}
		return true;
	}

	@Override
	public Object selectOne(String statement) {
		this.count++;
		return this.session.selectOne(statement);
	}

	public Object selectOne(String statement, Object parameter) {
		this.count++;
		return this.session.selectOne(statement, parameter);
	}

	public List selectList(String statement) {
		this.count++;
		return this.session.selectList(statement);
	}

	public List selectList(String statement, Object parameter) {
		this.count++;
		return this.session.selectList(statement, parameter);
	}

	public List selectList(String statement, Object parameter,
			RowBounds rowBounds) {
		this.count++;
		return this.session.selectList(statement, parameter, rowBounds);
	}

//	public Map selectMap(String statement, String mapKey) {
//		this.count++;
//		return this.session.selectMap(statement, mapKey);
//	}
//
//	public Map selectMap(String statement, Object parameter, String mapKey) {
//		this.count++;
//		return this.session.selectMap(statement, parameter, mapKey);
//	}
//
//	public Map selectMap(String statement, Object parameter, String mapKey,
//			RowBounds rowBounds) {
//		this.count++;
//		return this.session.selectMap(statement, parameter, mapKey, rowBounds);
//	}

	public void select(String statement, Object parameter, ResultHandler handler) {
		this.count++;
		this.session.select(statement, parameter, handler);
	}

//	public void select(String statement, ResultHandler handler) {
//		this.count++;
//		this.session.select(statement, handler);
//		
//	}

	public void select(String statement, Object parameter, RowBounds rowBounds,
			ResultHandler handler) {
		this.count++;
		this.session.select(statement, parameter, rowBounds, handler);		
	}

	public int insert(String statement) {
		this.count++;
		if(this.needX==NO_NEED_X){
			this.needX = NEED_X;
		}
		return this.session.insert(statement);
	}

	public int insert(String statement, Object parameter) {
		this.count++;
		if(this.needX==NO_NEED_X){
			this.needX = NEED_X;
		}
		return this.session.insert(statement, parameter);
	}

	public int update(String statement) {
		this.count++;
		if(this.needX==NO_NEED_X){
			this.needX = NEED_X;
		}
		return this.session.update(statement);
	}

	public int update(String statement, Object parameter) {
		this.count++;
		if(this.needX==NO_NEED_X){
			this.needX = NEED_X;
		}
		return this.session.update(statement, parameter);
	}

	public int delete(String statement) {
		this.count++;
		if(this.needX==NO_NEED_X){
			this.needX = NEED_X;
		}
		return this.session.delete(statement);
	}

	public int delete(String statement, Object parameter) {
		this.count++;
		if(this.needX==NO_NEED_X){
			this.needX = NEED_X;
		}
		return this.session.delete(statement, parameter);
	}

	public void clearCache() {
		this.session.clearCache();
	}

	public Configuration getConfiguration() {
		return this.session.getConfiguration();
	}

	public <T> T getMapper(Class<T> type) {
		return this.session.getMapper(type);
	}

	public Connection getConnection() {
		return this.session.getConnection();
	}

	public SqlSession getSession() {
		return session;
	}

	public void setSession(SqlSession session) {
		this.session = session;
	}
}
