package com.system.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringMVCUtils implements ApplicationContextAware {
	private static ApplicationContext context;
	public static final String springMVCName = "org.springframework.web.servlet.FrameworkServlet.CONTEXT.spring_mvc";
	
	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		SpringMVCUtils.context = arg0;
	}

	@SuppressWarnings("unchecked")
	public static <T extends Object> T getSpringMVCBean(String beanId){
		return (T) context.getBean(beanId);
	}
	
	public static <T extends Object> T getSpringMVCBean(Class<T> clz){
		return context.getBean(clz);
	}

}
