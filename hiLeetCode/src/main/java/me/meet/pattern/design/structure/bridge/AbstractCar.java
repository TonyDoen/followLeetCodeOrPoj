package me.meet.pattern.design.structure.bridge;

public abstract class AbstractCar {
    protected Transmission gear;

    public abstract void run();

    public void setTransmission(Transmission gear) {
        this.gear = gear;
    }

}