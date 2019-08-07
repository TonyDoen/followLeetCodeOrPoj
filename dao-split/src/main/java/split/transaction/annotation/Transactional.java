package split.transaction.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import split.annotation.SplitPolicy.OperationType;

/**
 * flag of needing transaction
 * @author ZhuXinZe
 *
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Transactional {
	
	/**
	 * operation type, e.g : insert update delete update
	 * @return
	 */
	OperationType type() default OperationType.OTHERS;
	
}
