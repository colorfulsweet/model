<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html>
<head></head>
<body>
<div style="padding:10px;">
	<form class="addUserForm" method="post" action="user/save.html" onSubmit="return $css.ajaxSubmit(this)">
		<input type="hidden" name="id" value="${user_.id}" />
		<table class="zebra">
			<tr>
				<th style="width:20%;">用户名</th>
				<td style="width:80%;"><input type="text" name="username" value="${user_.username }"/></td>
			</tr>
			<tr>
				<th style="width:20%;">密码</th>
				<td style="width:80%;"><input type="password" name="password" /></td>
			</tr>
			<tr>
				<th style="width:20%;">昵称</th>
				<td style="width:80%;"><input type="text" name="realName" value="${user_.realName }"/></td>
			</tr>
			<tr>
				<th style="width:20%;">电子邮箱</th>
				<td style="width:80%;"><input type="text" name="email" value="${user_.email }"/></td>
			</tr>
			<tr>
				<th style="width:20%;">电话号码</th>
				<td style="width:80%;"><input type="text" name="tel" value="${user_.tel }"/></td>
			</tr>
			<tr>
				<th style="width:20%;">是否启用</th>
				<td style="width:80%;">
					<div class="slideCheckbox">  
						<input type="checkbox" value="true" name="status" id="slideCheckbox"
								<c:if test="${user_.status==null || user_.status}">checked</c:if>
						/>
						<label for="slideCheckbox"></label>
					</div>
				</td>
			</tr>
			<tr>
				<th style="width:20%;"></th>
				<td style="width:80%;"><input type="submit" class="btn green medium" value="保存" /></td>
			</tr>
		</table>
	</form>
</div>
</body>
</html>