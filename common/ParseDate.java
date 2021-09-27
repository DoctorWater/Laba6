package common;

import java.io.Serial;
import java.io.Serializable;
import java.text.*;
import java.util.*;

public class ParseDate implements Serializable {
    @Serial
    private static final long serialVersionUID = 13L;
    public static Date parseDate (String str){
        Date date = new Date();
        SimpleDateFormat ft = new SimpleDateFormat ("dd-MM-yyyy");
        try {
            date = ft.parse(str);
        }catch (ParseException e) {
            System.out.println("Ошибка парсинга даты!");
        }
        return date;
    }
}
