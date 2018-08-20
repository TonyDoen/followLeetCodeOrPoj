package me.meet.tomcat.broadcast.single;

import me.meet.tomcat.broadcast.AbstractNode;

import java.io.IOException;

public class Server extends AbstractNode {
    public static void main(String[] args) {
        try {
            receiverExample();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
