package mainServer.java;

import common.Command;
import mainClient.java.Connector;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Arrays;

public class TransferServer {
    private final SocketAddress socketAddress;
    private final DatagramChannel datagramChannel = DatagramChannel.open();


    public TransferServer(InetSocketAddress address) throws IOException {
        this.socketAddress = address;
    }

    public void send(Command com) throws IOException {
        DatagramSocket datagramSocket = new DatagramSocket();
        InetAddress IPAddress = InetAddress.getByName("localhost");
        byte[] buffer = new byte[1024];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(com);
        oos.flush();
        buffer = baos.toByteArray();
        DatagramPacket sendingPacket = new DatagramPacket(buffer, buffer.length,IPAddress,1);
        datagramSocket.send(sendingPacket);
    }

    public Command receive() throws IOException, ClassNotFoundException {
        datagramChannel.bind(socketAddress);
        ByteBuffer bb = ByteBuffer.allocate(1024);
        datagramChannel.receive(bb);
        ByteArrayInputStream bais = new ByteArrayInputStream(bb.array());
        ObjectInputStream ois = new ObjectInputStream(bais);
        return (Command) ois.readObject();
    }
}
