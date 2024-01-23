package dai;

public class App {

    public static void main(String[] args) {

        Thread udpThread = new Thread(new Auditor.UDPServer());
        Thread tcpThread = new Thread(new Auditor.TCPServer());

        udpThread.start();
        tcpThread.start();
    }
}
