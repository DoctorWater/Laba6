package mainServer.java;

import common.Command;
import mainClient.java.Connector;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class TransferServer {
    private final SocketAddress socketAddress;
    private final DatagramChannel datagramChannel = DatagramChannel.open();
    private final DatagramSocket datagramSocket=new DatagramSocket();

    public TransferServer(InetSocketAddress address) throws IOException {
        this.socketAddress = address;
    }

    public void send(Command com) throws IOException {
        ByteBuffer bb;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(com);
        bb=ByteBuffer.wrap(baos.toByteArray(),0,baos.size());
        DatagramPacket packet = new DatagramPacket(bb.array(),bb.array().length, socketAddress);
        datagramSocket.send(packet);
    }

    public Command receive() throws IOException, ClassNotFoundException {
        datagramChannel.bind(socketAddress);
        ByteBuffer bb = ByteBuffer.allocate(1024);
        datagramChannel.receive(bb);
        System.out.println("Принято");
        ByteArrayInputStream bais = new ByteArrayInputStream(bb.array());
        ObjectInputStream ois = new ObjectInputStream(bais);
        return (Command) ois.readObject();
    }
}
