<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.threebody.com/cp" prefix="cp" %>

<div class="btn-header">
	<a href="page/addOrUpdateMenu.html" id="btn" class="easyui-linkbutton addMenu" data-options="iconCls:'icon-add'" >添加菜单</a>
</div>
<table class="bordered" id="menuList">
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
			<a href="javascript:void(0);" class="fa fa-list-ul" onclick="openSubmenList('${menu.id}')"></a>
			<span>子菜单</span>
			<a href="page/addOrUpdateMenu.html?id=${menu.id}" class="editMneu fa fa-edit"></a>
			<span>编辑</span>
			<a href="menu/delete.html?id=${menu.id}" class="delMenu fa fa-trash"></a>
			<span>删除</span>
		</td>
	</tr>
	</c:forEach>
</table>
<div class="pageSplit">
	<cp:pageSplit page="${page}" />
</div>
<div id="subMenu"></div>
<script type="text/javascript">
$(function(){
	$("#subMenu").prev(".pageSplit").find("a.page").on("click",jumpPage);
	$("#menuList")
	.on("click","a.delMenu",{url:"admin/menuManage.html"},delRecord)
	.on("click","a.editMneu",{tabName:"编辑菜单"},editRecord);
	$("a.addMenu").on("click",{tabName:"添加菜单"},editRecord);
});
function openSubmenList(menuId){
	var save = function(e){
		//e中的currentTarget对象是点击触发对象
		if(newLineFlag){
			//只有在新增了一行以后,保存与编辑才有效
			return;
		}
		var $form = $("form#submenus");
		$.post($form.attr("action"),$form.serializeArray(),function(res){
			var edit_tr = $form.find("tr:last");
			var new_tr = edit_tr.clone();
			new_tr.find(":input").each(function(index,element){
				$(element).replaceWith(element.value);
			});
			edit_tr.before(new_tr);
			addLine(edit_tr.find("span.comment"));
		});
	};
	var cancel = function(e){
		if(newLineFlag){
			//只有在新增了一行以后,保存与编辑才有效
			return;
		}
		var $form = $("form#submenuList");
		addLine($form.find("tr:last span.comment"));
	};
	$("#subMenu").dialog({
	    title: "配置子菜单",
		width: 600,
		height: 400,
		closed: false,
		cache: false,
		href: "menu/submenuList.html?id="+menuId,
		buttons:[{
			text:"保存",
			handler:save,
			iconCls:"icon-save"
		},{
			text:"取消",
			handler:cancel,
			iconCls:"icon-cancel"
		}]
	});
}
</script>