package com.system.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
/**
 * 角色实体类
 * @author 41882
 *
 */
public class Role implements Serializable {
	private static final long serialVersionUID = 8474627251848571845L;
	private String id;//主键
	private String roleName;//角色名称
	private Date createTime;//创建时间
	private Set<Menu> menus;//该角色对应的菜单
	public String getId() {
		return id;
	}
	public void setId(String id) {
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
}
