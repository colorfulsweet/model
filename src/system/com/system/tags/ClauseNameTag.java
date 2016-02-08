package com.system.tags;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.system.bean.Dict.DictClause;
import com.system.service.IHibernateDao;
import com.system.util.SpringUtils;

public class ClauseNameTag extends TagSupport {
	private static final long serialVersionUID = 7383074582043904436L;
	private String dictCode;//字典编码
	private String clauseCode;//字典项编码
	private IHibernateDao<Object,String> hibernateDao;
	private Map<String,String> dictMap;
	public ClauseNameTag(){
		hibernateDao = SpringUtils.getBean("hibernateDao");
	}
	@SuppressWarnings("unchecked")
	@Override
	public int doStartTag() throws JspException {
		//从pageConext当中获取这个字典的Map对象
		dictMap = (Map<String, String>) pageContext.getAttribute(dictCode+"_clause_map");
		if(dictMap == null){
			//如果pageContext当中没有这个字典的信息,就查询这个字典的信息
			List<DictClause> result = (List<DictClause>) hibernateDao.excuteQueryName("dictClauseList", dictCode);
			dictMap = new LinkedHashMap<String,String>();
			for(DictClause dictClause : result){
				dictMap.put(dictClause.getClauseCode(), dictClause.getClauseName());
			}
			//并以LinkedHashMap对象的形式放入到pageContext当中去
			//避免同一个页面多次执行查询
			pageContext.setAttribute(dictCode+"_clause_map", dictMap);
		}
		return SKIP_BODY;
	}
	@Override
	public int doEndTag() throws JspException {
		JspWriter out = pageContext.getOut();
		try {
			out.print(dictMap.get(clauseCode)==null?"":dictMap.get(clauseCode));
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}
	public String getDictCode() {
		return dictCode;
	}
	public void setDictCode(String dictCode) {
		this.dictCode = dictCode;
	}
	public String getClauseCode() {
		return clauseCode;
	}
	public void setClauseCode(String clauseCode) {
		this.clauseCode = clauseCode;
	}
	
	
}
