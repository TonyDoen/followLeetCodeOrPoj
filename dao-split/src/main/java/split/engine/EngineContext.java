package split.engine;

import java.util.LinkedList;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * thread scope and thread safe, can only be used in ThreadLocal domain
 * @author zhuxinze
 *
 * @param <T>
 */
public abstract class EngineContext<T extends EngineStatus>{
	
	private Logger logger = LoggerFactory.getLogger(EngineContext.class);
	
	/**
	 * the container of engine
	 */
	protected ThreadLocal<EngineContextStatus> LocalContextStatus = new ThreadLocal<EngineContextStatus>();
	
	/**
	 * push the engine to the top of the stack
	 * @param engine
	 * @throws Exception 
	 */
	public void push(T engine) throws Exception{
		EngineContextStatus contextStatus = this.LocalContextStatus.get();
		if(contextStatus == null){
			contextStatus = new EngineContextStatus();
			this.LocalContextStatus.set(contextStatus);
			logger.debug("====create ContextStatus for current thread::"+Thread.currentThread().getId());
		}
		LinkedList<T> listQueue = contextStatus.getQueue();
		if(listQueue == null){
			listQueue = new LinkedList<T>();
			contextStatus.setQueue(listQueue);
		}
		listQueue.push(engine);
	}
	
	/**
	 * pop the engine on the top of the stack
	 * @return
	 */
	public T pop() throws Exception{
		EngineContextStatus contextStatus = this.LocalContextStatus.get();
		if(contextStatus==null){
			logger.error("there is no EngineContextStatus bound with current thread, should push an engine first");
			throw new Exception("there is no EngineContextStatus bound with current thread, should push an engine first");
		}
		LinkedList<T> listQueue = contextStatus.getQueue();
		if(CollectionUtils.isEmpty(listQueue)){
			logger.error("there is no EngineContextStatus bound with current thread, should push an engine first");
			throw new Exception("there is no EngineContextStatus bound with current thread, should push an engine first");
		}else{
			T temp = listQueue.getFirst();
			return temp;
		}
	}
	
	/**
	 * used for nested invoking. when the procedure running, the first function that bound with Holistic annotation 
	 * will be pushed first and subsequence will be pushed one by one with need or no need holistic management flag, 
	 * if does't encounter function that bound with holistic annotation, this procedure does't need holistic management.
	 * when the function completed, its management flag should be popped, when the last one completed then executing the 
	 * holistic management code.
	 * @param isHolistic
	 */
	public void setHolistic(boolean isHolistic) throws Exception{
		EngineContextStatus contextStatus = this.LocalContextStatus.get();
		if(contextStatus==null){
			if(isHolistic){
				contextStatus = new EngineContextStatus();
				if(contextStatus.getHolistic()==null){
					contextStatus.setHolistic(new LinkedList<Boolean>());
				}
				contextStatus.getHolistic().push(isHolistic);
				this.LocalContextStatus.set(contextStatus);
			}
		}else{
			if(contextStatus.getHolistic()==null){
				if(isHolistic){
					contextStatus.setHolistic(new LinkedList<Boolean>());
					contextStatus.getHolistic().push(isHolistic);
				}
			}else if(contextStatus.getHolistic().size()==0){
				if(isHolistic){
					contextStatus.getHolistic().push(isHolistic);
				}
			}else{
				contextStatus.getHolistic().push(isHolistic);
			}
		}
	}
	
	/**
	 * whether use holistic management
	 * @return
	 * @throws Exception
	 */
	public int getHolisticNestedLevel() throws Exception{		
		if(this.LocalContextStatus.get()==null){
			return 0;
		}else{
			return this.LocalContextStatus.get().getHolistic().size();
		}
	}
	
	/**
	 * when some one function completed, should pop from the stack
	 * @return
	 */
	public int removeHolistic(){
		if(this.LocalContextStatus.get()==null){
			return 0;
		}else{
			if(this.LocalContextStatus.get().getHolistic().size()>0){
				this.LocalContextStatus.get().getHolistic().pop();
				return this.LocalContextStatus.get().getHolistic().size()+1;
			}else{
				return 0;
			}
		}
	}
	
	/**
	 * roll back all engine execution
	 */
	public void rollback(){
		EngineContextStatus engineContextStatus = this.LocalContextStatus.get();
		if(engineContextStatus==null){
			logger.info("there is no engine execution to roll back");
			return;
		}
		LinkedList<T> queue = engineContextStatus.getQueue();
		if(CollectionUtils.isEmpty(queue)){
			logger.info("there is no engine execution to roll back");
			return;
		}
		this.mergeEngine();
		queue = engineContextStatus.getQueue();
		if(CollectionUtils.isEmpty(queue)){
			logger.info("you have roll back or commit or close  all engine execution manually");
			return;
		}
		for(EngineStatus engineStatus : queue){
			if(engineStatus==null){
				continue;
			}
			if(engineStatus.needX == EngineStatus.NEED_X){
				engineStatus.statusRollback();
			}
		}
	}
	
	/**
	 * commit all engine execution
	 */
	public void commit(){
		EngineContextStatus engineContextStatus = this.LocalContextStatus.get();
		if(engineContextStatus==null){
			logger.info("there is no engine execution to commit");
			return;
		}
		LinkedList<T> queue = engineContextStatus.getQueue();
		if(CollectionUtils.isEmpty(queue)){
			logger.info("there is no engine execution to commit");
			return;
		}
		this.mergeEngine();
		queue = engineContextStatus.getQueue();
		if(CollectionUtils.isEmpty(queue)){
			logger.info("you have rolled back or committed or closed all engine execution manually");
			return;
		}
		for(EngineStatus engineStatus : queue){
			if(engineStatus==null){
				continue;
			}
			if(engineStatus.needX == EngineStatus.NEED_X){
				engineStatus.statusCommit();
			}
		}
	}
	
	/**
	 * close all engine
	 */
	public void close(){
		EngineContextStatus engineContextStatus = this.LocalContextStatus.get();
		if(engineContextStatus==null){
			logger.info("there is no engine execution to close");
			return;
		}
		LinkedList<T> queue = engineContextStatus.getQueue();
		if(CollectionUtils.isEmpty(queue)){
			logger.info("there is no engine execution to close");
			return;
		}
		this.mergeEngine();
		queue = engineContextStatus.getQueue();
		if(CollectionUtils.isEmpty(queue)){
			logger.info("you have rolled back or committed or closed all engine execution manually");
			return;
		}
		for(EngineStatus engineStatus : queue){
			if(engineStatus==null){
				continue;
			}
			engineStatus.statusClose();
		}
	}
	
	/**
	 * clear local thread context
	 */
	public void clearContext(){
		this.LocalContextStatus.set(null);
	}
	
	
	/**
	 * check if there is some error happened in the process
	 * @return
	 */
	public abstract boolean checkCount();
	
	/**
	 * check whether the engine can be reused
	 * @return
	 */
	public abstract boolean checkValid(T engine);
	
	/**
	 * traverse the context, find the exist engine
	 * @return
	 */
	public abstract T getExistEngine(String index);
	
	/**
	 * merge the same engines, and filter out the complete engine 
	 */
	public abstract void mergeEngine();
	
	/**
	 * used for recording engineContext status
	 *
	 */
	protected class EngineContextStatus {

		private LinkedList<Boolean> holistic = new LinkedList<Boolean>();
		
		/**
		 * the stack of engine status
		 */
		private LinkedList<T> queue;

		public LinkedList<T> getQueue() {
			return queue;
		}

		public void setQueue(LinkedList<T> queue) {
			this.queue = queue;
		}

		public LinkedList<Boolean> getHolistic() {
			return holistic;
		}

		public void setHolistic(LinkedList<Boolean> holistic) {
			this.holistic = holistic;
		}
	}	
}
