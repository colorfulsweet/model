<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head></head>
<body>
<div style="padding:10px;">
	<form class="addDictForm" method="post" action="dict/save.html" onSubmit="return $css.ajaxSubmit(this,'admin/dictManage.html')">
		<input type="hidden" name="id" value="${dict.id}" />
		<table class="table-input">
			<tr>
				<th style="width:15%;">字典编码</th>
				<td style="width:35%;"><input type="text" name="dictCode" value="${dict.dictCode}" required="required"/></td>
				<th style="width:15%;">字典名称</th>
				<td style="width:35%;"><input type="text" name="dictName" value="${dict.dictName}" required="required"/></td>
			</tr>
			<tr>
				<th>备注</th>
				<td colspan="3"><textarea name="remark" >${dict.remark}</textarea></td>
			</tr>
		</table>
		<div class="btn-box">
		<input class="btn green medium" type="submit" value="保存" />
		</div>
	</form>
</div>
</body>
</html>