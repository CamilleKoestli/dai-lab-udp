/**
 * Musician class simulates someone who plays an instrument in an orchestra.
 *
 * @author Camille Koestli <camille.koestli@heig-vd.ch>
 * @author Vitória Oliveira <maria.cosmodeoliveira@heig-vd.ch>
 */

package dai;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Musician {

    // Map of instruments and their sounds
    static final Map<String, String> instruments = new ConcurrentHashMap<>();

    static {
        instruments.put("piano", "ti-ta-ti");
        instruments.put("trumpet", "pouet");
        instruments.put("flute", "trulu");
        instruments.put("violin", "gzi-gzi");
        instruments.put("drum", "boum-boum");
    }

    private final String uuid;
    private final String instrument;
    private final String sound;
    private long lastTimePlayed;

    File instrumentsJson;

    public Musician(String instrument) throws IOException {
        this.uuid = UUID.randomUUID().toString();
        this.instrument = instrument;
        this.sound = setSoundFromInstrument(instrument);
        this.lastTimePlayed = 0;
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
        return instruments.get(instrument);
    }

    public long getLastTimePlayed() {
        return lastTimePlayed;
    }

    public void setLastTimePlayed(long lastTimePlayed) {
        this.lastTimePlayed = lastTimePlayed;
    }
}
