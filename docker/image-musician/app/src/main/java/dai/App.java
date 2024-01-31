/**
 * App class for Musician. It assigns the instrument passed in command line to a Musician. As long as the sender is
 * running, it will make the musician emit the sound of it's instrument every second.
 *
 * @author Camille Koestli <camille.koestli@heig-vd.ch>
 * @author Vitória Oliveira <maria.cosmodeoliveira@heig-vd.ch>
 */

package dai;

import java.io.IOException;

public class App {

    public static void main(String[] args) throws InterruptedException, IOException {
        if (args.length == 0) {
            System.out.println("Please provide an instrument name as a command-line argument.");
            System.exit(1);
        }

        // Create a musician with the instrument passed in command line and create sender for the musician
        String instrument = args[0];
        Musician musician = new Musician(instrument);
        MusicianBroadcastSender sender = new MusicianBroadcastSender(musician);

        System.out.println("Starting sender...");

        // Send the musician's sound every second
        while (true) {
            sender.run("");
            Thread.sleep(1000);
        }
    }
}