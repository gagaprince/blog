package com.prince.myproj.weixin.config;

import com.prince.myproj.weixin.bean.WXAccessToken;
import com.prince.myproj.weixin.bean.WXbasic;
import org.apache.log4j.Logger;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class WeiXinConfig {
	private Configuration config;
	private static final Logger logger =  Logger.getLogger(WeiXinConfig.class);
	
	private WXAccessToken wxAccessToken;
	private WXbasic wxBasic;
	private String wxShareHost;
	private String userMsgUrl;
	private String wxLoginAuthorizeUrl;
	
	
	
	public String getWxLoginAuthorizeUrl() {
		return wxLoginAuthorizeUrl;
	}

	public void setWxLoginAuthorizeUrl(String wxLoginAuthorizeUrl) {
		this.wxLoginAuthorizeUrl = wxLoginAuthorizeUrl;
	}

	public String getUserMsgUrl() {
		return userMsgUrl;
	}

	public void setUserMsgUrl(String userMsgUrl) {
		this.userMsgUrl = userMsgUrl;
	}

	public String getWxShareHost() {
		return wxShareHost;
	}

	public void setWxShareHost(String wxShareHost) {
		this.wxShareHost = wxShareHost;
	}

	private String jsapiTicket;
	
	public String getJsapiTicket() {
		return jsapiTicket;
	}

	public void setJsapiTicket(String jsapiTicket) {
		this.jsapiTicket = jsapiTicket;
	}

	private String jsapiTicketGetUrl ;
	private String loginAccessTokenUrl;

	
	public WXbasic getWxBasic() {
		if(wxBasic==null){
			wxBasic = new WXbasic();
			wxBasic.setAppId(get("appid"));
			wxBasic.setAppSecret(get("AppSecret"));
		}
		return wxBasic;
	}

	public void setWxBasic(WXbasic wxBasic) {
		this.wxBasic = wxBasic;
	}

	@PostConstruct
	private void init() {
		logger.info("weixin config init begin");
		try {
			config = new PropertiesConfiguration("weixin.properties");// src/main/
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
		initData();
		logger.info("weixin config init end");
	}
	
	private void initData(){
		jsapiTicketGetUrl = get("jsapi_ticket_url");
		loginAccessTokenUrl = get("login_access_token");
		userMsgUrl = get("login_usermsgurl");
		wxLoginAuthorizeUrl = get("login_redirect_url");
		wxShareHost = get("AppHost");
		initTicket();
		initToken();
	}
	
	public String getLoginAccessTokenUrl() {
		return loginAccessTokenUrl;
	}

	public void setLoginAccessTokenUrl(String loginAccessTokenUrl) {
		this.loginAccessTokenUrl = loginAccessTokenUrl;
	}

	public String getJsapiTicketGetUrl() {
		return jsapiTicketGetUrl;
	}

	public void setJsapiTicketGetUrl(String jsapiTicketGetUrl) {
		this.jsapiTicketGetUrl = jsapiTicketGetUrl;
	}

	private void initTicket(){

	}
	
	private void initToken(){
		wxAccessToken = null;
	}

	public String get(String key) {
		if (config == null) {
			init();
		}
		return config.getString(key);
	}
	
	
	public void setAccessToken(WXAccessToken token){
		wxAccessToken = token;
	}
	
	public WXAccessToken getAccessToken(){
		return wxAccessToken;
	}
}
