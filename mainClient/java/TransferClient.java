package mainClient.java;

import common.Command;
import mainClient.java.Connector;

import javax.xml.crypto.Data;
import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Arrays;

public class TransferClient {
    private final SocketAddress socketAddress = new InetSocketAddress("localhost", 1);
    private final DatagramChannel datagramChannel=DatagramChannel.open();
    private final DatagramSocket datagramSocket=new DatagramSocket(1);

    public TransferClient() throws IOException {

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
        baos.close();
    }

    public Command receive() throws IOException, ClassNotFoundException {
        byte[] buffer= new byte[1024];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        datagramSocket.receive(packet);
        ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
        ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(bais));
        Command command = (Command) ois.readObject();
        ois.close();
        return command;
    }
}
