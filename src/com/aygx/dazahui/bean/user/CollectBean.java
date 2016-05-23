package com.aygx.dazahui.bean.user;

import cn.bmob.v3.BmobObject;

public class CollectBean extends BmobObject{
	
	public CollectBean(String table){
		this.setTableName(table);
	}
	private String title;
	private String date;
	private String url;
	private String img_url;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getImg_url() {
		return img_url;
	}
	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}
}
