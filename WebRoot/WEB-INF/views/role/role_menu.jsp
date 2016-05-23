<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<form action="role/saveRoleMenu.html" method="post" id="role_menu">
<input type="hidden" name="roleId" value="${role.id}" />
<table class="bordered" id="menuList" >
	<tr>
		<th style="width:10%;"></th>
		<th style="width:45%;">菜单名称</th>
		<th style="width:45%;">备注</th>
	</tr>
	<c:forEach var="menu" items="${_menuList}" >
	<tr>
		<td><input type="checkbox" name="menuId" value="${menu.id}" /></td>
		<td>${menu.menuName}</td>
		<td>${menu.remark}</td>
	</tr>
	</c:forEach>
</table>
</form>
