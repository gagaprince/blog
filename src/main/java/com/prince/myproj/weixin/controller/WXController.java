package com.prince.myproj.weixin.controller;

import com.alibaba.fastjson.JSON;
import com.prince.myproj.weixin.bean.WXAccessToken;
import com.prince.myproj.weixin.bean.WXIps;
import com.prince.myproj.weixin.bean.WXShareConfigModel;
import com.prince.myproj.weixin.services.WeiXinInService;
import com.prince.myproj.weixin.services.WeiXinMsgService;
import com.prince.myproj.weixin.services.WeiXinService;
import com.prince.myproj.weixin.services.WeiXinShareService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 用来测试和刷新微信的操作 返回结果
 * @author zidong.wang
 *
 */


@Controller
@RequestMapping("/blog/wx")
public class WXController {
	@Autowired
	private WeiXinService wxService;
	@Autowired
	private WeiXinInService wxInService;
	@Autowired
	private WeiXinMsgService wxMsgService;
	@Autowired
	private WeiXinShareService wxShareService;
	
	private static final Logger logger =  Logger.getLogger(WXController.class);

	private Thread self=null;

	@RequestMapping(value = "/index")
	@ResponseBody
	public String wxCall(HttpServletRequest request){
		String rspStr = "";
		String method = request.getMethod();
		logger.info(method);
		if("GET".equals(method)){
			logger.info("新的号码要接入了");
			//微信验证开发者
			rspStr = wxInService.doExcuteInWeiXin(request);
		}else if("POST".equals(method)){
			String xml=null;
			try {
				xml = getRequestBody(request);
			} catch (IOException e) {
				e.printStackTrace();
			}
			//接收微信消息
			rspStr = wxMsgService.doExcuteMsg(xml);
		}
		return rspStr;
	}
	
	@RequestMapping(value = "/getToken", method = RequestMethod.GET)
	@ResponseBody
	public String getWeiXinAccessToken(HttpServletRequest request, Model model){
		WXAccessToken wxAccessToken = wxService.getCanUseToken();
		if(self==null){
			initSelfThread();
		}
		return wxAccessToken.getAccessToken();
	}
	@RequestMapping(value = "/reloadToken", method = RequestMethod.GET)
	@ResponseBody
	public String reloadWeiXinAccessToken(){
		wxService.reloadToken();
		WXAccessToken wxAccessToken = wxService.getCanUseToken();
		return wxAccessToken.getAccessToken();
	}
	
	@RequestMapping(value = "/getTicket", method = RequestMethod.GET)
	@ResponseBody
	public String getJsapiTicket(HttpServletRequest request){
		String ticket = wxService.getCanUseTicket();
		return ticket;
	}
	
	@RequestMapping(value = "/reloadTicket", method = RequestMethod.GET)
	@ResponseBody
	public String reloadJsapiTicket(){
		wxService.reloadTicket();
		String ticket = wxService.getCanUseTicket();
		return ticket;
	}
	
	
	@RequestMapping(value = "/getWeiXinIps", method = RequestMethod.GET)
	@ResponseBody
	public String getWeiXinIps(HttpServletRequest request, Model model){
		WXIps wxIps = wxService.iNeedWxIps();
		return JSON.toJSONString(wxIps);
	}
	@RequestMapping(value = "/getShareBisic")
	@ResponseBody
	public String getShareBisicMsg(HttpServletRequest request){
		String url = request.getParameter("location");
		logger.info(url);
		WXShareConfigModel wxShareConfigModel = wxShareService.getCanUseShareConfig(url);
		return JSON.toJSONString(wxShareConfigModel);
	}

	@RequestMapping(value = "/loginForOpenId")
	@ResponseBody
	public String getOpenIdByCode(HttpServletRequest request){
		String code = request.getParameter("code");
		if(code==null){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("errorMsg","code 不能为空");
			map.put("code",11);
			return JSON.toJSONString(map);
		}else{
			return wxService.getOpenIdByCode(code);
		}
	}


	private String getRequestBody(HttpServletRequest request) throws IOException {
		InputStreamReader isr = new InputStreamReader(request.getInputStream());
		BufferedReader br = new BufferedReader(isr);
		StringBuffer sb = new StringBuffer("");
		String line = null;
		while((line=br.readLine())!=null){
			sb.append(line);
		}
		br.close();
		return sb.toString();
	}
	private void initSelfThread(){
		final WXController _this = this;
		self = new Thread(new Runnable() {
			public void run() {
				while(true){
					try {
						Thread.sleep(7100000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					_this.reloadJsapiTicket();
					_this.reloadWeiXinAccessToken();
				}
			}
		});
		self.start();
	}
}
