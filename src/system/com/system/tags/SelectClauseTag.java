package com.system.tags;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.system.bean.Dict.DictClause;
import com.system.service.IHibernateDao;
import com.system.util.SpringUtils;
/**
 * 根据字典项产生一个下拉框
 * @author Administrator
 *
 */
public class SelectClauseTag extends TagSupport {
	private static final long serialVersionUID = -975708524751949380L;
	private String name;
	private String value;
	private String dictCode;
	private Map<String,String> dictMap;
	
	private IHibernateDao<Object,String> hibernateDao;
	public SelectClauseTag(){
		hibernateDao = SpringUtils.getBean("hibernateDao");
	}
	@SuppressWarnings("unchecked")
	@Override
	public int doStartTag() throws JspException {
		dictMap = (Map<String, String>) pageContext.getAttribute(dictCode+"_clause_map");
		if(dictMap == null){
			List<DictClause> result = (List<DictClause>) hibernateDao.excuteQueryName("dictClauseList", dictCode);
			dictMap = new LinkedHashMap<String,String>();
			for(DictClause dictClause : result){
				dictMap.put(dictClause.getClauseCode(), dictClause.getClauseName());
			}
			pageContext.setAttribute(dictCode+"_clause_map", dictMap);
		}
		return SKIP_BODY;
	}
	@Override
	public int doEndTag() throws JspException {
		JspWriter out = pageContext.getOut();
		try {
			out.println("<select name=\""+(name==null?dictCode:name)+"\">");
			Set<Entry<String,String>> entries = dictMap.entrySet();
			for(Entry<String,String> entry : entries){
				out.println("<option value=\"" + entry.getKey() + "\""
							+ (entry.getKey().equals(value)?"selected":"")
							+ ">"+entry.getValue() 
							+ "</option>");
			}
			out.println("</select>");
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getDictCode() {
		return dictCode;
	}
	public void setDictCode(String dictCode) {
		this.dictCode = dictCode;
	}
}
