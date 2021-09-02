public class RecursionExeption extends Exception {
    private String problemFilename;
    public String getProblemFilename() {
        return problemFilename;
    }
    public RecursionExeption(String message, String Filename){
        super(message);
        problemFilename=Filename;
    }
}
