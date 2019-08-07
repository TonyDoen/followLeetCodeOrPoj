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
import split.aop.DAOSplitHandler;
import split.impl.mybatis.dao.base.BaseDAOImpl;
import split.impl.mybatis.engine.MybatisEngineContext;
import split.impl.mybatis.engine.MybatisEngineSupport;
import split.support.exception.LoaderException;

/**
 * load the layer of DAO's class which need to be delegated to the proxy
 * @author zhuxinze
 *
 */
public class DAOLoader {

	private static Logger logger = LoggerFactory.getLogger(DAOLoader.class);
	
	private static Map<Class<?>, Object> DAOInstances;
	
	@SuppressWarnings("rawtypes")
	public static Object getSingleTon(Class<?> clazz){
		try{
			if(DAOInstances==null){
				synchronized (DAOLoader.class) {
					if(DAOInstances==null){
						DAOInstances = new HashMap<Class<?>, Object>();
						//initial DAO class
						String strBase = CommonPropertiesUtil.get("dao.base.package");
						String strBaseInFile = strBase.replace(".", "/");
						URL location = DAOLoader.class.getProtectionDomain().getCodeSource().getLocation();
						String strURI = location.getFile();
						//load from file directory
						if(!strURI.endsWith(".jar")){
							URL resource = Thread.currentThread().getContextClassLoader().getResource(strBaseInFile);
							File file = new File(resource.toURI());
							File[] files = file.listFiles();
							for(File tempFile : files){
								//if the file is directory or class 'BaseDAOImpl' then omit
								if(tempFile.isDirectory() || tempFile.getName().startsWith("BaseDAOImpl")){
									continue;
								}
								
								//if the class neither expands 'BaseDAOImpl' or has not super interface then omit
								String strClassName = strBase+"."+tempFile.getName();
								strClassName = strClassName.substring(0,strClassName.length()-6);
								Class clazzTemp = Thread.currentThread().getContextClassLoader().loadClass(strClassName);
								Class[] interfaces = clazzTemp.getInterfaces();
								Class superClazz = clazzTemp.getSuperclass();
								if(ArrayUtils.isEmpty(interfaces) || !superClazz.equals(BaseDAOImpl.class)){
									continue;
								}
								
								//create the proxy instance
								Object newInstance = clazzTemp.newInstance();
								DAOSplitHandler handler = new DAOSplitHandler(newInstance,MybatisEngineSupport.singleTon(),MybatisEngineContext.singleTon());
								DAOInstances.put(interfaces[0], handler.newProxyInstance());
							}
						}else{//load from jar package
//							JarFile jarFile = ((JarURLConnection)location.openConnection()).getJarFile();
							JarFile jarFile = new JarFile(new File(location.toURI()));
							Enumeration<JarEntry> entries = jarFile.entries();
							while(entries.hasMoreElements()){
								JarEntry entry = entries.nextElement();
								//if it is directory then omit
								if(entry.isDirectory()){
									continue;
								}
								
								//if not class type then omit
								String strName = entry.getName();
								if(!strName.startsWith(strBaseInFile) || !strName.endsWith(".class") || strName.indexOf("BaseDAOImpl")!=-1){
									continue;
								}
								
								//the class in the sub package, omit
								if(strName.lastIndexOf("/")>strBaseInFile.length()){
									continue;
								}
								
								String strClassName = strName.substring(0,strName.length()-6);
								strClassName = strClassName.replace("/", ".");
								
								//if the class neither expands 'BaseDAOImpl' nor has super interface then omit
								Class clazzTemp = Thread.currentThread().getContextClassLoader().loadClass(strClassName);
								Class[] interfaces = clazzTemp.getInterfaces();
								Class superClazz = clazzTemp.getSuperclass();
								if(ArrayUtils.isEmpty(interfaces) || !superClazz.equals(BaseDAOImpl.class)){
									continue;
								}
								
								//create the proxy instance
								Object newInstance = clazzTemp.newInstance();
								DAOSplitHandler handler = new DAOSplitHandler(newInstance,MybatisEngineSupport.singleTon(),MybatisEngineContext.singleTon());
								DAOInstances.put(interfaces[0], handler.newProxyInstance());
							}
							jarFile.close();
						}
					}
				}
			}
		}catch(Exception e){
			logger.error("error happend when load DAO dynamic proxy",e);
			throw new LoaderException("error happend when load DAO dynamic proxy",e);
		}
		return DAOInstances.get(clazz);
	}
}
