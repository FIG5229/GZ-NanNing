<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>考评单位</title>
	<link href="${ctxStatic}/common/common.css" type="text/css" rel="stylesheet" />
	<link href="${ctxStatic}/common/modal-custom.css" type="text/css" rel="stylesheet" />
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treeview.jsp" %>
	<style type="text/css">
		.ztree {overflow:scroll;padding:10px 0 0 10px;max-height: 600px;}
	</style>
</head>
<body class="ztree">
<div id="content" class="row-fluid">
<%--	<div id="top">
		<%@include file="/WEB-INF/views/modules/exam/step.jsp" %>
	</div>--%>
	<div id="left" class="accordion-group">
		<div class="accordion-heading">
			<a class="accordion-toggle">考评单位<i class="icon-refresh pull-right" onclick="refreshTree();"></i></a>
		</div>
		<div>
			<ul class="nav nav-tabs">
				<li id="unEvaluate" class="active" onclick="changeActive(1)"><a href="javascript:;">未自评</a></li>
<%--				<li id="doEvaluate" class="" onclick="changeActive(2)"><a href="javascript:;">已自评</a></li>--%>
				<li id="unExamine" class="" onclick="changeActive(3)"><a href="javascript:;">未初核</a></li>
				<li id="doExamine" class="" onclick="changeActive(4)"><a href="javascript:;">已初核</a></li>
			</ul>
		</div>
<%--			<input id="" class="btn btn-primary" type="button" value="通过" onclick="passExamine()"/>--%>

		<div>
			<c:if test="${status != 6 }">
				<input id="btnBack" class="btn btn-primary" type="button" value="退回修改"
					   <c:if test="${history == 'true'}">disabled="disabled"</c:if>
					   onclick="backExam()"/>
			</c:if>

			<c:choose>
				<c:when test="${nNextStatus == 3}">
					<input id="btnEnd" class="btn btn-primary" type="button" value="初次公示"
						   <c:if test="${history == 'true'}">disabled="disabled"</c:if>
						   onclick="endExam()"/>
				</c:when>
				<c:otherwise>
					<input id="btnEnd" class="btn btn-primary" type="button" value="通过"
						   <c:if test="${history == 'true'}">disabled="disabled"</c:if>
						   onclick="endExam()"/>
				</c:otherwise>
			</c:choose>
			<c:if test="${nNextStatus == 8}">
				<div>
					请在管理页面进行最终结果公示
				</div>
			</c:if>
			<div>
				<sys:treeselect id="person" name="personId" value="" labelName="personName" labelValue="" smallBtn="true"
								title="用户" url="/sys/office/treeData?type=3" cssClass="" allowClear="true" notAllowSelectParent="true"
								isAll="true"/>
				<input class="btn btn-primary" type="button" value="确定" onclick="selectPerson()"/>选择用户后点击确定
			</div>
			<div id="ztree" class="ztree" style="height: 80%;"></div>
		</div>

	</div>
	<div id="openClose" class="close">&nbsp;</div>
	<div id="right">
		<iframe id="applicantContent" src="" width="100%" height="91%" frameborder="0"></iframe>
	</div>
</div>
<script type="text/javascript">

	/*结束考核*/
	function endExam(){
		/*下一步为全局公示时，由绩效办开启下个环节*/
		var nNextStatus = '${nNextStatus}';
		if (nNextStatus == '99'){
			jBox.tip("请等待最终公示");
		} else {
			var treeObj = $.fn.zTree.getZTreeObj("ztree");
			var nodes = treeObj.getCheckedNodes(true);//在提交表单之前将选中的checkbox收集
			var array = new Array();
			for (var i = 0; i < nodes.length; i++) {
				if (!nodes[i].isParent)
					array.push(nodes[i].id);
			}

			if (array.length == 0) {
				jBox.tip("请选择结束的单位");
				return;
			}
			top.$.jBox.confirm("请先保存选择的考评项，确认要结束选中的考核吗", '系统提示', function (v, h, f) {
				if (v == 'ok') {
					$.getJSON("${ctx}/exam/examWorkflowDatas/saveByIdsBeta",
							{
								"fillPersonIds": array,
								"status":"${status}",
								"standardId":"${standardId}",
								"workflowId":'${workflowId}',
								"nextStatus":'${nNextStatus}',
								'objName':'${objName}',
								"history":'end',
								"processType":"${processType}",
								"personType":'${personType}'
							},
							function (data) {
								if (data.success){
									window.location.replace("${ctx}/exam/examWorkflowDatas/appraise/examIndex?examWorkflowId=${workflowId}&history="+data.result);
									// location.reload();
									// $("#btnBack").setAttribute("disabled",true);
									// $("#btnEnd").setAttribute("disabled",true);
								} else {
									jBox.tip(data.message);
								}
							});
				}
			})

			<%--return confirmx('确认要结束该考核吗？', '${ctx}/exam/examWorkflow/saveBeta?id=${workflowId}&operation=end')--%>
		}
	}
	function changeActive(index) {
		var unEvaluate=document.getElementById('unEvaluate')
		var doEvaluate=document.getElementById('doEvaluate')
		var unExamine=document.getElementById('unExamine')
		var doExamine=document.getElementById('doExamine')
		// a.style.backgroundColor = "red";
		switch (index) {
			case 1:
				unEvaluate.className='active';
				unExamine.className = '';
				doExamine.className = '';
				$("#btnEnd").val("通过");
				refreshTree('1');
				$("#btnEnd").show();
				$("#btnBack").show();
				break
			case 2:
				unEvaluate.className = '';
				unExamine.className = '';
				doExamine.className = '';
                refreshTree('2');
				$("#btnEnd").show();
				$("#btnBack").show();
				break
			case 3:
				unEvaluate.className = '';
				unExamine.className = 'active';
				doExamine.className = '';
				$("#btnEnd").val("初次公示");
                refreshTree('3');
				$("#btnEnd").show();
				$("#btnBack").show();
				break
			case 4:
				unEvaluate.className = '';
				unExamine.className = '';
				doExamine.className = 'active';
				$("#btnEnd").val("通过");
				$("#btnEnd").hide()
				$("#btnBack").hide();
                refreshTree('4');
				break
		}
	}

	/*退回考评*/
	function backExam() {

		var treeObj = $.fn.zTree.getZTreeObj("ztree");
		var nodes = treeObj.getCheckedNodes(true);//在提交表单之前将选中的checkbox收集
		var array = new Array();
		for (var i = 0; i < nodes.length; i++) {
			if (!nodes[i].isParent)
				array.push(nodes[i].id);
		}
		if (array.length == 0) {
			jBox.tip("请选择退回的单位");
			return;
		}

		<%--let tip = confirmx("确认要退回选中的考核吗!",'${ctx}/exam/examWorkflow/examIndex?examWorkflowId=${workflowId}');--%>
		top.$.jBox.confirm("确认要退回选中的考核吗", '系统提示', function (v, h, f) {

			if (v == 'ok') {
				$.post("${ctx}/exam/examWorkflowDatas/saveByIdsBeta",
						{
							"fillPersonIds": array,
							"standardId": "${standardId}",
							"workflowId": '${workflowId}',
							"status": '${status}',
							"perStatus":'${perStatus}',
							'objName': '${objName}',
							"history": 'back',
							"processType": "${processType}",
							"personType": '${personType}'

						},
						function (data) {

							if (data.success == true) {
								window.location.replace("${ctx}/exam/examWorkflowDatas/appraise/examIndex?examWorkflowId=${workflowId}&history=true");
								// location.reload();
								// $("#btnBack").attr({"disabled":"disabled"});
								// $("#btnEnd").setAttribute("disabled",false);
							} else {
								jBox.tip(data.message);
							}
						});
			}
		});

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
						if (data.success){

						} else {

						}
					});

			/*选中的节点Id  不包含父节点，父节点是是单位iD，找不到用户Id*/
			console.log(array);
		} else {

		}
	}


	function selectPerson() {
		var treeObj = $.fn.zTree.getZTreeObj("ztree");
		var nodes = treeObj.getCheckedNodes(true);//在提交表单之前将选中的checkbox收集
		var array = new Array();
		for (var i = 0; i < nodes.length; i++) {
			if (!nodes[i].isParent)
				array.push(nodes[i].id);
		}
		let person = $("#person").val();
		let personId = $("#personId").val();
		if (array.length == 0) {
			jBox.tip("请选择被审核的单位");
			return;
		}

		$.getJSON("${ctx}/exam/examWorkflowDatas/selectPersonByIdsBeta",
				{
					"fillPersonIds": array,
					"nextStatus": '${nNextStatus}',
					"standardId":"${standardId}",
					"workflowId":'${workflowId}',
					"status":'${status}',
					'objName':'${objName}',
					"history":'${history}',
					"processType":"${processType}",
					"personType":'${personType}',
					"personId":$("#personId").val(),
					"person":$("#person").val()
				},
				function (data) {
					if (data.success){
						// location.reload();
						<%--window.location.replace("${ctx}/exam/examWorkflowDatas/appraise/examIndex?examWorkflowId=${workflowId}");--%>
					} else {

					}
				});
	}
</script>
<script type="text/javascript">
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
			/*key: {
				title:"t"
			},*/
			simpleData: {
				enable: true,
				idKey: "id",
				pIdKey: "pId",
				rootPId: ""
			}
		},
		callback:{onClick:function(event, treeId, treeNode){
				//var id = treeNode.pId == '0' ? '' :treeNode.pId;
				/*父节点不可点击*/
				if (!treeNode.isParent) {
					var id = treeNode.pId == '0' ? '' :treeNode.id;
					$('#applicantContent').attr("src","${ctx}/exam/examWorkflowDatas/examBeta?fillPersonId="+id+"&standardId=${standardId}"+
					"&examWorkflowId=${workflowId}&status=${status}&objName=${objName}&history=${history}&processType=${processType}"+
					"&personType=${personType}");
				}else {
					return !treeNode.isParent;//当是父节点 返回false 不让选取
				}
			}
		}
	};

	function refreshTree(type){
		$.getJSON("${ctx}/exam/examWorkflowDatas/userTree?workflowId=${workflowId}&examType=${examType}&type="+type,function(data){
			var tree = $.fn.zTree.init($("#ztree"), setting, data);
			tree.expandAll(true);
			var node = tree.getNodes()[0];
			tree.selectNode(node);
			setting.callback.onClick(null, tree.setting.treeId, node);
		});
	}
	refreshTree();
	var leftWidth = 240; // 左侧窗口大小
	var htmlObj = $("html"), mainObj = $("#main");
	var frameObj = $("#left, #openClose, #right, #right iframe");
	function wSize(){
		var strs = getWindowSize().toString().split(",");
		htmlObj.css({"overflow-x":"hidden", "overflow-y":"hidden"});
		mainObj.css("width","auto");
		frameObj.height(strs[0] - 5);
		var leftWidth = ($("#left").width() < 0 ? 0 : $("#left").width());
		$("#right").width($("#content").width()- leftWidth - $("#openClose").width() -5);
		$(".ztree").width(leftWidth - 10).height(frameObj.height()-190);
	}

</script>
<script src="${ctxStatic}/common/wsize.min.js" type="text/javascript"></script>
</body>
</html>