package dai;


import java.util.Map;

public class Musician {
    private String uuid;
    private String instrument;
    private long lastActivity;

    public Musician(String uuid, String instrument, long lastActivity) {
        this.uuid = uuid;
        this.instrument = instrument;
        this.lastActivity = lastActivity;
    }
    public static Map<String,Musician> getActiveMusicians(){
        return null;
    }

    public static Musician dropMusician(String uuid){
        return null;
    }

}
