package com.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.system.bean.Menu;
import com.system.service.IHibernateDao;
import com.system.util.StatusText;

@Controller
@RequestMapping(value="/menu")
public class MenuController {
	
	@Autowired
	private IHibernateDao<Menu, String> hibernateDao;
	
	@RequestMapping(value="/save.html")
	public String saveOrUpdate(Menu menu){
		hibernateDao.saveOrUpdate(menu);
		return StatusText.SUCCESS;
	}
	
	@RequestMapping(value="/delete.html")
	public String delMenu(Menu menu){
		hibernateDao.del(menu, true);
		return StatusText.SUCCESS;
	}
}
