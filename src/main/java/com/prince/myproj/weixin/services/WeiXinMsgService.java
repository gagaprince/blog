package com.prince.myproj.weixin.services;

import com.prince.myproj.util.StringUtil;
import com.prince.myproj.weixin.services.msgtool.MsgCenterFactory;
import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeiXinMsgService {
	private static final Logger logger =  Logger.getLogger(WeiXinMsgService.class);
	@Autowired
	private MsgCenterFactory msgCenterFactory;
	
	public String doExcuteMsg(String xml){
		if(xml==null)return "";
//		xml = StringUtil.getStringUTF8(xml);
		logger.info("接收到的xml:"+xml);
		return StringUtil.getStringISO88591(dispatch(xml));
	}
	
	private String dispatch(String xml){
		try {
			String rep = msgCenterFactory.executeMsg(xml);
			return rep;
		} catch (DocumentException e) {
			e.printStackTrace();
			logger.error("读取xml内容出错：-----"+xml);
		}
		
		return "";
	}
	
}
