import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class CountGreaterPNCommand implements Command {
    private final Hashtable<String,Product> productHashtable;
    private final int checker;

    public CountGreaterPNCommand(Hashtable<String,Product> theTable, int theChecker){
        productHashtable=theTable;
        checker=theChecker;
    }
    public void execute (){
        int counter=0;
        List<Product> tableValues= new ArrayList<>(productHashtable.values());
        for(Product  p : tableValues){
            if (Integer.parseInt(p.getPartNumber())>checker){
                counter++;
            }
        }
        System.out.println("Количество элементов, значение поля partNumber которых больше заданного: " + counter);
    }
}
