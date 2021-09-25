package mainServer.java;

import common.Command;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class ServerReceiver {
    private static SocketAddress socketAddressChannel = new InetSocketAddress("localhost", 2030);
    public static Command receive() throws IOException, ClassNotFoundException {
        ByteBuffer bb = ByteBuffer.allocate(1024);
        DatagramChannel datagramChannel = DatagramChannel.open();
        datagramChannel.bind(socketAddressChannel);
        datagramChannel.configureBlocking(false);
        while (true) {
            bb.clear();
            SocketAddress from = datagramChannel.receive(bb);
            if (from!=null) {
                bb.flip();
                break;
            }
        }
        ByteArrayInputStream bais = new ByteArrayInputStream(bb.array());
        ObjectInputStream ois = new ObjectInputStream(bais);
        Command command;
        command = (Command) ois.readObject();
        datagramChannel.close();
        ois.close();
        bais.close();
        bb.clear();

        return command;
    }
}
