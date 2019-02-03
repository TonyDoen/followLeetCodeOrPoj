package me.meet.pattern.design.structure.composite;

import java.util.List;
import java.util.logging.Logger;

// 复合组件（公司）
public class Company extends Organization {
    //  private static Logger LOGGER = LoggerFactory.getLogger(Company.class);
    private static final Logger LOG = Logger.getLogger(Department.class.getName());

    public Company(String name) {
        super(name);
    }

    public void inform(String info) {
        LOG.info("" + info + getName());
        List<Organization> allOrg = getAllOrg();
        allOrg.forEach(org -> org.inform(info + "-"));
    }
}