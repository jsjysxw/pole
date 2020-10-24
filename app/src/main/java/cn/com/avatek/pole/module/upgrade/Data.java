package cn.com.avatek.pole.module.upgrade;

public class Data {
	private int ver ;
	private String url;
	private String title;
	private String desc;
	private String txtconfirm;
	private String txtcancel;
	private String dateadded;
	private String force_upgrade;
	public int getVer() {
		return ver;
	}
	public void setVer(int ver) {
		this.ver = ver;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getTxtconfirm() {
		return txtconfirm;
	}
	public void setTxtconfirm(String txtconfirm) {
		this.txtconfirm = txtconfirm;
	}
	@Override
	public String toString() {
		return "Date [ver=" + ver + ", url=" + url + ", title=" + title
				+ ", desc=" + desc + ", txtconfirm=" + txtconfirm
				+ ", txtcancel=" + txtcancel + ", dateadded=" + dateadded
				+ ", force_upgrade=" + force_upgrade + "]";
	}
	public String getTxtcancel() {
		return txtcancel;
	}
	public void setTxtcancel(String txtcancel) {
		this.txtcancel = txtcancel;
	}
	public String getDateadded() {
		return dateadded;
	}
	public void setDateadded(String dateadded) {
		this.dateadded = dateadded;
	}
	public String getForce_upgrade() {
		return force_upgrade;
	}
	public void setForce_upgrade(String force_upgrade) {
		this.force_upgrade = force_upgrade;
	}
}
