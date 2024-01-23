package dai;

public class App {

    public static void main(String[] args) {

        Thread udpThread = new Thread(new UDPServer());
        Thread tcpThread = new Thread(new TCPServer());

        udpThread.start();
        tcpThread.start();
    }
}
