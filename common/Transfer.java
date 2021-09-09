package common;

import mainClient.java.Connector;
import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class Transfer {
    private final SocketAddress socketAddress;
    private final DatagramChannel datagramChannel;
    public Transfer(Command command, InetAddress address, int port) throws IOException {
        this.socketAddress = new InetSocketAddress(address, port);
        this.datagramChannel = (DatagramChannel) Connector.getDatagramChannel(this.socketAddress).configureBlocking(false);
    }

    public void send(Command com) throws IOException {
        ByteBuffer bb;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(com);
        oos.flush();
        bb=ByteBuffer.wrap(baos.toByteArray(),0,baos.size());
        datagramChannel.send(bb,socketAddress);
        baos.close();
    }

    public Command receive() throws IOException, ClassNotFoundException {
        ByteBuffer bb = ByteBuffer.allocate(1024);
        datagramChannel.receive(bb);
        ByteArrayInputStream bais = new ByteArrayInputStream(bb.array());
        ObjectInputStream ois = new ObjectInputStream(bais);
        return (Command) ois.readObject();
    }
}
