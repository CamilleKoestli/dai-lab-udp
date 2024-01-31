/**
 * Auditor class that starts the UDP and TCP servers.
 *
 * @author Camille Koestli <camille.koestli@heig-vd.ch>
 * @author Vitória Oliveira <maria.cosmodeoliveira@heig-vd.ch>
 */

package dai;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

class Sound {
    public String uuid;
    public String sound;
}

public class Auditor {
    final static int PORT_TCP = 2205;
    final static int PORT_UDP = 9904;
    final static String IPADDRESS = "239.255.22.5";

    // UDP server
    static class UDPServer implements Runnable {
        public void run() {
            System.out.println("UDP server running");
            try (MulticastSocket socket = new MulticastSocket(PORT_UDP)) {
                System.out.println("UDP server listening on port " + PORT_UDP);
                var group_address = new InetSocketAddress(IPADDRESS, PORT_UDP);
                NetworkInterface netif = NetworkInterface.getByName("eth0");

                socket.joinGroup(group_address, netif);
                while (true) {
                    System.out.println("Joined multicast group " + IPADDRESS + ", port " + PORT_UDP);
                    byte[] buffer = new byte[1024];
                    var packet = new DatagramPacket(buffer, buffer.length);
                    socket.receive(packet);
                    String message = new String(packet.getData(), 0, packet.getLength(), StandardCharsets.UTF_8);

                    ObjectMapper mapper = new ObjectMapper();
                    Sound sound = mapper.readValue(message, Sound.class);

                    Musician.dropMusicians();

                    if (Musician.getActiveMusicians().containsKey(sound.uuid)) {
                        Musician.getActiveMusicians().get(sound.uuid).setLastActivity(System.currentTimeMillis());
                    } else {
                        Musician musician = new Musician(sound.uuid, sound.sound, System.currentTimeMillis());
                        Musician.getActiveMusicians().put(sound.uuid, musician);
                    }

                    System.out.println("Active musicians:");
                    for (Musician musician : Musician.getActiveMusicians().values()) {
                        System.out.println(musician.toString());
                    }
                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    // TCP server
    static class TCPServer implements Runnable {
        public void run() {
            System.out.println("TCP server running");
            ObjectMapper mapper = new ObjectMapper();
            try (ServerSocket serverSocket = new ServerSocket(PORT_TCP)) {
                System.out.println("TCP server listening on port " + PORT_TCP);
                while (true) {
                    System.out.println("Waiting for client connection");
                    Socket socket = serverSocket.accept();
                    try (var out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8))) {

                        // Mise à jour de la liste des musiciens et formatage du message
                        String report = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(Musician.getActiveMusicians());

                        System.out.println("Sending report: " + report);
                        out.write(report + "\n");
                        out.flush();
                    } catch (JsonProcessingException e) {
                        System.out.println("JSON processing error: " + e);
                    } catch (IOException e) {
                        System.out.println("Error on accept or buffers: " + e);
                    }
                }
            } catch (IOException e) {
                System.out.println("Error with serverSocket: " + e);
            }
        }
    }
}
