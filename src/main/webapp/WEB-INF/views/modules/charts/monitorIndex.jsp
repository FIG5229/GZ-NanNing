<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<%@include file="/WEB-INF/views/include/treeview.jsp" %>
<style>
.ztree {overflow:auto;margin:0;_margin-top:10px;padding:10px 0 0 10px;}
.content {
	float: left;
}
</style>
<script type="text/javascript" src="${ctxStatic}/echarts/echarts.js"></script>
</head>
<body>
	<div id="content" class="row-fluid">
		<div id="left" class="accordion-group">
			<div class="accordion-heading">
		    	<a class="accordion-toggle">组织机构<i class="icon-refresh pull-right" onclick="refreshTree();"></i></a>
		    </div>
			<div id="ztree" class="ztree"></div>
		</div>
		<div id="openClose" class="close">&nbsp;</div>
		<div id="right">
			<iframe id="publicityContent" src="" width="100%" height="91%" frameborder="0"></iframe>
		</div>
	</div>
	<script type="text/javascript">
		var setting = {data:{simpleData:{enable:true,idKey:"id",pIdKey:"pId",rootPId:'0'}},
			callback:{onClick:function(event, treeId, treeNode){
					$('#publicityContent').attr("src","${ctx}/sys/charts/monitor?id="+treeNode.id);
				}
			}
		};
		
		function refreshTree(){
			$.getJSON("${ctx}/sys/office/treeData",function(data){
				var tree = $.fn.zTree.init($("#ztree"), setting, data);
				tree.expandAll(true);
				var node = tree.getNodes()[0];
				tree.selectNode(node);
				setting.callback.onClick(null, tree.setting.treeId, node);
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