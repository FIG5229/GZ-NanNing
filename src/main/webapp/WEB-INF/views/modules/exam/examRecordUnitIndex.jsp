<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>单位考评档案</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treeview.jsp" %>
	<style type="text/css">
		.ztree {overflow:auto;margin:0;_margin-top:0px;padding:10px 0 0 10px;height: auto}
		.treeTab{height: 100%;width: auto;font-size: 16px;}
		.check_tab{color: black;font-size: 18px}
	</style>
	<script>
		function toggleIdex(url) {
			self.location.href = url;
		}
	</script>
</head>
<body>
<sys:message content="${message}"/>
<div id="content" class="row-fluid">
	<%--<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/exam/examRecord/?type=unit">单位</a></li>
		<li><a href="${ctx}/exam/examRecord/?type=person">个人</a></li>
	</ul>--%>
	<div id="left" class="accordion-group">
		<div class="accordion-heading">
			<a class="accordion-toggle" style="height: 100%;">
				<span class="treeTab check_tab" onclick="toggleIdex('${ctx}/exam/examRecord/?type=unit')">单位</span>
				<span class="treeTab" onclick="toggleIdex('${ctx}/exam/examRecord/?type=person')">个人</span>
				<i class="icon-refresh pull-right" onclick="refreshTree();"></i>
			</a>

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
				//var id = treeNode.pId == '0' ? '' :treeNode.pId;
				var id = treeNode.id == '0' ? '' :treeNode.id;
				$('#officeContent').attr("src","${ctx}/exam/examRecord/list?type=unit&unitId="+id+"&name="+treeNode.name+"&parentIds="+treeNode.pIds);
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

	var leftWidth = 230; // 左侧窗口大小
	var htmlObj = $("html"), mainObj = $("#main");
	var frameObj = $("#left, #openClose, #right, #right iframe");
	function wSize(){
		var strs = getWindowSize().toString().split(",");
		htmlObj.css({"overflow-x":"hidden", "overflow-y":"hidden"});
		mainObj.css("width","auto");
		frameObj.height(strs[0] - 5);
		var leftWidth = ($("#left").width() < 0 ? 0 : $("#left").width());
		$("#right").width($("#content").width()- leftWidth - $("#openClose").width() -5);
		$(".ztree").width(leftWidth - 10).height(frameObj.height() - 46);
	}
</script>
<script src="${ctxStatic}/common/wsize.min.js" type="text/javascript"></script>
</body>
</html>