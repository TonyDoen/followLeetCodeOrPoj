package split.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface SplitPolicy {
	
	/**
	 * operation type, e.g : insert update delete query multipleQuery
	 * @return
	 */
	OperationType type();
	
	/**
	 * the method name to get DB id 
	 * @return
	 */
	String methodName() default "";
	
	/**
	 * the field that the id should setup
	 * @return
	 */
	String fieldName2setId() default "";
	
	/**
	 * Operation Type enum
	 *
	 */
	public enum OperationType{

		INSERT("insert"),
		UPDATE("update"),
		DELETE("delete"), 
		QUERY("query"), 
		MULTIQUERY("multipleQuery"),
		OTHERS("others");
		
		/**
		 * operation type
		 */
		private String type;
		
		OperationType(String type){
			this.setType(type);
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

	}
}
