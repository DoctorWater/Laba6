package common;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

public class InfoCommand implements Command, Serializable {
    private static final long serialVersionUID = 10L;
    private  Hashtable<String, Product> table;
    private final Date initializationDate;
    public InfoCommand(Date theInitializationDate){
        initializationDate=theInitializationDate;
    }
    public void execute(){
        SimpleDateFormat formatForDate = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
        System.out.println("Размер коллекции: "+table.size()+"\n");
        System.out.println("Дата инициализации: "+ formatForDate.format(initializationDate));
        System.out.println("Тип коллекции: HashTable");
        System.out.println("");
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