<%@ page language="java" pageEncoding="UTF-8"%>

<div class="iconList">
	<ul>
		<li class="fa fa-adjust">adjust</li>
		<li class="fa fa-anchor">anchor</li>
		<li class="fa fa-archive">archive</li>
		<li class="fa fa-area-chart">area-chart</li>
	</ul>
	<ul>
		<li class="fa fa-arrows">arrows</li>
		<li class="fa fa-arrows-h">arrows-h</li>
		<li class="fa fa-arrows-v">arrows-v</li>
		<li class="fa fa-asterisk">asterisk</li>
	</ul>
</div>
<script type="text/javascript">
$(function(){
	var iconList = $(".iconList:last");
	iconList.on("click",iconList.find("li.fa"),function(event){
		var content = event.target.innerHTML;
		iconList.prevAll("input[type=text]").val(content);
		iconList.prevAll("span").removeClass().addClass("fa fa-"+content);
	});
});
</script>