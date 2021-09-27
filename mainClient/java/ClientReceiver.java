package mainClient.java;

import common.Command;
import java.io.*;
import java.net.*;

public class ClientReceiver implements Serializable{
    private static final long serialVersionUID = 26L;
    public static Command receive(InetSocketAddress socketAddress) throws ClassNotFoundException {
        Command com = null;
        try {
            byte[] buffer = new byte[2048];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            DatagramSocket socket = new DatagramSocket(socketAddress.getPort()-1);
            socket.setSoTimeout(5000);
            socket.receive(packet);
            ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
            ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(bais));
            com= (Command) ois.readObject();
            socket.close();
            ois.close();
            bais.close();
        }
        catch (SocketTimeoutException e)
        {
            System.out.println("Сервер недоступен!");
            System.exit(0);
        }
        finally {
            return com;
        }
    }
}
