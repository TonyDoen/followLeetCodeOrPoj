package me.meet.pattern.design.structure.adapter;


import java.util.logging.Logger;

public class Adapter implements ITarget {
  private static final java.util.logging.Logger LOG = Logger.getLogger(Adapter.class.getName());

  private Adaptee adaptee = new Adaptee();

  @Override
  public void request() {
    LOG.info("Adapter.request");
    adaptee.onRequest();
  }

}