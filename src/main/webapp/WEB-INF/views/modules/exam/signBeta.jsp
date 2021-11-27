<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<link href="${ctxStatic}/common/common.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/common/modal-custom.css" type="text/css" rel="stylesheet" />
<script type="text/javascript"  src="${ctxStatic}/js/common.js?t='20210413'"></script>
<meta name="decorator" content="default"/>
<script	type="text/javascript">

	let height =  $(window).height()-200;
	/*子iframe获取不到let类型 可以获取到var类型*/
	var test =  "咋获取不到呢";
	let width =  $(window).width()-200;



	function selectTerm() {
		var url = "iframe:${ctx}/exam/examWorkflow/selectTerm?standardId=${standardInfoList.get(0).id}&workflowId=${workflowId}&objId=${objId}&fillPersonId=${fillPersonId}";
		this.$.jBox.open(url, "选择考评项",width,height,{
			id:"termIFrame",
			target:top,
			buttons:{"关闭":true},
			loaded:function(h){
				$(".jbox-content", top.document).css("overflow-y","hidden");
			},closed:function (){
				self.location.href="${ctx}/exam/examWorkflowDatas/examBeta?examWorkflowId=${workflowId}&examType=${examType}";
				self.location.href="${ctx}/exam/workflowDatas/examBeta/fillPersonId=${fillPersonId}&standardId=${standardId}"+
						"&examWorkflowId=${workflowId}&status=${status}&objName=${objName}&history=${history}&processType=${processType}&personType=${personType}";
			}
		});
	}
	//得到被选中的checkbox的数据id
	function getIds(checkBoxsName) {
		var ids = [];
		$("input:checkbox[type=checkbox]:checked").each(function () {
			if ($(this).val() != "on"){
				ids.push($(this).val());
			}
		});
		return ids;
	};
	function deleteByIdsBeta(url,checkAllId,checkBoxsName) {
		var delIds = getIds(checkBoxsName);
		if (delIds.length > 0) {
			$.ajax({
				type:"post",
				url:url,
				data:{ids:delIds},
				dataType:"json",
				success:function(data){
					$.jBox.tip(data.message);
					// resetCheckBox(checkAllId,checkBoxsName);
					location.reload();
				}
			})
		} else {
			$.jBox.tip('请先选择要删除的内容');
		}
	};
	function removeByIds() {
		jBox.tip("请稍后......");
		deleteByIdsBeta('${ctx}/exam/examWorkflowDatas/removeByIds','checkAll','selectedBox');

	}



	/*从workflowData表中修改选中状态 改为未修改 还未做*/
	function reduce() {
		$('input[name="selectedBox"]:checked').each(function () {
			$("#r_" + $(this).val()).replaceWith("");
		});
	}

	function formSubmit(processType) {
		$("#processType").val(processType);
		$("#inputForm").submit();
	}
	function sendSubmit(processType, personType) {
		if(null == $("#personId").val()||'' == $("#personId").val()){
			jBox.alert("请选择推送人");
			return false;
		}
		var workflowId = '${examWorkflow.id}';
		$("#workflowId").attr("value",workflowId);
		$("#processType").val(processType);
		$("#personType").val(personType);
		$("#inputForm").submit();
	}
</script>
<div>
	<div id="top">
		<%@include file="/WEB-INF/views/modules/exam/step.jsp" %>
	</div>
	<form:form id="inputForm" action="${ctx}/exam/examWorkflowDatas/saveBeta" method="post" class="form-horizontal">
		<input id="num" name="num" value="0" type="hidden"/>
		<input id="status" name="status" value="${status}" type="hidden"/>
		<input id="workflowId" name="workflowId" value="${workflowId}" type="hidden"/>
		<input id="operationStatus" name="operationStatus" value="" type="hidden"/>
		<input id="processType" name="processType" value="${processType}" type="hidden"/>
		<input id="personType" name="personType" value="${personType}" type="hidden"/>
		<input id="nextStatus" name="nextStatus" value="${nextStep}" type="hidden"/>
		<input id="fillPersonId" name="fillPersonId" value="${fillPersonId}" type="hidden"/>

		<sys:message content="${message}"/>
		<table id="contentTable" class="table table-striped table-bordered table-condensed">
			<thead>
			<tr>
				<th>选择</th>
					<%--<c:forEach items="${columnList}" var="examStandardTemplateItemDefine">
                        <th>${examStandardTemplateItemDefine.columnName}</th>
                    </c:forEach>--%>
				<th>项目</th>
				<th>类别</th>
				<th>评分标准</th>
				<th>标准分</th>
				<th>备注</th>
				<!--
                <th>上级检查评分情况</th>
                -->
				<%--<c:choose>
					<c:when test="${'2'.equals(modleType)}">
						<th>加分(实际)</th>
					</c:when>
					<c:otherwise>
						<th>扣分(实际)</th>
					</c:otherwise>
				</c:choose>--%>
				<th>加扣分(实际)</th>
				<th>具体事项</th>
				<th>附件</th>
				<th>理由</th>
				<th>状态</th>
				<c:if test="${processType == 'appraiseExam'}">
					<th>操作</th>
				</c:if>
			</tr>
			</thead>
			<tbody>
			<c:forEach items="${workflowDatasList}" varStatus="status"  var="workflowData">
				<tr>
					<td>
						<input class='i-checks' type='checkbox' name='selectedBox'
							   value='${workflowData.id}'/>
					</td>
					<td>${workflowData.s1}</td>
					<td>${workflowData.s2}</td>
					<td>${workflowData.s3}</td>
						<%--                            <td>${workflowData.s4}</td>--%>
					<td>${workflowData.s5}</td>
					<td>${workflowData.s6}</td>
					<td><input type='text' name='datas[${status.index}].value' style='width:30px;' class='input-xlarge  number required'
							   value='${workflowData.value}'<c:if test="${'3'.equals(row.operationStatus)}">readonly="readonly"</c:if>
					/></td>
					<td><textarea rows='2' name='datas[${status.index}].items' cols='10'>${workflowData.items}</textarea></td>
					<td>
						<input id="datas${status.index}-nameFile" name="datas[${status.index}].path" type="hidden"
							   value="${workflowData.path}" class="fileinput"/>
						<ol id="datas${status.index}-nameFilePreview"></ol>
						<div id="datas${status.index}-nameFileUploaderDiv" class="nameFileUploaderDiv">
							<div class="queueList">
								<div id="nameFileDndArea" class='placeholder'>
									<div class="nameFileFilePicker" onclick="setNumber('${status.index}')"></div>
								</div>
							</div>
							<div class="statusBar" style="display:none;">
								<div class="progress">
									<span class="text">0%</span>
									<span class="percentage"></span>
								</div><div class="info"></div>
								<div class="btns">
									<div id="datas${status.index}-nameFileFilePicker2"></div><div class="uploadBtn">开始上传</div>
								</div>
							</div>
						</div>
					</td>
					<td>${workflowData.detail}</td>
					<td>${ fns:getDictLabel (workflowData.operationStatus, "workflow_operation_status", "")}</td>
					<c:if test="${processType == 'appraiseExam'}">
						<td>
							<a href="javascript:;">删除</a>
							<a href="${ctx}/exam/examWorkflowDatas/id=${workflowData.id}}">推送</a>
						</td>
					</c:if>
					<input type='hidden' name='datas[${status.index}].standardId'
						   value='${workflowData.standardId}'/>
					<input type='hidden' name='datas[${status.index}].workflowId'
						   value='${workflowData.workflowId}'/>
					<input type='hidden' name='datas[${status.index}].rowId'
						   value='${workflowData.rowId}'/>
					<input type='hidden' name='datas[${status.index}].id'
						   value='${workflowData.id}'/>
				</tr>

			</c:forEach>
			</tbody>
		</table>
		<c:if test="${history != 'true'}">
                    <div class="tb-btn tb-jia" style="text-align: right;margin-bottom: 20px;"><i class="icon-plus-sign" onclick="selectTerm()"></i></div>
                    <div class="tb-btn tb-jian" style="text-align: right;margin-bottom: 20px;"><i class="icon-minus-sign" onclick="removeByIds()"></i></div>
                </c:if>
		<%--	<div class="pagination">${page}</div>--%>
		<div style="text-align: right;">
				<%--<input id="save" class="btn btn-primary" type="submit" value="保存" <c:if test="${examWorkflow.operationStatus =='1'}"> disabled="true"</c:if> />&nbsp;
                <input id="complete" class="btn btn-primary" type="button" value="结束并推送到" onclick="sendSubmit('${examWorkflow.status+1}','1')"
                        <c:if test="${'false'==isEdit}"> disabled="true" </c:if> />
                <sys:treeselect id="person" name="personId" value="${personId}" labelName="personName" labelValue="${person}"
                                title="用户" url="/sys/office/treeData?type=3" cssClass="" allowClear="true" notAllowSelectParent="true" isAll="true"/>--%>

			<c:choose>
				<c:when test="${'2'.equals(nextStep)&& !'true'.equals(history)}" >
					<input id="save" class="btn btn-primary" type="button" value="保存" onclick="formSubmit('save')"  <c:if test="${'false'==isEdit}"> disabled="true" </c:if> />&nbsp;&nbsp;&nbsp;&nbsp;
					<input id="complete" class="btn btn-primary" type="button" value="结束并推送到" onclick="sendSubmit('selfAppraise','appraiseExam')"  <c:if test="${'false'==isEdit}"> disabled="true" </c:if> />
					<sys:treeselect id="person" name="personId" value="${personId}" labelName="personName" labelValue="${person}"
									title="用户" url="/sys/office/treeData?type=3" cssClass="" allowClear="true" notAllowSelectParent="true" isAll="true"/>
				</c:when>
				<c:when test="${'3'.equals(nextStep) && '4'.equals(nnextStep)&& !'true'.equals(history)}" >
					<input id="save" class="btn btn-primary" type="button" value="保存" onclick="formSubmit('save')"  <c:if test="${'false'==isEdit}"> disabled="true" </c:if> />&nbsp;&nbsp;&nbsp;&nbsp;
					<input id="complete" class="btn btn-primary" type="button" value="结束并推送到" onclick="sendSubmit('appraiseExam','departSign')"  <c:if test="${'false'==isEdit}"> disabled="true" </c:if> />
					<sys:treeselect id="person" name="personId" value="${personId}" labelName="personName" labelValue="${person}"
									title="用户" url="/sys/office/treeData?type=3" cssClass="" allowClear="true" notAllowSelectParent="true" isAll="true"/>
				</c:when>
				<c:when test="${'3'.equals(nextStep) &&'5'.equals(nnextStep)&& !'true'.equals(history)}">
					<input id="save" class="btn btn-primary" type="button" value="保存22" onclick="formSubmit('save')"  <c:if test="${'false'==isEdit}"> disabled="true" </c:if> />&nbsp;&nbsp;&nbsp;&nbsp;
					<input id="complete" class="btn btn-primary" type="button" value="结束并推送到" onclick="sendSubmit('appraiseExam','partBureausSign')"  <c:if test="${'false'==isEdit}"> disabled="true" </c:if> />
					<sys:treeselect id="person" name="personId" value="${personId}" labelName="personName" labelValue="${person}"
									title="用户" url="/sys/office/treeData?type=3" cssClass="" allowClear="true" notAllowSelectParent="true" isAll="true"/>
				</c:when>
				<c:when test="${'3'.equals(nextStep) &&'6'.equals(nnextStep)&& !'true'.equals(history)}">
					<input id="save" class="btn btn-primary" type="button" value="保存" onclick="formSubmit('save')"  <c:if test="${'false'==isEdit}"> disabled="true" </c:if> />&nbsp;&nbsp;&nbsp;&nbsp;
					<input id="complete" class="btn btn-primary" type="button" value="确认并推送到" onclick="sendSubmit('appraiseExam','groupAdjust')"  <c:if test="${'false'==isEdit}"> disabled="true" </c:if> />
					<sys:treeselect id="person" name="personId" value="${personId}" labelName="personName" labelValue="${person}"
									title="用户" url="/sys/office/treeData?type=3" cssClass="" allowClear="true" notAllowSelectParent="true" isAll="true"/>
				</c:when>
				<c:when test="${'3'.equals(nextStep) &&'7'.equals(nnextStep)&& !'true'.equals(history)}">
					<input id="complete" class="btn btn-primary" type="button" value="结束并推送到" onclick="sendSubmit('appraiseExam','mainBureausSign')"  <c:if test="${'false'==isEdit}"> disabled="true" </c:if> />
					<sys:treeselect id="person" name="personId" value="${personId}" labelName="personName" labelValue="${person}"
									title="用户" url="/sys/office/treeData?type=3" cssClass="" allowClear="true" notAllowSelectParent="true" isAll="true"/>
				</c:when>
				<c:when test="${'4'.equals(nextStep)&& !'true'.equals(history)}">
					<input id="save" class="btn btn-primary" type="button" value="保存" onclick="formSubmit('save')"  <c:if test="${'false'==isEdit}"> disabled="true" </c:if> />&nbsp;&nbsp;&nbsp;&nbsp;
					<input id="complete" class="btn btn-primary" type="button" value="结束并推送到" onclick="sendSubmit('departSign','departSign')"  <c:if test="${'false'==isEdit}"> disabled="true" </c:if> />
					<sys:treeselect id="person" name="personId" value="${personId}" labelName="personName" labelValue="${person}"
									title="用户" url="/sys/office/treeData?type=3" cssClass="" allowClear="true" notAllowSelectParent="true" isAll="true"/>
				</c:when>
				<c:when test="${'4'.equals(nextStep)&&'6'.equals(nnextStep)&& !'true'.equals(history)}">
					<input id="save" class="btn btn-primary" type="button" value="保存" onclick="formSubmit('save')"  <c:if test="${'false'==isEdit}"> disabled="true" </c:if> />&nbsp;&nbsp;&nbsp;&nbsp;
					<input id="complete" class="btn btn-primary" type="button" value="结束并推送到" onclick="sendSubmit('departSign','groupAdjust')"  <c:if test="${'false'==isEdit}"> disabled="true" </c:if> />
					<sys:treeselect id="person" name="personId" value="${personId}" labelName="personName" labelValue="${person}"
									title="用户" url="/sys/office/treeData?type=3" cssClass="" allowClear="true" notAllowSelectParent="true" isAll="true"/>
				</c:when>
				<c:when test="${'4'.equals(nextStep)&&'7'.equals(nnextStep)&& !'true'.equals(history)}">
					<input id="save" class="btn btn-primary" type="button" value="保存" onclick="formSubmit('save')"  <c:if test="${'false'==isEdit}"> disabled="true" </c:if> />&nbsp;&nbsp;&nbsp;&nbsp;
					<input id="complete" class="btn btn-primary" type="button" value="结束并推送到" onclick="sendSubmit('departSign','mainBureausSign')"  <c:if test="${'false'==isEdit}"> disabled="true" </c:if> />
					<sys:treeselect id="person" name="personId" value="${personId}" labelName="personName" labelValue="${person}"
									title="用户" url="/sys/office/treeData?type=3" cssClass="" allowClear="true" notAllowSelectParent="true" isAll="true"/>
				</c:when>
				<c:when test="${'5'.equals(nextStep)&& !'true'.equals(history)}">
					<input id="save" class="btn btn-primary" type="button" value="保存" onclick="formSubmit('save')"  <c:if test="${'false'==isEdit}"> disabled="true" </c:if> />&nbsp;&nbsp;&nbsp;&nbsp;
					<input id="complete" class="btn btn-primary" type="button" value="结束并推送到" onclick="sendSubmit('partBureausSign','partBureausSign')"  <c:if test="${'false'==isEdit}"> disabled="true" </c:if> />
					<sys:treeselect id="person" name="personId" value="${personId}" labelName="personName" labelValue="${person}"
									title="用户" url="/sys/office/treeData?type=3" cssClass="" allowClear="true" notAllowSelectParent="true" isAll="true"/>
				</c:when>
				<c:when test="${'6'.equals(nextStep)&& !'true'.equals(history)}">
					<input id="save" class="btn btn-primary" type="button" value="保存" onclick="formSubmit('save')"  <c:if test="${'false'==isEdit}"> disabled="true" </c:if> />&nbsp;&nbsp;&nbsp;&nbsp;
					<input id="complete" class="btn btn-primary" type="button" value="确认并推送到1111" onclick="sendSubmit('groupAdjust','groupAdjust')"  <c:if test="${'false'==isEdit}"> disabled="true" </c:if> />
					<sys:treeselect id="person" name="personId" value="${personId}" labelName="personName" labelValue="${person}"
									title="用户" url="/sys/office/treeData?type=3" cssClass="" allowClear="true" notAllowSelectParent="true" isAll="true"  disabled="disabled"/><%--为啥disabled--%>
				</c:when>
				<c:when test="${'7'.equals(nextStep)&& !'true'.equals(history)}">
					<input id="save" class="btn btn-primary" type="button" value="保存" onclick="formSubmit('save')"  <c:if test="${'false'==isEdit}"> disabled="true" </c:if> />&nbsp;&nbsp;&nbsp;&nbsp;
					<input id="complete" class="btn btn-primary" type="button" value="结束并推送到" onclick="sendSubmit('mainBureausSign','mainBureausSign')"  <c:if test="${'false'==isEdit}"> disabled="true" </c:if> />
					<sys:treeselect id="person" name="personId" value="${personId}" labelName="personName" labelValue="${person}"
									title="用户" url="/sys/office/treeData?type=3" cssClass="" allowClear="true" notAllowSelectParent="true" isAll="true"/>
				</c:when>
				<c:when test="${'8'.equals(nextStep)&& !'true'.equals(history)}">
					<input id="save" class="btn btn-primary" type="button" value="保存" onclick="formSubmit('save')"  <c:if test="${'false'==isEdit}"> disabled="true" </c:if>  />&nbsp;&nbsp;&nbsp;&nbsp;
					<input id="complete" class="btn btn-primary" type="button" value="结束本项工作考评" onclick="formSubmit('appraiseExam','')" <c:if test="${status==nextStep-1}"> disabled="true" </c:if> />
				</c:when>
				<c:otherwise>
					<c:if test="${!'true'.equals(history)}">
						<input id="complete" class="btn btn-primary" type="button" value="结束并推送到" onclick="sendSubmit('appraiseExam')"/>
						<sys:treeselect id="person" name="personId" value="" labelName="" labelValue=""
										title="用户" url="/sys/office/treeData?type=3" cssClass="" allowClear="true" notAllowSelectParent="true" isAll="true"/>
					</c:if>
				</c:otherwise>
			</c:choose>


		</div>
	</form:form>
</div>
<script type="text/javascript">
	function nameFileDel(obj,num){
		var url = $(obj).prev().attr("url");
		$("#datas"+num+"-nameFile").val($("#datas"+num+"-nameFile").val().replace("|"+url,"","").replace(url+"|","","").replace(url,"",""));
		nameFilePreview(num);
	}
	function nameFileDelAll(num){
		$("#datas"+num+"-nameFile").val("");
		nameFilePreview();
	}
	function nameFilePreview(num){
		var li, urls = $("#"+"datas"+num+"-nameFile").val().split("|");
		$("#datas"+num+"-nameFilePreview").children().remove();
		for (var i=0; i<urls.length; i++){
			if (urls[i]!=""){//
				li = "<li><a href=\"/politics" +urls[i]+"\" url=\""+urls[i]+"\" target=\"_blank\">"+decodeURIComponent(urls[i].substring(urls[i].lastIndexOf("/")+1))+"</a>";//
				li += "&nbsp;&nbsp;<a href=\"javascript:\" onclick=\"nameFileDel(this,"+num+");\">×</a></li>";
				$("#datas"+num+"-nameFilePreview").append(li);
			}
		}
		if ($("#datas"+num+"-nameFilePreview").text() == ""){
			$("#datas"+num+"-nameFilePreview").html("<li style='list-style:none;padding-top:5px;'>无</li>");
		}
	}
	function setNumber(val) {
		//debugger;er
		$('#num').val(val);
	}

	function initFilePreview() {
		$(".fileinput").each(function (index,el) {
			var li, urls = $("#"+"datas"+index+"-nameFile").val().split("|");
			$("#datas"+index+"-nameFilePreview").children().remove();
			for (var i=0; i<urls.length; i++){
				if (urls[i]!=""){//
					li = "<li><a href=\"/politics" +urls[i]+"\" url=\""+urls[i]+"\" target=\"_blank\">"+decodeURIComponent(urls[i].substring(urls[i].lastIndexOf("/")+1))+"</a>";//
					li += "&nbsp;&nbsp;<a href=\"javascript:\" onclick=\"nameFileDel(this,"+index+");\">×</a></li>";
					$("#datas"+index+"-nameFilePreview").append(li);
				}
			}
			if ($("#datas"+index+"-nameFilePreview").text() == ""){
				$("#datas"+index+"-nameFilePreview").html("<li style='list-style:none;padding-top:5px;'>无</li>");
			}
		});
	}
	initFilePreview();
</script>
<script type="text/javascript">
	function init(){
		$(function() {
			var num = $('#num').val();
			var $wrap = $('#datas'+num+'-nameFileUploaderDiv'),
					$wrap = $('.nameFileUploaderDiv'),
					// 图片容器
					$queue = $( '<ul class="filelist"></ul>' )
							.appendTo( $wrap.find( '.queueList' ) ),

					// 状态栏，包括进度和控制按钮
					$statusBar = $wrap.find( '.statusBar' ),

					// 文件总体选择信息。
					$info = $statusBar.find( '.info' ),

					// 上传按钮
					$upload = $wrap.find( '.uploadBtn' ),

					// 没选择文件之前的内容。
					$placeHolder = $wrap.find( '.placeholder' ),
					$progress = $statusBar.find( '.progress' ).hide(),

					// 添加的文件数量
					fileCount = 0,
					// 添加的文件总大小
					fileSize = 0,

					// 优化retina, 在retina下这个值是2
					ratio = window.devicePixelRatio || 1,

					// 缩略图大小
					thumbnailWidth = 110 * ratio,
					thumbnailHeight = 110 * ratio,

					// 可能有pedding, ready, uploading, confirm, done.
					state = 'pedding',

					// WebUploader实例
					uploader;
			// 实例化
			uploader = WebUploader.create({
				auto:true,
				pick: {
					id: '.nameFileFilePicker',
					multiple:true,
					label: '选择'
				},
				formData: {
					path: "affair/exam"
				},
				//dnd: '#nameFileDndArea',
				//paste: '#nameFileUploaderDiv',
				swf: '${ctxStatic}/webuploader/0.1.5/Uploader.swf',
				chunked: false,
				server: '${ctx}/file/upload',
				// 禁掉全局的拖拽功能。这样不会出现图片拖进页面的时候，把图片打开。
				disableGlobalDnd: true,
				fileNumLimit: 20,
				fileSizeLimit: 200 * 1024 * 1024,    // 200 M
				fileSingleSizeLimit: 50 * 1024 * 1024    // 50 M
			});

			// 拖拽时不接受 js, txt 文件。
			uploader.on( 'dndAccept', function( items ) {
				var denied = false,
						len = items.length,
						i = 0,
						// 修改js类型
						unAllowed = 'text/plain;application/javascript ';

				for ( ; i < len; i++ ) {
					// 如果在列表里面
					if ( ~unAllowed.indexOf( items[ i ].type ) ) {
						denied = true;
						break;
					}
				}

				return !denied;
			});

			uploader.on('ready', function() {
				window.uploader = uploader;
			});

			// 当有文件添加进来时执行，负责view的创建
			function addFile( file ) {
				var $li = $( '<li id="' + file.id + '">' +
						'<p class="title">' + file.name + '</p>' +
						// '<p class="imgWrap"></p>'+
						'<p class="progress"><span></span></p>' +
						'</li>' ),

						$btns = $('<div class="file-panel">' +
								'<span class="cancel">删除</span>' +
								'<span class="rotateRight">向右旋转</span>' +
								'<span class="rotateLeft">向左旋转</span></div>').appendTo( $li ),
						$prgress = $li.find('p.progress span'),
						$wrap = $li.find( 'p.imgWrap' ),
						$info = $('<p class="error"></p>'),

						showError = function( code ) {
							switch( code ) {
								case 'exceed_size':
									text = '文件大小超出';
									break;
								case 'interrupt':
									text = '上传暂停';
									break;
								default:
									text = '上传失败，请重试';
									break;
							}
							$info.text( text ).appendTo( $li );
						};

				file.on('statuschange', function( cur, prev ) {
					if ( prev === 'progress' ) {
						$prgress.hide().width(0);
					} else if ( prev === 'queued' ) {
						$li.off( 'mouseenter mouseleave' );
						$btns.remove();
					}
					// 成功
					if ( cur === 'error' || cur === 'invalid' ) {
						showError( file.statusText );
						// percentages[ file.id ][ 1 ] = 1;
					} else if ( cur === 'interrupt' ) {
						showError( 'interrupt' );
					} else if ( cur === 'queued' ) {
						// percentages[ file.id ][ 1 ] = 0;
					} else if ( cur === 'progress' ) {
						$info.remove();
						$prgress.css('display', 'block');
					} else if ( cur === 'complete' ) {
						$li.append( '<span class="success"></span>' );
					}
					$li.removeClass( 'state-' + prev ).addClass( 'state-' + cur );
				});
				$li.on( 'mouseenter', function() {
					$btns.stop().animate({height: 30});
				});
				$li.on( 'mouseleave', function() {
					$btns.stop().animate({height: 0});
				});
				$btns.on( 'click', 'span', function() {
					var index = $(this).index(),
							deg;
					switch ( index ) {
						case 0:
							uploader.removeFile( file );
							return;
						case 1:
							file.rotation += 90;
							break;
						case 2:
							file.rotation -= 90;
							break;
					}
					if ( supportTransition ) {
						deg = 'rotate(' + file.rotation + 'deg)';
						$wrap.css({
							'-webkit-transform': deg,
							'-mos-transform': deg,
							'-o-transform': deg,
							'transform': deg
						});
					} else {
						$wrap.css( 'filter', 'progid:DXImageTransform.Microsoft.BasicImage(rotation='+ (~~((file.rotation/90)%4 + 4)%4) +')');
					}
				});
				$li.appendTo( $queue );
			}

			// 负责view的销毁
			function removeFile( file ) {
				var $li = $('#'+file.id);
				updateTotalProgress();
				$li.off().find('.file-panel').off().end().remove();
			}

			function updateTotalProgress() {
				updateStatus();
			}

			function updateStatus() {
				var text = '', stats;
				var typeName = '文件', unit='份';
				if ( state === 'ready' ) {
					text = '选中' + fileCount + unit + typeName+'，共' +
							WebUploader.formatSize(fileSize) + '。';
				} else if ( state === 'confirm' ) {
					stats = uploader.getStats();
					if ( stats.uploadFailNum ) {
						text = '已成功上传' + stats.successNum + unit + typeName+'，'
						stats.uploadFailNum + unit + typeName+'上传失败，<a class="retry" href="#">重新上传</a>失败'+typeName+''
					}

				} else {
					stats = uploader.getStats();
					text = '共' + fileCount + unit + '（' +
							WebUploader.formatSize(fileSize) +
							'），已上传' + stats.successNum + unit;

					if (stats.uploadFailNum) {
						text += '，失败' + stats.uploadFailNum + unit;
					}
				}
				if($(".fileinput").length>0){
					nameFilePreview($("#num").val());
				}
				//$info.html( text );
			}

			function setState( val ) {
				var file, stats;
				if ( val === state ) {
					return;
				}
				$upload.removeClass( 'state-' + state );
				$upload.addClass( 'state-' + val );
				state = val;
				var num = $('#num').val();
				switch ( state ) {
					case 'pedding':
						$placeHolder.removeClass( 'element-invisible' );
						$queue.hide();
						$statusBar.addClass( 'element-invisible' );
						uploader.refresh();
						break;
					case 'ready':
						$placeHolder.addClass( 'element-invisible' );
						$('#datas'+num+'nameFileFilePicker2').removeClass( 'element-invisible');
						$queue.show();
						$statusBar.removeClass('element-invisible');
						uploader.refresh();
						break;
					case 'uploading':
						$('#datas'+num+'nameFileFilePicker2').addClass( 'element-invisible' );
						$progress.show();
						//$upload.text( '暂停上传' );
						break;
					case 'paused':
						$progress.show();
						// $upload.text( '继续上传' );
						break;
					case 'confirm':
						$progress.hide();
						$('#datas'+num+'nameFileFilePicker2').removeClass( 'element-invisible' );
						//$upload.text( '开始上传' );
						stats = uploader.getStats();
						if ( stats.successNum && !stats.uploadFailNum ) {
							setState( 'finish' );
							return;
						}
						break;
					case 'finish':
						stats = uploader.getStats();
						if ( stats.successNum ) {

						} else {
							// 没有成功的图片，重设
							state = 'done';
							location.reload();
						}
						break;
				}
				updateStatus();
			}

			uploader.onUploadProgress = function( file, percentage ) {
				var $li = $('#'+file.id),
						$percent = $li.find('.progress span');
				$percent.css( 'width', percentage * 100 + '%' );
				//percentages[ file.id ][ 1 ] = percentage;
				updateTotalProgress();
			};

			uploader.onFileQueued = function( file ) {
				fileCount++;
				fileSize += file.size;
				if ( fileCount === 1 ) {
					$placeHolder.addClass( 'element-invisible' );
					$statusBar.show();
				}
				addFile( file );
				setState( 'ready' );
				updateTotalProgress();
			};

			uploader.onFileDequeued = function( file ) {
				fileCount--;
				fileSize -= file.size;
				if ( !fileCount ) {
					setState( 'pedding' );
				}
				removeFile( file );
				updateTotalProgress();
			};

			uploader.on( 'uploadSuccess', function( file,data) {
				if(data.success){
					var num = $('#num').val();
					var url = data.result;
					var newUrl = "";
					var oldUrl = $("#datas"+num+"-nameFile").val();
					if(oldUrl==""){
						newUrl = url;
					} else if (oldUrl.indexOf(url) == -1 ){
						newUrl = oldUrl + "|" + url;
					} else {
						var uArr = url.split("/");
						var name = uArr[uArr.length - 1];
						alert(name + " 已存在");
						newUrl = oldUrl;
					}
					$("#datas"+num+"-nameFile").val(newUrl);
				}
				$('.filelist').hide();
				$('.statusBar').hide();
			});

			uploader.on( 'all', function( type ) {
				var stats;
				switch( type ) {
					case 'uploadFinished':
						setState( 'confirm' );
						break;
					case 'startUpload':
						setState( 'uploading' );
						break;
					case 'stopUpload':
						setState( 'paused' );
						break;
				}
			});

			uploader.onError = function( code ) {
				var text = '';
				if(code=="Q_TYPE_DENIED"){
					text = "文件类型不支持，仅支持gif,jpg,jpeg,bmp,png格式图片";
				}else if(code=="F_DUPLICATE"){
					text = "文件已选中，请勿重复上传";
				}else if(code="F_EXCEED_SIZE"){
					text = "文件大小超出限制，不得超过5MB";
				} else{
					text = 'Error: ' + code;
				}
				$info.html( text );
			};

			$upload.on('click', function() {
				if ( $(this).hasClass( 'disabled' ) ) {
					return false;
				}
				if ( state === 'ready' ) {
					uploader.upload();
				} else if ( state === 'paused' ) {
					uploader.upload();
				} else if ( state === 'uploading' ) {
					uploader.stop();
				}
			});
			$info.on( 'click', '.retry', function() {
				uploader.retry();
			} );
			$info.on( 'click', '.ignore', function() {
			} );
			$upload.addClass( 'state-' + state );
			updateTotalProgress();
		});
	}
	init();
</script>