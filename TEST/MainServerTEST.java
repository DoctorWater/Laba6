package TEST;

import common.*;
import mainClient.java.IllegalVarValue;
import mainServer.java.ServerReceiver;
import mainServer.java.ServerSender;

import java.io.IOException;


public class MainServerTEST {
    public static void main(String[] args) throws IOException, ClassNotFoundException, RecursionExeption, IllegalVarValue {
        ShowCommand show = new ShowCommand();
        ServerReceiver server = new ServerReceiver();
        ServerSender.send(show);

    }
}