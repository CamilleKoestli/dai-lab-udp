/**
 * Musician class that represents a musician. It contains the uuid, the instrument and the last activity of the musician.
 *
 * @author Camille Koestli <camille.koestli@heig-vd.ch>
 * @author Vitória Oliveira <maria.cosmodeoliveira@heig-vd.ch>
 */

package dai;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Musician {
    private String uuid;
    private String instrument;
    private long lastActivity;
    private static ConcurrentHashMap<String, Musician> musicians = new ConcurrentHashMap<>() ;
    static Map<String, String> instruments = new ConcurrentHashMap<>() ;

    static {
        instruments.put("ti-ta-ti", "piano");
        instruments.put("pouet", "trumpet");
        instruments.put("trulu", "flute");
        instruments.put("gzi-gzi", "violin");
        instruments.put("boum-boum", "drum");
    }

    public Musician(){}

    public Musician(String uuid, String sound, long lastActivity) {
        this.uuid = uuid;
        this.instrument = getInstrumentFromSound(sound);
        this.lastActivity = lastActivity;
        musicians.put(uuid, this);
    }

    // Get the list of active musicians
    public static ConcurrentHashMap<String,Musician> getActiveMusicians(){
        return musicians;
    }

    // Drop musicians that have not played for 5 seconds
    public static void dropMusicians(){
        long currentTime = System.currentTimeMillis();
        for (Map.Entry<String, Musician> entry : musicians.entrySet()) {
            if(currentTime - entry.getValue().getLastActivity() > 5000)
                musicians.remove(entry.getKey());
        }
    }

    public String getUuid() {
        return uuid;
    }

    public String getInstrument() {
        return instrument;
    }
    public String getInstrumentFromSound(String sound){
        return instruments.get(sound);
    }

    public long getLastActivity() {
        return lastActivity;
    }

    public void setLastActivity(long lastActivity) {
        this.lastActivity = lastActivity;
    }

    public String toString(){
        return "Musician : uuid : " + uuid + " Instrument : " + instrument + " Last Activity : " + lastActivity;
    }
}
