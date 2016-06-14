import java.io.IOException;
import java.io.InputStream;

/**
 * Created by novas on 16-6-9.
 */
public class FileUtils {
    //Socket端传递过来数据格式2_99_参数
    //2表示参数长度的位数，比如99是两位数，99表示参数长度，参数协议为为{1}或者{2,fcm}
    public static byte[] getSocketParams(InputStream is) throws IOException {
        int W=is.read()-48;
        System.out.println("w="+W);
        byte[] paramsLengthBytes=new byte[W+2];
        is.read(paramsLengthBytes);
        int length=Integer.parseInt(new String(paramsLengthBytes,1,W));
        //保存最后的结果
        byte[] bytes=new byte[length];
        //
        byte[] tempbytes=new byte[1024];
        int nowLength=0;
        while (nowLength!=length)
        {
            int readLength=is.read(tempbytes);
            System.arraycopy(tempbytes,0,bytes,nowLength,readLength);
            nowLength=nowLength+readLength;
        }
       // System.out.println("res="+new String(bytes).charAt(length-5));
        return bytes;
    }
}
