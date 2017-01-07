package com.prince.myproj.weixin.bean;

public class WXAccessToken {
	private WXbasic wXbasic;
	private String accessToken;
	private String grantType;
	private String getUrl;
	public WXbasic getwXbasic() {
		return wXbasic;
	}
	public void setwXbasic(WXbasic wXbasic) {
		this.wXbasic = wXbasic;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getGrantType() {
		return grantType;
	}
	public void setGrantType(String grantType) {
		this.grantType = grantType;
	}
	public String getGetUrl() {
		return getUrl;
	}
	public void setGetUrl(String getUrl) {
		this.getUrl = getUrl;
	}
	
}
