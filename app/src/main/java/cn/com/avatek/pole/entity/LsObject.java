package cn.com.avatek.pole.entity;

/**
 * Created by shenxw on 2016/12/27.
 */

public class LsObject {
    private String now_page;
    private int total;
    private String path;
    private String url;
    private String ticket;
    private Object ls;
    private int aid;
    private String info;
    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getNow_page() {
        return now_page;
    }

    public void setNow_page(String now_page) {
        this.now_page = now_page;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Object getLs() {
        return ls;
    }

    public void setLs(Object ls) {
        this.ls = ls;
    }

}
