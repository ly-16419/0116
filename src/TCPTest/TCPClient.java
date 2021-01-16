package TCPTest;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class TCPClient {
    public static void main(String[] args) {
        try {
            Socket socket=new Socket("localhost",6666);
            InputStream inputStream=socket.getInputStream();
            InputStreamReader inputStreamReader=new InputStreamReader(inputStream);
            BufferedReader buffr=new BufferedReader(inputStreamReader);
            String mssage=buffr.readLine();//设置阻塞
            System.out.println("服务器说：" + mssage);

            Scanner reader=new Scanner(System.in);
            //向服务器发送信息
            while (true){
                System.out.println("请输入你要输入的信息");
                String information=reader.next();
                OutputStream outputStream=socket.getOutputStream();
                OutputStreamWriter outputStreamWriter=new OutputStreamWriter(outputStream);
                PrintWriter printWriter=new PrintWriter(outputStreamWriter);
                printWriter.println(information);
                printWriter.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
