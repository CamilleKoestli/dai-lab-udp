package dai;

public class App {

    public static void main(String[] args) {
        Thread udp = new Thread(new Auditor.UDPServer());
        Thread tcp = new Thread(new Auditor.TCPServer());

        udp.start();
        tcp.start();

    }
}