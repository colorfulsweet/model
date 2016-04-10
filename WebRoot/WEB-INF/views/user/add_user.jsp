<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head></head>
<body>
<div style="padding:10px;">
	<form class="addUserForm" method="post" action="user/save.html" onSubmit="return $css.ajaxSubmit(this)">
		<input type="hidden" name="id" value="${user_.id}" />
		<table class="zebra">
			<tr>
				<th style="width:30%;">用户名</th>
				<td style="width:70%;"><input type="text" name="username" value="${user_.username }"/></td>
			</tr>
			<tr>
				<th style="width:30%;">密码</th>
				<td style="width:70%;"><input type="password" name="password" /></td>
			</tr>
			<tr>
				<th style="width:30%;">昵称</th>
				<td style="width:70%;"><input type="text" name="realName" value="${user_.realName }"/></td>
			</tr>
			<tr>
				<th style="width:30%;">是否启用</th>
				<td style="width:70%;">
					<input type="radio" name="status" value="true" />启用
					<input type="radio" name="status" value="false" />禁用
				</td>
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