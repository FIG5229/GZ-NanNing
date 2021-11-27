<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>团员名册管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treeview.jsp" %>
	<style type="text/css">
		.ztree {overflow:auto;margin:0;_margin-top:10px;padding:10px 0 0 10px;}
	</style>
</head>
<body>
<sys:message content="${message}"/>
<div id="content" class="row-fluid">
	<div id="left" class="accordion-group">
		<div class="accordion-heading">
			<a class="accordion-toggle">组织<i class="icon-refresh pull-right" onclick="refreshTree();"></i></a>
		</div>
		<div id="ztree" class="ztree"></div>
	</div>
	<div id="openClose" class="close">&nbsp;</div>
	<div id="right">
		<iframe id="officeContent" src="" width="100%" height="91%" frameborder="0"></iframe>
	</div>
</div>
<script type="text/javascript">
	var tree, setting = {view:{selectedMulti:false,dblClickExpand:false},check:{enable:"${checked}",nocheckInherit:true},
        /*改为officeId  使用treeSelect 时能公用 不用修改过多代码     officeId 为之前的 partyBranchId*/
		async:{enable:true,url:"${ctx}/affair/affairTjRegister/treeData",autoParam:["id=officeId"]},
		data:{simpleData:{enable:true}},callback:{
			onClick:function(event, treeId, treeNode){
				tree.expandNode(treeNode);
				var id = treeNode.id == '0' ? '' :treeNode.id;
				if(id.startsWith("tym_")){
					//具体到人
					$('#officeContent').attr("src","${ctx}/affair/affairTjRegister/list?idNumber="+id.substr(4));
				}else{
					$('#officeContent').attr("src","${ctx}/affair/affairTjRegister/list?partyBranchId="+id+"&partyBranch="+treeNode.name);
				}
			}
		}
	};

	function refreshTree(){
		$.get("${ctx}/affair/affairTwBase/treeData?flag=tym",function(zNodes){
			// 初始化树结构
			tree = $.fn.zTree.init($("#ztree"), setting, zNodes);

			// 默认展开一级节点
			var nodes = tree.getNodesByParam("level", 0);
			for(var i=0; i<nodes.length; i++) {
				tree.expandNode(nodes[i], true, false, false);
			}
			//异步加载子节点（加载用户）
			var nodesOne = tree.getNodesByParam("isParent", true);
			for(var j=0; j<nodesOne.length; j++) {
				tree.reAsyncChildNodes(nodesOne[j],"!refresh",true);
			}

			var node = tree.getNodes()[0];
			tree.selectNode(node);
			setting.callback.onClick(null, tree.setting.treeId, node);
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