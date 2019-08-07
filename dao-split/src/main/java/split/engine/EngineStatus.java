package split.engine;

public abstract class EngineStatus {
	
	/**
	 * engine completed state
	 */
	public static final int ENGINE_COMPLETED = -1;

	/**
	 * engine in running state
	 */
	public static final int ENGINE_RUNNING = 1;
	
	/**
	 * engine just created state
	 */
	public static final int ENGINE_START = 0;
	
	/**
	 * no need transaction
	 */
	public static final int NO_NEED_X = 0;
	
	/**
	 * need transaction
	 */
	public static final int NEED_X = 1;
	
	/**
	 * transaction has been done, flag roll back
	 */
	public static final int DONE_ROLLBACK_X = -10;
	
	/**
	 * transaction has been done, flag commit
	 */
	public static final int DONE_COMMIT_X = -11;

	/**
	 * engine state
	 */
	protected int state=ENGINE_START;

	/**
	 * whether need transaction support
	 */
	protected int needX=NO_NEED_X;
	
	/**
	 * execute times
	 */
	protected int count;
	
	/**
	 * index of the engine
	 */
	protected String index;
	
	/**
	 * engine roll back
	 */
	public abstract void statusRollback();
	
	/**
	 * engine commit
	 */
	public abstract void statusCommit();
	
	/**
	 * close engine
	 */
	public abstract void statusClose();
	
	/**
	 * return whether can be reused
	 * @return
	 */
	public abstract boolean isValid();
	
	/**
	 * get actual executing engine
	 * @return
	 */
	public abstract <T> T getActualEngine();
		
	public int getState(){
		return this.state;
	}
	
	public int getNeedX(){
		return this.needX;
	}
	
	public int getCount(){
		return this.count;
	}
	
	public void setState(int state) {
		this.state = state;
	}

	public void setNeedX(int needX) {
		this.needX = needX;
	}

	public void setCount(int count) {
		this.count = count;
	}	
	
	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}
}
