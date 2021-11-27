<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>活动情况管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			var curWwwPath=window.document.location.href;
			//获取主机地址之后的目录，如： myproj/view/my.jsp
			var pathName=window.document.location.pathname;
			var pos=curWwwPath.indexOf(pathName);
			//获取主机地址，如： http://localhost:8080
			var localhostPaht=curWwwPath.substring(0,pos);
			//获取带"/"的项目名，如：/myproj
			var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
			//得到了 http://localhost:8080/myproj
			var realPath=localhostPaht+projectName;
			//var url = 'http://'+window.location.host+'/politics/public/view/index.html';
			var url = localhostPaht+projectName + '/public/view/index.html';
			window.location.href=url;
			//alert(url)
		});

	/*	http://192.168.73.127:8083/politics/public/view/index.html*/
	</script>
</head>
<body>
</body>
</html>