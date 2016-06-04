<%@ page language="java" pageEncoding="UTF-8"%>

<script type="text/javascript">
new $.TipBox({
	type:"tip",
	str:"登陆超时，请重新登陆",
	setTime:1500,
	hasMask:true,
	callBack:function(){
		location.reload(true);
	}
});
</script>
