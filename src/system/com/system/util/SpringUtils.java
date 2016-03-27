package com.system.util;

import javax.servlet.ServletContext;
import javax.servlet.jsp.PageContext;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.WebApplicationContext;

public class SpringUtils implements ApplicationContextAware {
	private static ApplicationContext context;
	private static WebApplicationContext attr;
	private static String springMVCName = "org.springframework.web.servlet.FrameworkServlet.CONTEXT.spring_mvc";
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
	
	public static <T extends Object> T getSpringMVCBean(PageContext pageContext,String beanId){
		ServletContext context = pageContext.getServletContext();
		return getSpringMVCBean(context, beanId);
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends Object> T getSpringMVCBean(ServletContext context,String beanId){
		if(attr == null){
			attr = (WebApplicationContext) context.getAttribute(springMVCName);
		}
		return (T) attr.getBean(beanId);
	}
}
