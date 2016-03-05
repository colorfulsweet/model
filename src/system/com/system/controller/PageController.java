package com.system.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.system.bean.Dict;
import com.system.bean.Menu;
import com.system.bean.Role;
import com.system.bean.User;
import com.system.service.IHibernateDao;
import com.system.service.ISystemService;

@Controller
@RequestMapping(value="/page")
public class PageController {
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private ISystemService systemService;
	
	@Autowired
	private IHibernateDao<Object, String> hibernateDao;
	
	@RequestMapping(value="/index.html")
	public String toIndex() {
		return "WEB-INF/views/explorer.jsp";
	}
	@RequestMapping(value="/welcome.html")
	public String toWelcome() {
		return "WEB-INF/views/welcome.jsp";
	}
	/**
	 * 登陆请求
	 * @param user 用户实体对象
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/login.html",method=RequestMethod.POST)
	public String login(User user,Model model){
		user = systemService.checkUser(user);
		if(user != null && user.getStatus()){
			session.setAttribute("user", user);
			session.setAttribute("menuList", user.getRole().getMenus());
			return "redirect:../index.jsp";
		} else {
			if(user == null){
				model.addAttribute("info", "用户名/密码错误");
			} else {
				model.addAttribute("info", "该用户已被禁用");
			}
			return "WEB-INF/views/login.jsp";
		}
	}
	/**
	 * 注销请求
	 * @return 重定向至首页
	 */
	@RequestMapping(value="/logout.html")
	public String logout(){
		session.invalidate();
		return "redirect:../index.jsp";
	}
	/**
	 * 新增/编辑用户请求
	 * @return 新增用户页面
	 */
	@RequestMapping(value="/addOrUpdateUser.html")
	public String addOrUpdateUser(User user,Model model){
		if(user.getId() != null){
			user = (User) hibernateDao.get(User.class, user.getId());
			model.addAttribute("user_", user);
		}
		return "WEB-INF/views/user/add_user.jsp";
	}
	
	@RequestMapping(value="/addOrUpdateDict.html")
	public String addOrUpdateDict(Dict dict,Model model){
		if(dict.getId() != null){
			dict = (Dict) hibernateDao.get(Dict.class, dict.getId());
			model.addAttribute("dict", dict);
		}
		return "WEB-INF/views/dict/add_dict.jsp";
	}
	
	@RequestMapping(value="/addOrUpdateMenu.html")
	public String addOrUpdateMenu(Menu menu,Model model){
		if(menu.getId() != null){
			menu = (Menu) hibernateDao.get(Menu.class,menu.getId());
			model.addAttribute("menu", menu);
		}
		return "WEB-INF/views/menu/add_menu.jsp";
	}
	
	@RequestMapping(value="/addOrUpdateRole.html")
	public String addOrUpdateRole(Role role,Model model){
		if(role.getId() != null){
			role = (Role) hibernateDao.get(Role.class,role.getId());
			model.addAttribute("role", role);
		}
		return "WEB-INF/views/role/add_role.jsp";
	}
}
