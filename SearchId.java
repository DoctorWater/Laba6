import java.io.Serializable;
import java.util.Hashtable;
import java.util.Scanner;

public class SearchId implements Serializable {
    public static String search(Hashtable<String, Product> table, String ID){
        Scanner scanner = new Scanner(System.in);

        boolean breaker = false;
        final String[] key = {null};
        while (!breaker)
        {
            try {
                Long.parseLong(ID);
                long finalId = Long.parseLong(ID);
                table.forEach((k, v)->{
                    if (v.getId()== finalId)
                    {
                        key[0]=v.getKey();
                    }
                });
                if (key[0]==null){
                    throw new NullPointerException();
                }
                breaker=true;
            }
            catch (NumberFormatException | NullPointerException e){
                System.out.println("Введен неверный ID! Введите новый ID");
                ID=scanner.nextLine();
                breaker=false;
            }
        }



        return key[0];
    }
}
