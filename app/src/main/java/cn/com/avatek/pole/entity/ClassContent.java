package cn.com.avatek.pole.entity;

import java.util.List;

public class ClassContent {

     private String name;
     private String type_id;
     private String class_id;
     private List<ContBean> content;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public String getClass_id() {
        return class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }

    public List<ContBean> getContent() {
        return content;
    }

    public void setContent(List<ContBean> content) {
        this.content = content;
    }
}
