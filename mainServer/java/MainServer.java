package mainServer.java;

import common.*;
import mainClient.java.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.InvalidPathException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class MainServer {
    public static void main(String[] args) throws ClassNotFoundException, IllegalVarValue {
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
                    if (address.equals(""))
                        throw new NoSuchElementException();
                    System.out.println("Порт: ");
                    port = in.nextInt();
                    if (port <= 0 || port>65535)
                        throw new NoSuchElementException();
                    break;
                } catch (NullPointerException | NoSuchElementException e) {
                    System.out.println("Укажите верный порт и адрес!");
                    in.nextLine();
                }
            }
            in.nextLine();
            InetSocketAddress socketAddress = new InetSocketAddress(address, port);
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
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Вы не передали адрес файла *.json!");
            System.exit(0);
        } catch (NoSuchFileException e) {
            System.out.println("Такого файла нет!");
            System.exit(0);
        } catch (IOException | RecursionExeption e) {
            e.printStackTrace();
        } catch (NoSuchElementException e) {
            System.out.println("Нажато Ctrl+D, программа завершена!");
            System.exit(0);
        } catch (InvalidPathException e) {
            System.out.println("Имя файла неверно!");

        } catch (NullPointerException e) {
            System.out.println("Что-то пошло не так");
            System.exit(0);
        } catch (IllegalArgumentException e) {
            System.out.println("А номер порта точно верный?");
            System.exit(0);
        } catch (Exception e) {
            System.out.println("Как вы вообще это сделали?.. Перезапускайте!");
            System.exit(0);
        }
    }
}