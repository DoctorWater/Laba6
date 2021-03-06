package common;

import mainClient.java.ProductComporators;

  
import java.io.Serializable;
import java.util.*;

public class PrintFieldDescendingPriceCommand implements Command, Serializable {
     
    private static final long serialVersionUID = 14L;
    private Hashtable<String, Product> table = new Hashtable<String, Product>();

    public PrintFieldDescendingPriceCommand(){

    }
    public void execute(){
        List<Product> tableValues= new ArrayList<>(table.values());
        Comparator<Product> pcomp = new ProductComporators.ProductPriceComparator();
        Collections.sort(tableValues,pcomp);
        for(Product  p : tableValues){
            System.out.println(p.getName() + " " + p.getPrice());
        }
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
