<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head></head>
<body>
<div style="padding:10px;">
	<form class="addDictForm" method="post" action="dict/save.html" onSubmit="return $css.ajaxSubmit(this)">
		<input type="hidden" name="id" value="${dict.id}" />
		<table class="zebra">
			<tr>
				<th style="width:30%;">字典编码</th>
				<td style="width:70%;"><input type="text" name="dictCode" value="${dict.dictCode}" /></td>
			</tr>
			<tr>
				<th style="width:30%;">字典名称</th>
				<td style="width:70%;"><input type="text" name="dictName" value="${dict.dictName}" /></td>
			</tr>
			<tr>
				<th style="width:30%;">备注</th>
				<td style="width:70%;"><textarea name="remark" >${dict.remark}</textarea></td>
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