package com.system.service;

import com.system.bean.User;

public interface ISystemService {
	/**
	 * 验证用户名和密码
	 * @param user
	 * @return
	 */
	public User checkUser(User user);
}
