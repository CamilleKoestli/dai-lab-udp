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
        System.out.println("Starting musician...");

        // Instrument assignement
        String instrument = args[0];
        //TODO améliorer gestion d'exception
        if(instrument == null){
            System.out.println("No instrument provided");
            System.exit(1);
        }
        String sound =




        try (DatagramSocket socket = new DatagramSocket()) {

            String message = "Hello group members!";
            byte[] payload = message.getBytes(UTF_8);
            InetSocketAddress dest_address = new InetSocketAddress(IPADDRESS, PORT);
            var packet = new DatagramPacket(payload, payload.length, dest_address);

            System.out.println("Sending UDP datagram: payload=" + message + ", dest=" + dest_address, + ", port=" + PORT);
            socket.send(packet);
            System.out.println("UDP datagram sent successfully.");

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            System.err.println("Error sending UDP datagram: " + ex.getMessage());
        }
    }

}