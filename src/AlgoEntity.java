import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by novas on 16-6-10.
 */
public class AlgoEntity {
    //算法名称
    public String algoname;
    //算法的参数
    public HashMap<String,Type> params=new HashMap<String, Type>();
    public void parseFromClass(Class cls)
    {
        String[] args=cls.getName().split("[.]");
        String name=args[args.length-1];
        algoname=name.substring(0,name.length()-6);
        Field[] fields=cls.getDeclaredFields();
        for(int i=0;i<fields.length;i++)
        {
            fields[i].setAccessible(true);
            params.put(fields[i].getName(),fields[i].getType());
        }
    }
//每个算法单位的字符串表示为{fcm,[{m,2},{c,2}]};
    //fcm表示算法名称，m c表示参数名称 2表示是Integer类型。
    @Override
    public String toString() {
        StringBuffer stringBuffer=new StringBuffer();
        stringBuffer.append("{");
        stringBuffer.append(algoname);
        stringBuffer.append(",[");
        for(Map.Entry<String,Type> entry:params.entrySet())
        {
            String name=entry.getKey();
            Type type=entry.getValue();
            String value=null;
            if(type==Constants.StringType)
            {
                value="1";
            }
            else if(type==Constants.IntegerType)
            {
                value="2";
            }
            else if(type==Constants.DoubleType)
            {
                value="3";
            }
            stringBuffer.append("{"+name+","+value+"},");
        }
        stringBuffer.deleteCharAt(stringBuffer.length()-1);
        stringBuffer.append("]}");
        return stringBuffer.toString();
    }
}
