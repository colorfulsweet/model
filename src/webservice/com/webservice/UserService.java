package com.webservice;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.system.model.Dict.DictClause;
import com.system.model.User;
import com.system.service.ISystemService;
import com.system.service.dao.IHibernateDao;
import com.system.util.SpringMVCUtils;

public class UserService {
	private ISystemService systemService;
	private IHibernateDao<Object, Serializable> hibernateDao;
	
	public String checkUser(String username, String password) {
		User user = new User(username, password);
		user = getSystemService().checkUser(user);
		if(user == null) {
			return null;
		} else {
			return JSON.toJSONString(user);
		}
	}
	
	@SuppressWarnings("unchecked")
	public String getDictClauseList(String dictCode) {
		List<DictClause> result = (List<DictClause>) getHibernateDao().excuteQueryName("dictClauseList", dictCode);
		if(result != null) {
			return JSON.toJSONString(result);
		} else {
			return null;
		}
	}
	
	protected IHibernateDao<Object, Serializable> getHibernateDao(){
		if(hibernateDao == null) {
			hibernateDao = SpringMVCUtils.getSpringMVCBean("hibernateDao");
		}
		return hibernateDao;
	}
	
	protected ISystemService getSystemService(){
		if(systemService == null) {
			systemService = SpringMVCUtils.getSpringMVCBean("systemService");
		}
		return systemService;
	}
}
