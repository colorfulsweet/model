package com.system.controller;

import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.system.bean.User;
import com.system.service.IHibernateDao;

@Controller
@RequestMapping(value="/user")
public class UserController {
	@Autowired
	private IHibernateDao<User,String> hibernateDao;
	
	@RequestMapping(value="/save.html")
	public @ResponseBody
	String saveOrUpdateUser(User user){
		user.setPassword(DigestUtils.sha256Hex(user.getPassword()));
		user.setCreateTime(new Date());
		user.setStatus(true);
		hibernateDao.saveOrUpdate(user);
		return "success";
	}
}
