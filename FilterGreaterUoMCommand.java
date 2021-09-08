 
import java.io.Serial;
import java.io.Serializable;
import java.util.*;

public class FilterGreaterUoMCommand implements Command, Serializable {
    @Serial
    private static final long serialVersionUID = 8L;
    private Hashtable<String, Product> table;
    private final String UoM;
    public FilterGreaterUoMCommand(String theUoM){
        UoM=theUoM;
    }
    public void execute(){
        try {
            if (!UoM.equals("SQUARE_METERS") && !UoM.equals("PCS") && !UoM.equals("LITERS") && !UoM.equals("MILLILITERS") && !UoM.equals("GRAMS"))
                throw new IllegalArgumentException("Неверная величина!");
            List<Product> tableValues= new ArrayList<>(table.values());
            Comparator<Product> pcomp = new ProductComporators.ProductUoMComparator();
            tableValues.sort(pcomp);
            for(Product  p : tableValues){
                if (p.getUnitOfMeasureString().compareTo(UoM)>0){
                    System.out.println(p.getName() + " " + p.getUnitOfMeasureString());
                }
            }
        }
        catch (IllegalArgumentException e)
        {
            System.out.println(e.getMessage());
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
