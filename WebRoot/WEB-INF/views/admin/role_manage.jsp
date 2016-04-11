<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.threebody.com/cp" prefix="cp" %>
<!DOCTYPE HTML>
<html>
<head></head>
<body>
<form action="admin/roleManage.html" method="post" >
	<div class="tab-search">
		<ul>
			<li>角色名称:<input type="text" name="roleName" value="${roleName}"/></li>
			<li>创建时间:<input type="text" name="createTime" value="${createTime}"/></li>
		</ul>
		<ul>
			<li>
				<a href="javascript:void(0);" class="easyui-linkbutton" 
						data-options="iconCls:'icon-search'" onclick="$css.tabSearch(this)">查询</a>
			</li>
			<li>
				<a href="javascript:void(0);" class="easyui-linkbutton" 
						data-options="iconCls:'icon-clear'" onclick="$css.form_reset(this)">重置</a>
			</li>
		</ul>
	</div>
	<div class="btn-header">
		<a href="page/addOrUpdateRole.html" class="easyui-linkbutton addRole" data-options="iconCls:'icon-add'" >创建角色</a>
	</div>
	<table class="bordered" id="roleList" >
		<tr>
			<th style="width:8%;">角色名称</th>
			<th style="width:18%;">创建时间</th>
			<th style="width:8%;">操作</th>
		</tr>
		<c:forEach var="role" items="${page.result}" >
		<tr>
			<td>${role.roleName}</td>
			<td><fmt:formatDate value="${role.createTime}" type="date" pattern="yyyy年MM月dd日 HH:mm"/></td>
			<td>
				<a href="page/addOrUpdateRole.html?id=${role.id}" class="editRole fa fa-edit" ></a>
				<span>编辑</span>
				<a href="role/delete.html?id=${role.id}" class="delRole fa fa-trash" ></a>
				<span>删除</span>
			</td>
		</tr>
		</c:forEach>
	</table>
</form>
<div class="pageSplit">
	<cp:pageSplit page="${page}" />
</div>
<script>
$(function(){
	$("#roleList").next(".pageSplit").find("a.page_btn").on("click",$css.jumpPage);
	$("a.addRole").on("click",{tabName:"创建角色"},$css.editRecord);
	$("#roleList")
	.on("click","a.delRole",{url:"admin/roleManage.html"},$css.delRecord)
	.on("click","a.editRole",{tabName:"编辑角色"},$css.editRecord);
});
</script>
</body>
</html>