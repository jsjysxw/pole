package cn.com.avatek.pole.ctrlview.activity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import java.lang.reflect.Field;

import cn.com.avatek.pole.R;
import cn.com.avatek.pole.utils.ExitAppUtils;
import cn.com.avatek.pole.utils.SystemBarTintManager;


/**
 * Created by Shenxw on 16/5/29.
 */
public class BaseActivity extends AppCompatActivity {

    private static final String TAG = "BaseActivity";
    public Context mContext;
    protected Dialog dialog;
    protected LinearLayout llBackLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExitAppUtils.getInstance().addActivity(this);
        mContext = this;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintColor(Color.TRANSPARENT);
            tintManager.setStatusBarTintEnabled(true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ExitAppUtils.getInstance().delActivity(this);
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

    protected void setStatusLine(){
        LinearLayout rl = new LinearLayout(this);
        //设置RelativeLayout布局的宽高
        LinearLayout.LayoutParams relLayoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, getStatusBarHeight());
        rl.setLayoutParams(relLayoutParams);
        rl.setBackgroundResource(R.color.theme_color);
        ((LinearLayout)((ViewGroup)findViewById(android.R.id.content)).getChildAt(0)).addView(rl,0);
    }
}
