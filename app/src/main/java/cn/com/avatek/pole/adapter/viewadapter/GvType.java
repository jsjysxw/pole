package cn.com.avatek.pole.adapter.viewadapter;

import android.content.Intent;

/**
 * Created by pc on 2017/1/5.
 */

public class GvType {
    private int drawableId;
    private String title;
    private Intent intent;
    private int bgId;
    private String web_url;
    private String web_title;
    private String info;

    public int getBgId() {
        return bgId;
    }

    public void setBgId(int bgId) {
        this.bgId = bgId;
    }

    public GvType(int drawableId, Intent intent, String title) {
        this.drawableId = drawableId;
        this.intent = intent;
        this.title = title;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public void setDrawableId(int drawableId) {
        this.drawableId = drawableId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }

    public String getWeb_url() {
        return web_url;
    }

    public void setWeb_url(String web_url) {
        this.web_url = web_url;
    }

    public String getWeb_title() {
        return web_title;
    }

    public void setWeb_title(String web_title) {
        this.web_title = web_title;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
