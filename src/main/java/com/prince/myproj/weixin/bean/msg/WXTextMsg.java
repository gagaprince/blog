package com.prince.myproj.weixin.bean.msg;

public class WXTextMsg extends WXMsg{
	private String content;
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public WXTextMsg(String toUserName, String fromUserName, String createTime,
			String msgId, String msgType, String content) {
		super(toUserName, fromUserName, createTime, msgId, msgType);
		this.content = content;
	}
	
	public WXTextMsg(WXMsg wxMsg,String content){
		super(wxMsg.getToUserName(), wxMsg.getFromUserName(), wxMsg.getCreateTime(), wxMsg.getMsgId(), wxMsg.getMsgType());
		this.content = content;
	}
	
	public String toXmlString(){
		String superXml = super.toXmlString();
		
		StringBuffer sb = new StringBuffer("");
		sb.append("<Content><![CDATA["+this.content+"]]></Content>");
		
		return superXml.replace("${outmsg}", sb.toString());
	}
	
}
