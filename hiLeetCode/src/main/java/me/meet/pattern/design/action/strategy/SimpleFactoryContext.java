package me.meet.pattern.design.action.strategy;

//import org.apache.commons.configuration.XMLConfiguration;
//import org.apache.commons.configuration.ConfigurationException;
//import org.reflections.Reflections;

//import java.util.Collections;
//import java.util.Map;
//import java.util.Set;
//import java.util.concurrent.ConcurrentHashMap;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
//import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

public class SimpleFactoryContext {
    //  private static final Logger LOG = LoggerFactory.getLogger(SimpleFactoryContext.class);
    private static final Logger LOG = Logger.getLogger(ConcreteStrategyA.class.getName());
    private Strategy            strategy;

    /**
     * 使用 简单工厂 + 注解
     * 引入简单工厂模式后，客户端不再需要直接实例化具体的策略类，也不需要判断应该使用何种策略，可以方便应对策略的切换。
     */
    //    private static Map<String, Class> allStrategies;
    //    static {
    //        Reflections reflections = new Reflections("me.meet.pattern.design.action.strategy");
    //        Set<Class<?>> annotatedClasses = reflections.getTypesAnnotatedWith(Strategy.class);
    //        allStrategies = new ConcurrentHashMap<String, Class>();
    //        for (Class<?> classObject : annotatedClasses) {
    //            Strategy strategy = (Strategy) classObject.getAnnotation(Strategy.class);
    //            allStrategies.put(strategy.name(), classObject);
    //        }
    //        allStrategies = Collections.unmodifiableMap(allStrategies);
    //    }
    //
    //    public SimpleFactoryContext() {
    //        String name = null;
    //        try {
    //            XMLConfiguration config = new XMLConfiguration("strategy.xml");
    //            name = config.getString("strategy.name");
    //            LOG.info(String.format("strategy name is %s", name));
    //        } catch (ConfigurationException ex) {
    //            LOG.info(String.format("Parsing xml configuration file failed %s", ex));
    //        }
    //        if (allStrategies.containsKey(name)) {
    //            LOG.info(String.format("Created strategy name is %s", name));
    //            try {
    //                strategy = (Strategy) allStrategies.get(name).newInstance();
    //            } catch (InstantiationException ex) {
    //                LOG.info(String.format("Instantiate Strategy failed %s", ex));
    //            } catch (IllegalAccessException ex) {
    //                LOG.info(String.format("Instantiate Strategy failed %s", ex));
    //            }
    //        } else {
    //            LOG.info(String.format("Specified Strategy name %s does not exist", name));
    //        }
    //    }

    /**
     * 使用 简单工厂 + 反射
     * 引入简单工厂模式后，客户端不再需要直接实例化具体的策略类，也不需要判断应该使用何种策略，可以方便应对策略的切换。
     */
    public SimpleFactoryContext() {
        String filePath = (ClassLoader.getSystemResource("") + "/settings.properties").substring(5); // file:
        String key = "strategy.name";
        Properties props = new Properties();

        InputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(filePath));
            props.load(in);
            in.close();

            String value = props.getProperty(key);
            Class<Strategy> instance = (Class<Strategy>) Class.forName(value);

            strategy = instance.newInstance();
        } catch (IOException e) {
            LOG.info(String.format("%s", e));
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            LOG.info(String.format("%s", e));
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            LOG.info(String.format("%s", e));
            e.printStackTrace();
        } catch (InstantiationException e) {
            LOG.info(String.format("%s", e));
            e.printStackTrace();
        }

        if (null == strategy) {
            class DefaultStrategy implements Strategy { // wow
                public void strategy(String input) {
                    LOG.info(String.format("DefaultStrategy for input : %s", input));
                }
            }
            strategy = new DefaultStrategy();
        }
    }

    public void action(String input) {
        strategy.strategy(input);
    }

}