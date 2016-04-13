package com.system.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.system.model.Role;
import com.system.service.dao.IHibernateDao;
import com.system.util.SystemMessage;

@Controller
@RequestMapping(value="/role")
public class RoleController {
	@Autowired
	private IHibernateDao<Object,String> hibernateDao;
	
	private static final String datePettern = "yyyy-MM-dd HH:mm:ss";
	private static SimpleDateFormat dateFormat;
	
	@RequestMapping(value="/save.html",produces="text/html;charset=utf-8")
	@ResponseBody
	public String saveRole(Role role){
		if(role.getCreateTime() == null){
			role.setCreateTime(new Date());
		}
		hibernateDao.saveOrUpdate(role);
		return SystemMessage.getMessage("success");
	}
	
	@RequestMapping(value="/delete.html",produces="text/html;charset=utf-8")
	@ResponseBody
	public String delRole(Role role){
		hibernateDao.del(role);
		return SystemMessage.getMessage("deleteSuccess");
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		if(dateFormat == null){
			dateFormat = new SimpleDateFormat(datePettern);
		}
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
}
