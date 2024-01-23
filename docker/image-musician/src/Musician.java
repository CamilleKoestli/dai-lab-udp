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

    private static final String CONFIG_FILE_PATH;
    private static final String MULTICAST_GROUP;
    private static final int PORT;
    private static final Gson GSON = new Gson();

    int lastTimePlayed;

    public Musician(String configFilePath) {
        CONFIG_FILE_PATH = configFilePath;
        GSON = new Gson();
        lastTimePlayed = 0;

        // Getting config file
        try {
            config = GSON.fromJson(new FileReader(CONFIG_FILE_PATH), JsonObject.class);
        } catch (IOException e) {
            System.err.println("Error reading configuration file: " + e.getMessage());
        }

        // Getting multicast group
        MULTICAST_GROUP = config.get("multicast-group").getAsString();
        System.out.println("Multicast group: " + MULTICAST_GROUP);

        // Getting port
        PORT = config.get("multicast-port").getAsInt();
        System.out.println("Port: " + PORT);
    }


    public void run(String arg) {
        System.out.println("Starting musician...");

        if(arg == null){
            System.out.println("No instrument provided");
            System.exit(1);
        }

        // Instrument assignement
        String instrument = arg;
        System.out.println("Instrument provided: " + instrument);

        // Getting sound corresponding to assigned instrument
        try{
            String sound = config.get("instruments").getAsJsonObject().get(instrument).getAsString();
            System.out.println("Sound: " + sound);
        } catch (NUllPointerException e | IOException e | JsonParseException e) {
            System.err.println("Error reading or parsing configuration file: " + e.getMessage());
            if(sound == null | sound.isEmpty()){
                System.out.println("No sound found for instrument " + instrument);
                System.exit(1);
            } else{
                System.out.println("Received sound: " + sound);
            }
        }

        // Sending UDP datagram
        try (DatagramSocket socket = new DatagramSocket()) {

            // Generating payload
            JsonObject payload = new JsonObject();
            payload.addProperty("uuid", UUID.randomUUID().toString());
            payload.addProperty("sound", sound);
            payload.addProperty("played for the last time at", lastTimePlayed);

            byte[] payloadBytes = GSON.toJson(payload).getBytes(UTF_8);

            // Generating datagram
            InetSocketAddress dest_address = new InetSocketAddress(MULTICAST_GROUP, PORT);
            var packet = new DatagramPacket(payloadBytes, payload.length, dest_address);

            // Sending UDP datagram
            System.out.println("Sending UDP datagram: payload=" + message + ", dest=" + MULTICAST_GROUP, + ", port=" + PORT);
            socket.send(packet);
            System.out.println("UDP datagram sent successfully.");

            lastTimePlayed = System.currentTimeMillis();

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            System.err.println("Error sending UDP datagram: " + ex.getMessage());
        }
    }

}