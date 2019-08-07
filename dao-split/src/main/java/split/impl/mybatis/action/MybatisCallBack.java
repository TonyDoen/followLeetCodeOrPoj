package split.impl.mybatis.action;

import org.apache.ibatis.session.SqlSession;

import split.action.CallBack;

/**
 * callback Interface for execution
 * @author zhuxinze
 *
 */
public interface MybatisCallBack extends CallBack<SqlSession> {
	
}
