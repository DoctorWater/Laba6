package common;

import mainClient.java.ClientSender;
import mainClient.java.IllegalVarValue;
import mainClient.java.SearchId;
import mainClient.java.ClientReceiver;

import java.io.*;
import java.util.*;

public class DetermineCommand implements Serializable {
    @Serial
    private static final long serialVersionUID = 6L;
    private String c;
    private Hashtable<String, Product> products;
    private final ArrayList<String> commands;
    private final ArrayList<String> previousFilenames;
    private final Date initializationDate;
    public DetermineCommand (String theC,  ArrayList<String> theCommands, ArrayList<String> thePreviousFilenames, Date theInitializationDate) throws IOException {
        c=theC;
        commands=theCommands;
        previousFilenames=thePreviousFilenames;
        initializationDate=theInitializationDate;
    }
    public void execute() throws IOException, RecursionExeption {
        Scanner scanner = new Scanner(c);
        try {
            switch (c) {
                case "help":
                    Command help = new HelpCommand();
                    help.setProductHashtable(products);
                    help.execute();
                    products = help.returnTable();
                    break;
                case "info":
                    InfoCommand info = new InfoCommand(initializationDate);
                    ClientSender.send(info);
                    info= (InfoCommand) ClientReceiver.receive();
                    info.print();
                    System.out.println("Команда выполнена!");
                    break;
                case "show":
                    ShowCommand show = new ShowCommand();
                    ClientSender.send(show);
                    show = (ShowCommand) ClientReceiver.receive();
                    show.executeClient();
                    System.out.println("Команда выполнена!");
                    break;
                case "clear":
                    Command cleaner = new ClearCommand();
                    ClientSender.send(cleaner);
                    System.out.println("Команда выполнена!");
                    break;
                case "exit":
                    System.exit(0);
                    break;
                case "print_field_descending_price":
                    PrintFieldDescendingPriceCommand printer = new PrintFieldDescendingPriceCommand();
                    ClientSender.send(printer);
                    printer= (PrintFieldDescendingPriceCommand) ClientReceiver.receive();
                    printer.execute();
                    System.out.println("Команда выполнена!");
                    break;
                case "history":
                    for (String command : commands) System.out.println(command);
                    System.out.println("Команда выполнена!");
                    break;
                default:
                    if (scanner.findInLine("^insert+\\s+\\w+") != null) {
                        try {
                            scanner = new Scanner(c);
                            c = scanner.findInLine("\\s+\\w+\\s*");
                            scanner = new Scanner(c);
                            c = scanner.findInLine("\\w+");
                            CreateNewProductCommand creator = new CreateNewProductCommand(c);
                            creator.executeClient();
                            ClientSender.send(creator);
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
                                RemoveCommand remover = new RemoveCommand(c);
                                ClientSender.send(remover);
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
                                    CreateNewProductCommand creatorUp = new CreateNewProductCommand(c);
                                    creatorUp.setProductHashtable(products);
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
                                    ExecuteCommand execution = new ExecuteCommand(c, previousFilenames, commands, initializationDate);
                                    execution.execute();
                                } else {
                                    if (scanner.findInLine("^filter_greater_than_unit_of_measure+\\s+") != null) {
                                        scanner = new Scanner(c);
                                        c = scanner.findInLine("\\s+\\w+");
                                        scanner = new Scanner(c);
                                        c = scanner.findInLine("\\w+");
                                        Command filter = new FilterGreaterUoMCommand(c);
                                        filter.setProductHashtable(products);
                                        filter.execute();
                                        System.out.println("Команда выполнена!");
                                    } else {
                                        if (scanner.findInLine("^remove_greater+\\s+") != null) {
                                            scanner = new Scanner(c);
                                            c = scanner.findInLine("\\s+\\w+");
                                            scanner = new Scanner(c);
                                            c = scanner.findInLine("\\w+");
                                            RemoveGreaterCommand removeG = new RemoveGreaterCommand(c);
                                            removeG.setProductHashtable(products);
                                            removeG.execute();
                                            products=removeG.returnTable();
                                            System.out.println("Команда выполнена!");
                                        } else {
                                            if (scanner.findInLine("^remove_lower+\\s+") != null) {
                                                scanner = new Scanner(c);
                                                c = scanner.findInLine("\\s+\\w+");
                                                scanner = new Scanner(c);
                                                c = scanner.findInLine("\\w+");
                                                RemoveSmallerCommand removeS = new RemoveSmallerCommand(c);
                                                removeS.setProductHashtable(products);
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
                                                        CountGreaterPNCommand counter = new CountGreaterPNCommand(Integer.parseInt(c));
                                                        counter.setProductHashtable(products);
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
        } catch (NullPointerException | IllegalVarValue | ClassNotFoundException e) {
            System.out.println("Аргумент пуст!");

        }
    }
}