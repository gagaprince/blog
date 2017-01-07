package com.prince.myproj.weixin.bean.msg;

public class WXMsg {
	private String toUserName;
	private String fromUserName;
	private String createTime;
	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	private String msgId;
	private String msgType;

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public WXMsg(String toUserName, String fromUserName, String createTime,
			String msgId, String msgType) {
		super();
		this.toUserName = toUserName;
		this.fromUserName = fromUserName;
		this.createTime = createTime;
		this.msgId = msgId;
		this.msgType = msgType;
	}
	
	public String toXmlString(){
		StringBuffer sb = new StringBuffer("");
		sb.append("<xml>");
		sb.append("<ToUserName><![CDATA["+this.toUserName+"]]></ToUserName>");
		sb.append("<FromUserName><![CDATA["+this.fromUserName+"]]></FromUserName>");
		sb.append("<CreateTime>"+this.createTime+"</CreateTime>");
		sb.append("${outmsg}");
		sb.append("<MsgType><![CDATA["+this.msgType+"]]></MsgType>");
		sb.append("</xml>");
		return sb.toString();
	}
}
