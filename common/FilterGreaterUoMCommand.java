package common;

import mainClient.java.ProductComporators;

  
import java.io.Serializable;
import java.util.*;
import java.util.stream.Stream;

public class FilterGreaterUoMCommand implements Command, Serializable {
     
    private static final long serialVersionUID = 8L;
    private Hashtable<String, Product> table;
    private final String UoM;

    public FilterGreaterUoMCommand(String UoM) {
        this.UoM = UoM;
    }

    public void execute() {

    }

    public void executeClient() {
        try {

            if (!UoM.equals("SQUARE_METERS") && !UoM.equals("PCS") && !UoM.equals("LITERS") && !UoM.equals("MILLILITERS") && !UoM.equals("GRAMS"))
                throw new IllegalArgumentException("Неверная величина!");
            List<Product> tableValues = new ArrayList<>(table.values());
            Product[] products = tableValues.toArray(new Product[0]);
            Comparator<Product> pcomp = new ProductComporators.ProductUoMComparator();
            Stream<Product> stream = Arrays.stream(products);
            stream.sorted(pcomp).filter(x -> x.getUnitOfMeasureString().compareTo(UoM) > 0).forEach(x -> System.out.println(x.getName() + " " + x.getUnitOfMeasureString()));
        }
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Hashtable<String, Product> returnTable() {
        return table;
    }

    @Override
    public void setProductHashtable(Hashtable<String, Product> productHashtable) {
        this.table = productHashtable;
    }
}
