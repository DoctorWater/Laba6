package common;

import mainClient.java.IllegalVarValue;

import java.io.IOException;
import java.io.Serializable;
import java.util.Hashtable;

public class UpdateCommand implements Command, Serializable {
    private static final long serialVersionUID = 32L;
    private Hashtable<String,Product> table = new Hashtable<>();
    private Product product;
    private String ID;
    private String key;
    @Override
    public void execute() throws IOException, RecursionExeption, IllegalVarValue, ClassNotFoundException {
        key=SearchId.search(table,ID);
        table.remove(key);
        table.put(key,product);
    }

    @Override
    public Hashtable<String, Product> returnTable() {
        return table;
    }

    @Override
    public void setProductHashtable(Hashtable<String, Product> productHashtable) {
        table = productHashtable;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getKey() {
        return key;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
