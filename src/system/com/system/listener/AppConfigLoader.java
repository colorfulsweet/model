package com.system.listener;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

public class AppConfigLoader implements ServletContextListener {
	private static Logger log = Logger.getLogger(AppConfigLoader.class);
	private Properties props;
	
	@Override
	public void contextInitialized(ServletContextEvent event) {
		ServletContext context = event.getServletContext();
		props = new Properties();
		InputStream input = AppConfigLoader.class.getResourceAsStream("/appConfig.properties");
		try {
			props.load(input);
			Set<Entry<Object, Object>> entries = props.entrySet();
			log.info("===加载应用程序配置项===");
			for(Entry<Object, Object>entry : entries) {
				log.info(entry.getKey() + " : " + entry.getValue());
				context.setAttribute(entry.getKey().toString(), entry.getValue());
			}
		} catch (IOException e) {
			log.error("加载配置文件出错!",e);
		}
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		//TODO 服务关闭时的一些处理
	}
}
