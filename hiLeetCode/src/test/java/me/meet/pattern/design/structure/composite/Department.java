package me.meet.pattern.design.structure.composite;

import java.util.logging.Logger;

// 简单组件（部门）
public class Department extends Organization {
  //  private static Logger LOGGER = LoggerFactory.getLogger(Department.class);
  private static final Logger LOG = Logger.getLogger(Department.class.getName());

    public Department(String name) {
        super(name);
    }

    public void inform(String info) {
        LOG.info("" + info + getName());
    }
}