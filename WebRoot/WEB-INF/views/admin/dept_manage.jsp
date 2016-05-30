<%@ page language="java" pageEncoding="UTF-8"%>
<div style="width:200px;height:60%;border:1px solid #ccc;">
	<ul id="deptTree" class="easyui-tree" ></ul>
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
	$("#deptTree").tree({
		animate:true,
		lines:true,
		url:url,
		onClick:function(node){
			if(node && !node.loaded){
				appendNode(node);
			}
		}
	});
});

</script>