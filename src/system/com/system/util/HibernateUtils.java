package com.system.util;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
/**
 * Hibernate使用工具类(创建Session工厂)
 * @author Administrator
 *
 */
@Component
public class HibernateUtils {
	
	@Autowired(required=true)
	private SessionFactory factory;
	
	public Session getSession()
			throws HibernateException{
		Session session = factory.openSession();
		session.beginTransaction();
		return session;
	}
	public void closeSession(Session session){
		try{
			if(session != null && session.isOpen()){
				session.close();
			}
		} catch (HibernateException e){
			e.printStackTrace();
		}
	}
}