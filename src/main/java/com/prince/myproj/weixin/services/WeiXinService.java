package com.prince.myproj.weixin.services;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.prince.myproj.weixin.bean.WXAccessToken;
import com.prince.myproj.weixin.bean.WXIps;
import com.prince.myproj.weixin.bean.WXbasic;
import com.prince.myproj.weixin.config.WeiXinConfig;
import com.prince.util.httputil.HttpUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
public class WeiXinService {
	@Autowired
	private WeiXinConfig wxConfig;
	@Autowired
	private WeiXinErrorService wxErrorService;
	@Autowired
	private HttpUtil httpUtil;
	
	private static final Logger logger =  Logger.getLogger(WeiXinService.class);
	/**
	 * 获取一个wxToken
	 * @return
	 */
	public WXAccessToken iNeedAToken(){
		WXAccessToken wxToken = giveMeNoPreparedToken();
		getWeiXinAccessToken(wxToken);
		return wxToken;
	}
	public String iNeedTicket(){
		WXAccessToken wxToken = getCanUseToken();
		String ticket = getJsapiTicketFromHttp(wxToken);
		return ticket;
	}
	
	/**
	 * 通过http请求获取token完整信息
	 */
	private void getWeiXinAccessToken(WXAccessToken wxAccessToken){
		String url = getWeixinAccessTokenUrl(wxAccessToken);
		String result = httpUtil.getContentByUrl(url);
		logger.info("get weixin accessToken:"+result);
		JSONObject accessJson = JSONObject.parseObject(result);
		String accessToken = (String)accessJson.get("access_token");
		if(accessToken!=null){
			wxAccessToken.setAccessToken(accessToken);
			wxConfig.setAccessToken(wxAccessToken);
		}else{
			logger.error((String)accessJson.get("errmsg"));
		}
		
	}
	//获取jsapiticket 通过http
	private String getJsapiTicketFromHttp(WXAccessToken wxAccessToken){
		String url = getWeixinJsTecketUrl(wxAccessToken);
		String result = httpUtil.getContentByUrl(url);
		logger.info("get weixin jsapiticket :" +result);
		JSONObject ticket = JSONObject.parseObject(result);
		String ticketStr = ticket.getString("ticket");
		if(ticketStr==null){
			logger.error((String)ticket.get("errmsg"));
			ticketStr = (String)wxErrorService.filter(ticket.getIntValue("errcode"),new WeiXinErrorService.ErrorCallBack() {
				public Object onErrorFinish() {
					return iNeedTicket();
				}
			});
		}else{
			wxConfig.setJsapiTicket(ticketStr);
		}
		return ticketStr;
	}
	
	/**
	 * 获取一个微信基本信息bean
	 * @return
	 */
	private WXbasic giveMeTheWXbasic(){
		WXbasic wxBasic = wxConfig.getWxBasic();
		return wxBasic;
	}
	/**
	 * 获取一个初始化token 没有token信息
	 * @return
	 */
	private WXAccessToken giveMeNoPreparedToken(){
		WXAccessToken wxAccessToken = new WXAccessToken();
		wxAccessToken.setwXbasic(giveMeTheWXbasic());
		wxAccessToken.setGetUrl(wxConfig.get("tokenUrl"));
		wxAccessToken.setGrantType(wxConfig.get("grant_type"));
		return wxAccessToken;
	}
	/**
	 * 拼装微信获取token的url
	 * @param wxAccessToken
	 * @return
	 */
	private String getWeixinAccessTokenUrl(WXAccessToken wxAccessToken){
		WXbasic wxBasic = wxAccessToken.getwXbasic();
		String url = wxAccessToken.getGetUrl()+"?grant_type="+wxAccessToken.getGrantType()+"&appid="+wxBasic.getAppId()+"&secret="+wxBasic.getAppSecret();
		logger.info("token_url:"+url);
		return url;
	}
	
	private String getWeixinJsTecketUrl(WXAccessToken wxAccessToken){
		String url = wxConfig.getJsapiTicketGetUrl()+"?access_token="+wxAccessToken.getAccessToken()+"&type=jsapi";
		logger.info("ticket_url:"+url);
		return url;
	}
	
	public WXIps iNeedWxIps(){
		WXAccessToken wxAccessToken = getCanUseToken();
		WXIps wxIps = new WXIps();
		wxIps.setGetUrl(wxConfig.get("ipUrl"));
		wxIps.setWxAccessToken(wxAccessToken);
		getWeiXinIps(wxIps);
		return wxIps;
	}
	
	private void getWeiXinIps(WXIps wxIps){
		String url = getWeixinIpsUrl(wxIps);
		String result = httpUtil.getContentByUrl(url);
		logger.info("get weixin ips:"+result);
		JSONObject ipsJson = JSONObject.parseObject(result);
		JSONArray ipsArray = ipsJson.getJSONArray("ip_list");
		if(ipsArray!=null){
			List<String> ips = new ArrayList<String>();
			for(int i=0;i<ipsArray.size();i++){
				String ip = ipsArray.getString(i);
				ips.add(ip);
			}
			wxIps.setIps(ips);
		}else{
			logger.error((String)ipsJson.get("errmsg"));
		}
	}
	
	public String getCanUseTicket(){
		String ticket = wxConfig.getJsapiTicket();
		if(ticket==null){
			ticket = iNeedTicket();
		}
		return ticket;
	}
	
	public WXAccessToken getCanUseToken(){
		WXAccessToken token = wxConfig.getAccessToken();
		if(token==null){
			token = iNeedAToken();
		}
		return token;
	}
	
	private String getWeixinIpsUrl(WXIps wxIps){
		String url = wxIps.getGetUrl()+"?access_token="+wxIps.getWxAccessToken().getAccessToken();
		return url;
	}
	
	public void reloadToken(){
		wxConfig.setAccessToken(null);
	}
	public void reloadTicket(){
		wxConfig.setJsapiTicket(null);
	}
}
