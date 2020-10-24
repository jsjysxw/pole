package cn.com.avatek.pole.module.upgrade;

import android.content.Context;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.Hashtable;

public class NetworkUtils {

	public static String postContentBody(String url, String contentBody) {

		String result = null;
		InputStream is = null;
//		try {
//			HttpPost mPost = new HttpPost(url);
//			ByteArrayEntity baEntity = new ByteArrayEntity(contentBody.getBytes("UTF8"));
//			baEntity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
//			mPost.setEntity(baEntity);
//			System.out.println("======="+contentBody.toString());
//			HttpGet mPost = new HttpGet(url);
//			HttpClient httpClient = new DefaultHttpClient();
//			HttpResponse httpResponse = httpClient.execute(mPost);
//			HttpEntity httpEntity = httpResponse.getEntity();
//			if (httpEntity != null) {
//				is = httpEntity.getContent();
//				result = StringUtils.convertStreamToString(is);
//				 Log.i("=======", "Result: " + result);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				if (is != null && is.available() > 0) {
//					is.close();
//					;
//				}
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
		String str = null;
		try {
			URL url2 = new URL(url);
			if (url!=null){
				HttpURLConnection connection = (HttpURLConnection) url2.openConnection();
				connection.setConnectTimeout(3000);
				connection.setDoInput(true);
				connection.setRequestMethod("GET");
				int code = connection.getResponseCode();
				if (code == 200){
					is = connection.getInputStream();
					result = StringUtils.convertStreamToString(is);
				}
			}
		}catch (Exception e){

		}
		return result;
	}

	public static String generateClassJSonParams(Hashtable htable) {
		if (htable == null) {
			return "{}";
		}
		boolean alreadyAddParams = false;
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		Enumeration eeKeys = htable.keys();
		while (eeKeys.hasMoreElements()) {
			if (alreadyAddParams) {
				sb.append(",");
			}
			String key = (String) eeKeys.nextElement();
			Object value = htable.get(key);
			if (value instanceof Hashtable) {
				sb.append("\"data\":" + generateJSonParams((Hashtable) value) + "");
			} else {
				// if (value instanceof String) {
				String sValue = formatValue(value);
				if (sValue.startsWith("{") && sValue.endsWith("}")) {
					// aleady is json array
					sb.append("\"" + key + "\":" + "" + formatValue(value) + "");
				} else {
					sb.append("\"" + key + "\":" + "\"" + formatValue(value) + "\"");
				}
				// }

			}
			alreadyAddParams = true;
		}

		sb.append("}");

		return sb.toString();
	}

	/**
	 * 暂时自己写参数，是否考虑用JSon lib 暂时不考虑
	 * 
	 * @param htable
	 *            key : value value 可以是 Integer, String 简单数据对象 也可以是Hashtable, 这时key是 list
	 * @return
	 */
	private static String generateJSonParams(Hashtable htable) {
		if (htable == null) {
			return "{}";
		}

		boolean alreadyAddParams = false;
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		Enumeration eeKeys = htable.keys();
		while (eeKeys.hasMoreElements()) {
			if (alreadyAddParams) {
				sb.append(",");
			}
			String key = (String) eeKeys.nextElement();
			Object value = htable.get(key);
			if (value instanceof Hashtable) {
				sb.append("\"list\":[" + generateJSonParams((Hashtable) value) + "]");// 如果参数是List
			} else {
				// if (value instanceof String) {
				String sValue = formatValue(value);
				if (sValue.startsWith("[") && sValue.endsWith("]")) {
					// aleady is json array
					sb.append("\"" + key + "\":" + "" + formatValue(value) + "");
				} else {
					sb.append("\"" + key + "\":" + "\"" + formatValue(value) + "\"");
				}
				// }

			}
			alreadyAddParams = true;// 已经添加过参数，下次会添加 ','
		}

		sb.append("}");
		return sb.toString();
	}

	/**
	 * 将value作为转移符编码
	 * 
	 * @param
	 * @return
	 */
	private static String formatValue(Object obj) {
		String value = "" + obj;
		value = value.replaceAll("\"", "\\\"");
		return value;
	}

	/**
	 * 班级首页
	 * 
	 * @param context
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Hashtable generateCheckUpdateParams(Context context, String app_name, String version_code, String channel) {

		Hashtable root = new Hashtable();
		root.put("action", "");
		Hashtable htable = new Hashtable();
		htable.put("app_name", app_name);
		htable.put("version_code", version_code);
		htable.put("channel", channel);
		htable.put("user_agent", getUserAgent(context));
		root.put("data", htable);
		return root;
	}

	public static String getUserAgent(Context context) {
		String deviceID = getDeviceID(context);
		String agent = android.os.Build.MODEL + "/android/" + getOSVersion() + "/" + deviceID;
		// DebugUtils.println("---agent is " + agent);
		return agent;
	}

	/**
	 * Get Phone's OS version
	 * 
	 * @return
	 */
	public static String getOSVersion() {
		String version = getRelease() + "," + getSDK();
		return version;
	}

	/**
	 * Get Phone's SDK
	 * 
	 * @return
	 */
	public static String getSDK() {
		return android.os.Build.VERSION.SDK;
	}

	/**
	 * Get Phone's Release
	 * 
	 * @return
	 */
	public static String getRelease() {
		return android.os.Build.VERSION.RELEASE;
	}

	/**
	 * TODO delete it for debug
	 */

	/**
	 * 
	 * @param context
	 * @return
	 */
	public static String getDeviceID(Context context) {
		String deviceID = null;
		deviceID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
		if (deviceID == null || deviceID.length() == 0) {
			TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			if (tm != null) {
				deviceID = tm.getDeviceId();
			}
		}
		return deviceID.toLowerCase();
	}
}
