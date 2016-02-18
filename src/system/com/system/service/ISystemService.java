package com.system.service;

import java.util.List;

import com.system.bean.Menu;
import com.system.bean.Menu.Submenu;
import com.system.bean.User;

public interface ISystemService {
	/**
	 * 验证用户名和密码
	 * @param user
	 * @return
	 */
	public User checkUser(User user);
	/**
	 * 获取某个菜单对应的子菜单列表
	 * @param menu
	 * @return 子菜单的List集合
	 */
	public List<Submenu> getSubmenuList(Menu menu);
	/**
	 * 保存子菜单
	 * @param submenu
	 */
	public void saveSubmenu(Submenu submenu);
}
