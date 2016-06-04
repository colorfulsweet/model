<%@ page language="java" pageEncoding="UTF-8"%>
<div class="tree-box">
	<ul id="deptTree" ></ul>
</div>
<div style="float:left;width:60%;">
	<div class="btn-header">
		<a href="javascript:;" class="easyui-linkbutton" id="addDept" data-options="iconCls:'icon-add'" >创建部门</a>
		<a href="javascript:;" class="easyui-linkbutton" id="delDept" data-options="iconCls:'icon-remove'" >删除部门</a>
	</div>
	<form class="addDeptForm" method="post" action="dept/save.html" onSubmit="return $css.ajaxSubmit(this,'admin/deptManage.html',true)">
		<input type="hidden" name="id" value="{{id}}" />
		<table class="table-input">
			<tr>
				<th style="width:20%;">上级部门ID</th>
				<td style="width:80%;" >
					<input type="text" name="parentId" v-model="parentId" title="当创建根节点，不需要上级部门时请清空" readonly="readonly"/>
					<button type="button" class="btn white medium" id="clearParentId">清空</button>
				</td>
			</tr>
			<tr>
				<th style="width:20%;">部门编码</th>
				<td style="width:80%;"><input type="text" name="deptCode" v-model="deptCode" required="required"/></td>
			</tr>
			<tr>
				<th style="width:20%;">部门名称</th>
				<td style="width:80%;"><input type="text" name="deptName" v-model="text" required="required"/></td>
			</tr>
			<tr>
				<th style="width:20%;">备注</th>
				<td style="width:80%;"><textarea name="remark" >{{remark}}</textarea></td>
			</tr>
			<tr>
				<th style="width:20%;">序号</th>
				<td style="width:80%;"><input type="text" name="deptIndex" v-model="deptIndex" required="required"/></td>
			</tr>
		</table>
		<div class="btn-box">
			<input type="submit" class="btn green medium" value="保存" />
		</div>
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
			_.extendOwn(deptData, node);
			if(node && !node.loaded){
				appendNode(node);
			}
		}
	});
	
	var createDept = function(){
		var selectedNode = $("#deptTree").tree("getSelected");
		if(selectedNode) {
			var nullObj = _.mapObject(deptData, function(val, key){
				return null;
			});
			_.extendOwn(deptData, nullObj);
			deptData.parentId = selectedNode.id;
		}
	};
	$("#addDept").click(createDept);
	$("#clearParentId").click(function(event){
		deptData.parentId = null;
		return false;
	});
});
</script>