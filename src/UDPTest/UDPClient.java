package UDPTest;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class UDPClient {
    public static void main(String[] args) {
        /*
        接收服务器的响应
        1、创建连接对象
        2、定义服务器的地址、端口号、数据
        3、创建数据报、接受服务器发送的信息
        4、设置阻塞
        5、读取发送过来的数据
         */

        try {
            DatagramSocket socket=new DatagramSocket();
            InetAddress address=InetAddress.getByName("localhost");
            int port=8888;

            byte data[]="连接上服务器".getBytes();
            DatagramPacket packet1=new DatagramPacket(data, data.length,address,port);
            socket.send(packet1);
            byte buff[]=new byte[1024];
            DatagramPacket packet=new DatagramPacket(buff, buff.length);
            socket.receive(packet);
            String reply=new String(buff,0,packet.getLength());
            System.out.println("服务器说：" + reply);

            socket.close();

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
