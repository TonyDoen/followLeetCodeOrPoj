package me.meet.pattern.design.action.state;

public class RedState extends State {
    /**
     * 状态切换顺序
     * push：blue-->black-->red-->blue
     * pull：blue-->red-->black-->blue
     */

    public void handlePush(Context c) {
        System.out.println("变成蓝色");
        c.setState(new BlueState());
    }

    public void handlePull(Context c) {
        System.out.println("变成黑色");
        c.setState(new BlackState());
    }

    public String getColor() {
        return "Color.red";
    }
}