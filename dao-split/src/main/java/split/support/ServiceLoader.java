package split.support;

import java.io.File;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import example.utils.CommonPropertiesUtil;
import split.aop.ServiceSplitHandler;
import split.support.exception.LoaderException;

/**
 * tools for load service proxy instance
 * @author zhuxinze
 *
 */
public class ServiceLoader {
	
	private static Logger logger = LoggerFactory.getLogger(ServiceLoader.class);
	
	private static Map<Class<?>, Object> ServiceInstances;

	@SuppressWarnings("rawtypes")
	public static Object getSingleTon(Class<?> clazz){
		try{
			if (ServiceInstances == null) {
				synchronized (ServiceLoader.class) {
					if(ServiceInstances == null){
						ServiceInstances = new HashMap<Class<?>, Object>();
						// initial Service class
						String strBase = CommonPropertiesUtil.get("service.base.package");
						String strBaseInFile = strBase.replace(".", "/");
						URL location = DAOLoader.class.getProtectionDomain().getCodeSource().getLocation();
						String strURI = location.getFile();
						// load from file directory
						if (!strURI.endsWith(".jar")) {
							URL resource = Thread.currentThread().getContextClassLoader().getResource(strBaseInFile);
							File file = new File(resource.toURI());
							File[] files = file.listFiles();
							for (File tempFile : files) {
								// if the file is directory
								if (tempFile.isDirectory()) {
									continue;
								}
			
								// if the class neither expands 'BaseDAOImpl' or has not
								// super interface then omit
								String strClassName = strBase + "." + tempFile.getName();
								strClassName = strClassName.substring(0, strClassName.length() - 6);
								Class clazzTemp = Thread.currentThread().getContextClassLoader().loadClass(strClassName);
								Class[] interfaces = clazzTemp.getInterfaces();
								if (ArrayUtils.isEmpty(interfaces) ) {
									continue;
								}
			
								// create the proxy instance
								Object newInstance = clazzTemp.newInstance();
								ServiceSplitHandler handler = new ServiceSplitHandler(newInstance);
								ServiceInstances.put(interfaces[0], handler.newInstance());
							}
						} else {// load from jar package
//							JarFile jarFile = ((JarURLConnection) location.openConnection()).getJarFile();
							JarFile jarFile = new JarFile(new File(location.toURI()));
							Enumeration<JarEntry> entries = jarFile.entries();
							while (entries.hasMoreElements()) {
								JarEntry entry = entries.nextElement();
								// if it is directory then omit
								if (entry.isDirectory()) {
									continue;
								}
			
								// if not class type then omit
								String strName = entry.getName();
								if (!strName.startsWith(strBaseInFile) || !strName.endsWith(".class")) {
									continue;
								}
			
								// the class in the sub package, omit
								if (strName.lastIndexOf("/") > strBaseInFile.length()) {
									continue;
								}
			
								String strClassName = strName.substring(0, strName.length() - 6);
								strClassName = strClassName.replace("/", ".");
			
								// if the class neither expands 'BaseDAOImpl' nor has super
								// interface then omit
								Class clazzTemp = Thread.currentThread().getContextClassLoader().loadClass(strClassName);
								Class[] interfaces = clazzTemp.getInterfaces();
								if (ArrayUtils.isEmpty(interfaces)) {
									continue;
								}
			
								// create the proxy instance
								Object newInstance = clazzTemp.newInstance();
								ServiceSplitHandler handler = new ServiceSplitHandler(newInstance);
								ServiceInstances.put(interfaces[0], handler.newInstance());
							}
							jarFile.close();
						}						
					}
				}
			}
		}catch(Exception e){
			logger.error("error happend when load Service dynamic proxy",e);
			throw new LoaderException("error happend when load Service dynamic proxy",e);
		}
		return ServiceInstances.get(clazz);
	}	
}
