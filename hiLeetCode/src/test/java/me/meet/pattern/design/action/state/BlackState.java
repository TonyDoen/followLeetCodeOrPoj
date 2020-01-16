package me.meet.pattern.design.action.state;

public class BlackState extends State {
    /**
     * 状态切换顺序
     * push：blue-->black-->red-->blue
     * pull：blue-->red-->black-->blue
     */

    public void handlePush(Context c) {
        System.out.println("变成黑色");
        c.setState(new RedState());
    }

    public void handlePull(Context c) {
        System.out.println("变成黑色");
        c.setState(new BlueState());
    }

    public String getColor() {
        return "Color.black";
    }
}