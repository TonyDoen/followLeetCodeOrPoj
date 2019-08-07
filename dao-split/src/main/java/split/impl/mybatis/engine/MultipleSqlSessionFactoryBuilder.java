package split.impl.mybatis.engine;

import java.io.Reader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.parsing.XPathParser;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * extend the SqlSessionFactoryBuilder class to supply function named 'buildMulti' 
 * which used to retrieve all data source session factory 
 * @author zhuxinze
 *
 */
public class MultipleSqlSessionFactoryBuilder extends SqlSessionFactoryBuilder {
	
	private Logger logger = LoggerFactory.getLogger(MultipleSqlSessionFactoryBuilder.class);
	
	/**
	 * get all data source's session factory
	 * @param reader
	 * @return
	 * @throws Exception
	 */
	public Map<String,SqlSessionFactory> buildMulti(Reader reader) throws Exception{
		
		Map<String,SqlSessionFactory> mapSessionFactory = new HashMap<String,SqlSessionFactory>();
		
		XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder(reader, null, null);	
		//get the embodied XPathParser object
		Field parseField = XMLConfigBuilder.class.getDeclaredField("parser");
		parseField.setAccessible(true);
		XPathParser parser = (XPathParser) parseField.get(xmlConfigBuilder);
		
		//get the Field of parsed flag
		Field parsedField = XMLConfigBuilder.class.getDeclaredField("parsed");
		parsedField.setAccessible(true);
		
		//get the private constructor
		Constructor<XMLConfigBuilder> constructor = XMLConfigBuilder.class.getDeclaredConstructor(XPathParser.class,String.class,Properties.class);
		constructor.setAccessible(true);
		
		//parse the configure file, get all data source index
		XNode evalNode = parser.evalNode("/configuration/environments");
		String strDefaultIndex = evalNode.getStringAttribute("default");
		
		List<XNode> listChildren = evalNode.getChildren();
		for(XNode xNode : listChildren){
			String strIndex = xNode.getStringAttribute("id");
			if(strIndex.equals(strDefaultIndex)){
				continue;
			}
			XMLConfigBuilder tempConfigBuilder = constructor.newInstance(parser,strIndex,null);
			parsedField.setBoolean(tempConfigBuilder, false);
			parseField.set(tempConfigBuilder, parser);
			Configuration configure = tempConfigBuilder.parse();
			SqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(configure);
			mapSessionFactory.put(strIndex, sqlSessionFactory);
			logger.debug("-------create SqlSessionFactory for datasource::"+strIndex);
		}
		
		//append suffix "_default" to the default factory's index that other module can check the default SqlSessionFactory
		// and add the default session factory
		strDefaultIndex = strDefaultIndex+"_default";
		mapSessionFactory.put(strDefaultIndex, new DefaultSqlSessionFactory(xmlConfigBuilder.parse()));
		
		return mapSessionFactory;
	}
}
