package me.meet.pattern.design.structure.proxy;

import java.util.Random;
import java.util.logging.Logger;

public class ProxySubject implements ISubject {
    /**
     * 静态代理
     */

    //  private static final Logger LOG = LoggerFactory.getLogger(ProxySubject.class);
    private static final Logger LOG = Logger.getLogger(ConcreteSubject.class.getName());
    private ISubject subject;

    // 此处就是 装饰模式 与 代理模式的区别
    public ProxySubject() {
        subject = new ConcreteSubject();
    }

    public ProxySubject(ISubject subject) {
        this.subject = subject;
    }

    @Override
    public void action() {
        preAction();
        if ((new Random()).nextBoolean()) {
            subject.action();
        } else {
            LOG.info("Permission denied");
        }
        postAction();
    }

    private void preAction() {
        LOG.info("ProxySubject.preAction()");
    }

    private void postAction() {
        LOG.info("ProxySubject.postAction()");
    }
}