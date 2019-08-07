package split.aop;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import split.annotation.SplitPolicy;
import split.engine.EngineContext;
import split.engine.EngineStatus;
import split.engine.EngineSupport;
import split.exception.SplitException;
import split.policy.IPolicy;
import split.transaction.annotation.Transactional;


/**
 * distributed database and table split for DAO layer
 * @author zhuxinze
 *
 */
public class DAOSplitHandler implements InvocationHandler {
	
	private Logger logger = LoggerFactory.getLogger(DAOSplitHandler.class);
	
	/**
	 * target object
	 */
	private Object targetObj;
	
	/**
	 * concrete EngineSupport implementation
	 */
	private EngineSupport<EngineStatus> engineSupport;
	
	/**
	 * concrete EngineContext implementation
	 */
	@SuppressWarnings("rawtypes")
	private EngineContext engineContext;
	
	/**
	 * cache the Method's Split annotation, 
	 * if there is no Split annotation, bind NULL to the Method
	 */
	private Map<Method,SplitPolicy> mapSplitAnnotation = new HashMap<Method,SplitPolicy>();
	
	/**
	 * cache the Method's transaction annotation
	 * if there is no Split annotation,bind NULL to the Method
	 */
	private Map<Method,Transactional> mapTransAnnotation = new HashMap<Method,Transactional>(); 
	
	/**
	 * constructor
	 * @param targetObject executor
	 * @param engineSupport
	 * @param engineContext
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public DAOSplitHandler(Object targetObject, EngineSupport engineSupport, EngineContext engineContext){
		this.targetObj = targetObject;
		this.engineSupport = engineSupport;
		this.engineContext = engineContext;
	}
	
	/**
	 * create proxy object
	 * @param targetObject
	 * @return
	 */
	public Object newProxyInstance(){
		return Proxy.newProxyInstance(this.targetObj.getClass().getClassLoader(), this.targetObj.getClass().getInterfaces(), this);
	}
	
	/**
	 * parse the annotation, do the specified command
	 */
	@SuppressWarnings({ "unchecked"})
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		SplitPolicy policy =null;
		if(this.mapSplitAnnotation == null){
			this.mapSplitAnnotation = new HashMap<Method,SplitPolicy>();
		}
		if(this.mapSplitAnnotation.containsKey(method)){
			policy = this.mapSplitAnnotation.get(method);
		}else{
			//get the annotation on the concrete implementation type
			Method methodConcrete = this.targetObj.getClass().getMethod(method.getName(), method.getParameterTypes());
			policy = methodConcrete.getAnnotation(SplitPolicy.class);
			//cache the Method's split annotation
			this.mapSplitAnnotation.put(method, policy);
		}
		
		Transactional trans = null;
		if(this.mapTransAnnotation == null){
			this.mapTransAnnotation = new HashMap<Method,Transactional>();
		}
		if(this.mapTransAnnotation.containsKey(method)){
			trans = this.mapTransAnnotation.get(method);
		}else{
			//get the annotation on the concrete implementation type
			Method methodConcrete = this.targetObj.getClass().getMethod(method.getName(), method.getParameterTypes());
			trans = methodConcrete.getAnnotation(Transactional.class);
			//cache the Method's split annotation
			this.mapTransAnnotation.put(method, trans);			
		}
		
		//if the policy is null, then select the default SqlSession or some other executor engine
		//first get the executor engine,push to the ThreadLocal stack;also execute attachment operations, 
		//such as generate primary Key, get the cluster id、database key、table key
		if(policy == null){
			logger.debug(">>>> the method uses default engine::"+method.getDeclaringClass()+"."+method.getName());
			
			EngineStatus engineStatus = this.getEngineStatus(engineSupport.getDefaultEngineIndex());
			this.engineContext.push(engineStatus);
		}else{
			logger.debug(">>>> the method uses split policy to find engine::"+method.getDeclaringClass()+"."+method.getName());
			
			//call the targetObj's respective split method
			switch(policy.type()){
				case INSERT:
					//get primary key first,if has given the method name,user it to generate id, 
					//else use the default method to generate id, we strongly suggest that end user use the default method, 
					//so it would use less time to execute
					
					//assign the id to the object
					Field field = null;
					if(StringUtils.isBlank(policy.fieldName2setId())){
						field = args[0].getClass().getDeclaredField(IPolicy.DEFAULT_NAME_SET_ID_FIELD);
					}else{
						field = args[0].getClass().getDeclaredField(policy.fieldName2setId());
					}
					field.setAccessible(true);
					Object id = field.get(args[0]);
					if(id==null){						
						Method methodCreate = null;
						if(StringUtils.isBlank(policy.methodName())){
							try{
								logger.debug(">>>> the object to be added uses the default method to generate id::"+args[0].getClass().getName());
								
								methodCreate = IPolicy.getMethod(IPolicy.DEFAULT_NAME_CREATE_KEY_METHOD);
							}catch(Exception e){
								throw new SplitException("use the split framework , you should implements the IPolicy "
										+ "interface or fill up the method name for generate id in SplitPolicy annotation",e);
							}
						}else{
							methodCreate = args[0].getClass().getMethod(policy.methodName(),new Class[0]);
							if(methodCreate==null){
								throw new SplitException("there is no method named '"+policy.methodName()+"' exists in the Class::"+args[0].getClass().getName()); 
							}
							logger.debug(">>>> the object to be added uses the given method::"+methodCreate.getName()+", to generate id::"+args[0].getClass().getName());
						}
						id = methodCreate.invoke(args[0]);
						field.set(args[0], id);
					}else{
						logger.debug(">>>> the object to be added takes id by self::"+args[0].getClass().getName()+",::"+id);
					}
					
					//select engine
					Method methodGetDbId  = null;
					if(StringUtils.isBlank(policy.methodName())){
						try{
							methodGetDbId = IPolicy.getMethod(IPolicy.DEFAULT_NAME_GET_DB_ID_METHOD);
						}catch(Exception e){
							throw new SplitException("use the split framework, you should implements the IPolicy "
									+ "interface or fill up the method name for get database id in SplitPolicy annotation",e);
						}
					}else{
						methodGetDbId = args[0].getClass().getMethod(policy.methodName(),new Class[0]);
						if(methodGetDbId==null){
							throw new SplitException("there is no method named '"+policy.methodName()+"' exists in the Class::"+args[0].getClass().getName()); 
						}						
					}
					//data source id
					String strDbId = (String) methodGetDbId.invoke(args[0]);
					EngineStatus engineStatus = this.getEngineStatus(strDbId);
					this.engineContext.push(engineStatus);
					break;
				case UPDATE:
					//select engine
					if(StringUtils.isBlank(policy.methodName())){
						try{
							methodGetDbId = IPolicy.getMethod(IPolicy.DEFAULT_NAME_GET_DB_ID_METHOD);
						}catch(Exception e){
							throw new SplitException("use the split framework , you should implements the IPolicy "
									+ "interface or setup the method name for get database id in SplitPolicy annotation",e);
						}
					}else{
						methodGetDbId = args[0].getClass().getMethod(policy.methodName(),new Class[0]);
						if(methodGetDbId==null){
							throw new SplitException("there is no method named '"+policy.methodName()+"' exists in the Class::"+args[0].getClass().getName());
						}
					}
					//data source id
					strDbId = (String) methodGetDbId.invoke(args[0]);
					engineStatus = this.getEngineStatus(strDbId);
					this.engineContext.push(engineStatus);
					break;
				case DELETE:
					//select engine
					if(StringUtils.isBlank(policy.methodName())){
						try{
						methodGetDbId = IPolicy.getMethod(IPolicy.DEFAULT_NAME_GET_DB_ID_METHOD);
						}catch(Exception e){
							throw new SplitException("use the split framework , you should implements the IPolicy "
									+ "interface or setup the method name for get database id in SplitPolicy annotation",e);
						}
					}else{
						methodGetDbId = args[0].getClass().getMethod(policy.methodName(),new Class[0]);
						if(methodGetDbId==null){
							throw new SplitException("there is no method named '"+policy.methodName()+"' exists in the Class::"+args[0].getClass().getName());
						}
					}
					//data source id
					strDbId = (String) methodGetDbId.invoke(args[0]);
					engineStatus = this.getEngineStatus(strDbId);
					this.engineContext.push(engineStatus);
					break;
				case QUERY:
					//select engine
					if(StringUtils.isBlank(policy.methodName())){
						try{
						methodGetDbId = IPolicy.getMethod(IPolicy.DEFAULT_NAME_GET_DB_ID_METHOD);
						}catch(Exception e){
							throw new SplitException("use the split framework , you should implements the IPolicy "
									+ "interface or setup the method name for get database id in SplitPolicy annotation",e);
						}
					}else{
						methodGetDbId = args[0].getClass().getMethod(policy.methodName(),new Class[0]);
						if(methodGetDbId==null){
							throw new SplitException("there is no method named '"+policy.methodName()+"' exists in the Class::"+args[0].getClass().getName());
						}
					}
					//data source id
					strDbId = (String) methodGetDbId.invoke(args[0]);
					engineStatus = this.getEngineStatus(strDbId);
					this.engineContext.push(engineStatus);
					break;
				case MULTIQUERY:
					//do nothing, let the end user to get data source manually
					break;
				default:
					//do nothing, let the end user to get data source manually
					break;
			}
		}
		
		//assign true to the transaction flag, if this operation needs transaction manage
		if(trans!=null){
			this.engineContext.pop().setNeedX(EngineStatus.NEED_X);
		}
		
		//need to close the engine manually
		if(this.engineContext.getHolisticNestedLevel()==0){
			logger.debug(">>>> the method invokation not in holistic controller:"+method.getName());
			
			try{
				Object objRes = method.invoke(targetObj, args);
				//if the session has not been committed by the end user manually, should be committed here
				if(this.engineContext.pop().getNeedX() == EngineStatus.NEED_X){
					this.engineContext.pop().statusCommit();
				}
				return objRes;
			}catch(Exception e){
				logger.error("some error happend when executing the DAO function",e);
				//roll back executed operation
				if(this.engineContext.pop().getNeedX() == EngineStatus.NEED_X){
					this.engineContext.pop().statusRollback();
				}
				throw new SplitException("some error happend where service executing",e);
			}finally{
				this.engineContext.pop().statusClose();
			}
		}else{
			logger.debug(">>>> the method invoke in holistic controller::"+method.getDeclaringClass()+"."+method.getName()+", and method level::"+this.engineContext.getHolisticNestedLevel());
			
			//delegate the engine management to service split handler
			Object objRes = method.invoke(targetObj, args);
			return objRes;
		}
	}
	
	/**
	 * get the given key's appropriate engine
	 * @param index
	 * @return
	 * @throws Exception 
	 */
	private EngineStatus getEngineStatus(String index) throws Exception{
		EngineStatus engineStatus = this.engineContext.getExistEngine(index);
		if(engineStatus!=null){
			return engineStatus;
		}
		Map<String, EngineStatus> map = this.engineSupport.getEngine(index);
		if(MapUtils.isNotEmpty(map) && map.get(index)!=null){
			return map.get(index);
		}
		throw new Exception("there is no appropriate engine bound to the given index::"+index);
	}
}
