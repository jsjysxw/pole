package cn.com.avatek.pole;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.StrictMode;

import com.google.gson.Gson;

import java.util.prefs.Preferences;

import cn.com.avatek.pole.constant.Constant;
import cn.com.avatek.pole.entity.UserBean;
import cn.com.avatek.pole.entity.UserResult;
import cn.com.avatek.pole.utils.CustomCrashHandler;
import cn.com.avatek.pole.utils.FileLog;
import cn.com.avatek.pole.utils.SharedPreferenceUtil;


/**
 *
 */
public class SvaApplication extends Application {

    private static Context context;
    private static SvaApplication svaApplication;
    private UserResult.ContentBean loginUser;
    @Override
    public void onCreate() {
        super.onCreate();
        svaApplication = this;
        FileLog.init();
        context = this.getApplicationContext();
        Gson gson = new Gson();
        loginUser= gson.fromJson((String) SharedPreferenceUtil.getData(this, Constant.FILE_NAME,Constant.KEY_USERBEAN,""), UserResult.ContentBean.class);
        initCrash();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
    }

    private void initCrash() {
        CustomCrashHandler mCustomCrashHandler = CustomCrashHandler.getInstance();
        mCustomCrashHandler.setCustomCrashHanler(getApplicationContext());
    }

    public static Context getContext() {
        return context;
    }
    public static SvaApplication getInstance(){
        return svaApplication;
    }

    public UserResult.ContentBean getLoginUser() {
        if(loginUser==null){
            loginUser = new UserResult.ContentBean();
        }
        return loginUser;
    }

    public void setLoginUser(UserResult.ContentBean loginUser) {
        this.loginUser = loginUser;
        saveUser();
    }

    private void saveUser(){
        Gson gson = new Gson();
        SharedPreferenceUtil.saveData(this, Constant.FILE_NAME,Constant.KEY_USERBEAN,gson.toJson(loginUser));
    }

    public UserResult.ContentBean updateUser(UserResult.ContentBean updateUser) {
        this.loginUser = updateUser;
        saveUser();
        return loginUser;
    }

}
