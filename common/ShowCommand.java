package common;

import mainClient.java.IllegalVarValue;

import java.io.IOException;
import java.io.Serializable;
import java.util.Hashtable;

public class ShowCommand implements Command, Serializable {
    private static final long serialVersionUID = 22L;
    Hashtable<String, Product> table = new Hashtable<String, Product>();
    public void executeClient() throws IllegalVarValue {
        System.out.println(table.isEmpty());
        System.out.println(table.toString());
    }

    @Override
    public void execute() throws IOException, RecursionExeption, IllegalVarValue, ClassNotFoundException {
        System.out.println("Выполенена команда Show");
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
