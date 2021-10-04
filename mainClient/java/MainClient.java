package mainClient.java;

import common.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.InvalidPathException;
import java.util.*;


public class MainClient {

    public static void main(String[] args) throws IOException {
        Date initializationDate = new Date();
        Scanner in = new Scanner(System.in);
        try {
            String buffer, bufferIfer;
            Scanner scanner;
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
                    if (port <= 0 || port > 65535)
                        throw new NoSuchElementException();
                    break;
                } catch (NullPointerException | NoSuchElementException e) {
                    System.out.println("Укажите верный порт и адрес!");
                    in.nextLine();
                }
            }
            InetSocketAddress socketAddress = new InetSocketAddress(address, port);
            ShowCommand test = new ShowCommand();
            ClientSender.send(test, socketAddress);
            test = (ShowCommand) ClientReceiver.receive(socketAddress);
            test.executeClient();
            ArrayList<String> commands = new ArrayList<>();
            in.nextLine();
            while (true) {
                while (true) {
                    try {
                        System.out.println("ВВЕДИТЕ КОМАНДУ (ВВЕДИТЕ help ДЛЯ ПОЛУЧЕНИЯ СПИСКА ИСПОЛНЯЕМЫХ КОМАНД): ");
                        buffer = in.nextLine();
                        if (buffer.equals(""))
                            throw new NullPointerException();
                        scanner = new Scanner(buffer);
                        bufferIfer = scanner.findInLine("^\\S+");
                        if (bufferIfer.equals("help") | bufferIfer.equals("info") | bufferIfer.equals("show") | bufferIfer.equals("insert") | bufferIfer.equals("update") | bufferIfer.equals("remove_key") | bufferIfer.equals("clear") | bufferIfer.equals("execute_script") | bufferIfer.equals("remove_greater") | bufferIfer.equals("remove_lower") | bufferIfer.equals("history") | bufferIfer.equals("count_greater_than_part_number") | bufferIfer.equals("filter_greater_than_unit_of_measure") | bufferIfer.equals("print_field_descending_price")) {
                            commands.add(bufferIfer);
                        }
                        break;
                    } catch (NullPointerException e) {
                        System.out.println("Кажется, вы ввели пустую строку!");
                    }
                }
                DetermineCommand determinator = new DetermineCommand(buffer, commands, new ArrayList<>(), initializationDate, socketAddress);
                determinator.execute();
            }
        } catch (IOException | RecursionExeption e) {
            e.printStackTrace();
        } catch (NoSuchElementException e) {
            System.out.println("Нажато Ctrl+D, программа завершена!");
            System.exit(0);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Вы не передали адрес файла *.json! Client");
            System.exit(0);
        } catch (InvalidPathException e) {
            System.out.println("Имя файла неверно!");
        } catch (IllegalArgumentException e) {
            System.out.println("А номер порта точно верный?");
            System.exit(0);
        } catch (Exception e) {
            System.out.println("Как вы вообще это сделали?.. Перезапускайте!");
            System.exit(0);
        }
    }
}