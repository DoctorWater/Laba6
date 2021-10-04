package mainClient.java;

import common.Command;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.UnresolvedAddressException;
import java.nio.channels.UnsupportedAddressTypeException;

public class ClientSender implements Serializable {
    private static final long serialVersionUID = 31L;

    public static void send(Command com, InetSocketAddress socketAddress){
        try {
            ByteBuffer bb;
            DatagramChannel datagramChannel = DatagramChannel.open();
            datagramChannel.bind(null);
            datagramChannel.configureBlocking(false);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(com);
            oos.flush();
            bb = ByteBuffer.wrap(baos.toByteArray(), 0, baos.size());
            datagramChannel.send(bb, socketAddress);
            baos.close();
            oos.close();
            datagramChannel.close();
            ((Buffer) bb).clear();
        }
        catch (UnresolvedAddressException| UnsupportedAddressTypeException e){
            System.out.println("Что-то не так с адресом сервера, пожалуйста, проверьте и перезапустите программу!");
            System.exit(0);
        } catch (IOException e) {
            System.out.println("Ошибка в отправке команды! Проверьте адрес и порт!");
            System.exit(0);
        }
        catch (Exception e){
            System.out.println("Произошла непредвиденная ошибка! Программа остановлена!");
            System.exit(-1);
        }
    }
}