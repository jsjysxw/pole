package cn.com.avatek.pole.module.upgrade;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by shenxw
 */
public class StringUtils {

    /**
     * 解析成数字类型的string
     * @param value
     * @return
     */
    public static String parseToNumberString(String value) {
        if (value == null || "".equals(value))
            return "0";

        return value;
    }

    public static String notNull(String value) {
        if (value == null)
            return "";

        return value;
    }

    public static String convertStreamToString(InputStream is) {
        ByteArrayOutputStream oas = new ByteArrayOutputStream();
        copyStream(is, oas);
        String t = oas.toString();
        try {
            oas.close();
            oas = null;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return t;
    }

    private static void copyStream(InputStream is, OutputStream os)
    {
        final int buffer_size = 1024;
        try
        {
            byte[] bytes=new byte[buffer_size];
            for(;;)
            {
                int count=is.read(bytes, 0, buffer_size);
                if(count==-1)
                    break;
                os.write(bytes, 0, count);
            }
        }
        catch(Exception ex){}
    }
}
