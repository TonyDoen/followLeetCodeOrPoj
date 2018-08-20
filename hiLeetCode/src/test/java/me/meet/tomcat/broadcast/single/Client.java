package me.meet.tomcat.broadcast.single;

import me.meet.tomcat.broadcast.AbstractNode;

import java.io.IOException;

public class Client extends AbstractNode {
    // 单播模式
    public static void main(String[] args) {
        try {
            senderExample();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
