package com.system.controller;

import java.util.Arrays;
import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.system.bean.User;
import com.system.service.IHibernateDao;
import com.system.util.ReflectUtils;
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
	public String saveUser(User user){
		user.setPassword(DigestUtils.sha256Hex(user.getPassword()));
		if(user!=null && user.getId()!=null && user.getId().length()!=0){
			//修改
			User destUser = hibernateDao.get(User.class, user.getId());
			try {
				ReflectUtils.transferFields(user, destUser);
			} catch (Exception e) {
				e.printStackTrace();
				return StatusText.ERROR;
			}
			hibernateDao.update(destUser);
		} else {
			//新增
			user.setCreateTime(new Date());
			hibernateDao.save(user);
		}
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
	
	@RequestMapping(value="/deleteUsers.html")
	@ResponseBody
	public String delUsers(@RequestParam(value="userId")String[] ids){
		if(ids.length == 0){
			return StatusText.ERROR;
		}
		StringBuilder hql = new StringBuilder("delete User u where u.id in (");
		for(int i=0 ; i<ids.length ; ++i){
			hql.append("?,");
		}
		hql.setLength(hql.length() - 1);
		hql.append(")");
		hibernateDao.excuteUpdate(hql.toString(), Arrays.asList(ids).toArray());
		return StatusText.SUCCESS;
	}
}
