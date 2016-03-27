package com.system.util;

import java.util.Properties;


public class SystemMessage {
	private static SystemMessage instance;
	private Properties msgs;
	
	public static String getMessage(String msgName){
		if(instance == null){
			instance = SpringUtils.getBean(SystemMessage.class);
		}
		return instance.getMsgs().getProperty(msgName);
	}
	
	public Properties getMsgs() {
		return msgs;
	}
	public void setMsgs(Properties msgs) {
		this.msgs = msgs;
	}
}
