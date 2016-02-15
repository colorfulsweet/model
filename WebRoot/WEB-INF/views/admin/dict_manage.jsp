<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.threebody.com/cp" prefix="cp" %>

<div class="btn-header">
	<a href="page/addOrUpdateDict.html" class="easyui-linkbutton addDict" data-options="iconCls:'icon-add'" >添加字典</a>
</div>
<table class="bordered" id="dictList">
	<tr>
		<th style="width:5%">序号</th>
		<th style="width:12%;">字典编码</th>
		<th style="width:15%;">字典名称</th>
		<th style="width:25%;">备注</th>
		<th style="width:12%;">操作</th>
	</tr>
	<% int i=0; %>
	<c:forEach var="dict" items="${page.result}" >
	<tr>
		<td><%=++i %></td>
		<td>${dict.dictCode}</td>
		<td>${dict.dictName}</td>
		<td>${dict.remark}</td>
		<td >
			<a href="javascript:void(0);" onclick="openDictClause('${dict.id}')" class="fa fa-bars"></a>
			<span>字典项</span>
			<a href="page/addOrUpdateDict.html?id=${dict.id}" class="editDict fa fa-edit"></a>
			<span>编辑</span>
			<a href="dict/delete.html?id=${dict.id}" class="delDict fa fa-trash" ></a>
			<span>删除</span>
		</td>
	</tr>
	</c:forEach>
</table>
<div class="pageSplit">
	<cp:pageSplit page="${page}" />
</div>
<div id="DictClause" ></div>
<script type="text/javascript" >
$(function(){
	//给分页按钮添加点击事件
	$("#DictClause").prev(".pageSplit").find("a.page").on("click",jumpPage);
	//给删除添加委托事件
	$("#dictList")
	.on("click","a.delDict",{url:"admin/dictManage.html"},delRecord)
	.on("click","a.editDict",{tabName:"编辑字典"},editRecord);
	$("a.addDict").on("click",{tabName:"添加字典"},editRecord);
});
/**
 * 打开配置字典项页面
 */
function openDictClause(dictId){
	var save = function(e){
		//e中的currentTarget对象是点击触发对象
		if(newLineFlag){
			//只有在新增了一行以后,保存与编辑才有效
			return;
		}
		var $form = $("form#dictClause");
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
		var $form = $("form#dictClause");
		addLine($form.find("tr:last span.comment"));
	};
	$("#DictClause").dialog({
	    title: "配置字典项",
		width: 600,
		height: 400,
		closed: false,
		cache: false,
		href: "dict/dictClause.html?id="+dictId,
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