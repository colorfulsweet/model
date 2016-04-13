<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE HTML>
<html>
<head></head>
<body>
<div style="padding:10px;">
	<form class="addRoleForm" method="post" action="role/save.html" onSubmit="return $css.ajaxSubmit(this)">
		<input type="hidden" name="id" value="${role.id}" />
		<input type="hidden" name="createTime" value="<fmt:formatDate value="${role.createTime}" type="date" pattern="yyyy-MM-dd HH:mm:ss"/>" />
		<table class="zebra">
			<tr>
				<th style="width:30%;">角色名称</th>
				<td style="width:70%;"><input type="text" name="roleName" value="${role.roleName }"/></td>
			</tr>
			<tr>
				<th style="width:30%;"></th>
				<td style="width:70%;"><input type="submit" value="保存" /></td>
			</tr>
		</table>
	</form>
</div>
</body>
</html>