package com.system.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringUtils implements ApplicationContextAware {
	private static ApplicationContext context;
	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		SpringUtils.context = arg0;
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends Object> T getBean(String beanId){
		return (T) context.getBean(beanId);
	}
	
	public static <T extends Object> T getBean(Class<T> clz){
		return context.getBean(clz);
	}
}
