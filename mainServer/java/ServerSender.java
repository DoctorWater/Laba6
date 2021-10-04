package mainServer.java;

import common.Command;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class ServerSender {
    public static void send(Command com, InetSocketAddress address) throws IOException {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            byte[] buffer;
            oos.writeObject(com);
            oos.flush();
            buffer = baos.toByteArray();
            DatagramSocket s = new DatagramSocket();
            int port = 1;
            if (address.getPort() == 1)
                port = 65535;
            else
                port = address.getPort() - 1;
            InetSocketAddress newAddress = new InetSocketAddress(address.getAddress(), port);
            DatagramPacket outputPacket = new DatagramPacket(buffer, buffer.length, newAddress);
            s.send(outputPacket);
            s.close();
            oos.close();
            baos.close();
        }
        catch (InvalidClassException| NotSerializableException e){
            System.out.println("А что вы отправляете?..");
        }
        catch (IOException e){
            System.out.println("Что-то пошло не так...");
        }
        catch (Exception e){
            System.out.println("Непредвиденная ошибка! Сервер остановлен!");
            System.exit(0);
        }
    }

}
