import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

public class InfoCommand implements Command{
    private Hashtable<String, Product> table;
    private Date initializationDate;
    public InfoCommand(Hashtable<String, Product> theTable, Date theInitializationDate){
        table=theTable;
        initializationDate=theInitializationDate;
    }
    public void execute(){
        SimpleDateFormat formatForDate = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
        System.out.println("Размер коллекции: "+table.size()+"\n");
        System.out.println("Дата инициализации: "+ formatForDate.format(initializationDate));
        System.out.println("Тип коллекции: HashTable");
        System.out.println("");
    }
}
