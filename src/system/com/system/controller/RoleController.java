package com.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.system.bean.Role;
import com.system.service.dao.IHibernateDao;
import com.system.util.StatusText;

@Controller
@RequestMapping(value="/role")
public class RoleController {
	@Autowired
	private IHibernateDao<Object,String> hibernateDao;
	
	@RequestMapping(value="/save.html")
	@ResponseBody
	public String saveRole(Role role){
		hibernateDao.saveOrUpdate(role);
		return StatusText.SUCCESS;
	}
	
	@RequestMapping(value="/delete.html")
	@ResponseBody
	public String delRole(Role role){
		hibernateDao.del(role);
		return StatusText.SUCCESS;
	}
}
