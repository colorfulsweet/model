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
import com.system.model.Role;
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
	private IHibernateDao<?,String> hibernateDao;
	
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
		int result = hibernateDao.excuteUpdate(hql,new String[]{"ids"},Arrays.asList(ids));
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
		InputStream input = icon.getBinaryStream();
		try(BufferedInputStream bufferInput = new BufferedInputStream(input);
			BufferedOutputStream bufferOutput = new BufferedOutputStream(output)){
			//try-with-resources可以对多个资源进行管理
			byte[] buf = new byte[2048];
			int len = 0;
			while((len=bufferInput.read(buf)) != -1){
				bufferOutput.write(buf,0,len);
			}
			bufferOutput.flush();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Role> getRoleList(User user){
		List<Role> roleList = (List<Role>) hibernateDao.dir(Role.class, null);
		String hql = "from User u join fetch u.role where u.id=?";
		List<User> result = (List<User>) hibernateDao.excuteQuery(hql, user.getId());
		if(!result.isEmpty()) {
			user.setRole(result.get(0).getRole());
		}
		return roleList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Menu> getMenuList(Role role) {
		String hql = "from Menu m left join fetch m.roles";
		List<Menu> result = (List<Menu>) hibernateDao.excuteQuery(hql);
		return result;
	}
	
	@Transactional
	@Override
	public void saveUserRole(String userId, String roleId) {
		User user = (User) this.getSessionFactory().getCurrentSession().get(User.class, userId);
		Role role = new Role();
		role.setId(roleId);
		user.setRole(role);
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
