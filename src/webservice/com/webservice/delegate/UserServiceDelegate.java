package com.webservice.delegate;

import javax.jws.WebService;

import com.webservice.UserService;

@WebService(targetNamespace = "http://service.com/", serviceName = "UserService", portName = "UserPort")
public class UserServiceDelegate {
	private UserService userService = new UserService();
	
	public String checkUser(String username, String password){
		return userService.checkUser(username, password);
	}
	public String getDictClauseList(String dictCode){
		return userService.getDictClauseList(dictCode);
	}
}
