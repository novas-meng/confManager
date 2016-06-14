import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by novas on 16-6-10.
 */
public class ClsUtils {

    static ArrayList<AlgoEntity> algoEntityArrayList=new ArrayList<AlgoEntity>();

    public static void init()
    {
        File file = new File("/home/novas/fcm.jar");
        URL url= null;
        try {
            url = new URL("jar:file:/home/novas/fcm.jar!/");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        URLClassLoader urlClassLoader=new URLClassLoader(new URL[]{url});
        JarFile jarFile = null;
        try {
            jarFile = new JarFile(file);
            Enumeration enumeration=jarFile.entries();
            while (enumeration.hasMoreElements())
            {
                JarEntry entry=(JarEntry)enumeration.nextElement();
                String name=entry.getName();
                if(name.endsWith(".class"))
                {
                    System.out.println("name="+name);
                    name=name.split("[.]")[0];
                    if(name.endsWith("Params"))
                    {
                        name=name.replaceAll("/",".");
                        AlgoEntity algoEntity=new AlgoEntity();
                        Class cls=urlClassLoader.loadClass(name);
                        algoEntity.parseFromClass(cls);
                        algoEntityArrayList.add(algoEntity);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    //  形式是[algoEntity,algoEntity]
    public static byte[] getAllAlgo()
    {
       if(algoEntityArrayList.size()==0)
       {
          init();
       }
       StringBuffer sb=new StringBuffer();
        sb.append("[");
        for (int i=0;i<algoEntityArrayList.size();i++)
        {
            sb.append(algoEntityArrayList.get(i).toString());
            sb.append("-");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append("]");
        String res=sb.toString();
        String len=res.length()+"";
        res=len.length()+"_"+len+"_"+res;
        return res.getBytes();
      // return sb.toString().getBytes();
    }
    public static byte[] getSpecAlgo(String algoName)
    {
       if(algoEntityArrayList.size()==0)
       {
           init();
       }
        for(int i=0;i<algoEntityArrayList.size();i++)
        {
            if(algoEntityArrayList.get(i).algoname.equals(algoName))
            {
                String res=algoEntityArrayList.get(i).toString();
                String len=res.length()+"";
                res=len.length()+"_"+len+"_"+res;
                return res.getBytes();
               // return .getBytes();
            }
        }
        return Constants.ERROR.getBytes();
    }
    //返回支持的所有算法个数[4]这样的形式
    public static byte[] getAlgoCount()
    {
        if(algoEntityArrayList.size()==0)
        {
            init();
        }
        String count=algoEntityArrayList.size()+"";
        StringBuffer sb=new StringBuffer();
        sb.append("["+count+"]");
        String res=sb.toString();
        String len=res.length()+"";
        res=len.length()+"_"+len+"_"+res;
        return res.getBytes();
    }
    public static byte[] parseRequest(byte[] clientByte)
    {
        String requestParams=new String(clientByte);
        requestParams=requestParams.substring(1,requestParams.length()-1);
        String[] args=requestParams.split(",");
        if(args[0].equals(Constants.GETALL))
        {
            return getAllAlgo();
        }
        else if(args[0].equals(Constants.SPEC))
        {
            return getSpecAlgo(args[1]);
        }
        else if(args[0].equals(Constants.ALGOCOUNT))
        {
            return getAlgoCount();
        }
        return Constants.ERROR.getBytes();
    }
}
