<%@ page language="java" pageEncoding="UTF-8"%>
<div style="padding:10px;">
	<form class="addUserForm" method="post" action="user/save.html" onSubmit="return $css.ajaxSubmit(this)">
		<input type="hidden" name="id" value="${user_.id}" />
		用户名<input type="text" name="username" value="${user_.username }"/>
		<br/>
		密码<input type="password" name="password" />
		<br/>
		昵称<input type="text" name="realName" value="${user_.realName }"/>
		<br/>
		是否启用
		<input type="radio" name="status" value="true" />启用
		<input type="radio" name="status" value="false" />禁用
		<br/>
		<input type="submit" value="保存" />
	</form>
</div>
