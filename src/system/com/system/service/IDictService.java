package com.system.service;

import java.util.List;

import com.system.bean.Dict;
import com.system.bean.Dict.DictClause;

public interface IDictService {
	/**
	 * 获取字典当中的字典项
	 * @param dict
	 * @return
	 */
	public List<DictClause> getDictClauseList(Dict dict);
}
