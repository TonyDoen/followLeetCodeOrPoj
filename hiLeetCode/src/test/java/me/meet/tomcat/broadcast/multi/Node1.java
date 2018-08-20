package me.meet.tomcat.broadcast.multi;

import me.meet.tomcat.broadcast.AbstractNode;

import java.io.IOException;

public class Node1 extends AbstractNode {
    public static void main(String[] args) {
        try {
//            groupSenderExample();
            broadSenderExample();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
