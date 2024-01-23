package dai;


import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Musician {
    private String uuid;
    private String instrument;
    private long lastActivity;
    private static Map<String, Musician> musicians = new ConcurrentHashMap<>() ;

    public Musician(){}

    public Musician(String uuid, String instrument, long lastActivity) {
        this.uuid = uuid;
        this.instrument = instrument;
        this.lastActivity = lastActivity;
        musicians.put(uuid, this);
    }

    public static Map<String,Musician> getActiveMusicians(){
        return musicians;
    }

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

    public long getLastActivity() {
        return lastActivity;
    }

    public void setLastActivity(long lastActivity) {
        this.lastActivity = lastActivity;
    }
}
