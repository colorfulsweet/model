package com.system.service;

import java.util.List;

import com.system.model.Menu;
import com.system.model.User;
import com.system.model.Menu.Submenu;

public interface ISystemService {
	/**
	 * 验证用户名和密码
	 * @param user
	 * @return
	 */
	public User checkUser(User user);
	/**
	 * 批量删除用户
	 * @param ids 需要删除用户的ID构成的数组
	 * @return 受影响的行数 
	 */
	public int delUsers(String[] ids);
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
