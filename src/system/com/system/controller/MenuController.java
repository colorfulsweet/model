package com.system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.system.bean.Menu;
import com.system.bean.Menu.Submenu;
import com.system.service.ISystemService;
import com.system.service.dao.IHibernateDao;
import com.system.util.StatusText;

@Controller
@RequestMapping(value="/menu")
public class MenuController {
	
	@Autowired
	private IHibernateDao<Object, String> hibernateDao;
	
	@Autowired
	private ISystemService systemService;
	
	@RequestMapping(value="/save.html")
	@ResponseBody
	public String saveOrUpdate(Menu menu){
		hibernateDao.saveOrUpdate(menu);
		return StatusText.SUCCESS;
	}
	
	@RequestMapping(value="/delete.html")
	@ResponseBody
	public String delMenu(Menu menu){
		hibernateDao.del(menu, true);
		return StatusText.SUCCESS;
	}
	
	@RequestMapping(value="/submenuList.html")
	public String openSubmenuList(Menu menu,Model model){
		List<Submenu> submenuList = systemService.getSubmenuList(menu);
		model.addAttribute("submenuList", submenuList);
		model.addAttribute("menu", menu);
		return "WEB-INF/views/menu/submenu.jsp";
	}
	
	@RequestMapping(value="/saveSubmenu.html")
	@ResponseBody
	public String saveSubmenu(Submenu submenu){
		systemService.saveSubmenu(submenu);
		return StatusText.SUCCESS;
	}
	
	@RequestMapping(value="/delSubmenu.html")
	@ResponseBody
	public String delSubmenu(Submenu submenu){
		hibernateDao.del(submenu);
		return StatusText.SUCCESS;
	}
}
