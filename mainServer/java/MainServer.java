package mainServer.java;

import common.*;
import mainClient.java.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class MainServer {
    public static void main(String[] args) throws IOException, ClassNotFoundException, RecursionExeption, IllegalVarValue {
        try {
            Scanner in = new Scanner(System.in);
            String buffer = args[0];
            Path filenamePath = Paths.get(buffer);
            try {
                if (filenamePath.toRealPath().toString().length() > 3 && filenamePath.toRealPath().toString().trim().startsWith("/dev"))
                    throw new InvalidPathException("", "Строка не может быть преобразована в путь!");
            } catch (InvalidPathException e) {
                System.out.println(e.getMessage());
            }
            String address;
            Integer port;

            while (true) {
                try {
                    System.out.println("Введите адрес и порт, пожалуйста: " +
                            "Адрес: ");
                    address = in.nextLine();
                    System.out.println("Порт: ");
                    port = in.nextInt();
                    if(port<2)
                        throw new InputMismatchException();
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Укажите верный порт!");
                    in.nextLine();
                }
            }
            InetSocketAddress socketAddress = new InetSocketAddress(address, port);
            try {
                Command currentCommand;
                ParseCommand parser = new ParseCommand(filenamePath.toString(), args);
                parser.execute();
                FilenameHolder.setFilename(filenamePath.toString());
                Hashtable<String, Product> table = parser.returnTable();
                while (true) {
                    System.out.println("Ожидание команды...");
                    currentCommand = ServerReceiver.receive(socketAddress);
                    currentCommand.setProductHashtable(table);
                    currentCommand.execute();
                    table = currentCommand.returnTable();
                    ServerSender.send(currentCommand, socketAddress);
                    System.out.println("Команда выполнена!");
                }
            } catch (NoSuchElementException e) {
                System.out.println("Нажато Ctrl+D, программа завершена!");
                System.exit(0);
            }
        }
        catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Вы не передали адрес файла *.json!");
            System.exit(0);
        }
    }
}