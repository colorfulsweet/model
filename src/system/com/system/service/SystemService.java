package com.system.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.system.model.Menu;
import com.system.model.Menu.Submenu;
import com.system.model.User;
import com.system.service.dao.IHibernateDao;
import com.system.util.DataCache;

@Service
public class SystemService extends HibernateDaoSupport implements ISystemService {
	
	@Autowired
	public void setSessionFactoryOverride(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	@Autowired
	private IHibernateDao<User,String> hibernateDao;
	
	@Autowired
	private DataCache dataCache;
	
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
	
	@Override
	public int delUsers(String[] ids) {
		String hql = "delete User u where u.id in (:ids)";
		int result = hibernateDao.excuteUpdate(hql,"ids",Arrays.asList(ids));
		dataCache.removeAllObjects(User.class, ids);
		return result;
	}
	
	@Override
	public void outputIcon(String userId, OutputStream output) throws IOException, SQLException {
		String hql = "select icon from User u where u.id=?";
		List<?> result = hibernateDao.excuteQuery(hql, userId);
		Blob icon = null;
		if(result.isEmpty() || (icon=(Blob)result.get(0)) == null){
			return;
		}
		byte[] buf = new byte[2048];
		InputStream input = icon.getBinaryStream();
		BufferedInputStream bufferInput = new BufferedInputStream(input);
		BufferedOutputStream bufferOutput = new BufferedOutputStream(output);
		int len = 0;
		while((len=bufferInput.read(buf)) != -1){
			bufferOutput.write(buf,0,len);
		}
		bufferOutput.flush();
		bufferOutput.close();
		bufferInput.close();
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
