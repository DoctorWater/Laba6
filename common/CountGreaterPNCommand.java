package common;

  
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.stream.Stream;

public class CountGreaterPNCommand implements Command, Serializable {
     
    private static final long serialVersionUID = 3L;
    private Hashtable<String,Product> productHashtable;
    private final int checker;
    private Integer counter=0;
    public Integer getCounter() {
        return counter;
    }

    public CountGreaterPNCommand(int theChecker){
        checker=theChecker;
    }

    @Override
    public void setProductHashtable(Hashtable<String,Product> productHashtable){
        this.productHashtable=productHashtable;
    }
    public void execute (){
        List<Product> tableValues= new ArrayList<>(productHashtable.values());
        Product[] products = tableValues.toArray(new Product[0]);
        Stream<Product> stream = Arrays.stream(products);
        stream.filter(x->Integer.parseInt(x.getPartNumber())>checker).forEach(x->counter++);
    }

    @Override
    public Hashtable<String, Product> returnTable() {
        return productHashtable;
    }
}
