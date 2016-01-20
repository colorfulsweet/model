<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="path" value="<%=request.getContextPath() %>" scope="page"/>
<c:set var="basePath" scope="page">
	<%=request.getScheme()+"://"+request.getServerName()+":"
		+request.getServerPort()+request.getContextPath()+"/" %>
</c:set>
<!DOCTYPE >
<html>
<head>
	<title>欢迎登陆</title>
	<link rel="stylesheet" type="text/css" href="${basePath}css/login.css" />
</head>
<body>
<div class="container">
	<section id="content">
		<form action="${basePath}page/login.html" method="post" >
			<h1>XX管理系统</h1>
			<div>
				<input type="text" placeholder="用户名" required="required" id="username" name="username" value="test" />
			</div>
			<div>
				<input type="password" placeholder="密码" required="required" id="password" name="password" value="123"/>
			</div>
			<div id="info" style="text-align:left;padding-left:20px;color:red;">${info}</div>
			<div>
				<input type="submit" value="登陆" />
			</div>
		</form>
	</section>
</div>
<script type="text/javascript" src="${basePath}js/jquery/jquery-1.11.3.min.js"></script>
</body>
</html>
