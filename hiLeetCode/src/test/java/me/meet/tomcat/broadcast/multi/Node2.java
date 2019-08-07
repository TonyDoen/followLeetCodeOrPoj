package me.meet.tomcat.broadcast.multi;

import me.meet.tomcat.broadcast.AbstractNode;

import java.io.IOException;

public class Node2 extends AbstractNode {
    public static void main(String[] args) {
        try {
//            groupReceiverExample();
            broadReceiverExample();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
