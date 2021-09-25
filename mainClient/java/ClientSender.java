package mainClient.java;

import common.Command;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class ClientSender implements Serializable {
    private static final long serialVersionUID = 31L;
    private static final SocketAddress socketAddressChannel = new InetSocketAddress("localhost", 2030);
    public static void send(Command com) throws IOException {
        ByteBuffer bb;
        DatagramChannel datagramChannel = DatagramChannel.open();
        datagramChannel.bind(null);
        datagramChannel.configureBlocking(false);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(com);
        oos.flush();
        bb = ByteBuffer.wrap(baos.toByteArray(), 0, baos.size());
        datagramChannel.send(bb, socketAddressChannel);
        baos.close();
        oos.close();
        datagramChannel.close();
        bb.clear();
    }
}