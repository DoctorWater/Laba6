package mainClient.java;

import common.Command;
import java.io.*;
import java.net.*;

public class ClientReceiver implements Serializable{
    private static final long serialVersionUID = 26L;
    public static Command receive(InetSocketAddress socketAddress) throws ClassNotFoundException {
        Integer port=1;
        Command com = null;
        try {
            byte[] buffer = new byte[2048];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            if(socketAddress.getPort()==1)
                port=65535;
            else
                port=socketAddress.getPort()-1;
            DatagramSocket socket = new DatagramSocket(port);
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
