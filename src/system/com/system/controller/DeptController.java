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
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.system.model.Dept;
import com.system.service.dao.IHibernateDao;
import com.system.util.SystemMessage;

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
		return JSON.toJSONString(result, SerializerFeature.WriteMapNullValue);
	}
	
	@RequestMapping(value="/save.html",produces="text/html;charset=utf-8")
	@ResponseBody
	public String saveDept(Dept dept) {
		if(dept.getParentId() != null){
			dept.setParentDept(new Dept());
			dept.getParentDept().setId(dept.getParentId());
		}
		hibernateDao.saveOrUpdate(dept);
		return SystemMessage.getMessage("success");
	}
	
	@RequestMapping(value="/delete.html",produces="text/html;charset=utf-8")
	@ResponseBody
	public String deleteDept(Dept dept) {
		hibernateDao.del(dept);
		return SystemMessage.getMessage("success");
	}
}
