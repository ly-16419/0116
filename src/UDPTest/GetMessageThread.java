package UDPTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

public class GetMessageThread {
    DatagramSocket socket;
    InetAddress address;
    int port;

    public GetMessageThread(DatagramSocket socket) {
        this.socket = socket;
    }

    //为服务器服务的收信息流
    public void run() throws IOException {
        while (true){
            byte buff[]=new byte[1024];
            DatagramPacket packet=new DatagramPacket(buff, buff.length);
            socket.receive(packet);

            address=packet.getAddress();
            port=packet.getPort();


            String reply=new String(buff, 0, buff.length);
            System.out.println("客户端说:"+reply);

        }
    }
}
