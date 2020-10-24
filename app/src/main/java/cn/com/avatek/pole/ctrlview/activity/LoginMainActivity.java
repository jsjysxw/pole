package cn.com.avatek.pole.ctrlview.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.avatek.pole.R;
import cn.com.avatek.pole.SvaApplication;
import cn.com.avatek.pole.constant.ApiAddress;
import cn.com.avatek.pole.constant.Constant;
import cn.com.avatek.pole.entity.UserResult;
import cn.com.avatek.pole.manage.NetCallBack;
import cn.com.avatek.pole.manage.NetManager;
import cn.com.avatek.pole.utils.AvatekDialog;
import cn.com.avatek.pole.utils.HLog;
import cn.com.avatek.pole.utils.IDropdownItemClickListener;
import cn.com.avatek.pole.utils.SharedPreferenceUtil;
import cn.com.avatek.pole.utils.TypePopupwindow;
import okhttp3.Call;


/**
 * Created by User on 2015/4/29.
 */
public class LoginMainActivity extends BaseActivity implements View.OnClickListener{

    private EditText et_login_phone,et_login_pwd;
    private Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        setContentView(R.layout.activity_login_inj);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setStatusLine();
        }

        initView();
        initEvent();
//        initWeb();

        initSharedPre();
    }

    private void initSharedPre() {
        String str = (String) SharedPreferenceUtil.getData(LoginMainActivity.this, Constant.FILE_NAME,Constant.KEY_TEL,"");
        String str1 = (String) SharedPreferenceUtil.getData(LoginMainActivity.this, Constant.FILE_NAME,Constant.KEY_PSD,"");
        et_login_phone.setText(str);
        et_login_pwd.setText(str1);
    }

    private void initEvent() {
        btn_login.setOnClickListener(this);

    }

    private void initView() {
        et_login_phone = (EditText) findViewById(R.id.et_login_phone);
        et_login_pwd = (EditText) findViewById(R.id.et_login_pwd);
        btn_login = (Button) findViewById(R.id.btn_login);
    }

    private void initWeb() {
        final String userId = et_login_phone.getText().toString().trim();
        final String password = et_login_pwd.getText().toString().trim();

        if(userId.equals("")){
            Toast.makeText(this, "手机号不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if(password.equals("")){
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        SharedPreferenceUtil.saveData(LoginMainActivity.this, Constant.FILE_NAME, Constant.KEY_TEL, userId);

        Map<String, String> params = new HashMap<>();
        params.put("tel",userId);
        params.put("password",password);
        dialog = AvatekDialog.createLoadingDialog(LoginMainActivity.this, "登录中...");
        dialog.show();
        NetManager.sendPost(ApiAddress.login, params, new NetCallBack() {
            @Override
            public void onError(String error, Call call) {
                HLog.e("webnet", "response2=" + error);
                Toast.makeText(LoginMainActivity.this, "登陆失败" + error, Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }

            @Override
            public void onSuccess(String response) {
                HLog.e("webnet", "response1=" + response);
                dialog.dismiss();
                try {
                Gson gson = new Gson();
                final UserResult userResult = gson.fromJson(response, UserResult.class);
                if (userResult.getState()>0) {
                    if (userResult.getContent() != null) {
                        SvaApplication.getInstance().setLoginUser(userResult.getContent());
                        SharedPreferenceUtil.saveData(LoginMainActivity.this, Constant.FILE_NAME, Constant.KEY_PSD, password);
                        startActivity(new Intent(LoginMainActivity.this, PoleMainActivity.class));
                        Toast.makeText(LoginMainActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                        SharedPreferenceUtil.saveData(LoginMainActivity.this, Constant.FILE_NAME, Constant.KEY_USERBEAN, new Gson().toJson(userResult.getContent()));
                        finish();
                    } else {
                        Toast.makeText(LoginMainActivity.this, "登陆失败：", Toast.LENGTH_SHORT).show();
                    }

                }

                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(LoginMainActivity.this, "登陆失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        List<String> sList = new ArrayList<String>();
        switch (view.getId()){
            case R.id.btn_login:
                initWeb();
                break;
//            case R.id.tv_type:
//                sList.add("社区人员");
//                sList.add("工作人员");
//                showPopup(sList, tv_type);
//                break;

            default:
                break;

        }
    }
//    private TypePopupwindow popup;

//    private void showPopup(List<String> sList, final TextView tv) {
//
//        int[] location = new int[2];
//        tv.getLocationInWindow(location);
//        location[1] += tv.getHeight();
//        popup = new TypePopupwindow(LoginMainActivity.this, sList);
//        popup.showAt(location, tv.getWidth(), tv.getHeight(), false);
//        popup.setItemClickListener(new IDropdownItemClickListener() {
//
//            @Override
//            public void onItemClick(String text, int position) {
//                tv.setText(text);
//            }
//        });
//    }

}