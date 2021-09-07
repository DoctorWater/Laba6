import java.io.IOException;
import java.util.Hashtable;

public interface Command {
    void execute() throws IOException, RecursionExeption, IllegalVarValue;
    Hashtable<String, Product> returnTable();
}
