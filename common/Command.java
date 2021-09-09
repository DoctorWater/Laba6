package common;

import mainClient.java.IllegalVarValue;

import java.io.IOException;
import java.util.Hashtable;

public interface Command {
    void execute() throws IOException, RecursionExeption, IllegalVarValue;
    Hashtable<String, Product> returnTable();
    void setProductHashtable(Hashtable<String,Product> productHashtable);
}
