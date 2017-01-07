package com.prince.myproj.weixin.services;

import com.prince.myproj.weixin.bean.WXInModel;
import com.prince.myproj.weixin.config.WeiXinConfig;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class WeiXinInService {
	private static final Logger logger =  Logger.getLogger(WeiXinInService.class);
	
	@Autowired
	private WeiXinConfig wxConfig;
	
	public String doExcuteInWeiXin(HttpServletRequest request){
		WXInModel wxInModel = parseWxInModel(request);
		boolean isWXserver = checkSignature(wxInModel);
		if(isWXserver){
			return wxInModel.getEchostr();
		}
		return "";
	}
	
	private static boolean checkSignature(WXInModel wxInModel){
		List<String> signList = new ArrayList<String>();
		signList.add(wxInModel.getTimestamp());
		signList.add(wxInModel.getNonce());
		signList.add(wxInModel.getInToken());
		Collections.sort(signList);
		StringBuffer sb = new StringBuffer("");
		for(int i=0;i<signList.size();i++){
			sb.append(signList.get(i));
		}
		String desStr = sb.toString();
		desStr = DigestUtils.sha1Hex(desStr);
		return desStr.equals(wxInModel.getSignature());
	}
	
	private WXInModel parseWxInModel(HttpServletRequest request){
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");
		String inToken = wxConfig.get("intoken");
		logger.info("signature:"+signature);
		logger.info("timestamp:"+timestamp);
		logger.info("nonce:"+nonce);
		logger.info("echostr:"+echostr);
		logger.info("inToken:"+inToken);
		WXInModel wxInModel = new WXInModel();
		wxInModel.setSignature(signature);
		wxInModel.setEchostr(echostr);
		wxInModel.setInToken(inToken);
		wxInModel.setNonce(nonce);
		wxInModel.setTimestamp(timestamp);
		return wxInModel;
	}
	
	public static void main(String[] args) {
		WXInModel wxInModel = new WXInModel();
		wxInModel.setSignature("");
		wxInModel.setEchostr("");
		wxInModel.setInToken("gagaprince");
		wxInModel.setNonce("z23456");
		wxInModel.setTimestamp("iojlkjlskjdflkjsdfj");
		checkSignature(wxInModel);
	}
}
