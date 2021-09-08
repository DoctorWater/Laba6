 
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Hashtable;

public class Transfer {
    private final Command com;
    private final SocketAddress socketAddress;
    private final DatagramChannel datagramChannel;
    public Transfer(Command command, InetAddress address, int port) throws IOException {
        com = command;
        this.socketAddress = new InetSocketAddress(address, port);
        this.datagramChannel = (DatagramChannel) Connector.getDatagramChannel(this.socketAddress).configureBlocking(false);
    }
    public void send() throws IOException {
        ByteBuffer bb;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(com);
        oos.flush();
        bb=ByteBuffer.wrap(baos.toByteArray(),0,baos.size());
        datagramChannel.send(bb,socketAddress);
        baos.close();
    }

}
