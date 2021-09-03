import java.util.*;

public class FilterGreaterUoMCommand implements Command {
    private final Hashtable<String, Product> table;
    private final String UoM;
    public FilterGreaterUoMCommand(Hashtable<String, Product> theTable, String theUoM){
        table=theTable;
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

}
