package com.prince.myproj.weixin.services;

import com.prince.myproj.weixin.bean.WXShareConfigModel;
import com.prince.myproj.weixin.config.WeiXinConfig;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class WeiXinShareService {
	@Autowired
	private WeiXinConfig wxConfig;
	@Autowired
	private WeiXinService wxService;
	
	private static final Logger logger =  Logger.getLogger(WeiXinShareService.class);
	public WXShareConfigModel getCanUseShareConfig(HttpServletRequest request){
		String url = getUrlFromReq(request);
//		logger.info("url:"+url);
		return getCanUseShareConfig(url);
	}

	public WXShareConfigModel getCanUseShareConfig(String url){
		WXShareConfigModel shareModel = new WXShareConfigModel();
		shareModel.setAppId(wxConfig.getWxBasic().getAppId());
		shareModel.setTimestamp((new Date().getTime()/1000)+"");
		shareModel.setNonceStr(getRandomString());
		shareModel.setUrl(url);
		shareModel.setTicket(wxService.getCanUseTicket());
		signat(shareModel);
		return shareModel;
	}
	
	private String getUrlFromReq(HttpServletRequest request){
		String uri = "http://"+wxConfig.getWxShareHost()+request.getRequestURI();
		String search = request.getQueryString();
		String url = "";
		if(search==null||"".equals(search)){
			url = uri;
		}else{
			url = uri+"?"+search;
		}
		return url;
	}
	
	private void signat(WXShareConfigModel shareModel){
		Map<String,String> params = new TreeMap<String, String>();
		params.put("jsapi_ticket", shareModel.getTicket());
		params.put("noncestr", shareModel.getNonceStr());
		params.put("timestamp", shareModel.getTimestamp());
		params.put("url", shareModel.getUrl());
		
		Set<String> keySet = params.keySet();

		Iterator<String> it = keySet.iterator();
		
		StringBuffer sb = new StringBuffer("");
		while(it.hasNext()){
			String key = it.next();
			sb.append(key+"="+params.get(key));
			sb.append("&");
		}
		String desStr = sb.deleteCharAt(sb.length()-1).toString();
		logger.info("排序后的字串："+desStr);
		desStr = DigestUtils.sha1Hex(desStr);
		logger.info("加密后的字串："+desStr);
		shareModel.setSignature(desStr);
	}
	
	private String getRandomString(){
		int randomValue = new Random().nextInt(1000);
		return DigestUtils.md5Hex(randomValue + "").substring(0,16);
	}
	
	public static void main(String[] args) {
		String str = DigestUtils.sha1Hex("jsapi_ticket=sM4AOVdWfPE4DxkXGEs8VIl4OE1KcC-kTCeDsjE5jqNAO0tvkWiC7_voDwtbODArkgnIhJz68fDbmGufCBhI2&noncestr=49c9adb18e44be07&timestamp=1439218259&url=http://gagalulu.wang/lovelulu/index");
		System.out.println(str);
	}
	
}
