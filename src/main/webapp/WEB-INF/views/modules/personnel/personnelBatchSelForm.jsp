<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>人员批量查询</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {

		});
		function queryPersonnel() {
			var uploadFile = $("#uploadFile").val();
			var selItems = $("#selItems").val();//查询项
			var sheetPage = $("#sheetPage").val();//所在sheet页
			var startLine = $("#startLine").val();//开始行
			var excelColumn = $("#excelColumn").val();//所在列
			var unitId = $("#unitId").val();//所属单位
			var status = $("#status").val();//状态
			if(valueIsEmpty(uploadFile)
					|| valueIsEmpty(selItems)
					|| valueIsEmpty(sheetPage)
					|| valueIsEmpty(startLine)
					|| valueIsEmpty(excelColumn)
			){
				$.jBox.tip('请完成必填项！！！');
			}else{
				loading('正在查询，请稍等...');
				var fileObj = document.getElementById("uploadFile").files[0]; // js 获取文件对象
				if (typeof (fileObj) == "undefined" || fileObj.size <= 0) {
					$.jBox.tip("请选择文件");
					return;
				}
				var formFile = new FormData();
				formFile.append("action", "UploadVMKImagePath");
				formFile.append("file", fileObj); //加入文件对象
				formFile.append("selItems",selItems);//查询项
				formFile.append("sheetPage",sheetPage);//所在sheet页
				formFile.append("startLine",startLine);//开始行
				formFile.append("excelColumn",excelColumn);//所在列
				formFile.append("unitId",unitId);//所属单位
				formFile.append("status",status);//状态

			var data = formFile;
			$.ajax({
				url:"${ctx}/personnel/personnelBase/batchSelQuery",
				type:"post",
				data:data,
				contentType:false,  // 不用任何编码 因为formdata对象自带编码 django能够识别该对象
				processData:false,  // 告诉浏览器不要处理我的数据 直接发就行
				success:function(d){
					//console.log(d);
					if(d.success == true){
						$.jBox.tip('查询成功');
						parent.$.jBox.close();
						//console.log(d.result)
					}else{
						closeLoading();
						//console.log(d.message);
						var messageBoxHtml = '<div id="messageBox" class="alert alert-error hide"><button data-dismiss="alert" class="close">×</button>'+d.message+'</div>';
						// 删除内容
						$('#messageDiv').html('');
						// 添加内容
						$('#messageDiv').append(messageBoxHtml);
						showMessage(d.message);
					}
				},
				error:function(){
					closeLoading();
					$.jBox.tip('发生错误');
				}
			});
			}

		}
		var continueDayTemp = '';
		function isNum(d) {
			var reg = /^[0-9]*$/;
			var inputValue = d.value;
			if(reg.test(inputValue)){
				continueDayTemp = inputValue;
			}else {
				$.jBox.tip('请输入数字');
				d.value=continueDayTemp;
				//showTipDialog(d);

			}
		}

		//展示提示框
		function showTipDialog(d) {
			top.$.jBox.error('请输入数字', '系统提示', {
				buttonsFocus: 1, closed: function () {
					d.value=continueDayTemp;
				}
			});
		}
		//判断是否为空
		function valueIsEmpty(d) {
			if(d==null||d==undefined||d==""){
				return true;
			}else{
				return false;
			}
		}

		function showMessage(d) {
			if(!top.$.jBox.tip.mess){
				top.$.jBox.tip.mess=1;
			}
			top.$.jBox.tip(d,"error",{persistent:true,opacity:0});
			$("#messageBox").show();
		}
	</script>
</head>
<body>
<%--
<form:form id="searchForm" modelAttribute="personnelBase" οnsubmit="return fuqueryPersonnelnc()" action="${ctx}/personnel/personnelBase/batchSelQuery" method="post" class="form-search" enctype="multipart/form-data">
--%>
<div id="messageDiv"></div>
<%--<div id="messageBox" class="alert alert-error hide"><button data-dismiss="alert" class="close">×</button>发生错误</div>--%>
<div id="searchForm"  class="form-search" >
		<div style="width: 100%;text-align: center;">
			<div style="text-align: left;background-color: #F5F5F5; width: 100%;padding-top: 7px;padding-bottom: 7px" >
				<h4 style="margin-left: 5px">选择文件</h4>
			</div>
			<br>
			<span style="text-align: left"><label><font color="red">*</font> 选择EXCEL文件：</label>
				<input id="uploadFile" name="file" type="file" style="width:330px" accept=".csv, application/vnd.ms-excel, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"/>
				<span><font color="red">*注意:请将文件的单元格格式设置为文本，
										并保证单元格内容前后不含空格；*表示为必填项</font></span>
				<br/><br/>
			</span>
		</div>
		<div style="width: 100%;">
			<div style="text-align: left;background-color: #F5F5F5; width: 100%;padding-top: 7px;padding-bottom: 7px" >
				<h4 style="margin-left: 5px">条件设置</h4>
			</div>
			<div class="modal-custom-content" style="width: 100%">
				<table id="contentTable" class="table table-striped table-bordered table-condensed">
					<tbody>
					<tr>
						<td>
							<label><font color="red">*</font>查询项：</label>
						</td>
						<td style="text-align: left">
							<span>
								<select class="input-medium" id="selItems" name="selItems">
									<option value=""></option>
									<option value="name">姓名</option>
									<option value="id_number">身份证号</option>
									<option value="police_id_number">警号</option>
								</select>
							</span>
						</td>
						<td>
							<label><font color="red">*</font>所在sheet页：</label>
						</td>
						<td style="text-align: left">
							<span><input type="text" name="sheetPage" id="sheetPage" class="input-medium" onkeyup="isNum(this)" placeholder="所在sheet页从1开始"/></span>
							<span class="help-inline"><font color="red">*只允许输入数字,从1开始</font> </span>
						</td>
					</tr>
					<tr>
						<td>
							<label><font color="red">*</font>所在列：</label>
						</td>
						<td style="text-align: left">
							<span><input  type="text" htmlEscape="false" name="excelColumn" id="excelColumn" class="input-medium" onkeyup="isNum(this)" placeholder="所在列从1开始"/></span>
							<span class="help-inline"><font color="red">*只允许输入数字,从1开始</font> </span>
						</td>

						<td>
							<label><font color="red">*</font>数据项开始行：</label>
						</td>
						<td style="text-align: left">
							<span><input type="text" name="startLine" id="startLine" class="input-medium" onkeyup="isNum(this)" placeholder="数据开始行从1开始"/></span>
							<span class="help-inline"><font color="red">*只允许输入数字,从1开始</font> </span>
						</td>
					</tr>
					<tr>
						<td >
							<label><%--<font color="red">*</font>--%>所属单位：</label>
						</td>
						<td style="text-align: left">
							<span>
								<sys:treeselect id="unit" name="workunitId" value="" labelName="workunitName" labelValue=""
													title="单位" url="/sys/office/treeData?type=2" cssClass="required input-medium" allowClear="true" notAllowSelectParent="false" dataMsgRequired="必填信息"/>
							</span>
						</td>
						<td>
							<label><%--<font color="red">*</font>--%>人员状态：</label>
						</td>
						<td style="text-align: left">
							<span>
								<select class="input-medium" name="status" id="status">
									<option value=""></option>
									<c:forEach items="${personnelStatusList}" var="personnelStatus">
										<option value="${personnelStatus.value}">${personnelStatus.label}</option>
									</c:forEach>
								</select>
							</span>
						</td>
					</tr>
					</tbody>
				</table>
			</div>
		</div>
	<div style="width: 100% ;">
		<span style="text-align: center;display:block;">
			<input class="btn btn-primary" type="button" onclick="window.location.href='${ctx}/personnel/personnelBase/batchSelForm'" value="重  置"  />
			<input id="btnSubmit" class="btn btn-primary" type="submit" onclick="queryPersonnel()" value="查  询"  />
		</span>
	</div>
</div>

<%--</form:form>--%>
</body>

</html>