import java.io.IOException;
import java.io.InputStream;
import java.net.*;

/**
 * Created by novas on 16-6-9.
 */
//这个类是用来控制用户请求的，用户请求消息协议为PARAMS_1或者PARAMS_2_fcm
    //1表示请求支持的所有方法，2表示请求的具体方法，后面fcm表示算法的具体名称
public class Controller {
    static ServerSocket serverSocket;

    public static void main(String[] args)
    {
        try {
            serverSocket=new ServerSocket();
            SocketAddress socketAddress=new InetSocketAddress(InetAddress.getByName("192.168.1.150"),9081);
            serverSocket.bind(socketAddress);
            while (true)
            {
                Socket client=serverSocket.accept();
                InputStream is=client.getInputStream();
                byte[] clientbytes=FileUtils.getSocketParams(is);
                System.out.println("recv="+new String(clientbytes));
                byte[] returnToClient=ClsUtils.parseRequest(clientbytes);
                client.getOutputStream().write(returnToClient);
                client.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
