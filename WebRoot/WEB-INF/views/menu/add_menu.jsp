<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head></head>
<body>
<div style="padding:10px;">
	<form class="addMenuForm" method="post" action="menu/save.html" onSubmit="return $css.ajaxSubmit(this,'admin/menuManage.html')">
		<input type="hidden" name="id" value="${menu.id}" />
		<table class="table-input">
			<tr>
				<th style="width:15%;">菜单名称</th>
				<td style="width:35%;"><input type="text" name="menuName" value="${menu.menuName}" required="required"/></td>
				<th style="width:15%;">URL地址</th>
				<td style="width:35%;"><input type="text" name="url" value="${menu.url}" /></td>
			</tr>
			<tr>
				<th>图标</th>
				<td>
					<input type="text" name="icon" v-model="icon_code" value="${menu.icon}" />
					<span class="fa fa-{{icon_code}}"></span>
				</td>
				<th>菜单序号</th>
				<td><input type="text" name="index" value="${menu.index}" required="required"/></td>
			</tr>
			<tr>
				<th>备注</th>
				<td colspan="3"><textarea name="remark">${menu.remark}</textarea></td>
			</tr>
		</table>
		<div class="btn-box">
			<input type="submit" class="btn green medium" value="保存" />
		</div>
	</form>
</div>
<script>
(function(){
	new Vue({
		el : ".addMenuForm",
		data : {icon_code : null}
	});
})();
</script>
</body>