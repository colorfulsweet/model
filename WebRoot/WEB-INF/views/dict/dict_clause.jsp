<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<form action="dict/saveClause.html" method="post" id="dictClause">
<input type="hidden" name="dict.id" value="${dict.id}" />
<table class="bordered" id="clauseList" >
	<tr>
		<th style="width:25%;">字典项编码</th>
		<th style="width:50%;">字典项名称</th>
		<th style="width:25%;">操作</th>
	</tr>
	<c:forEach var="dictClause" items="${dictClauseList}" >
	<tr>
		<td>${dictClause.clauseCode}</td>
		<td>${dictClause.clauseName}</td>
		<td>
			<a href="dict/delClause.html?id=${dictClause.id}" class="delClause fa fa-trash" ></a>
			<span>删除</span>
		</td>
	</tr>
	</c:forEach>
	<tr class="newLine">
		<td>
			<span class="comment" onclick="addLine(this)">点击添加</span>
			<input type="text" name="clauseCode" style="display:none;"/>
		</td>
		<td>
			<input type="text" name="clauseName" style="display:none;"/>
		</td>
		<td></td>
	</tr>
</table>
</form>
<script type="text/javascript" >
$(function(){
	//给删除添加委托事件
	$("#clauseList").on("click","a.delClause",delClause);
});
function delClause(event){
	var link = $(event.currentTarget).attr("href");
	$.get(link,null,function(res){
		$(event.currentTarget).parents("tr:first").remove();
	});
	return false;
}
</script>