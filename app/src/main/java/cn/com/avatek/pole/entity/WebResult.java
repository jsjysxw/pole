package cn.com.avatek.pole.entity;


import java.util.List;

/**
 * Created by shenxw on 2017/2/6.
 */

public class WebResult {
    private String state;
    private List<ClassContent> content;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<ClassContent> getContent() {
        return content;
    }

    public void setContent(List<ClassContent> content) {
        this.content = content;
    }
}
