import java.util.*;

public class PrintFieldDescendingPrice implements Command {
    private Hashtable<String, Product> table;
    public PrintFieldDescendingPrice(Hashtable<String, Product> theTable){
        table=theTable;
    }
    public void execute(){
        List<Product> tableValues= new ArrayList<>(table.values());
        Comparator<Product> pcomp = new ProductComporators.ProductPriceComparator();
        Collections.sort(tableValues,pcomp);
        for(Product  p : tableValues){
            System.out.println(p.getName() + " " + p.getPrice());
        }
    }
}
