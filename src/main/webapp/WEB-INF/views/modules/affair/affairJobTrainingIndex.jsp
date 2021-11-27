<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>民警个人训历档案报表</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treeview.jsp" %>
	<style type="text/css">
		.ztree {overflow:auto;margin:0;_margin-top:10px;padding:10px 0 0 10px;}
	</style>
</head>
<body>
<sys:message content="${message}"/>
<ul class="nav nav-tabs">
    <shiro:hasPermission name="affair:affairTraining:view"><li><a href="${ctx}/affair/affairTraining/">练兵比武</a></li></shiro:hasPermission>
    <%--<shiro:hasPermission name="affair:affairTrainingManage:view"><li><a href="${ctx}/affair/affairTrainingManage/list">成绩管理员练兵比武</a></li></shiro:hasPermission>--%>
    <shiro:hasPermission name="affair:affairJobTraining:view"><li class="active"><a href="${ctx}/affair/affairJobTraining/">岗位练兵</a></li></shiro:hasPermission>
    <shiro:hasPermission name="affair:affairTrainCombat:view"><li><a href="${ctx}/affair/affairTrainCombat/">实弹训练</a></li></shiro:hasPermission>
    <shiro:hasPermission name="affair:affairSwapExercise:view"><li><a href="${ctx}/affair/affairSwapExercise/">交流锻炼</a></li></shiro:hasPermission>
    <shiro:hasPermission name="affair:affairTrainOutsource:view"><li><a href="${ctx}/affair/affairTrainOutsource/">委外培训</a></li></shiro:hasPermission>
    <shiro:hasPermission name="affair:affairSendTeacher:view"><li><a href="${ctx}/affair/affairSendTeacher/">送教上门</a></li></shiro:hasPermission>
</ul>
<div id="content" class="row-fluid">
	<div id="left" class="accordion-group" style="height: 100px">
		<div class="accordion-heading">
			<a class="accordion-toggle">组织机构<i class="icon-refresh pull-right" onclick="refreshTree();"></i></a>
		</div>
		<div id="ztree" class="ztree"></div>
	</div>
	<div id="openClose" class="close">&nbsp;</div>
	<div id="right">
		<iframe id="officeContent" src="" width="100%" height="91%" frameborder="0"></iframe>
	</div>
</div>
<script type="text/javascript">
	var setting = {data:{simpleData:{enable:true,idKey:"id",pIdKey:"pId",rootPId:'0'}},
		callback:{onClick:function(event, treeId, treeNode){
				var id = treeNode.pId == '0' ? '' :treeNode.id;
				$('#officeContent').attr("src","${ctx}/affair/affairJobTraining/list?organizationId="+treeNode.id+"&organization="+treeNode.name);
			}
		}
	};

	function refreshTree(){
		$.getJSON("${ctx}/sys/office/treeData",function(data){
			//$.fn.zTree.init($("#ztree"), setting, data).expandAll(false);
			var treeObj =$.fn.zTree.init($("#ztree"), setting, data);
			var nodes = treeObj.getNodes();
			for (var i = 0; i < nodes.length; i++) { //设置节点展开
				treeObj.expandNode(nodes[i], true, false, true);
			}
			var node = treeObj.getNodes()[0];
			treeObj.selectNode(node);
			setting.callback.onClick(null, treeObj.setting.treeId, node);
		});
	}
	refreshTree();

	var leftWidth = 250; // 左侧窗口大小
	var htmlObj = $("html"), mainObj = $("#main");
	var frameObj = $("#left, #openClose, #right, #right iframe");
	function wSize(){
		var strs = getWindowSize().toString().split(",");
		htmlObj.css({"overflow-x":"hidden", "overflow-y":"hidden"});
		mainObj.css("width","auto");
		frameObj.height(strs[0] - 40);
		var leftWidth = ($("#left").width() < 0 ? 0 : $("#left").width());
		$("#right").width($("#content").width()- leftWidth - $("#openClose").width() -5);
		$(".ztree").width(leftWidth - 10).height(frameObj.height() - 60);
	}
</script>
<script src="${ctxStatic}/common/wsize.min.js" type="text/javascript"></script>
</body>
</html>