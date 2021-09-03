import java.util.ArrayList;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;

public class RemoveSmallerCommand implements Command, Returnable {
    private Hashtable<String, Product> table;
    private  String key;
    public RemoveSmallerCommand(Hashtable<String, Product> theTable, String theKey){
        table=theTable;
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
    public Hashtable<String, Product> returnCommand(){
        return table;
    }
}
