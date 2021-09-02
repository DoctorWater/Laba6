import java.util.*;

public class CompareProductsCommand implements Command{
    private final Hashtable<String,Product> theTable;
    public CompareProductsCommand (Hashtable<String,Product> table){
        theTable=table;
    }
    @Override
    public void execute() {
        List<Product> tableValues= new ArrayList<>(theTable.values());
        Comparator<Product> pcomp = new ProductComporators.ProductNameComparator().thenComparing(new ProductComporators.ProductPriceComparator());
        tableValues.sort(pcomp);
        for(Product  p : tableValues){
            System.out.println(p.getName() + " " + p.getPrice());
        }
    }

}
