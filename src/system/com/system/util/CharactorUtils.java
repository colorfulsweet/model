package com.system.util;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class CharactorUtils {
	private static final String requestEncoding = "ISO8859-1";
	private static final String targetEncoding = "UTF-8";
	
	public static void charactorHandle(Map<String,Object> map){
		if(map.containsKey("_")) {
			map.remove("_");
		}
		Set<Entry<String, Object>> entries = map.entrySet();
		for(Entry<String, Object> entry : entries) {
			Object value = entry.getValue();
			if(value instanceof String) {
				try {
					entry.setValue(new String(((String)value).getBytes(requestEncoding), targetEncoding));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
