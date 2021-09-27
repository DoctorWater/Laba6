package mainServer.java;

import common.Command;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
public class ServerSender {
    public static void send(Command com, InetSocketAddress address) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        byte[] buffer;
        oos.writeObject(com);
        oos.flush();
        buffer = baos.toByteArray();
        DatagramSocket s = new DatagramSocket();
        int port =address.getPort()-1;
        InetSocketAddress newAddress = new InetSocketAddress(address.getAddress(),port);
        DatagramPacket outputPacket = new DatagramPacket(buffer, buffer.length, newAddress);
        s.send(outputPacket);
        s.close();
        oos.close();
        baos.close();
    }

}
