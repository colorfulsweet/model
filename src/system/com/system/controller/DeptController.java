package com.system.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.system.model.Dept;
import com.system.service.dao.IHibernateDao;

@Controller
@RequestMapping(value="/dept")
public class DeptController {
	@Autowired
	private IHibernateDao<Dept,String> hibernateDao;
	
	@RequestMapping(value="/getDeptTree.html",
			method=RequestMethod.POST,produces="text/html;charset=utf-8")
	@ResponseBody
	public String getDeptTreeJson(){
		Map<String,Object> criteria = new HashMap<String,Object>();
		criteria.put("hasChild", "true");
		List<Dept> result = hibernateDao.dir(Dept.class, criteria);
		return JSON.toJSON(result).toString();
	}
}
