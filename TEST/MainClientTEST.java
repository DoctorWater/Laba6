package TEST;

import common.*;
import mainClient.java.ClientReceiver;

import java.io.IOException;
import java.net.InetSocketAddress;


public class MainClientTEST {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ClientReceiver client = new ClientReceiver();
        Command command = client.receive(new InetSocketAddress("localhost",1));
    }
}