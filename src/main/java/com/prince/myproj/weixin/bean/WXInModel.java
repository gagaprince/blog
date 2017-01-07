package com.prince.myproj.weixin.bean;

public class WXInModel {
	private String signature;
	private String timestamp;
	private String nonce;
	private String echostr;
	private String inToken;
	public String getInToken() {
		return inToken;
	}
	public void setInToken(String inToken) {
		this.inToken = inToken;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getNonce() {
		return nonce;
	}
	public void setNonce(String nonce) {
		this.nonce = nonce;
	}
	public String getEchostr() {
		return echostr;
	}
	public void setEchostr(String echostr) {
		this.echostr = echostr;
	}
	
}
