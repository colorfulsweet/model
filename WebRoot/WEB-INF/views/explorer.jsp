<%@ page language="java" import="java.util.*,com.system.bean.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="path" value="<%=request.getContextPath() %>" scope="page"/>
<c:set var="basePath" scope="page">
	<%=request.getScheme()+"://"+request.getServerName()+":"
		+request.getServerPort()+request.getContextPath()+"/" %>
</c:set>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>XX管理系统</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">		
	<meta http-equiv="description" content="主页面">
	
	<link rel="stylesheet" type="text/css" href="${basePath}css/common.css"/>
	<link rel="stylesheet" type="text/css" href="${basePath}css/font-awesome.min.css" />
	<link rel="stylesheet" type="text/css" href="${basePath}js/easyUI/theme/easyui.css"/>
	<link rel="stylesheet" type="text/css" href="${basePath}js/easyUI/theme/icon.css"/>
</head>
<body>
	<div class="main">
		<div class="top">
			<a href="${basePath}page/logout.html" id="cancel">注销</a>
			<a href="javascript:void(0);" >修改密码</a>
		</div>
		<ul id="accordion" class="accordion">
			<c:forEach items="${menuList}" var="menu">
			<li>
				<div class="link"><i class="fa ${menu.icon}"></i>${menu.menuName}<i class="fa fa-chevron-down"></i></div>
				<ul class="submenu">
					<c:forEach items="${menu.childrenMenu}" var="submenu">
					<li><a href="${basePath}${submenu.url}" >${submenu.submenuName}</a></li>
					</c:forEach>
				</ul>
			</li>
			</c:forEach>
		</ul>
		
		<div class="easyui-tabs" id="content-tab" style="width:1200px;height:600px;">
			<div title="欢迎页" >
				<jsp:include page="../../page/welcome.html"/>
			</div>
		</div>	
	</div>
	<script type="text/javascript" src="${basePath}js/jquery/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" src="${basePath}js/easyUI/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${basePath}js/app.js"></script>
</body>
</html>
