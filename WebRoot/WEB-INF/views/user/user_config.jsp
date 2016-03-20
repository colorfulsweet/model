<%@ page language="java" pageEncoding="UTF-8"%>

<!DOCTYPE HTML>
<html>
<head></head>
<body>
<form action="user/uploadIcon.html" method="post" 
		enctype="multipart/form-data" onsubmit="return $css.uploadFile(this)">
	<div class="image-preview" id="user_icon"></div>
	<input type="file" name="attach" id="image-file"
		accept="image/jpeg,image/png,image/gif" />
	<p id="file-info"></p>
	<input type="submit" value="提交" />
</form>
<script type="text/javascript">
(function() {
	//文件上传 表单元素
	var fileInput = document.getElementById("image-file");
	//文件信息显示区域
	var info = document.getElementById("file-info");
	//图片预览区域
	var preview = document.getElementById("user_icon");

	// 监听change事件:
	fileInput.addEventListener("change",function() {
		// 清除背景图片:
		preview.style.backgroundImage = null;
		// 检查是否选择文件:
		if (!fileInput.value) {
			info.innerHTML = null;
			return;
		}
		// 获取File对象:
		var file = fileInput.files[0];
		// 获取File信息:
		info.innerHTML = "文件名: " + file.name + "<br>"
					+ "文件大小: " + file.size + "<br>"
					+ "修改时间: " + file.lastModifiedDate;
		// 读取文件:
		var reader = new FileReader();
		reader.onload = function(e) {
			//成功读取后的回调函数
			var data = e.target.result; //base64编码
			preview.style.backgroundImage = "url(" + data + ")";
		};
		// 以DataURL的形式读取文件:
		reader.readAsDataURL(file);
	});
})();
</script>
</body>
</html>
