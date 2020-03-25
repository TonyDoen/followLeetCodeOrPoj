package me.meet.pattern.design.action.state;

public abstract class State {

    public abstract void handlePush(Context c);

    public abstract void handlePull(Context c);

    public abstract String getColor();
}