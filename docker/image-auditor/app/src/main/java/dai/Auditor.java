package dai;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.*;
import static java.nio.charset.StandardCharsets.UTF_8;

public class Auditor {
    final static int PORT_TCP = 2205;
    final static int PORT_UDP = 9904;
    final static String IPADDRESS = "239.255.22.5";


    //UDP server
    static class UDPServer implements Runnable {

        class Sound {
            String uuid;
            String sound;
        }
        public void run() {
            try (MulticastSocket socket = new MulticastSocket(PORT_UDP)) {
                var group_address = new InetSocketAddress(IPADDRESS, PORT_UDP);
                NetworkInterface netif = NetworkInterface.getByName("eth0");

                socket.joinGroup(group_address, netif);
                byte[] buffer = new byte[1024];
                var packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                String message = new String(packet.getData(), 0, packet.getLength(), UTF_8);
                System.out.println("Received message: " + message + " from " + packet.getAddress() + ", port " + packet.getPort());

                ObjectMapper mapper = new ObjectMapper();
                Sound sound = mapper.readValue(message, Sound.class);

                if (Musician.getActiveMusicians().containsKey(sound.uuid)) {
                    Musician.getActiveMusicians().get(sound.uuid).setLastActivity(System.currentTimeMillis());
                } else {
                    Musician musician = new Musician(sound.uuid, sound.sound, System.currentTimeMillis());
                    Musician.getActiveMusicians().put(sound.uuid, musician);
                }
                socket.leaveGroup(group_address, netif);

            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    // TCP server
    static class TCPServer implements Runnable {
        public void run() {
            try (ServerSocket serverSocket = new ServerSocket(PORT_TCP)) {
                while (true) {
                    try (Socket clientSocket = serverSocket.accept();
                         PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
                        Musician.dropMusicians();
                        ObjectMapper mapper = new ObjectMapper();
                        String json = mapper.writeValueAsString(Musician.getActiveMusicians());

                        out.println(json);
                    } catch (IOException e) {
                        System.err.println("Error client connection: " + e.getMessage());
                    }
                }
            } catch (IOException e) {
                System.err.println("Problem listen " + PORT_TCP + ": " + e.getMessage());
            }
        }
    }
}
