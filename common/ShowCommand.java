package common;

import mainClient.java.IllegalVarValue;

import java.io.IOException;
import java.io.Serializable;
import java.util.Hashtable;

public class ShowCommand implements Command, Serializable {
    Hashtable<String, Product> table = new Hashtable<String, Product>();
    @Override
    public void execute() throws IOException, RecursionExeption, IllegalVarValue {
        System.out.println(table.toString());
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
