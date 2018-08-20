package me.meet.pattern.design.structure.decorator;

import java.util.logging.Logger;

public class SubjectPostDecorator implements ISubject {
    //  private static final Logger LOG = LoggerFactory.getLogger(SubjectPostDecorator.class);
    private static final Logger LOG = Logger.getLogger(SubjectPostDecorator.class.getName());
    private ISubject subject;

    public SubjectPostDecorator(ISubject subject) {
        this.subject = subject;
    }

    @Override
    public void action() {
        subject.action();
        postAction();
    }

    private void postAction() {
        LOG.info("SubjectPostDecorator.postAction()");
    }
}