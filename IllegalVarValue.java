package mainClient.java;

public class IllegalVarValue extends Exception{
    private String message=null;
    public IllegalVarValue(){

    }
    public IllegalVarValue(String message){
        this.message=message;
    }
    public String getMessage(String message){
        return message;
    }
}
