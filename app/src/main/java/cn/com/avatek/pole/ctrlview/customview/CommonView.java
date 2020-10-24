package cn.com.avatek.pole.ctrlview.customview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import cn.com.avatek.pole.entity.ClassContent;

/**
 * Created by shenxw on 2017/4/17.
 */

public class CommonView extends LinearLayout {
    protected ClassContent classContent;
    protected OnShowListListenner onShowListListenner;

    public CommonView(Context context) {
        super(context);
    }

    public CommonView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public String getResult() {
        return "";
    }

    public String getTitle() {
        return "";
    }


    protected boolean isNoText() {
        return true;
    }

    public void setDetailData(ClassContent classContent) {
        this.classContent = classContent;
    }

    public ClassContent getDetailData() {
        return classContent;
    }

    public void setOnShowListListenner(OnShowListListenner onShowListListenner) {
        this.onShowListListenner = onShowListListenner;
    }
    public interface OnShowListListenner{
        void onShowChanged(String formid, String liststr,String title);
    }
}
