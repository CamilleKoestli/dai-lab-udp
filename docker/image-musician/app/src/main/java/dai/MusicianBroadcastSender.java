/**
 * MusicianBroadcastSender class.
 *
 * @author Camille Koestli <camille.koestli@heig-vd.ch>
 * @author Vitória Oliveira <maria.cosmodeoliveira@heig-vd.ch>
 */

package dai;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class MusicianBroadcastSender extends BroadcastSender {
    private Musician musician;

    public MusicianBroadcastSender(Musician musician) {
        this.musician = musician;
    }

    public String formatToJson(String uuid, String sound) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode payload = objectMapper.createObjectNode()
                    .put("uuid", musician.getUuid())
                    .put("sound", musician.getSound());

            return objectMapper.writeValueAsString(payload);
        } catch (IOException e) {
            throw new RuntimeException("Error formatting JSON", e);
        }
    }

    public void run(String message) {
        super.run(formatToJson(musician.getUuid(), musician.getSound()));
        System.out.println("Sending UDP datagram: uuid: " + musician.getUuid() + ", sound: " + musician.getSound());

        // Update last time played
        musician.setLastTimePlayed(System.currentTimeMillis());
    }
}
