
package dai;

/**
 * BroadcastSender class
 *
 * @author Vitória Oliveira <maria.cosmodeoliveira@heig-vd.ch>
 */
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import static java.nio.charset.StandardCharsets.*;

class BroadcastSender {
    final static String IPADDRESS = "239.255.22.5";
    final static int PORT = 9904;

    public void run(String message) {
        try (DatagramSocket socket = new DatagramSocket()) {
            socket.setBroadcast(true);

            byte[] payload = message.getBytes(UTF_8);
            InetSocketAddress dest_address = new InetSocketAddress(IPADDRESS, PORT);
            var packet = new DatagramPacket(payload, payload.length, dest_address);
            socket.send(packet);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
