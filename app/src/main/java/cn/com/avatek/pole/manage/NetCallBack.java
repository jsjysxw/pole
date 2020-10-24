package cn.com.avatek.pole.manage;

import okhttp3.Call;

/**
 * Created by shenxw on 2017/1/18.
 */

public interface NetCallBack {
    //call为null时是success不为1的错误
    void onError(String error, Call call);

    void onSuccess(String response);
}
