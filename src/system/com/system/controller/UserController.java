package com.system.controller;

import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.system.bean.User;
import com.system.service.IHibernateDao;
import com.system.util.StatusText;

@Controller
@RequestMapping(value="/user")
public class UserController {
	@Autowired
	private IHibernateDao<User,String> hibernateDao;
	/**
	 * 创建/修改 用户
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/save.html")
	@ResponseBody
	public String saveOrUpdateUser(User user){
		user.setPassword(DigestUtils.sha256Hex(user.getPassword()));
		user.setCreateTime(new Date());
		user.setStatus(true);
		hibernateDao.saveOrUpdate(user);
		return StatusText.SUCCESS;
	}
	/**
	 * 删除用户
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/delete.html")
	@ResponseBody
	public String delUser(User user){
		hibernateDao.del(user);
		return StatusText.SUCCESS;
	}
}
