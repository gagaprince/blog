package com.prince.myproj.weixin.bean;

public class ShareModel {
	String appid;
	String timestamp;
	String nonceStr;
	String signaturel;
	String url;
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getNonceStr() {
		return nonceStr;
	}
	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}
	public String getSignaturel() {
		return signaturel;
	}
	public void setSignaturel(String signaturel) {
		this.signaturel = signaturel;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}
