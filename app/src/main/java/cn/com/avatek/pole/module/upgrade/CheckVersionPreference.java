package cn.com.avatek.pole.module.upgrade;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class CheckVersionPreference {

	private String packName = "com.xinweibao51.xwbversionupgrade.xwbclass";
	private Context app;
	private SharedPreferences settings;

	public CheckVersionPreference(Context ctx) {
		app = ctx;
		packName = ctx.getPackageName();
		settings = app.getSharedPreferences(packName, Context.MODE_PRIVATE);
	}

	/**
	 * 保存用户配置返回的请求
	 * 
	 * @param data
	 */
	public void saveVersionInfo(String data) {
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("versionInfo", data);
		editor.commit();
	}

	/**
	 * 获取最新请求对象
	 */
	public VersionInfo getVersionInfo() {
		String result = settings.getString("versionInfo", null);
		VersionInfo mVersionInfo = null;
		if (result == null) {
			return mVersionInfo;
		}
		if (result.length() == 0) {
			return mVersionInfo;
		}
		try {
			Gson gson = new Gson();
			java.lang.reflect.Type type = new TypeToken<VersionInfo>() {
			}.getType();
			mVersionInfo = gson.fromJson(result, type);
		} catch (Exception e) {
		}
		return mVersionInfo;
	}

	public void storeForceUpgrade(boolean forceUpgrade) {
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean("forceUpgrade", forceUpgrade);
		editor.commit();
	}

	/**
	 * 忽略的版本号
	 * 
	 * @param versionCode
	 * @return
	 */
	public void storeIgnoreVersion(int versionCode) {
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt("IgnoreVersionCode", versionCode);
		editor.commit();
	}
	/**
	 *  返回忽略的版本号
	 * @return
	 */
	public int getIgnoreVersion() {
		return settings.getInt("IgnoreVersionCode", 0);
	}

	public boolean getForceUpgrade() {
		return settings.getBoolean("forceUpgrade", false);
	}

	/**
	 * 最新版本code
	 * 
	 * @return
	 */
	public void storeNewVersionCode(int code) {
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt("newVersionCode", code);
		editor.commit();
	}

	public int getNewVersionCode() {
		return settings.getInt("newVersionCode", 0);
	}

}
