package com.prince.myproj.util;

import java.io.UnsupportedEncodingException;

public class StringUtil {
	public static String getStringUTF8(String instr){
		String outStr = "";
		try {
			outStr = new String(instr.getBytes("iso-8859-1"),"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return outStr;
	}
	
	public static String getStringISO88591(String instr){
		String outStr = "";
		try {
			outStr = new String(instr.getBytes("utf-8"),"iso-8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return outStr;
	}
}
