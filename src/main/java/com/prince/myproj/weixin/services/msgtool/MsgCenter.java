package com.prince.myproj.weixin.services.msgtool;

import com.prince.myproj.weixin.bean.msg.WXMsg;
import org.dom4j.Element;



public interface MsgCenter {
	public WXMsg executeMsg(Element root, WXMsg wxMsg);
	public String getMsgType();
}
