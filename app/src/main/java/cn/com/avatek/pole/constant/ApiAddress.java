package cn.com.avatek.pole.constant;

/**
 * Created by Shenxw on 16/5/29.
 * url都集中放在这里
 */
public class ApiAddress {
    public final static String host = "www.dayo.net.cn";
    public final static String Host = "http://"+host+"/pole/";

    //api的基础url
    private final static String hostAddress = Host+"api.php?";

    //自动更新
    public final static String AndroidUpdate = Host+"Public/download/androidQui.txt";

    //登录
    public final static String login = hostAddress +"method=login";
    //注册接口
    public final static String create_line = hostAddress +"method=create_line";
    //主页新闻、图标获取
    public final static String home_page = hostAddress +"method=home_page";
    //社区列表获取
    public final static String insert_point = hostAddress +"method=insert_point";
    public final static String line_list = hostAddress +"method=line_list";
    public final static String point_list = hostAddress +"method=point_list";
    public final static String point_material = hostAddress +"method=point_material";
    public final static String point_delete = hostAddress +"method=point_delete";
    public final static String point_update = hostAddress +"method=point_update";
    public final static String line_delete = hostAddress +"method=line_delete";
    public final static String material_group_list = hostAddress +"method=material_group_list";

}
