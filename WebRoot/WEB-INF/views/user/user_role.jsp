<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<form action="role/saveUserRole.html" method="post" id="user_role">
<input type="hidden" name="userId" value="${user.id}" />
<table class="bordered" id="submenuList" >
	<tr>
		<th style="width:10%;"></th>
		<th style="width:45%;">角色名称</th>
		<th style="width:45%;">创建时间</th>
	</tr>
	<c:forEach var="role" items="${roleList}" >
	<tr>
		<td><input type="radio" name="roleId" value="${role.id}"
			<c:if test="${role.id.equals(user.role.id)}">checked="true"</c:if>
		  /></td>
		<td>${role.roleName}</td>
		<td>${role.createTime}</td>
	</tr>
	</c:forEach>
</table>
</form>