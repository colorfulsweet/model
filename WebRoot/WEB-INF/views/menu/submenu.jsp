<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<form action="menu/saveSubmenu.html" method="post" id="submenus">
<input type="hidden" name="menu.id" value="${menu.id}" />
<table class="bordered" id="submenuList" >
	<tr>
		<th style="width:20%;">子菜单名称</th>
		<th style="width:30%;">URL地址</th>
		<th style="width:30%;">备注</th>
		<th style="width:20%;">操作</th>
	</tr>
	<c:forEach var="submenu" items="${submenuList}" >
	<tr>
		<td>${submenu.submenuName}</td>
		<td>${submenu.url}</td>
		<td>${submenu.remark}</td>
		<td>
			<a href="menu/delSubmenu.html?id=${submenu.id}" class="delSubmenu fa fa-trash" ></a>
			<span>删除</span>
		</td>
	</tr>
	</c:forEach>
	<tr class="newLine">
		<td>
			<span class="comment" onclick="addLine(this)">点击添加</span>
			<input type="text" name="submenuName" style="display:none;"/>
		</td>
		<td>
			<input type="text" name="url" style="display:none;"/>
		</td>
		<td>
			<input type="text" name="remark" style="display:none;"/>
		</td>
		<td></td>
	</tr>
</table>
</form>
<script type="text/javascript" >
$(function(){
	//给删除添加委托事件
	$("#submenuList").on("click","a.delSubmenu",delSubmenu);
});
function delSubmenu(event){
	var link = $(event.currentTarget).attr("href");
	$.get(link,null,function(res){
		$(event.currentTarget).parents("tr:first").remove();
	});
	return false;
}
</script>

