package mainClient.java;

import common.Command;
import mainClient.java.Connector;

import javax.xml.crypto.Data;
import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class TransferClient {
    private final SocketAddress socketAddress;
    private final DatagramChannel datagramChannel=DatagramChannel.open();
    private final DatagramSocket datagramSocket=new DatagramSocket();

    public TransferClient(InetSocketAddress address) throws IOException {
        this.socketAddress = address;
    }

    public void send(Command com) throws IOException {
        ByteBuffer bb;
        datagramChannel.connect(socketAddress);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(com);
        oos.flush();
        bb=ByteBuffer.wrap(baos.toByteArray(),0, baos.size());
        datagramChannel.send(bb,socketAddress);
        System.out.println("Я послал");
        baos.close();
    }

    public Command receive() throws IOException, ClassNotFoundException {
        ByteBuffer bb = ByteBuffer.allocate(1024);
        DatagramPacket i = new DatagramPacket(bb.array(), bb.array().length);
        datagramSocket.receive(i);
        ByteArrayInputStream bais = new ByteArrayInputStream(bb.array());
        ObjectInputStream ois = new ObjectInputStream(bais);
        return (Command) ois.readObject();
    }
}
