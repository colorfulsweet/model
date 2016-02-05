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
	<button id="btn" class="easyui-linkbutton" data-options="iconCls:'icon-add'" >创建角色</button>
</div>
<table class="bordered" >
	<tr>
		<th style="width:8%;">角色名称</th>
		<th style="width:18%;">创建时间</th>
		<th style="width:8%;">操作</th>
	</tr>
	<c:forEach var="role" items="${page.result}" >
	<tr>
		<td>${role.roleName}</td>
		<td><fmt:formatDate value="${role.createTime}" type="date" pattern="yyyy年MM月dd日 HH:mm"/></td>
		<td style="text-align:center;">
			删除 禁用/启用
		</td>
	</tr>
	</c:forEach>
</table>
<div class="pageSplit">
	<page:htmlPage page="${page}" />
</div>