package mainClient.java;

import common.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.file.InvalidPathException;
import java.util.*;


public class MainClient {

    public static void main(String[] args) throws IOException {
        Date initializationDate = new Date();
        Scanner in = new Scanner(System.in);
            try {String buffer, bufferIfer;
                Scanner scanner;
                String address;
                Integer port;
                while(true){
                    try {
                        System.out.println("Введите адрес и порт, пожалуйста: " +
                                "Адрес: ");
                        address = in.nextLine();
                        System.out.println("Порт: ");
                        port = in.nextInt();
                        if(port<2)
                            throw new InputMismatchException();
                        break;
                    }catch (InputMismatchException e) {
                        System.out.println("Укажите верный порт!");
                        in.nextLine();
                    }
                }
                in.nextLine();
                InetSocketAddress socketAddress = new InetSocketAddress(address,port);
                ArrayList<String> commands = new ArrayList<>();
                while (true) {
                    System.out.println("ВВЕДИТЕ КОМАНДУ (ВВЕДИТЕ help ДЛЯ ПОЛУЧЕНИЯ СПИСКА ИСПОЛНЯЕМЫХ КОМАНД): ");

                    buffer = in.nextLine();
                    scanner = new Scanner(buffer);
                    bufferIfer = scanner.findInLine("^\\S+");
                    if (bufferIfer.equals("help") | bufferIfer.equals("info") | bufferIfer.equals("show") | bufferIfer.equals("insert") | bufferIfer.equals("update") | bufferIfer.equals("remove_key") | bufferIfer.equals("clear") | bufferIfer.equals("execute_script") | bufferIfer.equals("remove_greater") | bufferIfer.equals("remove_lower") | bufferIfer.equals("history") | bufferIfer.equals("count_greater_than_part_number") | bufferIfer.equals("filter_greater_than_unit_of_measure") | bufferIfer.equals("print_field_descending_price")) {
                        commands.add(bufferIfer);
                    }
                    DetermineCommand determinator= new DetermineCommand(buffer, commands, new ArrayList<>(), initializationDate, socketAddress);
                    determinator.execute();
                }
            } catch ( IOException | RecursionExeption e) {
                e.printStackTrace();
            } catch (NoSuchElementException e) {
                System.out.println("Нажато Ctrl+D, программа завершена!");
                System.exit(0);
            }
            catch (ArrayIndexOutOfBoundsException e) {
               System.out.println("Вы не передали адрес файла *.json!");
               System.exit(0);
            }
            catch (InvalidPathException e){
                System.out.println("Имя файла неверно!");
            }
            catch (NullPointerException e){
                System.out.println("Что-то пошло не так");
            }
    }
}