package cn.com.avatek.pole.drawmap.utils;

/**
 * Created by owant on 22/03/2017.
 */

public class LOG {

    public static void jLogi(String format, Object... args) {
        if (AppConstants.CONFIG_DEBUG) {
            StringBuffer ft = new StringBuffer(format);
            if (!ft.toString().endsWith("\n")) {
                ft.append("\n");
            }
            System.out.printf(ft.toString(), args);
        }
    }

    public static void jLoge(String format, Object... args) {
        if (AppConstants.CONFIG_DEBUG) {
            StringBuffer ft = new StringBuffer(format);
            if (!ft.toString().endsWith("\n")) {
                ft.append("\n");
            }
            System.err.printf(ft.toString(), args);
        }
    }

}
