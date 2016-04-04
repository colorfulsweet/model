package com.system.service;

import java.util.List;

import com.system.model.Dict;
import com.system.model.Dict.DictClause;

public interface IDictService {
	/**
	 * 获取字典当中的字典项
	 * @param dict
	 * @return
	 */
	public List<DictClause> getDictClauseList(Dict dict);
	/**
	 * 保存字典项(包含字典项序列)
	 * @param dictClause
	 */
	public void saveDictClause(DictClause dictClause);
}
