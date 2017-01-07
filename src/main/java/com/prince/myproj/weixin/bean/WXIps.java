package com.prince.myproj.weixin.bean;

import java.util.List;

public class WXIps {
	private WXAccessToken wxAccessToken;
	private List<String> ips;
	private String getUrl;
	public WXAccessToken getWxAccessToken() {
		return wxAccessToken;
	}
	public void setWxAccessToken(WXAccessToken wxAccessToken) {
		this.wxAccessToken = wxAccessToken;
	}
	public List<String> getIps() {
		return ips;
	}
	public void setIps(List<String> ips) {
		this.ips = ips;
	}
	public String getGetUrl() {
		return getUrl;
	}
	public void setGetUrl(String getUrl) {
		this.getUrl = getUrl;
	}
	
}
