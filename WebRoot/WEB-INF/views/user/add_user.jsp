<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html>
<head></head>
<body>
<div style="padding:10px;">
	<form class="addUserForm" method="post" action="user/save.html" onSubmit="return $css.ajaxSubmit(this,'admin/userManage.html')">
		<input type="hidden" name="id" value="${user_.id}" />
		<table class="table-input">
			<tr>
				<th style="width:15%;">用户名</th>
				<td style="width:35%;"><input type="text" name="username" value="${user_.username }" required="required"/></td>
				<th style="width:15%;">昵称</th>
				<td style="width:35%;"><input type="text" name="realName" value="${user_.realName }" required="required"/></td>
			</tr>
			<tr>
				<th>密码</th>
				<td><input type="password" name="password" required="required"/></td>
				<th>是否启用</th>
				<td>
					<div class="slideCheckbox">  
						<input type="checkbox" value="true" name="status" id="slideCheckbox"
								<c:if test="${user_.status==null || user_.status}">checked</c:if>
						/>
						<label for="slideCheckbox"></label>
					</div>
				</td>
			</tr>
			<tr>
				<th>电子邮箱</th>
				<td><input type="text" name="email" value="${user_.email }"/></td>
				<th>电话号码</th>
				<td><input type="text" name="tel" value="${user_.tel }"/></td>
			</tr>
		</table>
		<div class="btn-box">
			<input type="submit" class="btn green medium" value="保存" />
		</div>
	</form>
</div>
</body>
</html>