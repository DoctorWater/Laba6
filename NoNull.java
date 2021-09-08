 
public class NoNull extends Exception{
    public NoNull(String message){
        super(message);
    }
}

class NullCheck{
    public static void isNull(String s) throws NoNull {
        if (s==null) throw new NoNull("Строка не может быть пуста!");
    }
}