package com.system.bean;

import java.io.Serializable;
import java.util.Date;
/**
 * 用户实体类
 * @author 41882
 *
 */
public class User implements Serializable {
	private static final long serialVersionUID = 5930838851780880904L;
	private String id;//主键
	private String username;//用户名
	private String realName;//昵称
	private String password;//密码(MD5)
	private Date createTime;//创建时间
	private String eMail;//邮箱
	private String tel;//电话
	private boolean status;//状态
	private Role role;//用户角色
	
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
	public String geteMail() {
		return eMail;
	}
	public void seteMail(String eMail) {
		this.eMail = eMail;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public boolean getStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
}
