package mainServer.java;

import common.Command;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.UnresolvedAddressException;
import java.util.Arrays;

public class ServerReceiver {
    public static Command receive(InetSocketAddress socketAddressChannel) throws IOException, ClassNotFoundException {
        Command command=null;
        try {
            ByteBuffer bb = ByteBuffer.allocate(2048);
            DatagramChannel datagramChannel = DatagramChannel.open();
            datagramChannel.bind(socketAddressChannel);
            datagramChannel.configureBlocking(false);
            while (true) {
                bb.clear();
                SocketAddress from = datagramChannel.receive(bb);
                if (from != null) {
                    bb.flip();
                    break;
                }
            }
            ByteArrayInputStream bais = new ByteArrayInputStream(bb.array());
            ObjectInputStream ois = new ObjectInputStream(bais);
            System.out.println(Arrays.toString(bb.array()));
            command = (Command) ois.readObject();
            datagramChannel.close();
            ois.close();
            bais.close();
            bb.clear();
        }catch (UnresolvedAddressException e)
        {
            System.out.println("Что-то пошло не так при подключении к клиенту!");
        }
        finally {
            return command;
        }

    }
}
