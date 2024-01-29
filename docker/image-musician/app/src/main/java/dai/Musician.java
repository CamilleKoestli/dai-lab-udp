
package dai;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import  java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Musician class simulates someone who plays an instrument in an orchestra. When the app is started, it is
 * assigned an instrument (piano, flute, etc.). As long as it is running, every second it will emit a sound (well...
 * simulate the emission of a sound: we are talking about a communication protocol). Of course, the sound depends on the
 * instrument.
 *
 * @author Camille Koestli <cami>
 * @author Vitória Oliveira <maria.cosmodeoliveira@heig-vd.ch>
 */


public class Musician {

    static final Map<String, String> soundsByInstrument = new ConcurrentHashMap<>();
    static {
        soundsByInstrument.put("piano", "ti-ta-ti");
        soundsByInstrument.put("trumpet", "pouet");
        soundsByInstrument.put("flute", "trulu");
        soundsByInstrument.put("violin", "gzi-gzi");
        soundsByInstrument.put("drum", "boum-boum");
    }
    private final String uuid;
    private final String instrument;
    private final String sound;

    private long lastTimePlayed;

    File instrumentsJson;

    public Musician(String instrument) throws IOException {
        this.uuid = UUID.randomUUID().toString();
        this.instrument = instrument;
        //this.instrumentsJson = new File(instrumentsJson);
        this.sound = setSoundFromInstrument(instrument);
        this.lastTimePlayed = 0;

        System.out.println("Musician created: " + this.instrument + " " + this.sound + " " + this.uuid);
    }

    public String getUuid() {
        return uuid;
    }

    public String getInstrument() {
        return instrument;
    }

    public String getSound() {
        return sound;
    }
    public String setSoundFromInstrument(String instrument) throws IOException {
        /*ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(instrumentsJson);

        return jsonNode.get(instrument).asText();*/
        return soundsByInstrument.get(instrument);
    }

    public long getLastTimePlayed() {
        return lastTimePlayed;
    }
    public void setLastTimePlayed(long lastTimePlayed) {
        this.lastTimePlayed = lastTimePlayed;
    }
}
