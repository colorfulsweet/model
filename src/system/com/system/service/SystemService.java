package com.system.service;

import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.system.bean.Menu;
import com.system.bean.User;
import com.system.util.HibernateUtils;

@Service
public class SystemService implements ISystemService {
	
	@Autowired
	private HibernateUtils hibernateUtils;
	
	@Override
	public User checkUser(User user) {
		Session session = null;
		List<?> result = null;
		try{
			session = hibernateUtils.getSession();
			String hql = "from User u "
					+ "join fetch u.role "
					+ "join fetch u.role.menus "
					+ "where u.username=? and u.password=? ";
			result = session.createQuery(hql)
					.setParameter(0, user.getUsername())
					.setParameter(1, DigestUtils.sha256Hex(user.getPassword())).list();
			if(!result.isEmpty()){
				user = (User) result.get(0);
				for(Menu menu : user.getRole().getMenus()){
					//溢出子菜单项目当中的null
					menu.getChildrenMenu().remove(null);
				}
			} else {
				user = null;
			}
			session.getTransaction().commit();
		} catch (HibernateException e){
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally{
			hibernateUtils.closeSession(session);
		}
		return user;
	}

}
