import java.util.*;

public class RemoveCompare {
    public static Hashtable<String, Product> removeGreater(Hashtable<String,Product> productHashtable,String key){
        List<Product> tableValues= new ArrayList<>(productHashtable.values());
        Comparator<Product> pcomp = new ProductComporators.ProductNameComparator().thenComparing(new ProductComporators.ProductPriceComparator());
        tableValues.sort(pcomp);
        for(Product  p : tableValues){
            if (tableValues.indexOf(p)<tableValues.indexOf(productHashtable.get(key))){
                productHashtable.remove(p.getKey());
            }
        }
        return  productHashtable;
    }
    public static Hashtable<String, Product> removeSmaller(Hashtable<String,Product> productHashtable,String key){
        List<Product> tableValues= new ArrayList<>(productHashtable.values());
        Comparator<Product> pcomp = new ProductComporators.ProductNameComparator().thenComparing(new ProductComporators.ProductPriceComparator());
        tableValues.sort(pcomp);
        for(Product  p : tableValues){
            if (tableValues.indexOf(p)>tableValues.indexOf(productHashtable.get(key))){
                productHashtable.remove(p.getKey());
            }
        }
        return  productHashtable;
    }
}
