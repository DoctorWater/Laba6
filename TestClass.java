import java.io.IOException;
import java.util.Hashtable;

public class TestClass {
    Command determine = new CountGreaterPNCommand(new Hashtable<String,Product>(),3);
    public void testMethod() throws RecursionExeption, IOException {
        determine.execute();
    }
}