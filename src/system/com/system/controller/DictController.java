package com.system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.system.bean.Dict;
import com.system.bean.Dict.DictClause;
import com.system.service.IDictService;
import com.system.service.IHibernateDao;
import com.system.util.StatusText;

@Controller
@RequestMapping(value="/dict")
public class DictController {
	
	@Autowired
	private IDictService dictService;
	
	@Autowired
	private IHibernateDao<Object,String> hibernateDao;
	
	@RequestMapping(value="/dictClause.html")
	public String getDictClause(Dict dict,Model model){
		List<DictClause> dictClauses = dictService.getDictClauseList(dict);
		model.addAttribute("dictClauseList", dictClauses);
		model.addAttribute("dict",dict);
		return "WEB-INF/views/dict/dict_clause.jsp";
	}
	
	@RequestMapping(value="/saveClause.html")
	@ResponseBody
	public String saveDictClause(DictClause dictClause){
		dictService.saveDictClause(dictClause);
		return StatusText.SUCCESS;
	}
	
	@RequestMapping(value="/delClause.html")
	@ResponseBody
	public String delClause(DictClause dictClause){
		hibernateDao.del(dictClause);
		return StatusText.SUCCESS;
	}
	
	@RequestMapping(value="/save.html")
	@ResponseBody
	public String saveDict(Dict dict){
		hibernateDao.saveOrUpdate(dict);
		return StatusText.SUCCESS;
	}
	
	@RequestMapping(value="/delete.html")
	@ResponseBody
	public String delDict(Dict dict){
		hibernateDao.del(dict,true);
		return StatusText.SUCCESS;
	}
}
