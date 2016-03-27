package com.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.system.bean.Role;
import com.system.service.dao.IHibernateDao;
import com.system.util.SystemMessage;

@Controller
@RequestMapping(value="/role")
public class RoleController {
	@Autowired
	private IHibernateDao<Object,String> hibernateDao;
	
	@RequestMapping(value="/save.html",produces="text/html;charset=utf-8")
	@ResponseBody
	public String saveRole(Role role){
		hibernateDao.saveOrUpdate(role);
		return SystemMessage.getMessage("success");
	}
	
	@RequestMapping(value="/delete.html",produces="text/html;charset=utf-8")
	@ResponseBody
	public String delRole(Role role){
		hibernateDao.del(role);
		return SystemMessage.getMessage("deleteSuccess");
	}
}
