package me.meet.spi;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * url: https://blog.csdn.net/qiangcai/article/details/77750541 
 */
public class ServiceMain {

    public static void main(String[] args) {

        ServiceLoader<DubboService> spiLoader = ServiceLoader.load(DubboService.class); // funny failed
        Iterator<DubboService> iteratorSpi = spiLoader.iterator();
        while (iteratorSpi.hasNext()) {
            DubboService dubboService = iteratorSpi.next();
            dubboService.sayHello();
        }

    }
}