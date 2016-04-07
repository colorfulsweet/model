package com.system.tags;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import com.system.model.Dict.DictClause;
import com.system.service.dao.IHibernateDao;
import com.system.util.SpringUtils;
/**
 * 根据字典项产生一个下拉框
 * @author Administrator
 *
 */
public class SelectClauseTag extends TagSupport {
	private static final long serialVersionUID = 8200571419076435613L;
	private String name;
	private String value;
	private String dictCode;
	private Map<String,String> dictMap;
	
	private static IHibernateDao<Object,String> hibernateDao;
	@Override
	public int doStartTag() throws JspException {
		dictMap = bulidDictMap(pageContext,dictCode);
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
	/**
	 * 创建Map结构的某个字典的字典项集合,并放入到pageContext当中
	 * @param pageContext 当前页面上下文对象
	 * @param dictCode 字典编码
	 * @return Map类型的字典项集合
	 */
	@SuppressWarnings("unchecked")
	static Map<String, String> bulidDictMap(
				PageContext pageContext,
				String dictCode){
		if(hibernateDao == null){
			hibernateDao = SpringUtils.getSpringMVCBean(pageContext.getServletContext(), "hibernateDao");
		}
		//从pageConext当中获取这个字典的Map对象
		Map<String, String> dictMap = (Map<String, String>) pageContext.getAttribute(dictCode+"_clause_map");
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
		return dictMap;
	}
}
