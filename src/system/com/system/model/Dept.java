package com.system.model;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
/**
 * 组织机构类
 * @author 41882
 *
 */
@JSONType(ignores={"hasChild","parentDept","childDept"})
public class Dept implements Serializable,Comparable<Dept> {
	private static final long serialVersionUID = -4450844426367095228L;
	
	private String id;
	private String deptCode;//部门编码
	@JSONField(name="text")
	private String deptName;//部门名称
	private String remark;
	private String parentId;
	private int deptIndex;
	//对应上级部门(多对一)
	private Dept parentDept;
	//对应下级部门(一对多)
	private List<Dept> childDept;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Dept getParentDept() {
		return parentDept;
	}
	public void setParentDept(Dept parentDept) {
		this.parentDept = parentDept;
	}
	public List<Dept> getChildDept() {
		return childDept;
	}
	public void setChildDept(List<Dept> childDept) {
		this.childDept = childDept;
	}
	public int getDeptIndex() {
		return deptIndex;
	}
	public void setDeptIndex(int deptIndex) {
		this.deptIndex = deptIndex;
	}
	
	@Override
	public int compareTo(Dept target) {
		return this.deptIndex - target.deptIndex;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
}
