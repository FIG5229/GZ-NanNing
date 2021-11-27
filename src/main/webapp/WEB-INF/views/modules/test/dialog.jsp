<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
	<head>
		<title>统计分析</title>
		<meta name="decorator" content="default" />
		<style type="text/css">
			.modalPeo{
				width: 1167px;
				height: 780px;
			}
		</style>
	</head>
	<body>
		<div id="openPeo">打开人员信息集</div>
		<div id="openNav">打开人员信息导航</div>
		<div id="openInfo1">打开详细信息1</div>
		<div id="openInfo2">打开详细信息2</div>
		<div id="openSysSelf">绩效考评-系统自评</div>
		<div id="openSysFirst">绩效考评-系统初步考核</div>
		<div id="openSysPublic">绩效考评-系统公示</div>
		<div id="openSysDepartment">绩效考评-负责人签字</div>
		<div id="openAllPublic">绩效考评-负责人签字</div>
		<script type="text/javascript">
			$('#openPeo').click(function(){
				var openPeo = window.parent.document.getElementById("modalPeo").style.display = 'block';
			})
			$('#openNav').click(function(){
				var openPeo = window.parent.document.getElementById("modalNav").style.display = 'block';
			})
			$('#openInfo1').click(function(){
				var openPeo = window.parent.document.getElementById("modalInfo1").style.display = 'block';
			})
			$('#openInfo2').click(function(){
				var openPeo = window.parent.document.getElementById("modalInfo2").style.display = 'block';
			})
			$('#openSysSelf').click(function(){
				var openPeo = window.parent.document.getElementById("modalSysSelf").style.display = 'block';
			})
			$('#openSysFirst').click(function(){
				var openPeo = window.parent.document.getElementById("modalSysFirst").style.display = 'block';
			})
			$('#openSysPublic').click(function(){
				var openPeo = window.parent.document.getElementById("modalSysPublic").style.display = 'block';
			})
			$('#openSysDepartment').click(function(){
				var openPeo = window.parent.document.getElementById("modalSysDepartment").style.display = 'block';
			})
			$('#openAllPublic').click(function(){
				var openPeo = window.parent.document.getElementById("modalAllPublic").style.display = 'block';
			})
			
		</script>
	</body>
</html>
