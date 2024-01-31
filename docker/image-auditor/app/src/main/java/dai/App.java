/**
 * App class that starts the UDP and TCP servers.
 *
 * @author Camille Koestli <camille.koestli@heig-vd.ch>
 * @author Vitória Oliveira <maria.cosmodeoliveira@heig-vd.ch>
 */

package dai;

public class App {

    public static void main(String[] args) {
        Thread udp = new Thread(new Auditor.UDPServer());
        Thread tcp = new Thread(new Auditor.TCPServer());

        udp.start();
        tcp.start();
    }
}