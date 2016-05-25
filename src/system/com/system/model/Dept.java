package com.system.model;

import java.io.Serializable;
import java.util.List;
/**
 * 组织机构类
 * @author 41882
 *
 */
public class Dept implements Serializable {
	private static final long serialVersionUID = -7095783350569319857L;
	
	private String id;
	private String deptCode;//部门编码
	private String deptName;//部门名称
	private String remark;
	
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
}
