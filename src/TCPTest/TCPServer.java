package TCPTest;

import javax.swing.plaf.OptionPaneUI;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
    public static void main(String[] args) {
        /*
            向客户端发送信息，确认连接成功
            1、设置连接，提供端口
            2、设置阻塞，实现多用户连接
            3、输入流，向客户端发送信息
         */

        try {
            ServerSocket ss=new ServerSocket(6666);
            Socket socket=ss.accept();
            OutputStream ops=socket.getOutputStream();//设置通道
            OutputStreamWriter opsw=new OutputStreamWriter(ops);//创建流，存储需要发送的信息，发送到通道中
            PrintWriter printWriter=new PrintWriter(opsw);//打印流
            printWriter.println("欢迎连接");
            printWriter.flush();//提交

            //调用收消息流
            GetMessageThread getMessageThread=new GetMessageThread(socket);
            getMessageThread.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
