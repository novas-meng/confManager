import java.io.*;
import java.net.*;

/**
 * Created by novas on 16-6-9.
 */
//这个类是用来控制用户请求的，用户请求消息协议为PARAMS_1或者PARAMS_2_fcm
    //1表示请求支持的所有方法，2表示请求的具体方法，后面fcm表示算法的具体名称
    //args表示的参数为本机ip 和端口号
public class Controller {
    static ServerSocket serverSocket;
    static int port;
    static String ip;
    public static void main(String[] args)
    {
        ip=args[0];
        port=Integer.parseInt(args[1]);
        File file=new File("/etc/profile");
        try {
            BufferedReader br=new BufferedReader(new FileReader(file));
            String line=br.readLine();
            while (line!=null)
            {
                if(line.contains("NOVAS_HOME"))
                {
                    String[] var=line.split("=");
                    Constants.jarPath=var[1]+"/jar/algocenter.jar";
                    break;
                }
                line=br.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(Constants.jarPath);
        try {
            serverSocket=new ServerSocket();
            InetAddress address=InetAddress.getLocalHost();
            System.out.println(address.getHostAddress());
            SocketAddress socketAddress=new InetSocketAddress(InetAddress.getByName(ip),port);
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
