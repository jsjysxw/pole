package cn.com.avatek.pole.utils;


/**
 * Created by ios on 16/4/6.
 */
public class StringUtil {

    /**
     * trim首尾的","
     * @param str
     * @return
     */
    public static String trimOne(String str){
        if(str.startsWith(","))
            return trimOne(new String(new StringBuffer(str).deleteCharAt(0)));
        else if(str.endsWith(","))
            return trimOne(new String(new StringBuffer(str).deleteCharAt(str.length()-1)));
        else
            return str;
    }

    /**
     * trim末尾共同的0
     * @param
     * @return
     */
    public static String trimEnd(String str){
        String[] arr = str.split(",");
        if(arr.length==2){
            String str1 = arr[0];
            String str2 = arr[1];
            if(str1.endsWith("0")&&str2.endsWith("0")){
                return trimEnd(new String(new StringBuffer(str1).deleteCharAt(str1.length()-1))+","+new String(new StringBuffer(str2).deleteCharAt(str2.length()-1)));
            }else {
                return str;
            }

        }else {
            return "";
        }
    }
}
