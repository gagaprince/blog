package com.prince.myproj.weixin.services.msgtool;

import com.prince.myproj.weixin.bean.msg.WXMsg;
import com.prince.myproj.weixin.bean.msg.WXTextMsg;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.queryparser.classic.ParseException;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Date;

public class TextMsgCenter implements MsgCenter {
	private String executeMsgType;

	public WXMsg executeMsg(Element root,WXMsg wxMsg) {
		if(executeMsgType==null || !executeMsgType.equals(wxMsg.getMsgType()))return null;

		String content = root.elementText("Content");
		WXTextMsg wxTextMsg = new WXTextMsg(wxMsg,content);
		return giveMeARepByMsg(wxTextMsg);
	}
	
	private WXMsg giveMeARepByMsg(WXTextMsg wxTextMsg){
		String toUser = wxTextMsg.getToUserName();
		wxTextMsg.setToUserName(wxTextMsg.getFromUserName());
		wxTextMsg.setFromUserName(toUser);
		wxTextMsg.setCreateTime(new Date().getTime()+"");
		String repContent = "";//iNeedRep(wxTextMsg.getContent());

		wxTextMsg.setContent(repContent);
		return wxTextMsg;
	}
	
//	private String iNeedRep(String key){
//		String result = "";
//		result = getAnswerContent(key);
//		if("".equals(result)){
//			result = getDuanziContent(key);
//		}
//		if("".equals(result)){
//			result = getDefaultAnswerContent();
//		}
//		return result;
//	}
	
//	private String getAnswerContent(String qu){
//		AnswerBean asbean = qaService.getAnswer(qu);
//		return asbean.getAnswer();
//	}
	
//	private String getDefaultAnswerContent(){
//		return qaService.getDefaultAnswer();
//	}
//
//	private String getDuanziContent(String key){
//		try {
//			return duanziService.IWantOneDuanzi(key);
//		} catch (CorruptIndexException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		return "";
//	}

	public String getMsgType(){
		return executeMsgType;
	}
	
	public void setMsgType(String executeMsgType){
		this.executeMsgType=executeMsgType;
	}

}
