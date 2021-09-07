import java.io.Serial;
import java.io.Serializable;

public class RecursionExeption extends Exception implements Serializable {
    @Serial
    private static final long serialVersionUID = 16L;
    private String problemFilename;
    public String getProblemFilename() {
        return problemFilename;
    }
    public RecursionExeption(String message, String Filename){
        super(message);
        problemFilename=Filename;
    }
}
