package common;

import mainClient.java.IllegalVarValue;
import mainServer.java.FilenameHolder;
import mainServer.java.SaveToFileCommand;

import java.io.IOException;
import java.io.Serializable;
import java.util.Hashtable;

public class ExitCommand implements Command, Serializable {
    private static final long serialVersionUID = 23L;
    Hashtable<String, Product> table = new Hashtable<>();
    @Override
    public void execute() throws IOException, RecursionExeption, IllegalVarValue {
        SaveToFileCommand save = new SaveToFileCommand(FilenameHolder.getFilename());
        save.setProductHashtable(table);
        save.execute();
        System.exit(0);
    }

    @Override
    public Hashtable<String, Product> returnTable() {
        return table;
    }

    @Override
    public void setProductHashtable(Hashtable<String, Product> productHashtable) {
        table=productHashtable;
    }
}
