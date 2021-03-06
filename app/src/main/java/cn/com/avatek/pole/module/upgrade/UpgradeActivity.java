package cn.com.avatek.pole.module.upgrade;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Hashtable;
import java.util.List;

import cn.com.avatek.pole.R;


public class UpgradeActivity extends FragmentActivity {
    public static final String KEY_APP_ALIAS = "app_alias"; // 别名
    public static final String KEY_IS_UPDATE_DOWNLOAD_URL = "is_update_url"; // 是否更新下载的URL
    public static final String KEY_VERSION_INFO = "version"; // 版本信息
    private Context context;
    private XWBDialog myPromptDialog;
    private XWBDialog errorDialog;
    private LoadingDialog myLoadingDialog;
    private static final int RESULT_ERROR = -0x0001;
    private static final int RESULT_LOADING = 0x0001;
    private static final int RESULT_DOWNLOAD_SUSS = 0x0002;
    private int old_process = 0;
    private CheckVersionPreference preference;
    private VersionInfo oldVersionInfo;
    private String appName;
    private boolean isUpdateUrl;
    private RelativeLayout all;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upgrade_activity);

//		ExitAppUtils.getInstance().addActivity(this);

        setupData();
        isUpdateUrl = getIntent().getBooleanExtra(KEY_IS_UPDATE_DOWNLOAD_URL, false);
        oldVersionInfo = (VersionInfo) getIntent().getSerializableExtra(KEY_VERSION_INFO);
        appName = getIntent().getStringExtra(KEY_APP_ALIAS);

        if (oldVersionInfo.data.force_upgrade) {
            mandatoryUpgrade(oldVersionInfo, isUpdateUrl); // 强制升级
        } else {
            if (isServiceRunning(context, VersionService.class.getName())) { // service
                // 运行中
                finish();
                return;
            }
//			promptUpgrade(oldVersionInfo); // 提示升级
            mandatoryUpgrade(oldVersionInfo, isUpdateUrl); // 强制升级
        }

    }

    private void setupData() {
        this.context = UpgradeActivity.this;
        preference = new CheckVersionPreference(context);
    }

    /**
     * error
     */
    private void showErrorDialog(String errorMeaasge) {
        errorDialog = new XWBDialog(context, XWBDialog.STYLE_TWO_BUTTON, new XWBDialog.MyDialogOnClick() {
            @Override
            public void onClick(int result) {
                if (XWBDialog.RESUTL_CONFIRM == result) {
                    if (!checkNet(context)) {
                        Toast.makeText(context, getResources().getString(R.string.upgrade_network_unavailable), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    // 重试
                    new GetUpgradeInfo().execute(appName);
                    errorDialog.dismiss();
                } else if (XWBDialog.RESUTL_CANCEL == result) {
                    errorDialog.dismiss();
                    Log.d("sdasd", "取消");
                    finishAll();
                }
            }
        }, "提示", errorMeaasge, "重试", "退出");
        errorDialog.setOnTouchCancel(true);
        errorDialog.show();
    }

    /**
     * 通知关闭
     */
    private void finishAll() {
        finish();
        VersionUpgradeObserver.getInstance().versionUpgradeResult(VersionUpgradeObserver.COMPULSORY_UPGRADE_ERROR_EXIT);
        sendExitBroadcast(XWBCheckUpdate.VERSION_UPGRADE_BROADCAST);
    }

    /**
     * 发送广播升级失败
     */
    private void sendExitBroadcast(String action) {
        Intent intent = new Intent(action);
        intent.putExtra("message", "Mandatory upgrade failed");
        UpgradeActivity.this.sendBroadcast(intent);
    }

    @Override
    protected void onDestroy() {
        closeAllDialog();
        super.onDestroy();
//		ExitAppUtils.getInstance().delActivity(this);
    }

    private void closeAllDialog() {
        if (myPromptDialog != null && myPromptDialog.isShowing()) {
            myPromptDialog.dismiss();
        }

        if (myLoadingDialog != null && myLoadingDialog.isShowing()) {
            myLoadingDialog.dismiss();
        }

        if (errorDialog != null && errorDialog.isShowing()) {
            errorDialog.dismiss();
        }
    }

    /**
     * 强制升级
     */
    private void mandatoryUpgrade(final VersionInfo versionInfo, final boolean isUpdateUrl) {
        myPromptDialog = new XWBDialog(context, XWBDialog.STYLE_TWO_BUTTON, new XWBDialog.MyDialogOnClick() {
            @Override
            public void onClick(int result) {
                if (XWBDialog.RESUTL_CONFIRM == result) {
                    if (!checkNet(context)) {
                        Toast.makeText(context, getResources().getString(R.string.upgrade_network_unavailable), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    // 前台强制升级
                    myPromptDialog.dismiss();
                    showLoadProgress(0);
                    if (isUpdateUrl) {
                        new GetUpgradeInfo().execute(appName);
                    } else {
                        downloadAPK(versionInfo.data.url);
                    }
                } else if (XWBDialog.RESUTL_CANCEL == result) {
                    myPromptDialog.dismiss();
                    finish();
                    preference.storeIgnoreVersion(versionInfo.data.ver); // 保存要忽略的版本号

                }
            }
        }, versionInfo.data.title, versionInfo.data.desc, versionInfo.data.txtconfirm, versionInfo.data.txtcancel);
        myPromptDialog.setOnTouchCancel(true);
        myPromptDialog.show();
    }

    /**
     * 提示升级
     */
    private void promptUpgrade(final VersionInfo versionInfo) {
        myPromptDialog = new XWBDialog(context, XWBDialog.STYLE_TWO_BUTTON, new XWBDialog.MyDialogOnClick() {
            @Override
            public void onClick(int result) {
                if (XWBDialog.RESUTL_CONFIRM == result) {
                    if (!checkNet(context)) {
                        Toast.makeText(context, getResources().getString(R.string.upgrade_network_unavailable), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    // 提示升级
                    myPromptDialog.dismiss();
                    Intent intent = new Intent(context, VersionService.class);
                    intent.putExtra(VersionService.KEY_DOWNLOAD_URL, versionInfo.data.url);
                    context.startService(intent);
                    finish();
                } else if (XWBDialog.RESUTL_CANCEL == result) {
                    myPromptDialog.dismiss();
                    finish();
                    preference.storeIgnoreVersion(versionInfo.data.ver); // 保存要忽略的版本号

                }
            }
        }, versionInfo.data.title, versionInfo.data.desc, versionInfo.data.txtconfirm, versionInfo.data.txtcancel);
        myPromptDialog.setOnTouchCancel(true);
        myPromptDialog.show();
    }

    /**
     * 用来判断服务是否运行.
     *
     * @param className 判断的服务名字
     * @return true 在运行 false 不在运行
     */
    public static boolean isServiceRunning(Context mContext, String className) {
        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList = activityManager.getRunningServices(30);
        if (!(serviceList.size() > 0)) {
            return false;
        }
        for (int i = 0; i < serviceList.size(); i++) {
            if (serviceList.get(i).service.getClassName().equals(className) == true) {
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }

    private void downloadAPK(final String url) {
        new Thread() {
            public void run() {
                loadFile(url);
            }
        }.start();
    }

    public void loadFile(String url) {

        try {
            URL url2 = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) url2.openConnection();
            connection.setConnectTimeout(3000);
            connection.setDoInput(true);
            connection.setRequestMethod("GET");
            int code = connection.getResponseCode();
            if (code == 200) {
                InputStream is = connection.getInputStream();
                float length = connection.getContentLength();
                FileOutputStream fileOutputStream = null;
                if (is != null) {
                    File file = new File(Environment.getExternalStorageDirectory(), VersionService.FILENAME);
                    fileOutputStream = new FileOutputStream(file);
                    byte[] buf = new byte[1024];
                    int ch = -1;
                    float count = 0;
                    while ((ch = is.read(buf)) != -1) {
                        fileOutputStream.write(buf, 0, ch);
                        count += ch;
                        sendMsg(RESULT_LOADING, (int) (count * 100 / length));
                    }
                }
                sendMsg(RESULT_DOWNLOAD_SUSS, 0);
                fileOutputStream.flush();
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            }
        } catch (Exception e) {
            sendMsg(RESULT_ERROR, 0);
        }
    }

    private void sendMsg(int flag, int process) {
        if (flag == RESULT_LOADING) {
            if (old_process == process) {
                return;
            }
            old_process = process;
        }

        Message msg = new Message();
        msg.what = flag;
        msg.arg1 = process;
        handler.sendMessage(msg);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {// 定义一个Handler，用于处理下载线程与UI间通讯
            if (!Thread.currentThread().isInterrupted()) {
                switch (msg.what) {
                    case RESULT_LOADING:
                        showLoadProgress(msg.arg1);

                        break;
                    case RESULT_DOWNLOAD_SUSS:
                        // showLoadProgress(100);
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory(), VersionService.FILENAME)), "application/vnd.android.package-archive");
                        context.startActivity(intent);
                        finishAll();
                        break;
                    case RESULT_ERROR:
                        String error = msg.getData().getString("error");
                        showErrorDialog(context.getString(R.string.download_dialog_unkownerror_msg));
                        break;
                }
            }
            super.handleMessage(msg);
        }
    };

    private void showLoadProgress(int progress) {
        if (myLoadingDialog == null) {
            myLoadingDialog = new LoadingDialog(context);
        }
        if (!myLoadingDialog.isShowing()) {
            myLoadingDialog.show();
        }
        myLoadingDialog.setProgress(progress);
    }

    // 检查网络
    public static boolean checkNet(Context context) {
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo info = connectivity.getActiveNetworkInfo();
                if (info != null && info.isConnected()) {
                    if (info.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * 获取版本升级所需的URL
     *
     * @author pengjia
     */
    public class GetUpgradeInfo extends AsyncTask<String, Integer, VersionInfo> {
        @Override
        protected VersionInfo doInBackground(String... params) {
            if (params.length == 0) {
                return null;
            }
            String app_name = params[0]; // 别名
            String version_code = "" + UpdateUtils.getVersionCode(context);// 版本code
            String channel = UpdateUtils.getQudaoKey(context); // 渠道
            VersionInfo mVersionInfo = null;
            @SuppressWarnings("rawtypes")
            Hashtable htable = NetworkUtils.generateCheckUpdateParams(context, app_name, version_code, channel);
            String data = NetworkUtils.generateClassJSonParams(htable);
            String result = NetworkUtils.postContentBody(XWBCheckUpdate.CHECK_VERSION_URL, data);
            try {
                Gson gson = new Gson();
                java.lang.reflect.Type type = new TypeToken<VersionInfo>() {
                }.getType();
                mVersionInfo = gson.fromJson(result, type);
                if (mVersionInfo != null && mVersionInfo.data != null) {
                    preference.saveVersionInfo(result); // 保存请求
                }
            } catch (Exception e) {
            }
            return mVersionInfo;
        }

        @Override
        protected void onPostExecute(VersionInfo result) {
            if (result == null) {
                showErrorDialog(context.getString(R.string.download_dialog_unkownerror_msg));
                return; // 请求失败
            }
            if (result.data == null) {
                showErrorDialog(context.getString(R.string.download_dialog_unkownerror_msg));
                return; // 请求失败
            }

            downloadAPK(result.data.url);
        }
    }

}
