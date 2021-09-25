package common;

import mainClient.java.IllegalVarValue;

import java.io.IOException;
import java.io.Serializable;
import java.util.Hashtable;

public class RemoveCommand implements Command, Serializable {
    private static final long serialVersionUID = 21L;
    private String key;
    private Hashtable<String, Product> table = new Hashtable<String, Product>();
    public RemoveCommand(String key){
        this.key=key;
    }
    @Override
    public void execute() throws IOException, RecursionExeption, IllegalVarValue, ClassNotFoundException {
        table.remove(key);
    }

    @Override
    public Hashtable<String, Product> returnTable() {
        return table;
    }

    @Override
    public void setProductHashtable(Hashtable<String, Product> productHashtable) {
        this.table = productHashtable;
    }
}
