package com.system.util;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;

public class CharactorUtils {
	private static Logger log = Logger.getLogger(CharactorUtils.class);
	private static final String targetEncoding = "UTF-8";
	private static final String[] encodeNameList = {"UTF-8", "ISO8859-1", "GB2312", "GBK"};
	public static void charactorHandle(Map<String,Object> map){
		if(map.containsKey("_")) {
			map.remove("_");
		}
		Set<Entry<String, Object>> entries = map.entrySet();
		for(Entry<String, Object> entry : entries) {
			Object value = entry.getValue();
			if(value instanceof String) {
				try {
					String paramEncoding = getEncoding((String)value);
					entry.setValue(new String(((String)value).getBytes(paramEncoding), targetEncoding));
				} catch (UnsupportedEncodingException e) {
					log.error("编码类型转换错误!", e);
				}
			}
		}
	}
	
	public static String getEncoding(String str) throws UnsupportedEncodingException{
		for(String encodeName : encodeNameList){
			if(str.equals(new String(str.getBytes(encodeName),encodeName))){
				return encodeName;
			}
		}
		return null;
	}
}
