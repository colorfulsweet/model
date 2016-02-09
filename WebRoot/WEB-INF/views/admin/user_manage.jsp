<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.threebody.com/cp" prefix="cp" %>

<div class="btn-header">
	<a href="page/addOrUpdateUser.html" class="easyui-linkbutton addUser" data-options="iconCls:'icon-add'" >创建用户</a>
	<a href="user/deleteUsers.html" class="easyui-linkbutton delUsers" data-options="iconCls:'icon-remove'" >批量删除</a>
</div>
<table class="bordered" id="userList">
	<tr>
		<th style="width:3%;"><input type="checkbox" class="all"/></th>
		<th style="width:5%;">序号</th>
		<th style="width:8%;">用户名</th>
		<th style="width:8%;">昵称</th>
		<th style="width:18%;">创建时间</th>
		<th style="width:18%;">电子邮箱</th>
		<th style="width:18%;">电话号码</th>
		<th style="width:10%;">状态</th>
		<th style="width:10%;">操作</th>
	</tr>
	<% int i=0; %>
	<c:forEach var="user" items="${page.result}" >
	<tr>
		<td style="text-align:center;"><input type="checkbox" name="userId" value="${user.id}"/></td>
		<td><%=++i %></td>
		<td>${user.username}</td>
		<td>${user.realName}</td>
		<td><fmt:formatDate value="${user.createTime}" type="date" pattern="yyyy年MM月dd日 HH:mm"/></td>
		<td>${user.email}</td>
		<td>${user.tel}</td>
		<td><cp:dictType dictCode="d_userStatus" clauseCode="${user.status}"/></td>
		<td>
			<a href="user/delete.html?id=${user.id}" class="delUser fa fa-trash" ></a>
			<span>删除</span>
			<a href="page/addOrUpdateUser.html?id=${user.id}" class="editUser fa fa-edit" ></a>
			<span>编辑</span>
		</td>
	</tr>
	</c:forEach>
</table>
<div class="pageSplit">
	<cp:pageSplit page="${page}" />
</div>
<div id="userRole"></div>
<script type="text/javascript" >
$(function(){
	//给分页按钮添加点击事件
	$("#userRole").prev(".pageSplit").find("a.page").on("click",jumpPage);
	//给删除添加委托事件
	$("#userList")
	.on("click","a.delUser",{url:"admin/userManage.html"},delRecord)
	.on("click","a.editUser",{tabName:"编辑用户"},editRecord)
	.on("change",".all:checkbox",selectAll);
	$("a.addUser").on("click",{tabName:"创建用户"},editRecord);
	$("a.delUsers").on("click",{tableId:"userList",url:"admin/userManage.html"},delAllRecord);
});
</script>