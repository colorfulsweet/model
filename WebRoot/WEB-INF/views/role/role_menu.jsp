<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<form action="role/saveRoleMenu.html" method="post" id="role_menu">
<input type="hidden" name="roleId" value="${role.id}" />
<table class="bordered" id="menuList" >
	<tr>
		<th style="width:10%;"></th>
		<th style="width:35%;">菜单名称</th>
		<th style="width:20%;">图标</th>
		<th style="width:35%;">备注</th>
	</tr>
	<c:forEach var="menu" items="${_menuList}" >
	<tr>
		<td>
		<div class="squaredCheckbox">
			<input type="checkbox" name="menuId" id="${menu.id}" value="${menu.id}"
				<c:if test="${menu.roles.contains(role)}">checked="true"</c:if>
			 />
			<label for="${menu.id}"></label>
		</div>
		</td>
		<td>${menu.menuName}</td>
		<td><span class="fa fa-${menu.icon}"></span>&nbsp;&nbsp;${menu.icon}</td>
		<td>${menu.remark}</td>
	</tr>
	</c:forEach>
</table>
</form>
