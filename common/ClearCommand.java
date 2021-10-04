package common;

import mainClient.java.IllegalVarValue;

import java.io.IOException;
import java.io.Serializable;
import java.util.Hashtable;

public class ClearCommand implements  Command, Serializable {
    private static final long serialVersionUID = 27L;
    private Hashtable<String, Product> table = new Hashtable<String, Product>();

    @Override
    public void execute() throws IOException, RecursionExeption, IllegalVarValue {
        table.clear();
    }

    @Override
    public Hashtable<String, Product> returnTable() {
        return table;
    }

    @Override
    public void setProductHashtable(Hashtable<String, Product> productHashtable) {
        this.table=productHashtable;
    }
}
