package common;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class CountGreaterPNCommand implements Command, Serializable {
    @Serial
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
        for(Product  p : tableValues){
            if (Integer.parseInt(p.getPartNumber())>checker){
                counter++;
            }
        }
        //System.out.println("Количество элементов, значение поля partNumber которых больше заданного: " + counter);
    }

    @Override
    public Hashtable<String, Product> returnTable() {
        return productHashtable;
    }
}
