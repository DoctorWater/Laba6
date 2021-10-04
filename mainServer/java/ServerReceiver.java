package mainServer.java;

import common.Command;

import java.io.*;
import java.net.*;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.UnresolvedAddressException;
import java.nio.channels.UnsupportedAddressTypeException;
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
                ((Buffer) bb).clear();
                SocketAddress from = datagramChannel.receive(bb);
                if (from != null) {
                    ((Buffer) bb).flip();
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
            ((Buffer) bb).clear();
        }catch (UnresolvedAddressException| UnsupportedAddressTypeException e){
            System.out.println("Что-то не так с адресом клиента, пожалуйста, проверьте и перезапустите программу!");
            System.exit(0);
        } catch (IOException e) {
            System.out.println("Ошибка в приеме команды! Проверьте адрес и порт!");
            System.exit(0);
        }
        catch (Exception e){
            System.out.println("Произошла непредвиденная ошибка! Программа остановлена!");
            System.exit(-1);
        }
        finally {
            return command;
        }

    }
}
