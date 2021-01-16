# UDP和TCP在java中的连接

- TCP是一种面向连接的传输层协议，而UDP是传输层中面向无连接的协议，故传送的数据包不能保证有序和不丢失，实现UDP通信主要用到了两个类：DatagramPacket和DatagramSocket。

- TCP 提供可靠传输

- UDP不提供可靠传输

## TCP

### 1、OutputStream 

这个抽象类是表示字节输出流的所有类的超类。 输出流接收输出字节并将其发送到某个接收器

需要定义OutputStream子类的应用OutputStream必须至少提供一个写入一个字节输出的方法。

- BufferedOutputStream 

该类实现缓冲输出流。 通过设置这样的输出流，应用程序可以向底层输出流写入字节，而不必为写入的每个字节导致底层系统的调用

- ByteArrayOutputStream
- DataOutputStream
- FilterOutputStream 
- InputStream 
- write(int) 

### 2、InputStream

这个抽象类是表示输入字节流的所有类的超类。 

### 3、TCP小案例

```java
//服务器
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

            //调用收消息线程
            GetMessageThread getMessageThread=new GetMessageThread(socket);
            getMessageThread.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

```java
//收信息线程，实现接受多个客户端的信息
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
```

```java
//客户端
public class TCPClient {
    public static void main(String[] args) {
        /*
            获取服务器信息：
            1、设置连接，提供ip和端口
            2、输入流
            3、缓存
            4、设置阻塞
        */
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
```

## UDP 

- Sever端程序

一，调用DatagramSocket(int port)创建一个数据报套接字，绑定在指定端口上；

二，调用DatagramPacket(byte[] buf,int length),建立一个字节数组来接收UDP包；

三，调用DatagramSocket.receive()；
四，最后关闭数据报套接字。

```java
package UDPTest;

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
```


- Client端程序

一，调用DatagramSocket()创建一个数据报套接字；

二，调用DatagramPacket(byte[] buf,int offset,InetAddress address,int port),建立要发送的UDP包

三，调用DatagramSocket类的send方法发送数据包；
四，关闭数据报套接字。

> 数据报套接字发送成功后，相当于建立了一个虚连接，双方可以发送数据。

```java
package UDPTest;

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
```