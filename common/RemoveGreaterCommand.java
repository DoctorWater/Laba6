package common;

import mainClient.java.ProductComporators;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Stream;

public class RemoveGreaterCommand implements Command, Serializable {
    @Serial
    private static final long serialVersionUID = 17L;
    private Hashtable<String, Product> table;
    private final String key;
    public RemoveGreaterCommand(String theKey){
        key=theKey;
    }
    public void execute(){
        List<Product> tableValues= new ArrayList<>(table.values());
        Product[] products = tableValues.toArray(new Product[0]);
        Stream<Product> stream = Arrays.stream(products);
        Integer index = tableValues.indexOf(table.get(key));
        Comparator<Product> pcomp = new ProductComporators.ProductNameComparator().thenComparing(new ProductComporators.ProductPriceComparator());
        stream.sorted(pcomp).skip(index).forEach(x->table.remove(x.getKey()));
    }
    public Hashtable<String, Product> returnTable(){
        return table;
    }

    @Override
    public void setProductHashtable(Hashtable<String, Product> productHashtable) {
        this.table=productHashtable;
    }
}