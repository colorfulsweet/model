package com.system.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import com.alibaba.fastjson.annotation.JSONField;
/**
 * 角色实体类
 * @author 41882
 *
 */
public class Role implements Serializable {
	private static final long serialVersionUID = -6533673733234493181L;
	private String id;//主键
	private String roleName;//角色名称
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date createTime;//创建时间
	private String remark;//备注
	private Set<Menu> menus;//该角色对应的菜单
	public String getId() {
		return id;
	}
	public void setId(String id) {
		if(id!=null && id.length()==0){
			id = null;
		}
		this.id = id;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Set<Menu> getMenus() {
		return menus;
	}
	public void setMenus(Set<Menu> menus) {
		this.menus = menus;
	}
	public String getRemark(){
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
