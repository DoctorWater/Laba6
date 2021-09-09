package common;

import mainClient.java.ProductComporators;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;

public class RemoveSmallerCommand implements Command, Serializable {
    @Serial
    private static final long serialVersionUID = 18L;
    private Hashtable<String, Product> table;
    private final String key;
    public RemoveSmallerCommand(String theKey){
        key=theKey;
    }
    public void execute(){
        List<Product> tableValues= new ArrayList<>(table.values());
        Comparator<Product> pcomp = new ProductComporators.ProductNameComparator().thenComparing(new ProductComporators.ProductPriceComparator());
        tableValues.sort(pcomp);
        for(Product  p : tableValues){
            if (tableValues.indexOf(p)>tableValues.indexOf(table.get(key))){
                table.remove(p.getKey());
            }
        }
    }
    public Hashtable<String, Product> returnTable(){
        return table;
    }

    @Override
    public void setProductHashtable(Hashtable<String, Product> productHashtable) {
        this.table=productHashtable;
    }
}