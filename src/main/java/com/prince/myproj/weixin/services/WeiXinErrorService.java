package com.prince.myproj.weixin.services;

import com.prince.myproj.weixin.config.WeiXinConfig;
import com.prince.myproj.weixin.enums.WeiXinError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeiXinErrorService {
	@Autowired
	private WeiXinConfig wxConfig;
	public Object filter(int errorCode,ErrorCallBack callBack){
		Object returnO = null;
		switch (errorCode) {
			case WeiXinError.TOKEN_EXPIRED:
				returnO = doWhenTokenExpired(callBack);
				break;
		}
		return returnO;
	}
	
	private Object doWhenTokenExpired(ErrorCallBack callBack){
		wxConfig.setAccessToken(null);
		return callBack.onErrorFinish();
	}
	
	public interface ErrorCallBack{
		public Object onErrorFinish();
	}
}
