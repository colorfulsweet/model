package com.system.util;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.system.bean.Dict.DictClause;
import com.system.service.IHibernateDao;

@Component
public class DictUtils {
	
	@Autowired
	private IHibernateDao<DictClause,String> hibernateDao;
	/**
	 * 获取字典项的名称
	 * @param dictCode 字典编码
	 * @param clauseCode 字典项编码
	 * @return 字典项名称
	 */
	public String getDictClauseName(String dictCode,String clauseCode){
		//TODO 查询字典项名称
		String hql = "select a.clauseName from DictClause a where a.dict.dictCode=? and a.clauseCode=?";
		List<?> result = hibernateDao.excuteQuery(hql, new Object[]{dictCode,clauseCode});
		if(result.isEmpty()){
			return null;
		} else {
			return result.get(0).toString();
		}
	}
}
