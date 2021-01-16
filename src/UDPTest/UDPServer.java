package UDPTest;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPServer {
    public static void main(String[] args) {


        try {
            DatagramSocket socket=new DatagramSocket(8888);
            byte buff[]=new byte[1024];
            DatagramPacket packet=new DatagramPacket(buff,buff.length);
            socket.receive(packet);
            InetAddress address=packet.getAddress();
            int port=packet.getPort();

            String reply=new String(buff,0, packet.getLength());
            System.out.println("客户端说：" + reply);

            byte data[]="欢迎连接".getBytes();
            DatagramPacket packet1=new DatagramPacket(data, data.length,address,port);
            socket.send(packet1);

            socket.close();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
