package split.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import split.annotation.HolisticController;
import split.exception.HolisticException;
import split.impl.mybatis.engine.MybatisEngineContext;

/**
 * distributed database transaction manger for Service layer
 * @author zhuxinze
 *
 */
public class ServiceSplitHandler implements InvocationHandler {

	private Logger logger = LoggerFactory.getLogger(ServiceSplitHandler.class);
	
	private Object targetObject;
	
	private Map<Method,HolisticController> mapHolisticAnnotation = new HashMap<Method, HolisticController>();
	
	public ServiceSplitHandler(Object targetObject){
		this.targetObject = targetObject;
	}
	
	public Object newInstance(){
		return Proxy.newProxyInstance(this.targetObject.getClass().getClassLoader(), this.targetObject.getClass().getInterfaces(), this);
	}

	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		HolisticController holistic =null;
		if(this.mapHolisticAnnotation == null){
			this.mapHolisticAnnotation = new HashMap<Method,HolisticController>();
		}
		if(this.mapHolisticAnnotation.containsKey(method)){
			holistic = this.mapHolisticAnnotation.get(method);
		}else{
			//get the annotation on the concrete implementation type
			Method methodConcrete = this.targetObject.getClass().getMethod(method.getName(), method.getParameterTypes());
			holistic = methodConcrete.getAnnotation(HolisticController.class);
			//cache the Method's split annotation
			this.mapHolisticAnnotation.put(method, holistic);
		}
		
		//this method needs holistic transaction management, assign true to the holistic flag
		if(holistic!=null){
			logger.debug(">>>> method::"+method.getDeclaringClass()+"."+method.getName()+" supports holistic controller");
			MybatisEngineContext.singleTon().setHolistic(true);
		}else{
			logger.debug(">>>> method::"+method.getDeclaringClass()+"."+method.getName()+" does't support holistic controller");
			MybatisEngineContext.singleTon().setHolistic(false);
		}
		
		try{
			Object objRes = method.invoke(targetObject, args);
			return objRes;
		}catch(Exception e){
			logger.error("some error happend where service executing",e);
			//some error happen, roll back all execution
			if(MybatisEngineContext.singleTon().getHolisticNestedLevel()==1){
				MybatisEngineContext.singleTon().rollback();
			}
			throw new HolisticException("some error happend where service executing",e);
		}finally{
			logger.debug(">>>> method::"+method.getDeclaringClass()+"."+method.getName()+", the level num in the holistic controller stack::"+MybatisEngineContext.singleTon().getHolisticNestedLevel());
			if(MybatisEngineContext.singleTon().getHolisticNestedLevel()==1){
				MybatisEngineContext.singleTon().removeHolistic();
				//if this function is the outmost one close all engine resource
				MybatisEngineContext.singleTon().close();
				//once executed, clear the context that bound to current thread
				MybatisEngineContext.singleTon().clearContext();
			}else if(MybatisEngineContext.singleTon().getHolisticNestedLevel()>1){
				MybatisEngineContext.singleTon().removeHolistic();
			}else if(MybatisEngineContext.singleTon().getHolisticNestedLevel()==0){
				MybatisEngineContext.singleTon().clearContext();
			}
		}
	}

}
