package common;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.NoSuchElementException;

public class ExecuteCommand implements Serializable, Command {
    private static final long serialVersionUID = 7L;
    private static final ArrayList<String> s = new ArrayList<>();
    private final String filename;
    private final ArrayList<String> commands;
    private final ArrayList<String> previousFilenames;
    private final Date initializationDate;
    private final InetSocketAddress socketAddress;
    private Hashtable<String, Product> table = new Hashtable<String, Product>();

    public ExecuteCommand(String theFilename, ArrayList<String> thePreviousFilenames, ArrayList<String> theCommands, Date theInitializationDate, InetSocketAddress socketAddress) {
        filename = theFilename;
        previousFilenames = thePreviousFilenames;
        commands = theCommands;
        initializationDate = theInitializationDate;
        this.socketAddress = socketAddress;
    }

    public void execute() throws IOException, RecursionExeption {
        try {
            Path filenamePath = Paths.get(filename);
            if (filenamePath.toRealPath().toString().length() > 3 && filenamePath.toRealPath().toString().trim().startsWith("/dev"))
                throw new InvalidPathException("", "Строка не может быть преобразована в путь!");
            for (String object : previousFilenames) {
                if (filename.equals(object)) {
                    throw new RecursionExeption("Эта команда создаст рекурсию и вызовет поломку программы. Устраните ее для корректного исполнения скрипта: execute " + object, object);
                }
            }
            previousFilenames.add(filename);
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line = "";
            while ((line = reader.readLine()) != null) {
                s.add(line);
            }

            for (String cur : s) {
                DetermineCommand determination = new DetermineCommand(cur, commands, previousFilenames, initializationDate,socketAddress);
                determination.execute();
            }
        } catch (RecursionExeption recursionExeption) {
            System.out.println(recursionExeption.getMessage());
        } catch (IOException | OutOfMemoryError e) {
            System.out.println("Неверное имя файла! " + e.getCause().toString());
        } catch (NoSuchElementException e) {
            System.out.println("Нажато Ctrl+D, программа завершена!");
            System.exit(0);
        } catch (InvalidPathException e) {
            System.out.println("Имя файла неверно!");
        }
    }

    @Override
    public Hashtable<String, Product> returnTable() {
        return table;
    }

    @Override
    public void setProductHashtable(Hashtable<String, Product> productHashtable) {
        table = productHashtable;
    }
}
