<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>人员复合查询</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {

		});
		function showMessage(d) {
			if(!top.$.jBox.tip.mess){
				top.$.jBox.tip.mess=1;
			}
			top.$.jBox.tip(d,"error",{persistent:true,opacity:0});
			$("#messageBox").show();
		}
	</script>
	<style>
		.table{height: 30%;width:100%}
		.columnCheckDiv{
			height: calc(60vh);
			width:100%}
	</style>
</head>
<body>
<div id="messageDiv"></div>
<h5>查询规则</h5>
<table class="table">
	<thead>
	<th style="text-align: center;">选择信息集</th>
	<th style="text-align: center;">选择信息集字段</th>
	<th style="text-align: center;">操作符</th>
	<th style="text-align: center;">值</th>
	<th style="text-align: center;">关系</th>
	<th style="text-align: center;">操作</th>
	</thead>
	<tbody id="selTbody">
	<tr id="tr0">
		<td>
			<select class="input-medium" onchange="setColumnSel(this,'0')" id="tableSel0">
				<option value=""></option>
				<c:forEach items="${tableList}" var="table">
					<option value="${table.tablename}">${table.name}</option>
				</c:forEach>
			</select>
		</td>
		<td>
			<select class="input-medium" id="columnSel0" onchange="setInputVal(this,'0')">
				<option value="">请先选择信息集</option>
			</select>
		</td>
		<td>
			<select class="input-xlarge required" id="operator0" onchange="addTips(this,'0')">
				<option value=""></option>
				<c:forEach items="${operatorList}" var="operator">
					<option value="${operator.value}">${operator.label}</option>
				</c:forEach>
			</select>
			<%--<select class="input-medium">
				<option value="">请先选择信息集</option>
			</select>--%>
		</td>
		<td>
			<input type="text" class="input-medium" id="input_value0" onclick="openCheckForm('0')" readonly="readonly" placeholder="点击进行编辑">
			<input type="text" class="input-medium" id="input_text0"  onclick="openCheckForm('0')" style="display: none" readonly = "readonly" placeholder="点击进行编辑">
		</td>
		<td>
			<select class="input-medium" id="relation0">
				<option value="">请选择</option>
				<option value="and">并且</option>
				<option value="or">或</option>
			</select>
		</td>
		<td>
			<input id="btnAdd0" class="btn btn-primary" type="button" onclick="addRow()" value="追加" />
			<%--<input id="btnCancel0" class="btn" type="button" onclick="removeTr('btnCancel0')" value="移除" />--%>
		</td>
	</tr>
	</tbody>
</table>
<h5>信息项展示设置</h5>
<p id="from_tableName_input" hidden></p>
<p><span>选择字段名：</span><span id="checkColumntext"></span></p>
<p id="checkColumnvalue" hidden></p>
<div class="columnCheckDiv">
	<iframe src="${ctx}/personnel/personnelBase/ComplexSelIndex" style="width:100% ;height: 100%"></iframe>
</div>
<br>
<div style="width: 100%">
	<input id="selBtn" class="btn btn-primary" type="button" onclick="queryPersonnel()" value="查  询" style="display:block;margin:0 auto" />
</div>

<script>
	var rowIndex = "tr0,"
	//根据选择信息集生成相应的信息字段
	function setColumnSel(d,columnSelId) {
		var tableName = d.value;
		loading('正在生成信息集字段，请稍等...');
		$.ajax({
			url:"${ctx}/personnel/personnelBase/selColumnByTableName",
			type:"post",
			data:{tableName:tableName},
			dataType:"json",
			success:function (data) {
				if (data.success == true){
					$("#columnSel"+columnSelId).empty();
					if (data.result.length>0){
						$("#columnSel"+columnSelId).append('<option value="">请先选择信息集</option>');
						for(var i = 0;i<data.result.length;i++){
							$("#columnSel"+columnSelId).append("<option value="+data.result[i]["name"]+">"+data.result[i]["comment"]+"</option>")
						}
						closeLoading();
						$.jBox.tip('生成成功');
					}else{
						closeLoading();
						$.jBox.tip('未找到改信息表字段');
						$("#columnSel"+columnSelId).append('<option value="">请先选择信息集</option>')
					}
				}
			},
			error:function(d){
				$.jBox.tip('发生错误，查询字段失败');
			}

		});

	}
	//当信息集字段发生变化后，值输入框清空，设置不可编辑
	function setInputVal(d,nowIndex) {
		$("#input_value"+nowIndex).attr("readonly","readonly");
		$("#input_value"+nowIndex).val("");
		$("#input_text"+nowIndex).val("");
	}
	//操作符发生变化，值输入框设置不可编辑，如果为多条件选择，为值输入框增加提示
	function addTips(d,nowIndex) {
		$("#input_value"+nowIndex).attr("readonly","readonly");
		if(d.value=='11'){
			$("#input_value"+nowIndex).attr("placeholder","例如：值1-值2-...");
		}
		/*else if(d.value=='12' || d.value=='13'){
			$("#input_value"+nowIndex).attr("readonly","readonly");
		}else{
			$("#input_value"+nowIndex).removeAttr("readonly");
			$("#input_value"+nowIndex).attr("placeholder","");
		}*/

	}

	var idNum = 0;
	//追加
	function addRow(){
		idNum =idNum + 1;
		var addTr = '<tr id="tr'+idNum+'">\n' +
				'<td>\n' +
				'<select class="input-medium" onchange="setColumnSel(this,\''+idNum+'\')" id="tableSel'+idNum+'">\n' +
				'<option value=""></option>\n' +
				'<c:forEach items="${tableList}" var="table">\n' +
				'<option value="${table.tablename}">${table.name}</option>\n' +
				'</c:forEach>\n' +
				'</select>\n' +
				'</td>\n' +
				'<td>\n' +
				'<select class="input-medium" id="columnSel'+idNum+'" onchange="setInputVal(this,\''+idNum+'\')">\n' +
				'<option value="">请先选择信息集</option>\n' +
				'</select>\n' +
				'</td>\n' +
				'<td>\n' +
				'<select class="input-medium" id="operator'+idNum+'" onchange="addTips(this,\''+idNum+'\')">\n' +
				'<option value=""></option>\n' +
				'<c:forEach items="${operatorList}" var="operator">\n' +
				'<option value="${operator.value}">${operator.label}</option>\n' +
				'</c:forEach>' +
				'</select>\n' +
				'</td>\n' +
				'<td>\n' +
				'<input type="text" class="input-medium" id="input_value'+idNum+'" onclick="openCheckForm(\''+idNum+'\')" readonly="readonly" placeholder="点击进行编辑" >\n' +
				'<input type="text" class="input-medium" id="input_text'+idNum+'" onclick="openCheckForm(\''+idNum+'\')" style="display: none" readonly = "readonly" placeholder="点击进行编辑">\n' +
				'</td>\n' +
				'<td>\n' +
				'<select class="input-medium" id="relation'+idNum+'">\n' +
				'<option value="">请选择</option>\n' +
				'<option value="and">并且</option>\n' +
				'<option value="or">或</option>\n' +
				'</select>\n' +
				'</td>\n' +
				'<td>\n' +
				'<input id="btnAdd'+idNum+'" class="btn btn-primary" type="button" value="追加" onclick="addRow()" />\n' +
				'<input id="btnCancel'+idNum+'" class="btn" type="button" onclick="removeTr(\'btnCancel'+idNum+'\')" value="移除" />\n' +
				'</td>\n' +
				'</tr>';
		var selTbody = $("#selTbody");
		selTbody.append(addTr);
		$("#selTbody").trigger("create");
		rowIndex += "tr"+idNum+",";
	}
	//移除
	function removeTr(d) {
		var a=$("#"+d).parent().parent().attr('id')
		$("#"+a).remove();
		rowIndex = rowIndex.replace(a+",","");
	}
	/*
	* 查询
	* */
	function queryPersonnel() {
		var submitTableName = $("#from_tableName_input").text();//表名
		var submitCheckColumntext = $("#checkColumntext").text();//字段名
		var submitCheckColumnvalue = $("#checkColumnvalue").text();//字段
		if(submitCheckColumntext == null || submitCheckColumntext == ''){
			$.jBox.tip('请选择显示字段');
		}else{
			loading('正在查询，请稍等...');
			//alert(rowIndex);
			var rowTrIdList = rowIndex.split(",");
			var list_map = new Array();
			if(rowTrIdList.length>0){
				for(var i =0;i<rowTrIdList.length-1;i++){
					var trIdIndex = rowTrIdList[i].replace("tr","");
					var tableSel = $("#tableSel"+trIdIndex).val();//选择表
					var columnSel = $("#columnSel"+trIdIndex).val();//选择字段
					var operator = $("#operator"+trIdIndex).val();//操作符
					var input_value = $("#input_value"+trIdIndex).val();//值
					var relation = $("#relation"+trIdIndex).val();//关系
					//var jsonObj = {"tableSel":tableSel,"columnSel":columnSel,"operator":operator,"inputValue":input_value,"relation":relation};
					//如果值为空，设置个默认值
					if(isEmpty(tableSel)){
						tableSel = "XG_isNull";
					}
					if(isEmpty(columnSel)){
						columnSel = "XG_isNull";
					}
					if(isEmpty(operator)){
						operator = "XG_isNull";
					}
					if(isEmpty(input_value)){
						input_value = "XG_isNull";
					}
					if(isEmpty(relation)){
						relation = "XG_isNull";
					}
					list_map.push({"tableSel":tableSel,"columnSel":columnSel,"operator":operator,"inputValue":input_value,"relation":relation});
				}
			}
			var listMapStr = JSON.stringify(list_map);
			$.ajax({
				traditional: true,
				url:"${ctx}/personnel/personnelBase/customSelSql",
				type:"post",
				data:{submitTableName:submitTableName,submitCheckColumntext:submitCheckColumntext,submitCheckColumnvalue:submitCheckColumnvalue,listMap:listMapStr},
				dataType:"json",
				//contentType: 'application/json',
				success:function (data) {
					if(data.success==true){
						//console.log(data.result.customSql);
						closeLoading();
						$.jBox.tip('查询成功');
						parent.$.jBox.close();
					}else{
						closeLoading();
						var messageBoxHtml = '<div id="messageBox" class="alert alert-error hide"><button data-dismiss="alert" class="close">×</button>'+data.message+'</div>';
						// 删除内容
						$('#messageDiv').html('');
						// 添加内容
						$('#messageDiv').append(messageBoxHtml);
						showMessage(data.message);

					}
				},
				error:function(d){
					closeLoading();
					$.jBox.tip('发生错误，请重试');
				}
			})
		}
	}
	//判断字符是否为空的方法
	function isEmpty(obj){
		if(typeof obj == "undefined"){
			return true;
		}
		obj = obj.replace(/(^\s*)|(\s*$)/g, "");//去掉字符串两边空格;
		if(obj == null || obj == ""){
			return true;
		}else{
			return false;
		}
	}
	//弹出字典值选框
    function openCheckForm(idIndex) {
		$("#input_text"+idIndex).css('display','none');
		$("#input_value"+idIndex).css('display','block');
		var tableSel = $("#tableSel"+idIndex).val();//选择表
		var columnSel = $("#columnSel"+idIndex).val();//选择字段
		var operator = $("#operator"+idIndex).val();//操作符
		if(isEmpty(tableSel) || isEmpty(columnSel) || isEmpty(operator)){
			$.jBox.tip('请先完成信息集、信息集字段、操作符的内容选择');
		}else{
			if(operator == '12' || operator == '13'){//操作符 是空  /   非空
				$("#input_value"+idIndex).attr("readonly","readonly");
			}else{
				loading('正在加载,请稍等...');
				$.ajax({
					type:"post",
					url:"${ctx}/personnel/personnelBase/isDictFields",
					data:{tableSel:tableSel,columnSel:columnSel},
					dataType:"json",
					success:function(d){
						if(d.success == true){
							if(d.result['isDictFields'] == true){
								$("#input_text"+idIndex).css('display','block');
								$("#input_text"+idIndex).val('');
								$("#input_value"+idIndex).css('display','none');
								$("#input_value"+idIndex).val('');
								var dictList = d.result['dictList'];//字典集合
								var html = '<table id="contentTable" class="table table-striped table-bordered table-condensed">';
								html += '<thead><tr><th>选择</th><th>值</th><th>标签</th><th>描述</th></tr></thead>';
								html += '<tbody>';
								for(var i=0; i< dictList.length; i++) {
									if(operator == '11'){
										html += '<tr><td><input type="checkbox" name="selected" value="'+i+'"></td>';
									}else{
										html += '<tr><td><input type="radio" name="selected" value="'+i+'"></td>';
									}
									html += '<td>'+dictList[i].value+'</td>';
									html += '<td>'+dictList[i].label+'</td>';
									html += '<td>'+dictList[i].description+'</td>';
									html += '</tr>';
								}
								html +=	'</tbody>';
								html +=	'</table>';
								var submit = function (v, h, f) {
									if(operator == '11'){
										var selectList = h.find("input:checkbox:checked");
										if(selectList.length>0){
											var selValue = "";
											var selLabel = "";
											for(var i = 0;i<selectList.length;i++){
												if(i==0){
													selValue += dictList[selectList[i].value].value;
													selLabel += dictList[selectList[i].value].label;
												}else{
													selValue += '-'+dictList[selectList[i].value].value;
													selLabel += '-'+dictList[selectList[i].value].label;
												}
											}
											$("#input_value"+idIndex).val(selValue);
											$("#input_text"+idIndex).val(selLabel);
										}
									}else{
										$("#input_value"+idIndex).val(dictList[f.selected].value);
										$("#input_text"+idIndex).val(dictList[f.selected].label);
									}
									return true;
								};
								top.$.jBox(html, { title: "输入",width: 600, height: 300, submit: submit });
								closeLoading();
							}else{
								closeLoading();
								$("#input_text"+idIndex).css('display','none');
								$("#input_value"+idIndex).css('display','block');
								$("#input_value"+idIndex).removeAttr("readonly");
							}
						}else{
							closeLoading();
							$("#input_text"+idIndex).css('display','none');
							$("#input_value"+idIndex).css('display','block');
							$.jBox.tip(''+d.message+'');
						}
					},
					error:function(d){
						closeLoading();
						$("#input_text"+idIndex).css('display','none');
						$("#input_value"+idIndex).css('display','block');
						$.jBox.tip('发生错误请重试');
					}
				});
			}
		}

	}
</script>

</body>
</html>