package com.system.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.system.model.Menu;
import com.system.model.Role;
import com.system.model.User;
import com.system.service.ISystemService;
import com.system.service.dao.IHibernateDao;
import com.system.util.SystemMessage;

@Controller
@RequestMapping(value="/role")
public class RoleController {
	@Autowired
	private IHibernateDao<Object,String> hibernateDao;
	
	@Autowired
	private ISystemService systemService;
	
	private final String datePettern = "yyyy-MM-dd HH:mm:ss";
	private SimpleDateFormat dateFormat;
	
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
	
	@RequestMapping(value="/roleList.html")
	public String getRoleList(User user, Model model){
		List<Role> roleList = systemService.getRoleList(user);
		model.addAttribute("user", user);
		model.addAttribute("roleList", roleList);
		return "WEB-INF/views/user/user_role.jsp";
	}
	
	@RequestMapping(value="/saveUserRole.html",produces="text/html;charset=utf-8")
	@ResponseBody
	public String saveUserRole(String userId, String roleId) {
		systemService.saveUserRole(userId, roleId);
		return SystemMessage.getMessage("success");
	}
	
	@RequestMapping(value="/saveRoleMenu.html",produces="text/html;charset=utf-8")
	@ResponseBody
	public String saveRoleMenu(String roleId, String[] menuId) {
		Role role = (Role) hibernateDao.get(Role.class, roleId);
		Set<Menu> menus = new HashSet<Menu>();
		for(String id : menuId) {
			Menu menu = new Menu();
			menu.setId(id);
			menus.add(menu);
		}
		role.setMenus(menus);
		hibernateDao.update(role);
		return SystemMessage.getMessage("success");
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		if(dateFormat == null){
			dateFormat = new SimpleDateFormat(datePettern);
		}
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
}
