<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="path" value="<%=request.getContextPath() %>" scope="page"/>
<c:set var="basePath" scope="page">
	<%=request.getScheme()+"://"+request.getServerName()+":"
		+request.getServerPort()+request.getContextPath()+"/" %>
</c:set>
<!DOCTYPE HTML>
<html>
<head>
	<title>${app_name}-登陆</title>
	<link rel="shortcut icon" type="image/x-icon" href="${basePath}${icon_path}" media="screen" />
	<link rel="stylesheet" type="text/css" href="${basePath}css/login.css" />
</head>
<body>
<div class="htmleaf-container">
	<div class="wrapper">
		<div class="container">
			<h1>${app_name}</h1>
			
			<form action="${basePath}page/login.html" method="post" >
				<input type="text" name="username" placeholder="用户名" required="required"/>
				<input type="password" name="password" placeholder="密码" required="required"/>
				<div id="info" >${info} &nbsp;</div>
				<button type="submit" id="login-button">登陆</button>
			</form>
		</div>
		
		<ul class="bg-bubbles">
			<li></li>
			<li></li>
			<li></li>
			<li></li>
			<li></li>
			<li></li>
			<li></li>
			<li></li>
			<li></li>
			<li></li>
		</ul>
	</div>
</div>
</body>
</html>
