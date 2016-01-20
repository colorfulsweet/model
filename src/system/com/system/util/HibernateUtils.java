package com.system.util;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
/**
 * Hibernate使用工具类(创建Session工厂)
 * @author Administrator
 *
 */
public class HibernateUtils {
	
	private static SessionFactory factory;
	private static Configuration cfg;
	
	static {
		factory = (SessionFactory) SpringUtils.getBean("sessionFactory");
	}
	
	public static Session getSession()
			throws HibernateException{
		Session session = factory.openSession();
		session.beginTransaction();
		return session;
	}
	public static void closeSession(Session session){
		try{
			if(session != null && session.isOpen()){
				session.close();
			}
		} catch (HibernateException e){
			e.printStackTrace();
		}
	}
	public static SessionFactory getFactory(){
		return factory;
	}
	public static Configuration getConfiguration(){
		return cfg;
	}
}