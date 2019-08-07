package example.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.collections.CollectionUtils;

/**
 * common properties utils
 * @author zhuxinze
 *
 */
public class CommonPropertiesUtil {
	
	private static Properties commonProperties = new Properties();
	
	static{
		try {
			@SuppressWarnings("static-access")
			InputStream input = CommonPropertiesUtil.class.getClassLoader().getSystemResourceAsStream("configure.properties");
			commonProperties.load(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * get string value
	 * @param key
	 * @return
	 */
	public static String get(String key){
		return commonProperties.getProperty(key);
	}
	
	/**
	 * get integer value
	 * @param key
	 * @return
	 */
	public static Integer getInteger(String key){
		String strValue = get(key);
		return Integer.parseInt(strValue);
	}
	
	/**
	 * get long value
	 * @param key
	 * @return
	 */
	public static Long getLong(String key){
		String strValue = get(key);
		return Long.parseLong(strValue);
	}
}
