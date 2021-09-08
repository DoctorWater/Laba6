 
import java.io.Serial;
import java.io.Serializable;
import java.util.*;

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
        Comparator<Product> pcomp = new ProductComporators.ProductNameComparator().thenComparing(new ProductComporators.ProductPriceComparator());
        tableValues.sort(pcomp);
        for(Product  p : tableValues){
            if (tableValues.indexOf(p)<tableValues.indexOf(table.get(key))){
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