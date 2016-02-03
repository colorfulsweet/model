<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

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
			<a href="dict/delClause.html?id=${dictClause.id}" class="delClause fa fa-trash" >
				<span>删除</span>
			</a>
		</td>
	</tr>
	</c:forEach>
</table>
<script type="text/javascript" >
$(function(){
	//给删除添加委托事件
	$("#clauseList").on("click","a.delClause",delClause);
	$("#clauseList a.fa").on("mouseover mouseout",showText);
});
function delClause(event){
	alert("aaaa");
	return false;
}
</script>