package me.meet.pattern.design.structure.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.logging.Logger;

public class ProxyFactory {
    /**
     * 创建动态代理对象
     * 动态代理不需要实现接口,但是需要指定接口类型
     */
    private static final Logger LOG = Logger.getLogger(ProxyFactory.class.getName());

    //维护一个目标对象
    private Object target;

    public ProxyFactory(Object target) {
        this.target = target;
    }

    //给目标对象生成代理对象
    public Object getProxyInstance() {
        return Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                        LOG.info("ProxyFactory.getProxyInstance; before invoke.");

                        //执行目标对象方法
                        Object returnValue = method.invoke(target, args);

                        LOG.info("ProxyFactory.getProxyInstance; after invoke.");
                        return returnValue;
                    }
                }
        );
    }

}