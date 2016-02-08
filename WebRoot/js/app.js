$(function() {
	//加载手风琴菜单样式
	var Accordion = function(el, multiple) {
		this.el = el || {};
		this.multiple = multiple || false;
		var links = this.el.find('.link');
		links.on('click', {el: this.el, multiple: this.multiple}, this.dropdown);
	};
	Accordion.prototype.dropdown = function(e) {
		var $el = e.data.el;
			$this = $(this),
			$next = $this.next();

		$next.slideToggle();
		$this.parent().toggleClass('open');
		if (!e.data.multiple) {
			$el.find('.submenu').not($next).slideUp().parent().removeClass('open');
		};
	};
	new Accordion($('#accordion'), false);
});

$(function(){
	$(".submenu a").on("click",function(e){
		e.preventDefault();
		var target = $(e.target);
		addTab(target.text(),target.attr("href"));
	});
});
/**
 * 新增页签
 * @param title 页签的标题
 * @param href 页签内容的URL地址
 * @param params 请求携带的参数
 */
function addTab(title,href,params){
	var callback = function(res){
		var $tab = $('#content-tab');
		//判断该选项卡是否已经存在
		if($tab.tabs("exists",title)){
			//如果存在则选中该选项卡
			$tab.tabs("select",title);
		} else {
			//如果不存在则新增一个选项卡
			var body = $(res);
			$tab.tabs('add',{"title":title,"content":body,"closable": true});
		}
	};
	$.ajax({
		type:"GET",
		url:href,
		data:params,
		success: callback,
		error:function(res){
			$.messager.alert("提示","加载页面失败!","warning");
		}
	});
}
/**
 * 页面跳转
 * @param event 触发的事件对象
 */
function jumpPage(event){
	var params = {
			pageNow : $(this).attr("page"),
			pageSize : $(this).nextAll("select").val()
		};
	var href = $(this).attr("href");
	$("#content-tab")
		.tabs("getSelected")
		.panel("refresh", href + "?" + $.param(params));
	event.preventDefault();
}
/**
 * 表单ajax提交
 * @param form 表单DOM对象
 */
function ajaxSubmit(form){
	$.post($(form).attr("action"),$(form).serialize(),function(res){
		$.messager.alert("提示","操作成功","info",function(){
			var $tab = $("#content-tab");
			var selected = $tab.tabs("getSelected");
			$tab.tabs("close",$tab.tabs('getTabIndex',selected));
		});
	});
	return false;
}
/**
 * 删除记录
 * @param event
 * @returns {Boolean}
 */
function delRecord(event){
	var target = $(event.currentTarget);
	var del = function(){
		$.get(target.attr("href"),null,function(res){
			$.messager.alert("提示","删除成功","info",function(){
				$("#content-tab")
				.tabs("getSelected")
				.panel("refresh", event.data.url);
			});
		});
	};
	$.messager.confirm("操作确认","确认删除吗?",function(res){
		if(res){del();}
	});
	return false;
}
/**
 * 编辑记录
 * @param event
 * @returns {Boolean}
 */
function editRecord(event){
	if(typeof event !== "undefined"){
		addTab(event.data.tabName,$(event.currentTarget).attr("href"),null);
	}
	return false;
}
/**
 * 添加一行记录(适用于无分页的页面)
 * @param target
 */
function addLine(target){
	$(target).toggle();
	$(target).parents("tr:first").find(":input").toggle().val("");
}
/**
 * 表格中的多选框 - 选中全部
 * @param event
 */
function selectAll(event){
	var checkboxs = $(event.currentTarget).parents("table:first").find("td>:checkbox");
	if($(event.currentTarget).is(":checked")){
		checkboxs.prop("checked",true);
	} else {
		checkboxs.removeAttr("checked");
	}
}
/**
 * 记录批量删除
 * (事件传递两个参数
 * 分别是表格的ID - tableId
 * 和当前页面的URL地址 - url)
 * @param event
 * @returns {Boolean}
 */
function delAllRecord(event){
	var tableId = event.data.tableId;
	var url = event.data.url;
	var checkedBox = $("#"+tableId).find(":checkbox:checked[class!=all]");
	var $form = $("<form></form>");
	$form.append(checkedBox.clone());
	var del = function(){
		$.post($(event.currentTarget).attr("href"),$form.serialize(),function(res){
			if(res == "success"){
				$.messager.alert("提示","删除成功","info",function(){
					$("#content-tab")
					.tabs("getSelected")
					.panel("refresh", url);
				});
			} else {
				$.messager.alert("提示","操作失败!","warning");
			}
		});
	};
	$.messager.confirm("操作确认","确认批量删除吗?",function(res){
		if(res){del();}
	});
	return false;
}