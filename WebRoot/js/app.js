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
			//给新添加的页面中的分页按钮绑定点击事件
			body.find("a.page").on("click",jumpPage);
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
 * 显示/隐藏 超链接当中的文字
 */
function showText(e){
	$(e.target).children("span").fadeToggle("fast");
}
