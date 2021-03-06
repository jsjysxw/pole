package cn.com.avatek.pole.module.upgrade;

import java.io.Serializable;

public class VersionInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public int status;
	public String message;
	public Content data;

	public static class Content implements Serializable {
		private static final long serialVersionUID = 1L;
		public int ver; // 版本号
		public String url; // 下载地址
		public String title; // 标题
		public String desc; // 描述
		public String txtconfirm; // 确认文字
		public String txtcancel; // 取消文字
		public String dateadded; // 时间
		public boolean force_upgrade; // 是否强制升级
	}
}
