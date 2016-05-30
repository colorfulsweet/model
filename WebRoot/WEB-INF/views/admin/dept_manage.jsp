<%@ page language="java" pageEncoding="UTF-8"%>
<div class="tree-box">
	<ul id="deptTree" ></ul>
</div>
<div style="float:left;width:60%;">
	<form class="addDeptForm" method="post" action="dept/save.html" onSubmit="return $css.ajaxSubmit(this)">
		<input type="hidden" name="id" value="{{id}}" />
		<table class="zebra">
			<tr>
				<th style="width:20%;">部门编码</th>
				<td style="width:80%;"><input type="text" name="deptCode" v-model="deptCode"/></td>
			</tr>
			<tr>
				<th style="width:20%;">部门名称</th>
				<td style="width:80%;"><input type="text" name="deptName" v-model="text"/></td>
			</tr>
			<tr>
				<th style="width:20%;">备注</th>
				<td style="width:80%;"><textarea name="remark" >{{remark}}</textarea></td>
			</tr>
			<tr>
				<th style="width:20%;">序号</th>
				<td style="width:80%;"><input type="text" name="deptIndex" v-model="deptIndex"/></td>
			</tr>
		</table>
	</form>

</div>
<script>
$(function(){
	var url = "dept/getDeptTree.html";
	var appendNode = function(node){
		$.post(url, {parentId:node.id},function(response){
			//标记当前节点的下级节点已加载过
			node.loaded = true;
			if(!response && response.length==0){
				return;
			}
			$("#deptTree").tree("append", {
				parent : node.target,
				data : response
			});
		},"json");
	};
	var deptData = new $css.ModelData(".addDeptForm");
	new Vue({
		el:".addDeptForm",
		data : deptData
	});
	$("#deptTree").tree({
		animate:true,
		lines:true,
		url:url,
		onClick:function(node){
			_.extend(deptData, node);
			if(node && !node.loaded){
				appendNode(node);
			}
		}
	});
});
</script>