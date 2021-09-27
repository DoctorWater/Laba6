package common;

import mainClient.java.IllegalVarValue;

import java.io.IOException;
import java.io.Serializable;
import java.util.Hashtable;

public class RemoveCommand implements Command, Serializable {
    private static final long serialVersionUID = 21L;
    private String key;
    private boolean checker=false;
    private Hashtable<String, Product> table = new Hashtable<String, Product>();
    public RemoveCommand(String key){
        this.key=key;
    }
    @Override
    public void execute() throws IOException, RecursionExeption, IllegalVarValue, ClassNotFoundException {
        if(table.containsKey(key))
            checker=true;
        table.remove(key);
    }

    public boolean isChecker() {
        return checker;
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
