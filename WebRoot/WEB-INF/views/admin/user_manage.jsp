<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.threebody.com/page" prefix="page" %>

<c:set var="path" value="<%=request.getContextPath() %>" scope="page"/>
<c:set var="basePath" scope="page">
	<%=request.getScheme()+"://"+request.getServerName()+":"
		+request.getServerPort()+request.getContextPath()+"/" %>
</c:set>
<div class="btn-header">
	<button id="btn" class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="addUser()">创建用户</button>
</div>
<table class="bordered" >
	<tr>
		<th style="width:5%">序号</th>
		<th style="width:8%;">用户名</th>
		<th style="width:8%;">昵称</th>
		<th style="width:18%;">创建时间</th>
		<th style="width:18%;">电子邮箱</th>
		<th style="width:18%;">电话号码</th>
		<th style="width:10%;">状态</th>
		<th style="width:10%;">操作</th>
	</tr>
	<% int i=0; %>
	<c:forEach var="user" items="${users}" >
	<tr>
		<td><%=++i %></td>
		<td>${user.username}</td>
		<td>${user.realName}</td>
		<td><fmt:formatDate value="${user.createTime}" type="date" pattern="yyyy年MM月dd日 HH:mm"/></td>
		<td>${user.eMail}</td>
		<td>${user.tel}</td>
		<td>${user.status}</td>
		<td style="text-align:center;">
			<a href="user/delete.html?id=${user.id}" class="delUser">删除</a>
			禁用/启用
		</td>
	</tr>
	</c:forEach>
</table>
<div class="pageSplit">
	<page:htmlPage page="${page}" />
</div>
<script type="text/javascript" >
$(function(){
	//给分页按钮添加点击事件
	$("a.page").on("click",jumpPage);
	//给删除添加委托事件
	$("table.bordered").on("click","a.delUser",{url:"admin/userManage.html"},delRecord);
});
function addUser() {
	//打开新增用户页面
	addTab("新增用户","${basePath}"+"page/addOrUpdateUser.html",null);
}
</script>