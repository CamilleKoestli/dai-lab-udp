package dai;

public class App {

    public static void main(String[] args) {

        Auditor auditor = new Auditor();
        auditor.UDPServer();
        auditor.TCPServer();
    }
}