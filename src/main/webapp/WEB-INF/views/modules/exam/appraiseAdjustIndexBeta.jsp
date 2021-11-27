<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>考评单位</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treeview.jsp" %>
	<style type="text/css">
		.ztree {overflow:auto;margin:0;_margin-top:10px;padding:10px 0 0 10px;}
	</style>
</head>
<body>
<div id="content" class="row-fluid">
	<div id="left" class="accordion-group">
		<div class="accordion-heading">
			<a class="accordion-toggle">考评单位<i class="icon-refresh pull-right" onclick="refreshTree();"></i></a>
		</div>
		<%--<div>未实现的功能隐藏
			<ul class="nav nav-tabs">
				<li id="unEvaluate" class="active" onclick="changeActive('1')"><a href="javascript:;">未自评</a></li>
				<li id="doEvaluate" class="" onclick="changeActive('2')"><a href="javascript:;">已自评</a></li>
				<li id="unExamine" class="" onclick="changeActive('3')"><a href="javascript:;">未初核</a></li>
				<li id="doExamine" class="" onclick="changeActive('4')"><a href="javascript:;">已初核</a></li>
			</ul>
			<input id="" class="btn btn-primary" type="button" value="通过" onclick="passExamine()"/>
			<input id="btnrint" class="btn btn-primary" type="button" value="退回"/>
			<input id="btrint" class="btn btn-primary" type="button" value="结束考核"/>
		</div>--%>
		<div id="ztree" class="ztree"></div>
	</div>
	<div id="openClose" class="close">&nbsp;</div>
	<div id="right">
		<iframe id="applicantContent" src="" width="100%" height="91%" frameborder="0"></iframe>
	</div>
</div>
<script type="text/javascript">
	function changeActive(index) {
		//debugger;er;
		switch (index) {
			case '1':
				$("#unEvaluate").className='active';
				$("#doEvaluate").className = '';
				$("#unExamine").className = '';
				$("#doExamine").className = '';
				break
			case '2':
				$("#unEvaluate").className = '';
				$("#doEvaluate").className='active';
				$("#unExamine").className = '';
				$("#doExamine").className = '';
				break
			case '3':
				$("#unEvaluate").className = '';
				$("#doEvaluate").className = '';
				$("#unExamine").className = 'active';
				$("#doExamine").className = '';
				break
			case '4':
				$("#unEvaluate").className = '';
				$("#doEvaluate").className = '';
				$("#unExamine").className = '';
				$("#doExamine").className = 'active';
				break
		}
	}
	function passExamine() {
		let tip = confirm("确认要通过选中的考核吗!");
		if (tip == true) {
			var treeObj = $.fn.zTree.getZTreeObj("ztree");
			var nodes = treeObj.getCheckedNodes(true);//在提交表单之前将选中的checkbox收集
			var array = new Array();
			for (var i = 0; i < nodes.length; i++) {
				if (!nodes[i].isParent)
					array.push(nodes[i].id);
			}
			$.getJSON("${ctx}/exam/examWorkflow/appraise/data/saveByIdsBeta",
					{
						"ids": array,
						"status": '',
						"standardId":"${standardId}",
						"workflowId":'${workflowId}',
						"status":'${status}',
						'objName':'${objName}',
						"history":'${history}',
						"processType":"${processType}",
						"personType":'${personType}'
					},
					function (data) {

					});

			/*选中的节点Id  不包含父节点，父节点是是单位iD，找不到用户Id*/
			console.log(array);
		} else {

		}
	}
</script>
<script type="text/javascript">

/*	var setting = {
		data: {
			simpleData:{
				enable: true,
				idKey: "id",
				pIdKey: "pId",
				rootPId: ''
			}
				},
		callback:{onClick:function(event, treeId, treeNode){
				//var id = treeNode.pId == '0' ? '' :treeNode.pId;
				var id = treeNode.pId == '0' ? '' :treeNode.id;
				$('#applicantContent').attr("src","${ctx}/exam/examWorkflow/appraise/adjust?treeId="+id);
			}
		},
		check: {
			enable: true ,//显示复选框
			chkStyle : "checkbox"
		},
		view: {
			dblClickExpand: true,
			selectedMulti : true,//可以多选
			showLine: true
		}
	};*/
	var setting = {
		view: {
			dblClickExpand: true,
			selectedMulti : true,//可以多选
			showLine: true
		},
		check: {
			enable: true ,//显示复选框
			chkStyle : "checkbox"
		},
		data: {
			key: {
				title:"t"
			},
			simpleData: {
				enable: true,
				idKey: "id",
				pIdKey: "pId",
				rootPId: null
			}
		},
		callback:{onClick:function(event, treeId, treeNode){
				//var id = treeNode.pId == '0' ? '' :treeNode.pId;
				var id = treeNode.pId == '0' ? '' :treeNode.id;
				$('#applicantContent').attr("src","${ctx}/exam/examWorkflow/appaise/content?fillPersonId="+id+"&standardId=${standardId}"+
				"&workflowId=${workflowId}&status=${status}&objName=${objName}&history=${history}&processType=${processType}"+
				"&personType=${personType}");
			}
		}
	};

	function refreshTree(){
		$.getJSON("${ctx}/sys/office/userTree?type=3",function(data){
			var tree = $.fn.zTree.init($("#ztree"), setting, data);
			tree.expandAll(false);
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
		$(".ztree").width(leftWidth - 10).height(frameObj.height());
	}

</script>
<script src="${ctxStatic}/common/wsize.min.js" type="text/javascript"></script>
</body>
</html>