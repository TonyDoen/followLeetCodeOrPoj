package split.policy;

import java.lang.reflect.Method;

import org.apache.commons.lang3.StringUtils;


/**
 * database table split policy interface
 * @author zhuxinze
 * K 主键类型
 */
public abstract class IPolicy<K> {
	
	/**
	 * default method name to create id
	 */
	public static final String DEFAULT_NAME_CREATE_KEY_METHOD = "createKey";
	
	
	/**
	 * default method to create id
	 */
	private static Method DEFAULT_CREATE_KEY_METHOD;
	
	/**
	 * default method name to get cluster id
	 */
	public static final String DEFAULT_NAME_GET_CLUSTER_ID_METHOD = "getClusterId";
	
	/**
	 * default method to get cluster id
	 */
	private static Method DEFAULT_GET_CLUSTER_ID_METHOD;
	
	/**
	 * default method name to get database id
	 */
	public static final String DEFAULT_NAME_GET_DB_ID_METHOD = "getDBId";
	
	/**
	 * default method to get database id
	 */
	private static Method DEFAULT_GET_DB_ID_METHOD;
	
	/**
	 * default method name to get table id
	 */
	public static final String DEFAULT_NAME_GET_TABLE_ID_METHOD = "getTableId";
	
	/**
	 * default method to get table id
	 */
	private static Method DEFAULT_GET_TABLE_ID_METHOD;
	
	/**
	 * default method name to set id
	 */
	public static final String DEFAULT_NAME_SET_ID_FIELD = "id";
	
	
	/**
	 * get default method
	 * @param methodName
	 * @return 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 */
	public static Method getMethod(String methodName) throws NoSuchMethodException, SecurityException{
		
		if(StringUtils.isBlank(methodName)){
			return null;
		}else if(methodName.equals(DEFAULT_NAME_CREATE_KEY_METHOD)){
			if(DEFAULT_CREATE_KEY_METHOD==null){
				DEFAULT_CREATE_KEY_METHOD = IPolicy.class.getMethod(DEFAULT_NAME_CREATE_KEY_METHOD, new Class[0]);
			}
			return DEFAULT_CREATE_KEY_METHOD;
		}else if(methodName.equals(DEFAULT_NAME_GET_CLUSTER_ID_METHOD)){
			if(DEFAULT_GET_CLUSTER_ID_METHOD==null){
				DEFAULT_GET_CLUSTER_ID_METHOD = IPolicy.class.getMethod(DEFAULT_NAME_GET_CLUSTER_ID_METHOD, new Class[0]); 
			}
			return DEFAULT_GET_CLUSTER_ID_METHOD;
		}else if(methodName.equals(DEFAULT_NAME_GET_DB_ID_METHOD)){
			if(DEFAULT_GET_DB_ID_METHOD==null){
				DEFAULT_GET_DB_ID_METHOD = IPolicy.class.getMethod(DEFAULT_NAME_GET_DB_ID_METHOD, new Class[0]);
			}
			return DEFAULT_GET_DB_ID_METHOD;
		}else if(methodName.equals(DEFAULT_NAME_GET_TABLE_ID_METHOD)){
			if(DEFAULT_GET_TABLE_ID_METHOD==null){
				DEFAULT_GET_TABLE_ID_METHOD = IPolicy.class.getMethod(DEFAULT_NAME_GET_TABLE_ID_METHOD, new Class[0]);
			}
			return DEFAULT_GET_TABLE_ID_METHOD;
		}else{
			return null;
		}
		
	}
	
	/**
	 * generate primary key
	 * @return
	 */
	public abstract K createKey();
		
	/**
	 * get cluster id
	 * @param id
	 * @return
	 */
	public abstract String getClusterId();
	
	/**
	 * get database id
	 * @param id
	 * @return
	 */
	public abstract String getDBId();
	
	/**
	 * get databases id by cluster id
	 * @param clusterId
	 * @return
	 */
	public abstract String[] getDBIdsByClusterId(String clusterId);
	
	/**
	 * get table id
	 * @param id
	 * @return
	 */
	public abstract String getTableId();
	
	/**
	 * get tables id by database id
	 * @return
	 */
	public abstract String[] getTableIdsByDbId(String id);
	
	/**
	 * get all clusters id
	 * @return
	 */
	public abstract String[] getAllClusterId();
	
	/**
	 * get all databases id
	 * @return
	 */
	public abstract String[] getAllDBId();
	
	/**
	 * get all tables id
	 * @return
	 */
	public abstract String[] getAllTableId();
}
