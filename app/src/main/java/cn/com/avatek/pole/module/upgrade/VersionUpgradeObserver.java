package cn.com.avatek.pole.module.upgrade;

import java.util.Observable;

public class VersionUpgradeObserver extends Observable {
	
	public final static int COMPULSORY_UPGRADE_ERROR_EXIT = -999; //强制升级失败
	public final static int PROMPT_UPGRADE_ERROR = -1; //提示升级失败
	
	private static VersionUpgradeObserver mSwitchFragmentObserver = null;

	private VersionUpgradeObserver() {
	}

	public static VersionUpgradeObserver getInstance() {
		if (mSwitchFragmentObserver == null) {
			mSwitchFragmentObserver = new VersionUpgradeObserver();
		}
		return mSwitchFragmentObserver;
	}

	/**
	 * 版本升级结果
	 * 
	 * @param tag
	 */
	public void versionUpgradeResult(int tag) {
		setChanged();
		notifyObservers(tag);
	}

}
