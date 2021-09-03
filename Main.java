import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


public class Main {

    public static void main(String[] args){
        //TODO: сделать вызов returnCommand() из execute()
        Scanner in = new Scanner(System.in);
            try {
                Date initializationDate = new Date();
                String buffer = args[0], bufferIfer;
                Scanner scanner = new Scanner(buffer);
                String filename = buffer;
                Path filenamePath= Paths.get(filename);
                if(filenamePath.toRealPath().toString().length()>3 && filenamePath.toRealPath().toString().trim().startsWith("/dev"))
                    throw new InvalidPathException("","Строка не может быть преобразована в путь!");
                ParseCommand parser = new ParseCommand(filename, args);
                parser.execute();
                Hashtable<String, Product> products = parser.returnCommand();
                ArrayList<String> commands = new ArrayList<>();
                while (true) {
                    System.out.println("ВВЕДИТЕ КОМАНДУ (ВВЕДИТЕ help ДЛЯ ПОЛУЧЕНИЯ СПИСКА ИСПОЛНЯЕМЫХ КОМАНД): ");
                    buffer = in.nextLine();
                    scanner = new Scanner(buffer);
                    bufferIfer = scanner.findInLine("^\\S+");
                    if (bufferIfer.equals("help") | bufferIfer.equals("info") | bufferIfer.equals("show") | bufferIfer.equals("insert") | bufferIfer.equals("update") | bufferIfer.equals("remove_key") | bufferIfer.equals("clear") | bufferIfer.equals("save") | bufferIfer.equals("execute_script") | bufferIfer.equals("remove_greater") | bufferIfer.equals("remove_lower") | bufferIfer.equals("history") | bufferIfer.equals("count_greater_than_part_number") | bufferIfer.equals("filter_greater_than_unit_of_measure") | bufferIfer.equals("print_field_descending_price")) {
                        commands.add(bufferIfer);
                    }
                    DetermineCommand determinator= new DetermineCommand(buffer, products, filename, commands, new ArrayList<>(), initializationDate);
                    determinator.execute();
                    products = determinator.returnCommand();
                }
            } catch (IllegalVarValue | IOException | RecursionExeption e) {
                System.out.println(e.getMessage());
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
    }

}