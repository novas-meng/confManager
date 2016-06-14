import java.io.IOException;
import java.net.*;
import java.util.Calendar;

/**
 * Created by novas on 16-6-11.
 */
public class test {
    public static void main(String[] args) throws IOException {
        Socket socket=new Socket();
        SocketAddress socketAddress=new InetSocketAddress(InetAddress.getByName("192.168.1.150"),9089);
        socket.connect(socketAddress);
        Calendar calendar=Calendar.getInstance();
        int time=(int)calendar.getTime().getTime();
        System.out.println("time="+time);
        String msg=time+" fcm {m=2}{c=3}";
        String length=msg.length()+"";
        msg=length.length()+"_"+length+"_"+msg;
        byte[] bytes=msg.getBytes();
        System.out.println(bytes[0]);
        socket.getOutputStream().write(bytes);
      //  for(int i=0;i<bytes.length;i++)
       // {
           // System.out.println(bytes[i]);
       // }
        socket.close();
    }
}
