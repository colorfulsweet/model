package com.system.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
	public String getDeptTreeJson(@RequestParam Map<String,Object> criteria){
		if(!criteria.containsKey("parentId")){
			criteria.put("parentId", null);
		}
		List<Dept> result = hibernateDao.dir(Dept.class, criteria);
		Collections.sort(result);
		return JSON.toJSON(result).toString();
	}
}
