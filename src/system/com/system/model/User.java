package com.system.model;

import java.io.Serializable;
import java.sql.Blob;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
/**
 * 用户实体类
 * @author 41882
 *
 */
@JSONType(ignores={"icon"})
public class User implements Serializable {
	private static final long serialVersionUID = 1219825686542965398L;
	
	private String id;//主键
	private String username;//用户名
	private String realName;//昵称
	private Blob icon;//头像
	private String password;//密码(SHA256)
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date createTime;//创建时间
	private String email;//邮箱
	private String tel;//电话
	private Boolean status;//状态
	private Role role;//用户角色
	
	public User(){}
	public User(String username,String password) {
		this.username = username;
		this.password = password;
	}
	
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
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
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public Blob getIcon() {
		return icon;
	}
	public void setIcon(Blob icon) {
		this.icon = icon;
	}
}
