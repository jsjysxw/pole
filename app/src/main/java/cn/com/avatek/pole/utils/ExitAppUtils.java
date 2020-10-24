package cn.com.avatek.pole.utils;

import android.app.Activity;

import java.util.LinkedList;
import java.util.List;

/**
 *	在onCreate中add
 *	在OnDestory中del
 */
public class ExitAppUtils {
	/**
	 *
	 */
	private List<Activity> mActivityList = new LinkedList<Activity>();
	private static ExitAppUtils instance = new ExitAppUtils();
	
	/**
	 *
	 */
	private ExitAppUtils(){};
	
	/**
	 *	单例
	 * @return
	 */
	public static ExitAppUtils getInstance(){
		return instance;
	}

	
	/**
	 *	加入
	 * @param activity
	 */
	public void addActivity(Activity activity){
		mActivityList.add(activity);
	}
	
	/**
	 *	移除
	 * @param activity
	 */
	public void delActivity(Activity activity){
		mActivityList.remove(activity);
	}
	
	
	/**
	 *	退出
	 */
	public void exit(){
		for(Activity activity : mActivityList){
			activity.finish();
		}
		
		System.exit(0);
	}
	

}
