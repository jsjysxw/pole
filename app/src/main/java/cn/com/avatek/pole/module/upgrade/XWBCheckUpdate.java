package cn.com.avatek.pole.module.upgrade;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Hashtable;

import cn.com.avatek.pole.constant.ApiAddress;

public class XWBCheckUpdate {
    public static final String CHECK_VERSION_URL = ApiAddress.AndroidUpdate;
    // 版本升级广播
    public static String VERSION_UPGRADE_BROADCAST = "com.xwbclass.version.broadcast"; // default

    private Context context;
    private CheckVersionPreference preference;
    private VersionInfo oldVersionInfo;
    private String appAlias;
    private boolean flag = false;

    public XWBCheckUpdate(Context context) {
        this.context = context;
        preference = new CheckVersionPreference(context);
        initData();
    }

    private void initData() {
        try {
            VERSION_UPGRADE_BROADCAST = UpdateUtils.getBroadcast(context);
        } catch (Exception e) {
        }

    }

    /**
     * 检测是否有新版本
     *
     * @param appAlias app 别名
     */
    public void checkVersion(String appAlias) {
        this.appAlias = appAlias;
        oldVersionInfo = preference.getVersionInfo();
        if (oldVersionInfo == null) { // 不存在老的版本信息 则 直接请求升级信息
            new CheckVersionAsyncTask().execute(appAlias);
            return;
        }
        if (oldVersionInfo.data == null) {
            new CheckVersionAsyncTask().execute(appAlias);
            return;
        }

        int nowVersonCOde = UpdateUtils.getVersionCode(context); // 当前版本号
        if (oldVersionInfo.data.force_upgrade) { // 是强制升级的请求
            if (nowVersonCOde >= oldVersionInfo.data.ver) { // 当前版本大于等于请求版本时 执行
                // 检测版本
                new CheckVersionAsyncTask().execute(appAlias);
                return;
            } else { // 强制升级
                showUpgrade(oldVersionInfo, true);
            }
        } else {
            new CheckVersionAsyncTask().execute(appAlias);
            return;
        }
    }


    /**
     * 检测是否有新版本
     *
     * @param appAlias app 别名
     */
    public void clickVersion(String appAlias) {
        this.appAlias = appAlias;
        flag = true;
        new CheckVersionAsyncTask().execute(appAlias);

    }


    /**
     * 版本检测
     *
     * @author pengjia
     */
    public class CheckVersionAsyncTask extends AsyncTask<String, Integer, VersionInfo> {
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

            String result = NetworkUtils.postContentBody(CHECK_VERSION_URL, data);
            try {
                Gson gson = new Gson();
                java.lang.reflect.Type type = new TypeToken<VersionInfo>() {
                }.getType();
                mVersionInfo = gson.fromJson(result, type);
                if (mVersionInfo != null && mVersionInfo.status == 0 && mVersionInfo.data != null && mVersionInfo.data.ver != 0) {
                    preference.saveVersionInfo(result); // 保存请求
                }
            } catch (Exception e) {
            }
            return mVersionInfo;
        }

        @Override
        protected void onPostExecute(VersionInfo result) {
            if (result == null || result.data == null || result.status != 0) {
                return; // 请求失败
            }
            if (result.data.ver == 0) { // 表示没有找到版本信息
                return;
            }
            if (result.data.force_upgrade&&UpdateUtils.getVersionCode(context)<result.data.ver) { // 检测到强制升级则忽略以前的配置
                showUpgrade(result, false);
                return;
            }
            try {
                int localVersionCode = UpdateUtils.getVersionCode(context);
                if (localVersionCode == result.data.ver) {
                    return;
                    // 版本号相同，则什么都不做
                }
                int ignoreCode = preference.getIgnoreVersion();

                /**
                 * 是不是每次要弹出框，还是就弹一次，还是每次都弹出，就是更改这个地方
                 */
//                if (flag&&UpdateUtils.getVersionCode(context)<result.data.ver) {
//                    showUpgrade(result, false);
//                    flag = false;
//                }

                if (UpdateUtils.getVersionCode(context)<result.data.ver) {
                    showUpgrade(result, false);
                    flag = false;
                }


                if (ignoreCode == result.data.ver && result.data.force_upgrade == false) { // 忽略的版本号等于请求的版本号
                    // 已经提示过
//					showUpgrade(result, false);
                    return;
                }
                // show upload
                //showUpgrade(result, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 跳转到activity进行升级
     *
     * @param result
     * @param isUpdateUrl
     */
    private void showUpgrade(VersionInfo result, boolean isUpdateUrl) {
        Intent intent = new Intent(context, UpgradeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(UpgradeActivity.KEY_APP_ALIAS, appAlias);
        intent.putExtra(UpgradeActivity.KEY_IS_UPDATE_DOWNLOAD_URL, isUpdateUrl);
        intent.putExtra(UpgradeActivity.KEY_VERSION_INFO, result);
        context.startActivity(intent);
    }

}
