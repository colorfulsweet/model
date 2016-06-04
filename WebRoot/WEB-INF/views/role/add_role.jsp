<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE HTML>
<html>
<head></head>
<body>
<div style="padding:10px;">
	<form class="addRoleForm" method="post" action="role/save.html" onSubmit="return $css.ajaxSubmit(this,'admin/roleManage.html')">
		<input type="hidden" name="id" value="${role.id}" />
		<input type="hidden" name="createTime" value="<fmt:formatDate value="${role.createTime}" type="date" pattern="yyyy-MM-dd HH:mm:ss"/>" />
		<table class="table-input">
			<tr>
				<th style="width:15%;">角色名称</th>
				<td style="width:85%;"><input type="text" name="roleName" value="${role.roleName }" required="required"/></td>
			</tr>
			<tr>
				<th>备注</th>
				<td><textarea name="remark" >${role.remark}</textarea></td>
			</tr>
		</table>
		<div class="btn-box">
			<input type="submit" class="btn green medium" value="保存" />
		</div>
	</form>
</div>
</body>
</html>