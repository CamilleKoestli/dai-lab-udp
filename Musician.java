/**
 * Musician class simulates someone who plays an instrument in an orchestra. When the app is started, it is
 * assigned an instrument (piano, flute, etc.). As long as it is running, every second it will emit a sound (well...
 * simulate the emission of a sound: we are talking about a communication protocol). Of course, the sound depends on the
 * instrument.
 *
 * @author Camille Koestli <cami>
 * @author Vitória Oliveira <maria.cosmodeoliveira@heig-vd.ch>
 */

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import static java.nio.charset.StandardCharsets.*;

class Musician {
    final static String IPADDRESS = "239.255.22.5";
    final static int PORT = 9904;

    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket()) {

            String message = "Hello group members!";
            byte[] payload = message.getBytes(UTF_8);
            InetSocketAddress dest_address = new InetSocketAddress(IPADDRESS, 44444);
            var packet = new DatagramPacket(payload, payload.length, dest_address);
            socket.send(packet);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}