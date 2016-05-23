<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.threebody.com/cp" prefix="cp" %>
<!DOCTYPE HTML>
<html>
<head></head>
<body>
<form action="admin/userManage.html" method="post" >
	<div class="tab-search">
		<ul>
			<li>用户名：<input type="text" name="username" value="${username}"/></li>
			<li>
			创建时间：
			<input type="text" class="Wdate" name="createTimeStart" value="${createTimeStart}" id="userCreateTimeStart"
					onclick="WdatePicker({maxDate:'#F{$dp.$D(\'userCreateTimeEnd\')}'})" />
			至
			<input type="text" class="Wdate" name="createTimeEnd" value="${createTimeEnd}" id="userCreateTimeEnd"
					onclick="WdatePicker({minDate:'#F{$dp.$D(\'userCreateTimeStart\')}'})" />
			</li>
		</ul>
		<ul>
			<li>昵称：<input type="text" name="realName" value="${realName}"/></li>
			<li>状态：<cp:dictSelect dictCode="d_userStatus" name="status" value="${status}"/></li>
			<li>
				<a href="javascript:void(0);" class="easyui-linkbutton" 
						data-options="iconCls:'icon-search'" onclick="$css.tabSearch(this)">查询</a>
				<a href="javascript:void(0);" class="easyui-linkbutton" 
						data-options="iconCls:'icon-clear'" onclick="$css.form_reset(this)">重置</a>
			</li>
		</ul>
	</div>
</form>
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
		<th style="width:15%;">创建时间</th>
		<th style="width:15%;">电子邮箱</th>
		<th style="width:15%;">电话号码</th>
		<th style="width:10%;">状态</th>
		<th style="width:19%;">操作</th>
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
			<a href="javascript:void(0);" userid="${user.id}" class="user_role fa fa-user" ></a>
			<span>角色配置</span>
			<a href="page/addOrUpdateUser.html?id=${user.id}" class="editUser fa fa-edit" ></a>
			<span>编辑</span>
			<a href="user/delete.html?id=${user.id}" class="delUser fa fa-trash" ></a>
			<span>删除</span>
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
	$("#userRole").prev(".pageSplit").find("a.page_btn").on("click",$css.jumpPage);
	//给删除添加委托事件
	$("#userList")
	.on("click","a.delUser",{url:"admin/userManage.html"},$css.delRecord)
	.on("click","a.editUser",{tabName:"编辑用户"},$css.editRecord)
	.on("change",".all:checkbox",$css.selectAll);
	$("a.addUser").on("click",{tabName:"创建用户"},$css.editRecord);
	$("a.delUsers").on("click",{tableId:"userList",url:"admin/userManage.html"},$css.delAllRecord);
	var openUserRole = function(event){
		var userId = $(event.currentTarget).attr("userid");
		var save = function(){
			var $form = $("form#user_role");
			$.post($form.attr("action"),$form.serializeArray(),function(res){
				$.messager.alert("提示",res["msg"],res["type"],function(){
					$("#userRole").dialog("close");
				});
			},"json");
		};
		$("#userRole").dialog({
		    title: "配置用户角色",
			width: 600,
			height: 400,
			closed: false,
			cache: false,
			href: "role/roleList.html?id="+userId,
			buttons:[{
				text:"保存",
				handler:save,
				iconCls:"icon-save"
			},{
				text:"取消",
				handler:function(){$("#userRole").dialog("close");},
				iconCls:"icon-cancel"
			}]
		});
	};
	$("#userList").on("click","a.user_role",openUserRole);
});
</script>
</body>
</html>