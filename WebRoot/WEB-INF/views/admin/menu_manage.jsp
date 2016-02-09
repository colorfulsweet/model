<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.threebody.com/cp" prefix="cp" %>

<div class="btn-header">
	<button id="btn" class="easyui-linkbutton" data-options="iconCls:'icon-add'" >创建菜单</button>
</div>
<table class="bordered" >
	<tr>
		<th style="width:3%;">序号</th>
		<th style="width:8%;">菜单名称</th>
		<th style="width:15%;">URL地址</th>
		<th style="width:5%;">图标</th>
		<th style="width:8%;">备注</th>
		<th style="width:10%;">操作</th>
	</tr>
	<% int i=0; %>
	<c:forEach var="menu" items="${page.result}" >
	<tr>
		<td><%=++i %></td>
		<td>${menu.menuName}</td>
		<td>${menu.url}</td>
		<td><span class="fa fa-${menu.icon}"></span>&nbsp;&nbsp;${menu.icon}</td>
		<td>${menu.remark}</td>
		<td>
			<a href="javascript:void(0);" class="fa fa-list-ul"></a>
			<span>子菜单</span>
			<a href="javascript:void(0);" class="fa fa-edit"></a>
			<span>编辑</span>
			<a href="javascript:void(0);" class="fa fa-trash"></a>
			<span>删除</span>
		</td>
	</tr>
	</c:forEach>
</table>
<div class="pageSplit">
	<cp:pageSplit page="${page}" />
</div>