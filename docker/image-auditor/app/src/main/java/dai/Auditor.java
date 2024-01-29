package dai;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;


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
            System.out.println("UDP server running");
            try (MulticastSocket socket = new MulticastSocket(PORT_UDP)) {
                System.out.println("UDP server listening on port " + PORT_UDP);
                var group_address = new InetSocketAddress(IPADDRESS, PORT_UDP);
                NetworkInterface netif = NetworkInterface.getByName("eth0");

                socket.joinGroup(group_address, netif);
                System.out.println("Joined multicast group " + IPADDRESS + ", port " + PORT_UDP);
                byte[] buffer = new byte[1024];
                var packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                System.out.println("Received UDP packet from " + packet.getAddress() + ", port " + packet.getPort());
                String message = new String(packet.getData(), 0, packet.getLength(), StandardCharsets.UTF_8);
                System.out.println("Received message: " + message + " from " + packet.getAddress() + ", port " + packet.getPort());

                ObjectMapper mapper = new ObjectMapper();
                Sound sound = mapper.readValue(message, Sound.class);

                if (Musician.getActiveMusicians().containsKey(sound.uuid)) {
                    System.out.println("Updating musician " + sound.uuid);
                    Musician.getActiveMusicians().get(sound.uuid).setLastActivity(System.currentTimeMillis());
                } else {
                    System.out.println("Adding musician " + sound.uuid);
                    Musician musician = new Musician(sound.uuid, sound.sound, System.currentTimeMillis());
                    Musician.getActiveMusicians().put(sound.uuid, musician);
                }
                System.out.println("Active musicians: " + Musician.getActiveMusicians().size());
                socket.leaveGroup(group_address, netif);
                System.out.println("Left multicast group " + IPADDRESS + ", port " + PORT_UDP);

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
                    System.out.println("Client connected: " + socket.getInetAddress() + ", port " + socket.getPort());
                    try (
                         var in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
                         var out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8))) {
                        System.out.println("Sending report");
                        // Mise à jour de la liste des musiciens et formatage du message
                        //String report = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(Musician.getActiveMusicians());
                        StringBuilder report = new StringBuilder();
                        for(Musician musician : Musician.getActiveMusicians().values()) {
                            report.append(musician.toString()).append("\n");
                        }
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
