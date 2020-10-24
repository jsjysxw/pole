package cn.com.avatek.pole.entity;

/**
 * Created by shenxw on 2016/12/27.
 */

public class UserBean {

    /**
     * id : 1
     * tel : 123456
     * name : 超级管理员
     * password : 123456
     */

    private String id;
    private String tel;
    private String name;
    private String password;
    private String icon;
    private String user_type_id = "";
    private String show_name;
    private String push_flag;

    public String getUser_type_id() {
        return user_type_id;
    }

    public void setUser_type_id(String user_type_id) {
        this.user_type_id = user_type_id;
    }

    public String getShow_name() {
        return show_name;
    }

    public void setShow_name(String show_name) {
        this.show_name = show_name;
    }

    public String getPush_flag() {
        return push_flag;
    }

    public void setPush_flag(String push_flag) {
        this.push_flag = push_flag;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
