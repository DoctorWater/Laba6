import java.util.*;

public class PrintFieldDescendingPrice {
    public static void print(Hashtable<String, Product> table){
        List<Product> tableValues= new ArrayList<>(table.values());
        Comparator<Product> pcomp = new ProductComporators.ProductPriceComparator();
        Collections.sort(tableValues,pcomp);
        for(Product  p : tableValues){
            System.out.println(p.getName() + " " + p.getPrice());
        }
    }
}
