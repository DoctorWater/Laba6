package mainServer.java;

import common.Command;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

public class ServerSender {
    private static SocketAddress address = new InetSocketAddress("localhost", 60000);
    public static void send(Command com) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        byte[] buffer;
        oos.writeObject(com);
        oos.flush();
        buffer = baos.toByteArray();
        DatagramSocket s = new DatagramSocket();
        DatagramPacket outputPacket = new DatagramPacket(buffer, buffer.length, address);
        s.send(outputPacket);
        s.close();
        oos.close();
        baos.close();
    }

}
