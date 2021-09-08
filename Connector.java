 
import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;

public class Connector {
    public static DatagramChannel getDatagramChannel(SocketAddress address) throws IOException {
        DatagramChannel channel =
                DatagramChannel.open();
        channel.connect(address);
        return channel;
    }
}
