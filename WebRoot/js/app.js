(function(){
var FuncTools = function(){
	/**
	 * 新增页签
	 * @param title 页签的标题
	 * @param href 页签内容的URL地址
	 * @param params 请求携带的参数
	 */
	this.addTab = function(title,href,params){
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
	};
	/**
	 * 页面跳转
	 * @param event 触发的事件对象
	 */
	this.jumpPage = function(event){
		var params = {
				pageNow : $(this).attr("page"),
				pageSize : $(this).nextAll("select").val()
			};
		var form = $(this).parents(".pageSplit").prevAll("form");
		$.each(form.find(":input"), function(index, element){
			params[element.name] = element.value;
		});
		var href = $(this).attr("href");
		$("#content-tab")
			.tabs("getSelected")
			.panel("refresh", href + "?" + $.param(params));
		event.preventDefault();
	};
	/**
	 * 执行模糊查询
	 * @param "查询"按钮DOM对象
	 */
	this.tabSearch = function(target){
		var form = $(target).parents("form");
		$("#content-tab")
			.tabs("getSelected")
			.panel("refresh", form.attr("action") + "?" + form.serialize());
		return false;
	};
	/**
	 * 表单重置
	 * @param "重置"按钮DOM元素
	 */
	this.form_reset = function(target){
		var form = $(target).parents("form");
		form.find("input[type=text]").attr("value","");
		form.find("select > option").removeAttr("selected");
		form[0].reset();
	};
	/**
	 * 表单ajax提交
	 * @param form 表单DOM对象
	 * @returns {Boolean}
	 */
	this.ajaxSubmit = function(form){
		var params = $(form).serializeArray();
		for(var index=params.length-1 ; index>=0 ; index--) {
			//将为空的字段从提交参数中移除(空串或者undefined)
			if(!params[index].value) {
				params.splice(index,1);
			}
		}
		$.post($(form).attr("action"),params,function(res){
			$.messager.alert("提示",res["msg"],res["type"],function(){
				var $tab = $("#content-tab");
				var selected = $tab.tabs("getSelected");
				$tab.tabs("close",$tab.tabs('getTabIndex',selected));
			});
		},"json");
		return false;
	};
	/**
	 * 删除记录
	 * @param event
	 * @returns {Boolean}
	 */
	this.delRecord = function(event){
		var del = function(){
			$.get($(event.currentTarget).attr("href"),null,function(res){
				$.messager.alert("提示",res["msg"],res["type"],function(){
					$("#content-tab")
					.tabs("getSelected")
					.panel("refresh", event.data.url);
				});
			},"json");
		};
		$.messager.confirm("操作确认","确认删除吗?",function(result){
			if(result){del();}
		});
		return false;
	};
	/**
	 * 编辑记录
	 * @param event
	 * @returns {Boolean}
	 */
	this.editRecord = function(event){
		if(event){
			window.$css.addTab(event.data.tabName,
					$(event.currentTarget).attr("href"),null);
		}
		return false;
	};
	/**
	 * 表格中的多选框 - 选中全部
	 * @param event
	 */
	this.selectAll = function(event){
		var checkboxs = $(event.currentTarget).parents("table:first").find("td>:checkbox");
		if($(event.currentTarget).is(":checked")){
			checkboxs.prop("checked",true);
		} else {
			checkboxs.removeAttr("checked");
		}
	};
	/**
	 * 记录批量删除
	 * (事件传递两个参数
	 * 分别是表格的ID - tableId
	 * 和当前页面的URL地址 - url)
	 * @param event
	 * @returns {Boolean}
	 */
	this.delAllRecord = function(event){
		var tableId = event.data.tableId;
		var checkedBox = $("#"+tableId).find(":checkbox:checked[class!=all]");
		var $form = $("<form></form>");
		$form.append(checkedBox.clone());
		var del = function(){
			$.post($(event.currentTarget).attr("href"),$form.serialize(),function(res){
				$.messager.alert("提示",res["msg"],res["type"],function(){
					$("#content-tab")
					.tabs("getSelected")
					.panel("refresh", event.data.url);
				});
			},"json");
		};
		$.messager.confirm("操作确认","确认批量删除吗?",function(result){
			if(result){del();}
		});
		return false;
	};
	this.newLineFlag = true;
	/**
	 * 添加一行记录(适用于无分页的页面)
	 * @param target
	 */
	this.addLine = function(target){
		if($(target).is(":hidden")){
			window.$css.newLineFlag = true;
		} else {
			window.$css.newLineFlag = false;
		}
		$(target).toggle();
		$(target).parents("tr:first").find(":input").toggle().val("");
	},
	/**
	 * 删除一行记录(适用于无分页的页面)
	 */
	this.delLine = function(event){
		var link = $(event.currentTarget).attr("href");
		$.get(link,null,function(res){
			$(event.currentTarget).parents("tr:first").remove();
		});
		event.preventDefault();
	};
	/**
	 * ajax上传表单当中的文件
	 */
	this.uploadFile = function(form){
		$.ajax({
		    url: $(form).attr("action"),
		    type: "POST",
		    cache: false,
		    data: new FormData(form),
		    processData: false,
		    contentType: false,
		    complete:function(res){
		    	var info = JSON.parse(res.responseText);
		    	$.messager.alert("提示",info["msg"],info["type"]);
		    },
		});
		return false;
	};
};
window.$css = new FuncTools();
$(function() {
	//加载手风琴菜单样式
	var Accordion = function(el, multiple) {
		this.el = el || {};
		this.multiple = multiple || false;
		var links = this.el.find('.link');
		links.on('click', {el: this.el, multiple: this.multiple}, this.dropdown);
	};
	Accordion.prototype.dropdown = function(e) {
		var $el = e.data.el,
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
	var openMenuTab = function(e){
		e.preventDefault();//阻止默认事件
		var target = $(e.target);
		var href = target.attr("href");
		if(href!==undefined && href.length != 0){
			$css.addTab(target.text(),target.attr("href"));
		}
	};
	$(".submenu a,.link a,.top>.nav .tabNav").on("click",openMenuTab);
	$(".top_icon").error(function(event){
		//头像加载失败时显示默认头像
		event.target.src = "images/default_icon.png";
	});
});
})();