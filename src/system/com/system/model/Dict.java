package com.system.model;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONType;

/**
 * 字典实体类
 * @author 41882
 *
 */
public class Dict implements Serializable {
	private static final long serialVersionUID = -6788795205288727827L;
	private String id;
	private String dictCode;//字典编码
	private String dictName;//字典名称
	private String remark;//备注
	private List<DictClause> clauses;//字典项列表
	/**
	 * 字典条目
	 * @author 41882
	 *
	 */
	@JSONType(ignores={"dict"})
	public static class DictClause implements Serializable{
		private static final long serialVersionUID = 292020742117220942L;
		private String id;
		private String clauseCode;//字典条目编码
		private String clauseName;//字典条目名称
		private Dict dict;
		public String getId() {
			return id;
		}
		public void setId(String id) {
			if(id!=null && id.length()==0){
				id = null;
			}
			this.id = id;
		}
		public String getClauseCode() {
			return clauseCode;
		}
		public void setClauseCode(String clauseCode) {
			this.clauseCode = clauseCode;
		}
		public String getClauseName() {
			return clauseName;
		}
		public void setClauseName(String clauseName) {
			this.clauseName = clauseName;
		}
		public Dict getDict() {
			return dict;
		}
		public void setDict(Dict dict) {
			this.dict = dict;
		}
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		if(id!=null && id.length()==0){
			id = null;
		}
		this.id = id;
	}
	public String getDictCode() {
		return dictCode;
	}
	public void setDictCode(String dictCode) {
		this.dictCode = dictCode;
	}
	public String getDictName() {
		return dictName;
	}
	public void setDictName(String dictName) {
		this.dictName = dictName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public List<DictClause> getClauses() {
		return clauses;
	}
	public void setClauses(List<DictClause> clauses) {
		this.clauses = clauses;
	}
}
