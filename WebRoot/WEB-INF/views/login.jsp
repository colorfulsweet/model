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
	<title>统一权限管理系统-登陆</title>
	<link rel="stylesheet" type="text/css" href="${basePath}css/login.css" />
</head>
<body>
<form action="${basePath}page/login.html" method="post">
	<div class="panel-lite">
		<div class="thumbur">
			<div class="icon-lock"></div>
		</div>
		<h4>统一权限管理系统</h4>
		<div class="form-group">
			<input type="text" class="form-control" required="required" name="username" />
			<label class="form-label">用户名</label>
		</div>
		<div class="form-group">
			<input type="password" class="form-control" required="required" name="password" />
			<label class="form-label">密　码</label>
		</div>
		<div id="info" >${info} &nbsp;</div>
		<button class="floating-btn" type="submit"><i class="icon-arrow"></i></button>
	</div>
</form>
</body>
</html>
