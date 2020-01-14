package me.meet.pattern.design.action.state;

public class BlueState extends State {
    /**
     * 状态切换顺序
     * push：blue-->black-->red-->blue
     * pull：blue-->red-->black-->blue
     */

    public void handlePush(Context c) {
        System.out.println("变成黑色");
        c.setState(new BlackState());
    }

    public void handlePull(Context c) {
        System.out.println("变成红色");
        c.setState(new RedState());
    }

    public String getColor() {
        return "Color.red";
    }
}