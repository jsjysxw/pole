package cn.com.avatek.pole.entity;

/**
 * Created by shenxw on 2016/10/17.
 */

public class DetailEntity {
    private int id;
    private String title;
    private String img_url;
    private String notice_url;
    private String notice_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getNotice_url() {
        return notice_url;
    }

    public void setNotice_url(String notice_url) {
        this.notice_url = notice_url;
    }

    public String getNotice_id() {
        return notice_id;
    }

    public void setNotice_id(String notice_id) {
        this.notice_id = notice_id;
    }
}
