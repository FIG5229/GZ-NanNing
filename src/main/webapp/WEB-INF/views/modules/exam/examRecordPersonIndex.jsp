<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>个人考评档案</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treeview.jsp" %>
	<style type="text/css">
		.ztree {overflow:auto;margin:0;_margin-top:10px;padding:10px 0 0 10px;height: auto}
		.treeTab{height: 100%;width: auto;font-size: 16px}
		.check_tab{color: black;font-size: 18px}
	</style>
	<script>
		function toggleIdex(url) {
			self.location.href = url;
		}
	</script>
</head>
<body>
<%--<ul class="nav nav-tabs">
	<li><a href="${ctx}/exam/examRecord/?type=unit">单位</a></li>
	<li class="active"><a href="${ctx}/exam/examRecord/?type=person">个人</a></li>
</ul>--%>
<sys:message content="${message}"/>
<div id="content" class="row-fluid">
	<div id="left" class="accordion-group">
		<div class="accordion-heading">
			<a class="accordion-toggle">
				<span class="treeTab " onclick="toggleIdex('${ctx}/exam/examRecord/?type=unit')">单位</span>
				<span class="treeTab check_tab" onclick="toggleIdex('${ctx}/exam/examRecord/?type=person')">个人</span>
				<i class="icon-refresh pull-right" onclick="refreshTree();"></i>
			</a>
			<%--<a class="accordion-toggle">个人<i class="icon-refresh pull-right" onclick="refreshTree();"></i></a>--%>
		</div>
		<div id="ztree" class="ztree"></div>
	</div>
	<div id="openClose" class="close">&nbsp;</div>
	<div id="right">
		<iframe id="personContent" src="" width="100%" height="91%" frameborder="0"></iframe>
		<%--${ctx}/exam/examRecord/list?type=person&idNumber=510103196112256775&name=徐祝&id=&parentIds=--%>
	</div>
</div>
<script type="text/javascript">
	var tree, setting = {view:{selectedMulti:false,dblClickExpand:false},check:{enable:"${checked}",nocheckInherit:true},
		async:{enable:true,url:"${ctx}/personnel/personnelBase/treeDataByOfficeId",autoParam:["id=officeId"]},
		data:{simpleData:{enable:true}},
		callback:{onClick:function(event, treeId, treeNode){
				//var id = treeNode.pId == '0' ? '' :treeNode.pId;
				tree.expandNode(treeNode);
				var id = treeNode.id == '0' ? '' :treeNode.id;
				if(!treeNode.isParent){
					$('#personContent').attr("src","${ctx}/exam/examRecord/list?type=person&personId="+id+"&idNumber="+treeNode.idNumber+"&name="+treeNode.name+"&parentIds="+treeNode.pId);
				}
			}
		}
	};

	function refreshTree(){
		$.getJSON("${ctx}/sys/office/treeData?type=3",function(zNodes){
			// 初始化树结构
			tree = $.fn.zTree.init($("#ztree"), setting, zNodes);

			//异步加载子节点（加载用户）
		    var nodesOne = tree.getNodesByParam("isParent", true);
			for(var j=0; j<nodesOne.length; j++) {
				tree.reAsyncChildNodes(nodesOne[j],"!refresh",true);
			}
			// 默认展开一级节点
			var nodes = tree.getNodesByParam("level",1);
			//var nodes = tree.getNodes();
			if(nodes[0].isParent){
				tree.expandNode(nodes[0], true, false);
			}
			/*for(var i=0; i<nodes.length; i++) {
				tree.expandNode(nodes[i], true, false);
			}*/
			var node = tree.getNodes()[0].children[0];
			console.log(node)
			if(node.isParent){
				//tree.expandNode(node,true);//需要展开/折叠的节点数据,
				//tree.selectNode(node);
				//var selNodes = tree.getSelectedNodes()[0];
				console.log(node.isParent)
			}else{
				setting.callback.onClick(null, tree.setting.treeId, node);
			}
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
		frameObj.height(strs[0] - 5);
		var leftWidth = ($("#left").width() < 0 ? 0 : $("#left").width());
		$("#right").width($("#content").width()- leftWidth - $("#openClose").width() -5);
		$(".ztree").width(leftWidth - 10).height(frameObj.height() - 46);
	}

</script>
<script src="${ctxStatic}/common/wsize.min.js" type="text/javascript"></script>
</body>
</html>