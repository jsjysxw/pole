package cn.com.avatek.pole.entity;


import java.util.List;

/**
 * Created by shenxw on 2017/2/6.
 */

public class ListResult {
    private String state;
    private String reason;
    private List<ContBean> content;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<ContBean> getContent() {
        return content;
    }

    public void setContent(List<ContBean> content) {
        this.content = content;
    }
}
