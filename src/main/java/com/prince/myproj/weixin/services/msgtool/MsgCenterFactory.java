package com.prince.myproj.weixin.services.msgtool;

import com.prince.myproj.weixin.bean.msg.WXMsg;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.StringReader;
import java.util.List;

public class MsgCenterFactory {
	private static final Logger logger =  Logger.getLogger(MsgCenterFactory.class);
	private List<MsgCenter> msgCenterList;
	
	public String executeMsg(String xml) throws DocumentException{
		if(msgCenterList==null)return "";
		
		SAXReader reader = new SAXReader();
		Document document;
		document = reader.read(new StringReader(xml));
		Element root = document.getRootElement();
		
		String msgType = root.elementText("MsgType");
		String toUserName = root.elementText("ToUserName");
		String fromUserName = root.elementText("FromUserName");
		String createTime = root.elementText("CreateTime");
		String msgId = root.elementText("MsgId");
	
		logger.info("xml type:"+msgType);
		logger.info("toUserName type:"+toUserName);
		logger.info("fromUserName type:"+fromUserName);
		logger.info("createTime type:"+createTime);
		logger.info("msgId type:"+msgId);
		
		WXMsg wxMsgGet = new WXMsg( toUserName,  fromUserName,  createTime,
				 msgId,  msgType);
		int size = msgCenterList.size();
		WXMsg wxMsg = null;
		for(int i=0;i<size;i++){
			MsgCenter msgCenter = msgCenterList.get(i);
			wxMsg = msgCenter.executeMsg(root,wxMsgGet);
			if(wxMsg!=null)break;
		}
		return wxMsg!=null?wxMsg.toXmlString():"";
	}

	public List<MsgCenter> getMsgCenterList() {
		return msgCenterList;
	}

	public void setMsgCenterList(List<MsgCenter> msgCenterList) {
		this.msgCenterList = msgCenterList;
	}
	
	
	
}
