package mainClient.java;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;

public class Connector {
    private static final long serialVersionUID = 25L;
    public static DatagramChannel getDatagramChannel(SocketAddress address) throws IOException {

        DatagramChannel channel =
                DatagramChannel.open();
        channel.connect(address);
        return channel;
    }
}
