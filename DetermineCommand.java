import java.io.*;
import java.util.*;

public class DetermineCommand implements Command, Serializable {
    @Serial
    private static final long serialVersionUID = 6L;
    private String c;
    private Hashtable<String, Product> products;
    private final String filename;
    private final ArrayList<String> commands;
    private final ArrayList<String> previousFilenames;
    private final Date initializationDate;

    public DetermineCommand (String theC, Hashtable<String, Product> theProducts, String theFilename, ArrayList<String> theCommands, ArrayList<String> thePreviousFilenames, Date theInitializationDate) throws IOException {
        c=theC;
        products=theProducts;
        filename=theFilename;
        commands=theCommands;
        previousFilenames=thePreviousFilenames;
        initializationDate=theInitializationDate;
    }
    public void execute() throws IOException, RecursionExeption {
        Scanner scanner = new Scanner(c);

        try {
            switch (c) {
                case "help":
                    Command help = new HelpCommand(products);

                    help.execute();
                    products = help.returnTable();
                    break;
                case "info":
                    Command info = new InfoCommand(products, initializationDate);
                    info.execute();
                    System.out.println("Команда выполнена!");
                    break;
                case "show":
                    System.out.println(products.toString());
                    System.out.println("Команда выполнена!");
                    break;
                case "clear":
                    products.clear();
                    System.out.println("Команда выполнена!");
                    break;
                case "exit":
                    System.exit(0);
                    break;
                case "print_field_descending_price":
                    Command printer = new PrintFieldDescendingPriceCommand(products);
                    printer.execute();
                    System.out.println("Команда выполнена!");
                    break;
                case "history":
                    for (int i = 0; i < commands.size(); i++)
                        System.out.println(commands.get(i));
                    System.out.println("Команда выполнена!");
                    break;
                case "save":
                    Command saver = new SaveToFileCommand(filename, products);
                    saver.execute();
                    break;
                default:
                    if (scanner.findInLine("^insert+\\s+\\w+") != null) {
                        try {
                            scanner = new Scanner(c);
                            c = scanner.findInLine("\\s+\\w+\\s*");
                            scanner = new Scanner(c);
                            c = scanner.findInLine("\\w+");

                            CreateNewProductCommand creatorIns = new CreateNewProductCommand(products, c);
                            creatorIns.execute();
                            products = creatorIns.returnTable();
                            System.out.println(products.toString());
                            System.out.println("Команда выполнена!");
                        } catch (NumberFormatException | NullPointerException e) {
                            System.out.println("Неверный ключ!");
                        }
                    } else {
                        if (scanner.findInLine("^remove_key+\\s+") != null) {
                            scanner = new Scanner(c);
                            c = scanner.findInLine("\\s+\\w+\\s*");
                            scanner = new Scanner(c);
                            c = scanner.findInLine("\\w+");
                            try {
                                products.remove(c);
                                System.out.println("Команда выполнена!");
                            } catch (NumberFormatException e) {
                                System.out.println("Неверный ключ!");
                            }
                        } else {
                            if (scanner.findInLine("^update+\\s+\\w+") != null) {
                                try {
                                    scanner = new Scanner(c);
                                    c = scanner.findInLine("\\s+\\w+\\s*");
                                    scanner = new Scanner(c);
                                    c = scanner.findInLine("\\w+");
                                    String key = SearchId.search(products, c);
                                    products.remove(key);
                                    CreateNewProductCommand creatorUp = new CreateNewProductCommand(products, c);
                                    creatorUp.execute();
                                    products = creatorUp.returnTable();
                                    System.out.println("Команда выполнена!");

                                } catch (NumberFormatException e1) {
                                    System.out.println("Неверный ID!");
                                }
                            } else {
                                if (scanner.findInLine("^execute_script+\\s+") != null) {
                                    scanner = new Scanner(c);
                                    c = scanner.findInLine("\\s+\\S+");
                                    scanner = new Scanner(c);
                                    c = scanner.findInLine("\\S+");
                                    ExecuteCommand execution = new ExecuteCommand(c, previousFilenames, products, filename, commands, initializationDate);
                                    execution.execute();
                                } else {
                                    if (scanner.findInLine("^filter_greater_than_unit_of_measure+\\s+") != null) {
                                        scanner = new Scanner(c);
                                        c = scanner.findInLine("\\s+\\w+");
                                        scanner = new Scanner(c);
                                        c = scanner.findInLine("\\w+");
                                        Command filter = new FilterGreaterUoMCommand(products, c);
                                        filter.execute();
                                        System.out.println("Команда выполнена!");
                                    } else {
                                        if (scanner.findInLine("^remove_greater+\\s+") != null) {
                                            scanner = new Scanner(c);
                                            c = scanner.findInLine("\\s+\\w+");
                                            scanner = new Scanner(c);
                                            c = scanner.findInLine("\\w+");
                                            RemoveGreaterCommand removeG = new RemoveGreaterCommand(products,c);
                                            removeG.execute();
                                            products=removeG.returnTable();
                                            System.out.println("Команда выполнена!");
                                        } else {
                                            if (scanner.findInLine("^remove_lower+\\s+") != null) {
                                                scanner = new Scanner(c);
                                                c = scanner.findInLine("\\s+\\w+");
                                                scanner = new Scanner(c);
                                                c = scanner.findInLine("\\w+");
                                                RemoveSmallerCommand removeS = new RemoveSmallerCommand(products,c);
                                                removeS.execute();
                                                products=removeS.returnTable();
                                                System.out.println("Команда выполнена!");
                                            } else {
                                                if (scanner.findInLine("^count_greater_than_part_number+\\s+") != null) {
                                                    scanner = new Scanner(c);
                                                    c = scanner.findInLine("\\s+\\w+");
                                                    scanner = new Scanner(c);
                                                    c = scanner.findInLine("\\w+");
                                                    try {
                                                        CountGreaterPNCommand counter = new CountGreaterPNCommand(products, Integer.parseInt(c));
                                                        counter.execute();
                                                        System.out.println("Команда выполнена!");
                                                    } catch (NumberFormatException e) {
                                                        System.out.println("Неверное сравнительное значение!");
                                                    } catch (NoSuchElementException e) {
                                                        System.out.println("Нажато Ctrl+D, программа завершена!");
                                                        System.exit(0);
                                                    }
                                                } else {
                                                    System.out.println("Введена неверная команда!");
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    break;
            }

        } catch (NullPointerException | IllegalVarValue e) {
            System.out.println("Аргумент пуст!");

        }
    }
    public Hashtable<String, Product> returnTable(){
        return products;
    }
}
