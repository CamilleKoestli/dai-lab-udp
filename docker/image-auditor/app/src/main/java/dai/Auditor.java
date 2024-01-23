package dai;

public class Auditor {
    /*const activeMusicians =new

    Map();

    //UDP server
    class UDPServer implements Runnable {
        public void run() {
            try (MulticastSocket socket = new MulticastSocket(PORT)) {
                var group_address = new InetSocketAddress(IPADDRESS, PORT);
                NetworkInterface netif = NetworkInterface.getByName("eth0");
                socket.joinGroup(group_address, netif);
                byte[] buffer = new byte[1024];
                var packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                String message = new String(packet.getData(), 0, packet.getLength(), UTF_8);
                System.out.println("Received message: " + message + " from " + packet.getAddress() + ", port " + packet.getPort());
                socket.leaveGroup(group_address, netif);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }

        // TCP server
        class TCPServer implements Runnable {
            public void run() {
                try (ServerSocket serverSocket = new ServerSocket(1234)) {
                    while (true) {
                        try (Socket socket = serverSocket.accept();
                             BufferedReader in = new BufferedReader(
                                     new InputStreamReader(socket.getInputStream(), UTF_8)); BufferedWriter out = new BufferedWriter(
                                new OutputStreamWriter(socket.getOutputStream(), UTF_8))) {
                            String line;
                            while ((line = in.readLine()) != null) {
                                out.write(line + "\n");
                                out.flush();
                            }
                        } catch (IOException e) {
                            System.out.println("Server: socket ex.: " + e);
                        }
                    }
                } catch (
                        IOException e) {
                    System.out.println("Server: server socket ex.: " + e);
                }
            }*/
}