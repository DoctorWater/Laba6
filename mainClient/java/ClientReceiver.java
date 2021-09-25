package mainClient.java;

import common.Command;
import java.io.*;
import java.net.*;

public class ClientReceiver implements Serializable{
    private static final long serialVersionUID = 26L;
    public static Command receive() throws IOException, ClassNotFoundException {
        byte[] buffer = new byte[2048];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        DatagramSocket socket = new DatagramSocket(60000);
        socket.receive(packet);
        ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
        ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(bais));
        Command com = (Command)ois.readObject();
        socket.close();
        ois.close();
        bais.close();
        return com;
    }
}
