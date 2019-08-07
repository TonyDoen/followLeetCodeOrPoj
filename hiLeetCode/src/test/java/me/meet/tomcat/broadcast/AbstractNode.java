package me.meet.tomcat.broadcast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;

public class AbstractNode {
    private final static int port = 8833;
    private final static String address = "228.0.0.4";

    // 单播模式
    protected static void senderExample() throws IOException {
        Socket socket = new Socket("localhost", 8833);

        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        DataInputStream dis = new DataInputStream(socket.getInputStream());

        dos.writeUTF("i am client, ask request.");
        String accept = dis.readUTF();

        System.out.println(accept);
        socket.close();
    }

    protected static void receiverExample() throws IOException {
        ServerSocket serverSocket = new ServerSocket(8833);
        Socket socket = serverSocket.accept();

        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        DataInputStream dis = new DataInputStream(socket.getInputStream());

        String accept = dis.readUTF();
        dos.writeUTF("accept success! accept content: " + accept);

        System.out.println(accept);
        socket.close();
        serverSocket.close();
    }

    // 组播通信
    // 可以在公网内传播，需要维护路由器与主机间的组间成员关系
    protected static void groupSenderExample() throws IOException, InterruptedException {
        System.setProperty("java.net.preferIPv4Stack", "true");

        InetAddress group = InetAddress.getByName(address);
        MulticastSocket mss = new MulticastSocket(port);
        mss.joinGroup(group);
        while (true) {
            String message = "hello, from sendExample";
            byte[] buffer = message.getBytes();
            DatagramPacket dpt = new DatagramPacket(buffer, buffer.length, group, port);
            mss.send(dpt);

            Thread.sleep(1000);
        }
    }

    protected static void groupReceiverExample() throws IOException {
        System.setProperty("java.net.preferIPv4Stack", "true");

        InetAddress group = InetAddress.getByName(address);
        MulticastSocket mss = new MulticastSocket(port);
        mss.joinGroup(group);
        byte[] buffer = new byte[1024*64];
        while (true) {
            DatagramPacket dpt = new DatagramPacket(buffer, buffer.length);
            mss.receive(dpt);
            String s = new String(dpt.getData(), 0, dpt.getLength());
            System.out.println("acceptExample receive: " + s);
        }
    }

    // 广播通信
    // 只能在局域网中传播，发送消息不管主机接不接
    protected static void broadSenderExample() throws IOException, InterruptedException {
        InetAddress ip = InetAddress.getByName("192.168.0.255");
        DatagramSocket ds = new DatagramSocket();

        byte[] message = "hello".getBytes();
        DatagramPacket dpt = new DatagramPacket(message, message.length, ip, port);
        ds.send(dpt);
        ds.close();

        Thread.sleep(1000);
    }

    protected static void broadReceiverExample() throws IOException {
        DatagramSocket ds = new DatagramSocket(port);
        byte[] buffer = new byte[1024*64];
        DatagramPacket dpt = new DatagramPacket(buffer, buffer.length);
        ds.receive(dpt);

        System.out.println(new String(buffer));
    }
}
