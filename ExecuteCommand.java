import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.NoSuchElementException;

public class ExecuteCommand implements Command{
    private static final ArrayList<String> s = new ArrayList<>();
    private final Hashtable<String, Product> table;
    private final String filename;
    private final ArrayList<String> commands;
    private final ArrayList<String> previousFilenames;
    private final Date initializationDate;
    private final String saveFilename;

    public ExecuteCommand(String theFilename, ArrayList<String> thePreviousFilenames, Hashtable<String, Product> theTable, String theSaveFilename, ArrayList<String> theCommands, Date theInitializationDate){
        filename=theFilename;
        previousFilenames=thePreviousFilenames;
        commands=theCommands;
        initializationDate=theInitializationDate;
        saveFilename=theSaveFilename;
        table=theTable;
    }
    public void execute() throws IOException, RecursionExeption {
        try {
            Path filenamePath= Paths.get(filename);
            if(filenamePath.toRealPath().toString().length()>3 && filenamePath.toRealPath().toString().trim().startsWith("/dev"))
                throw new InvalidPathException("","Строка не может быть преобразована в путь!");
            for (String object : previousFilenames) {
                if (filename.equals(object)) {
                    throw new RecursionExeption("Эта команда создаст рекурсию и вызовет поломку программы. Устраните ее для корректного исполнения скрипта: execute " + object,object);
                }
            }
            previousFilenames.add(filename);
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line = "";
        while ((line = reader.readLine()) != null) {
            s.add(line);
            DetermineCommand determination= new DetermineCommand(line,table,saveFilename, commands, previousFilenames, initializationDate);
            determination.execute();
        }
    } catch (RecursionExeption recursionExeption) {
            System.out.println(recursionExeption.getMessage());
        } catch (IOException|OutOfMemoryError e) {
            System.out.println("Неверное имя файла!");
        }
        catch (NoSuchElementException e) {
            System.out.println("Нажато Ctrl+D, программа завершена!");
            System.exit(0);
        }
        catch (InvalidPathException e){
        System.out.println("Имя файла неверно!");
    }
    }
}
