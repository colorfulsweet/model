<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div style="padding:10px;">
	<form class="addUserForm" method="post" action="user/save.html" onSubmit="return ajaxSubmit(this)">
		用户名<input type="text" name="username" />
		<br/>
		密码<input type="password" name="password" />
		<br/>
		昵称<input type="text" name="realName" />
		<br/>
		<input type="submit" value="保存" />
	</form>
</div>
