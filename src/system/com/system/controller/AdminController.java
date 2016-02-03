package com.system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.system.bean.Dict;
import com.system.bean.Role;
import com.system.bean.User;
import com.system.service.IHibernateDao;
import com.system.tags.Page;

@Controller
@RequestMapping(value="/admin")
public class AdminController {
	
	@Autowired
	private IHibernateDao<Object,String> hibernateDao;
	
	@RequestMapping(value="/menuManage.html")
	public String menuManage(Model model){
		
		return "/WEB-INF/views/admin/menu_manage.jsp";
	}
	
	@RequestMapping(value="/roleManage.html")
	public String roleManage(Model model,Page page){
		page.setLinkUrl("admin/roleManage.html");
		List<?> roleList = hibernateDao.dir(Role.class,page);
		model.addAttribute("roles", roleList);
		model.addAttribute("page", page);
		return "/WEB-INF/views/admin/role_manage.jsp";
	}
	
	@RequestMapping(value="/userManage.html")
	public String userManage(Model model,Page page){
		page.setLinkUrl("admin/userManage.html");
		List<?> userList = hibernateDao.dir(User.class,page);
		model.addAttribute("users", userList);
		model.addAttribute("page", page);
		return "/WEB-INF/views/admin/user_manage.jsp";
	}
	@RequestMapping(value="/dictManage.html")
	public String dictManage(Model model,Page page){
		page.setLinkUrl("admin/dictManage.html");
		List<?> dictList = hibernateDao.dir(Dict.class,page);
		model.addAttribute("dicts",dictList);
		model.addAttribute("page", page);
		return "/WEB-INF/views/admin/dict_manage.jsp";
	}
	
}
