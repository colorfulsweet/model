<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="path" value="<%=request.getContextPath() %>" scope="page"/>
<c:set var="basePath" scope="page">
	<%=request.getScheme()+"://"+request.getServerName()+":"
		+request.getServerPort()+request.getContextPath()+"/" %>
</c:set>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>${app_name}</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">		
	<meta http-equiv="description" content="主页面">
	<link rel="shortcut icon" type="image/x-icon" href="${basePath}${icon_path}" media="screen" />
	<link rel="stylesheet" type="text/css" href="${basePath}css/common.css"/>
	<link rel="stylesheet" type="text/css" href="${basePath}css/checkbox.css"/>
	<link rel="stylesheet" type="text/css" href="${basePath}plugin/fonts/font-awesome.min.css" />
	<link rel="stylesheet" type="text/css" href="${basePath}plugin/easyUI/theme/easyui.css"/>
	<link rel="stylesheet" type="text/css" href="${basePath}plugin/easyUI/theme/icon.css"/>
	<link rel="stylesheet" type="text/css" href="${basePath}css/mdialog.css"/>
</head>
<body>
	<div style="width:100%;">
		<div id="top">
			<div class="nav">
				<ul>
				<li><a href="${basePath}page/personalConfig.html" class="tabNav">个人设置</a></li>
				<li><a href="${basePath}page/logout.html" id="logout">注销</a></li>
				</ul>
			</div>
			<div class="tip">
				<img src="user/getIcon.html" id="top_icon" />
				<p>欢迎你, ${user.realName}</p>
			</div>
		</div>
		<div id="main-panel">
		<div id="accordion-panel">
		<ul id="accordion" class="accordion">
			<c:forEach items="${menuList}" var="menu">
			<li>
				<div class="link">
					<i class="fa fa-${menu.icon}"></i>
					<a href="${menu.url}">${menu.menuName}</a>
					<i class="fa fa-chevron-down"></i>
				</div>
				<ul class="submenu">
					<c:forEach items="${menu.childrenMenu}" var="submenu">
					<li><a href="${basePath}${submenu.url}" >${submenu.submenuName}</a></li>
					</c:forEach>
				</ul>
			</li>
			</c:forEach>
		</ul>
		</div>
		<div class="easyui-tabs" id="content-tab" >
			<div title="欢迎页" >
				<jsp:include page="../../page/welcome.html"/>
			</div>
		</div>
		</div>
	</div>
	<script type="text/javascript" src="${basePath}js/jquery/jquery-2.1.4.js"></script>
	<script type="text/javascript" src="${basePath}js/vue/vue.js"></script>
	<script type="text/javascript" src="${basePath}js/underscore/underscore-1.8.3.js"></script>
	<script type="text/javascript" src="${basePath}plugin/easyUI/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${basePath}plugin/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="${basePath}js/mdialog.js"></script>
	<script type="text/javascript" src="${basePath}js/app.js"></script>
</body>
</html>
