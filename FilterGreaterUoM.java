import java.util.*;

public class FilterGreaterUoM {
    public static void print (Hashtable<String, Product> table, String UoM){
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
