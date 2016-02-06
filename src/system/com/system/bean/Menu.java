package com.system.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
/**
 * 菜单实体类
 * @author 41882
 *
 */
public class Menu implements Serializable {
	private static final long serialVersionUID = -631139516597849321L;
	private String id;//主键
	private String menuName;//菜单名称
	private String url;//URL地址
	private String icon;//图标编码
	private String remark;//备注
	private List<Submenu> childrenMenu;//子菜单
	private Set<Role> roles;//该菜单对应的角色
	/**
	 * 子菜单实体类
	 * @author 41882
	 *
	 */
	public static class Submenu implements Serializable {
		private static final long serialVersionUID = 5711913326774633469L;
		private String id;
		private String submenuName;//子菜单名称
		private String url;//URL地址
		private String remark;//备注
		public String getId() {
			return id;
		}
		public void setId(String id) {
			if(id!=null && id.length()==0){
				id = null;
			}
			this.id = id;
		}
		public String getSubmenuName() {
			return submenuName;
		}
		public void setSubmenuName(String submenuName) {
			this.submenuName = submenuName;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public String getRemark() {
			return remark;
		}
		public void setRemark(String remark) {
			this.remark = remark;
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
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public List<Submenu> getChildrenMenu() {
		return childrenMenu;
	}
	public void setChildrenMenu(List<Submenu> childrenMenu) {
		this.childrenMenu = childrenMenu;
	}
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
