package split.impl.mybatis.engine;

import java.util.LinkedList;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import split.engine.EngineContext;
import split.engine.EngineStatus;

public class MybatisEngineContext extends EngineContext<MybatisEngineStatus> {
	
	private Logger logger = LoggerFactory.getLogger(MybatisEngineContext.class);
	
	/**
	 * singleton instance
	 */
	private static MybatisEngineContext engineContext;
	
	/**
	 * private constructor
	 */
	private MybatisEngineContext(){
		super();
	}
	
	/**
	 * get singleton instance
	 * @return
	 */
	public static MybatisEngineContext singleTon(){
		if(engineContext == null){
			synchronized(MybatisEngineContext.class){
				if(engineContext == null){
					engineContext = new MybatisEngineContext();
				}
			}
		}
		return engineContext;
	}

	@Override
	public boolean checkCount() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean checkValid(MybatisEngineStatus engine) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public MybatisEngineStatus getExistEngine(String index) {
		EngineContextStatus engineContextStatus = this.LocalContextStatus.get();
 		if(engineContextStatus==null){
			logger.info("there is no EngineContextStatus bound with current thread");
			return null;
		}
		LinkedList<MybatisEngineStatus> linkedList = engineContextStatus.getQueue();
		if(CollectionUtils.isNotEmpty(linkedList)){
			for(MybatisEngineStatus engineStatus : linkedList){
				if(engineStatus.getIndex().equals(index) && engineStatus.isValid()){
					logger.debug("there has been SqlSession bound with the given index:"+index+",in the current thread:"+Thread.currentThread().getId());
					return engineStatus;
				}
			}
		}
		logger.debug("there is no SqlSession bound with the given index:"+index+",in the current thread:"+Thread.currentThread().getId());
		return null;		
	}

	@Override
	public void mergeEngine() {
		//just simply implementations, need to rewrite later
		EngineContextStatus engineContextStatus = this.LocalContextStatus.get();
		if(engineContextStatus==null){
			logger.warn("there is no engineContextStatus bound with current thread:"+Thread.currentThread().getId());
			return;
		}
		LinkedList<MybatisEngineStatus> linkedList = engineContextStatus.getQueue();
		if(CollectionUtils.isEmpty(linkedList)){
			logger.warn("there is no engineStatus bound with current thread:"+Thread.currentThread().getId());
			return;
		}
		LinkedList<MybatisEngineStatus> linkedListLive = new LinkedList<MybatisEngineStatus>();
		logger.debug(">>>>>count of engineStatus before merged::"+engineContextStatus.getQueue().size());
		for(MybatisEngineStatus engineStatus : linkedList){
			//if has completed, discard directly
			if(engineStatus.getState() == EngineStatus.ENGINE_COMPLETED){
				continue;
			}
			
			boolean isExist = false;
			for(MybatisEngineStatus engineStatusTemp : linkedListLive){
				if(engineStatus==engineStatusTemp){
//					//reset state
//					engineStatusTemp.setState(engineStatusTemp.getState()>engineStatus.getState()?engineStatusTemp.getState():engineStatus.getState());
//					//reset need_x
//					if(engineStatus.getNeedX()==EngineStatus.NEED_X || engineStatusTemp.getNeedX()==EngineStatus.NEED_X){
//						engineStatus.setNeedX(EngineStatus.NEED_X);
//					}
//					if(engineStatus.getNeedX()==EngineStatus.DONE_COMMIT_X || engineStatus.getNeedX()==EngineStatus.DONE_ROLLBACK_X 
//							|| engineStatusTemp.getNeedX()==EngineStatus.DONE_COMMIT_X || engineStatusTemp.getNeedX()==EngineStatus.DONE_ROLLBACK_X){
//						engineStatus.setNeedX(EngineStatus.DONE_COMMIT_X);
//					}
					isExist = true;
					break;
				}
			}
			if(isExist == false){
				linkedListLive.add(engineStatus);
			}
		}
		engineContextStatus.setQueue(linkedListLive);
		logger.debug(">>>>>count of engineStatus after merged::"+engineContextStatus.getQueue().size());
	}
}
