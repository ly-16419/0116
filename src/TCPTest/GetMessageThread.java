package TCPTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class GetMessageThread {
    Socket socket;

    public GetMessageThread(Socket socket) {
        this.socket = socket;
    }

    //为服务器服务的收信息流
    public void run() throws IOException {
        while (true){
            InputStream ip=socket.getInputStream();
            InputStreamReader iread=new InputStreamReader(ip);
            BufferedReader buffR=new BufferedReader(iread);//缓存
            String mssage= buffR.readLine();//设置阻塞
            System.out.println("客户端说：" + mssage);
        }
    }
}
