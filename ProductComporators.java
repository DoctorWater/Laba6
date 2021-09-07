import java.io.Serial;
import java.io.Serializable;
import java.util.Comparator;

public class ProductComporators implements Serializable {
    @Serial
    private static final long serialVersionUID = 15L;
    public static class ProductPriceComparator implements Comparator<Product> {
        @Override
        public int compare(Product o1, Product o2) {
            if(o1.getPrice()> o2.getPrice())
                return 1;
            else if(o1.getPrice()< o2.getPrice())
                return -1;
            else
                return 0;
        }
    }
    public static class ProductNameComparator implements Comparator<Product> {
        @Override
        public int compare(Product o1, Product o2) {
            return o1.getName().compareTo(o2.getName());
        }
    }
    public static class ProductUoMComparator implements Comparator<Product> {
        @Override
        public int compare(Product o1, Product o2) {
            return o1.getUnitOfMeasureString().compareTo(o2.getUnitOfMeasureString());
        }
    }
}
