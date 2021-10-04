package common;

import mainClient.java.ClientSender;
import mainClient.java.IllegalVarValue;
import mainClient.java.ClientReceiver;
import java.io.*;
import java.net.InetSocketAddress;
import java.util.*;

public class DetermineCommand implements Serializable {
     
    private static final long serialVersionUID = 6L;
    private String c;
    private Hashtable<String, Product> products;
    private final ArrayList<String> commands;
    private final ArrayList<String> previousFilenames;
    private final Date initializationDate;
    private boolean serverAnswer = false;
    private final InetSocketAddress address;

    public DetermineCommand(String theC, ArrayList<String> theCommands, ArrayList<String> thePreviousFilenames, Date theInitializationDate, InetSocketAddress address) throws IOException {
        c = theC;
        commands = theCommands;
        previousFilenames = thePreviousFilenames;
        initializationDate = theInitializationDate;
        this.address=address;
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
                    ClientSender.send(info, address);
                    info = (InfoCommand) ClientReceiver.receive(address);
                    info.print();
                    System.out.println("Команда выполнена!");
                    break;
                case "show":
                    ShowCommand show = new ShowCommand();
                    ClientSender.send(show,address);
                    show = (ShowCommand) ClientReceiver.receive(address);
                    show.executeClient();
                    System.out.println("Команда выполнена!");
                    break;
                case "exit":
                    ExitCommand exitCommand = new ExitCommand();
                    ClientSender.send(exitCommand,address);
                    System.exit(0);
                    break;
                case "clear":
                    Command cleaner = new ClearCommand();
                    ClientSender.send(cleaner,address);
                    System.out.println("Команда выполнена!");
                    break;
                case "print_field_descending_price":
                    PrintFieldDescendingPriceCommand printer = new PrintFieldDescendingPriceCommand();
                    ClientSender.send(printer,address);
                    printer = (PrintFieldDescendingPriceCommand) ClientReceiver.receive(address);
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
                            ShowCommand helper = new ShowCommand();
                            ClientSender.send(helper,address);
                            helper = (ShowCommand) ClientReceiver.receive(address);
                            CreateNewProductCommand creator = new CreateNewProductCommand(c);
                            creator.setProductHashtable(helper.returnTable());
                            creator.executeClient();
                            ClientSender.send(creator,address);
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
                                ClientSender.send(remover,address);
                                remover = (RemoveCommand) ClientReceiver.receive(address);
                                String answer=remover.isChecker()?"Команда выполнена!":"Такого элемента нет!";
                                System.out.println(answer);

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
                                    RemoveCommand remover = new RemoveCommand(c);
                                    ClientSender.send(remover,address);
                                    remover = (RemoveCommand) ClientReceiver.receive(address);
                                    if(!remover.isChecker()){
                                        System.out.println("Такого элемента нет!");
                                    }
                                    else{
                                        UpdateCommand updateCommand = new UpdateCommand();
                                        ClientSender.send(updateCommand, address);
                                        updateCommand = (UpdateCommand) ClientReceiver.receive(address);
                                        CreateNewProductCommand creator = new CreateNewProductCommand(updateCommand.getKey());
                                        creator.setProductHashtable(updateCommand.returnTable());
                                        creator.executeClient();
                                        ClientSender.send(creator,address);
                                        System.out.println("Команда выполнена!");
                                    }
                                } catch (NumberFormatException e1) {
                                    System.out.println("Неверный ID!");
                                }
                            } else {
                                if (scanner.findInLine("^execute_script+\\s+") != null) {
                                    scanner = new Scanner(c);
                                    c = scanner.findInLine("\\s+\\S+");
                                    scanner = new Scanner(c);
                                    c = scanner.findInLine("\\S+");
                                    ExecuteCommand execution = new ExecuteCommand(c, previousFilenames, commands, initializationDate, address);
                                    execution.execute();
                                } else {
                                    if (scanner.findInLine("^filter_greater_than_unit_of_measure+\\s+") != null) {
                                        scanner = new Scanner(c);
                                        c = scanner.findInLine("\\s+\\w+");
                                        scanner = new Scanner(c);
                                        c = scanner.findInLine("\\w+");
                                        FilterGreaterUoMCommand filter = new FilterGreaterUoMCommand(c);
                                        ClientSender.send(filter,address);
                                        filter = (FilterGreaterUoMCommand) ClientReceiver.receive(address);
                                        filter.executeClient();
                                        System.out.println("Команда выполнена!");
                                    } else {
                                        if (scanner.findInLine("^remove_greater+\\s+") != null) {
                                            scanner = new Scanner(c);
                                            c = scanner.findInLine("\\s+\\w+");
                                            scanner = new Scanner(c);
                                            c = scanner.findInLine("\\w+");
                                            RemoveGreaterCommand removeG = new RemoveGreaterCommand(c);
                                            ClientSender.send(removeG,address);
                                            System.out.println("Команда выполнена!");
                                        } else {
                                            if (scanner.findInLine("^remove_lower+\\s+") != null) {
                                                scanner = new Scanner(c);
                                                c = scanner.findInLine("\\s+\\w+");
                                                scanner = new Scanner(c);
                                                c = scanner.findInLine("\\w+");
                                                RemoveSmallerCommand removeS = new RemoveSmallerCommand(c);
                                                ClientSender.send(removeS,address);
                                                System.out.println("Команда выполнена!");
                                            } else {
                                                if (scanner.findInLine("^count_greater_than_part_number+\\s+") != null) {
                                                    scanner = new Scanner(c);
                                                    c = scanner.findInLine("\\s+\\w+");
                                                    scanner = new Scanner(c);
                                                    c = scanner.findInLine("\\w+");
                                                    try {
                                                        CountGreaterPNCommand counter = new CountGreaterPNCommand(Integer.parseInt(c));
                                                        ClientSender.send(counter,address);
                                                        counter = (CountGreaterPNCommand) ClientReceiver.receive(address);
                                                        System.out.println(counter.getCounter());
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
            System.out.println("Аргумент пуст или сервер недоступен!");

        }
    }
}