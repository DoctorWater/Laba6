import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

public class InfoCommand {
    public static void info(Hashtable<String, Product> hash, Date initializationDate){
        SimpleDateFormat formatForDate = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
        System.out.println("Размер коллекции: "+hash.size()+"\n");
        System.out.println("Дата инициализации: "+ formatForDate.format(initializationDate));
        System.out.println("Тип коллекции: HashTable");
        System.out.println("");
    }


}
