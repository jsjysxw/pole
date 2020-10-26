package cn.com.avatek.pole.drawmap.bases;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import java.lang.reflect.Field;

import cn.com.avatek.pole.R;
import cn.com.avatek.pole.utils.SystemBarTintManager;


/**
 * Created by owant on 22/02/2017.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintColor(Color.TRANSPARENT);
            tintManager.setStatusBarTintEnabled(true);
        }
        onBaseIntent();

        onBasePreLayout();

        setContentView(onBaseLayoutId(savedInstanceState));

        onBaseBindView();

        onLoadData();
    }

    /**
     * Intent进来的数据处理
     */
    protected abstract void onBaseIntent();

    /**
     * 设置布局之前的处理
     */
    protected abstract void onBasePreLayout();

    /**
     * 返回布局文件
     *
     * @return id
     */
    protected abstract int onBaseLayoutId(@Nullable Bundle savedInstanceState);

    protected abstract void onBaseBindView();

    /**
     * 加载数据
     */
    protected abstract void onLoadData();
    protected void setStatusLine(){
        LinearLayout rl = new LinearLayout(this);
        //设置RelativeLayout布局的宽高
        LinearLayout.LayoutParams relLayoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, getStatusBarHeight());
        rl.setLayoutParams(relLayoutParams);
        rl.setBackgroundResource(R.color.theme_color);
        ((LinearLayout)((ViewGroup)findViewById(android.R.id.content)).getChildAt(0)).addView(rl,0);
    }

    /**
     * 获取状态栏的高度
     * @return
     */
    protected int getStatusBarHeight(){
        try
        {
            Class<?> c= Class.forName("com.android.internal.R$dimen");
            Object obj=c.newInstance();
            Field field=c.getField("status_bar_height");
            int x= Integer.parseInt(field.get(obj).toString());
            return  getResources().getDimensionPixelSize(x);
        }catch(Exception e){
            e.printStackTrace();
        }
        return 0;
    }
}
