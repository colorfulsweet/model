package com.system.service;

import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.system.model.Menu;
import com.system.model.User;
import com.system.model.Menu.Submenu;

@Service
public class SystemService extends HibernateDaoSupport implements ISystemService {
	
	@Autowired
	public void setSessionFactoryOverride(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	
	@Transactional
	@Override
	public User checkUser(User user) {
		
		String hql = "from User u "
				+ "join fetch u.role "
				+ "join fetch u.role.menus "
				+ "where u.username=? and u.password=? ";
		List<?> result = this.getSessionFactory().getCurrentSession().createQuery(hql)
				.setParameter(0, user.getUsername())
				.setParameter(1, DigestUtils.sha256Hex(user.getPassword())).list();
		if(!result.isEmpty()){
			user = (User) result.get(0);
			for(Menu menu : user.getRole().getMenus()){
				//移除子菜单项目当中的null
				menu.getChildrenMenu().remove(null);
			}
		} else {
			user = null;
		}
		return user;
	}

	@Transactional
	@Override
	public List<Submenu> getSubmenuList(Menu menu) {
		menu = (Menu) this.getSessionFactory().getCurrentSession().get(Menu.class, menu.getId());
		List<Submenu> submenuList = menu.getChildrenMenu();
		submenuList.remove(null);
		return submenuList;
	}

	@Transactional
	@Override
	public void saveSubmenu(Submenu submenu) {
		Menu menu = (Menu) this.getSessionFactory().getCurrentSession().get(Menu.class, submenu.getMenu().getId());
		if(menu != null){
			menu.getChildrenMenu().add(submenu);
		}
	}

}
