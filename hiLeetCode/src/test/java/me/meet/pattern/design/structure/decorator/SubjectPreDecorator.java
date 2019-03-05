package me.meet.pattern.design.structure.decorator;

import java.util.logging.Logger;

public class SubjectPreDecorator implements ISubject {
    //  private static final Logger LOG = LoggerFactory.getLogger(SubjectPreDecorator.class);
    private static final Logger LOG = Logger.getLogger(ConcreteSubject.class.getName());
    private ISubject subject;

    public SubjectPreDecorator(ISubject subject) {
        this.subject = subject;
    }

    @Override
    public void action() {
        preAction();
        subject.action();
    }

    private void preAction() {
        LOG.info("SubjectPreDecorator.preAction()");
    }
}