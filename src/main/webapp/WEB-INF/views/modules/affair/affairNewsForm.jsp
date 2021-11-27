<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>新闻宣传管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
			newsSdopt
			$("#newsSdopt").change(function () {
				//debugger;er;
				console.log($("#newsSdopt").val());
				switchAdopt();
			});
			function switchAdopt() {
				if ($("#newsSdopt").val() == '8'){
					$("#inputAdopt").css('display', 'inline-block');
				}else {
					$("#inputAdopt").css('display', 'none');
				}
			}

		});

		//根据姓名自动查询相关信息
		function setDefaults() {
			//清空
			$("#idNumber").val('');
			$.ajax({
				type:"post",
				url:"${ctx}/affair/affairNews/getPersonByName",
				data:{name:$("#name").val()},
				dataType:"json",
				success:function(data){
					if(data.success==true && data.result.length==1){
						$("#idNumber").val(data.result[0].idNumber);
					}else if(data.success==true && data.result.length>1){
						var html = '<table id="contentTable" class="table table-striped table-bordered table-condensed">';
						html += '<thead><tr><th></th><th>姓名</th><th>身份证号</th></tr></thead>';
						html += '<tbody>';
						for(var i=0; i< data.result.length; i++) {
							html += '<tr><td><input type="radio" name="selected" value="'+i+'"></td>';
							html += '<td>'+data.result[i].name+'</td>';
							html += '<td>'+data.result[i].idNumber+'</td>';
							html += '</tr>';
						}

						html +=	'</tbody>';
						html +=	'</table>';
						var submit = function (v, h, f) {
							$("#idNumber").val(data.result[f.selected].idNumber);
							return true;
						};
						top.$.jBox(html, { title: "输入",width: 600, height: 300, submit: submit });
					}else {
						$.jBox.tip('没有查询到该人名相关信息');
					}
				}
			})
		}

		/**
		 * 主子表的添加和删除列
		 */
		function addRow(list, idx, tpl, row) {
			$(list).append(Mustache.render(tpl, {
				idx: idx, delBtn: true, row: row
			}));
			$(list + idx).find("select").each(function () {
				$(this).val($(this).attr("data-value"));
			});
			$(list + idx).find("input[type='checkbox'], input[type='radio']").each(function () {
				var ss = $(this).attr("data-value").split(',');
				for (var i = 0; i < ss.length; i++) {
					if ($(this).val() == ss[i]) {
						$(this).attr("checked", "checked");
					}
				}
			});
		}

		function delRow(obj, prefix) {
			var id = $(prefix + "_id");
			var delFlag = $(prefix + "_delFlag");
			if (id.val() == "") {
				$(obj).parent().parent().remove();
			} else if (delFlag.val() == "0") {
				delFlag.val("1");
				$(obj).html("&divide;").attr("title", "撤销删除");
				$(obj).parent().parent().addClass("error");
			} else if (delFlag.val() == "1") {
				delFlag.val("0");
				$(obj).html("&times;").attr("title", "删除");
				$(obj).parent().parent().removeClass("error");
			}
		}



	</script>
</head>
<body>
	<br/>
	<form:form id="inputForm" modelAttribute="affairNews" action="${ctx}/affair/affairNews/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">新闻标题：</label>
			<div class="controls">
				<form:input path="title" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">媒体名称：</label>
			<div class="controls">
				<form:input path="mediaName" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">栏目：</label>
			<div class="controls">
				<form:input path="lm" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">刊发时间：</label>
			<div class="controls">
				<input name="date" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					   value="<fmt:formatDate value="${affairNews.date}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>

			</div>
		</div>
		<div class="control-group">
			<label class="control-label">宣传对象：</label>
			<div class="controls">
				<form:input path="object" htmlEscape="false" class="input-xlarge"/>
				<%--<span class="help-inline"><font color="red">*</font> </span>--%>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">篇幅字数：</label>
			<div class="controls">
				<form:input path="wordNum" htmlEscape="false" class="input-xlarge  digits"/>
				<%--<span class="help-inline"><font color="red">*</font> </span>--%>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">刊发类别：</label>
			<div class="controls">
				<form:select path="typr" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_news')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否在春运期间：</label>
			<div class="controls">
				<form:select path="isYear" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('is_new_year')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">稿件采用情况：</label>
			<div class="controls">
				<form:select id="newsSdopt" path="adopt" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('news_adopt')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<form:input id="inputAdopt" path="inputAdopt" htmlEscape="false" class="input-xlarge required" cssStyle="display: none"/>
			</div>
		</div>
		<%--需求变更
		刊稿稿件”添加稿件时“所属单位”“作者”“所属人”可能不止一个，所以在预设中应增加“所属单位”
  “作者”“所属人”多选功能，另稿件排名自动区分多个单位、作者、所属人并分别计算稿件。
   树单选择父节点选择不上。需要再沟通
  --%>
<%--		<div class="control-group">
			<label class="control-label">作者：</label>
			<div class="controls">
				<form:input path="author" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">作者：</label>
			<div class="controls">
				<table id="authorTable" class="table table-striped table-bordered table-condensed">
					<thead>
					<tr>
						<th class="hide"></th>
						<th>姓名</th>
						<th>身份证号</th>
						<th width="10">
<%--							<input  class="btn btn-primary"  type="button" value="导入" onclick="what()"/>--%>
<%--								<input  class="btn btn-primary"  type="button" value="导出" onclick="exportChild('${affairPoliceHome.id}')"/>--%>

						</th>
					</tr>
					</thead>
					<tbody id="affairNewsAuthorList">
					</tbody>
					<shiro:hasPermission name="affair:affairNews:edit">
						<tfoot>
						<tr>
							<td colspan="46">
								<a href="javascript:;"
								   onclick="addRow('#affairNewsAuthorList', affairNewsAuthorRowIdx, affairNewsAuthorChildrenTpl);affairNewsAuthorRowIdx = affairNewsAuthorRowIdx + 1;"
								   class="btn">新增</a>
							</td>
						</tr>
						</tfoot>
					</shiro:hasPermission>
				</table>
				<script type="text/template" id="affairNewsAuthorChildrenTpl">
				//<!--
                    <tr id="affairNewsAuthorList{{idx}}">
					<td class="hide">
						<input id="affairNewsAuthorList{{idx}}_id" name="affairNewsAuthorList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
						<input id="affairNewsAuthorList{{idx}}_delFlag" name="affairNewsAuthorList[{{idx}}].delFlag" type="hidden" value="0"/>
						<input id="affairNewsAuthorList{{idx}}_newsId" name="affairNewsAuthorList[{{idx}}].newsId" type="hidden" value="{{row.newsId}}"/>
					</td>

					<td>
						<input id="affairNewsAuthorList{{idx}}_newsAuthor" name="affairNewsAuthorList[{{idx}}].newsAuthor" type="text" value="{{row.newsAuthor}}" class="input-small "/>
					</td>

					<td>
						<input id="affairNewsAuthorList{{idx}}_idNumber" name="affairNewsAuthorList[{{idx}}].idNumber" type="text" value="{{row.idNumber}}" class="input-small required"/>
					</td>

					<td class="text-center" width="10">
						{{#delBtn}}<span class="close" onclick="delRow(this, '#affairNewsAuthorList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
					</td>
				</tr>
				//-->
				</script>
				<script type="text/javascript">
					var affairNewsAuthorRowIdx = 0,
                        affairNewsAuthorChildrenTpl = $("#affairNewsAuthorChildrenTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g, "");
					function initAuthorData(data){
						$('#affairNewsAuthorList').html('');
						for (var i = 0; i < data.length; i++) {
							addRow('#affairNewsAuthorList', affairNewsAuthorRowIdx, affairNewsAuthorChildrenTpl, data[i]);
                            affairNewsAuthorRowIdx = affairNewsAuthorRowIdx + 1;
						}
					}
					$(document).ready(function () {
						var data = ${fns:toJson(affairNews.affairNewsAuthorList)};
						initAuthorData(data);
					});
				</script>
			</div>
		</div>

<%--		<div class="control-group">--%>
<%--			<label class="control-label">所属单位：</label>--%>
<%--			<div class="controls">--%>
<%--				<sys:treeselect id="unit" name="unitId" value="${affairNews.unitId}" labelName="unit" labelValue="${affairNews.unit}"--%>
<%--								title="所属单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="false" dataMsgRequired="必填信息"/>--%>
				<%--<sys:treeselect id="unit" name="unitId" value="${affairNews.unitId}" labelName="unit" labelValue="${affairNews.unit}"
								title="所属单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" checked="true" notAllowSelectParent="false" dataMsgRequired="必填信息"/>--%>
<%--				<span class="help-inline"><font color="red">*</font> </span>--%>
<%--			</div>--%>
<%--		</div>--%>

	<%--	<div class="control-group">
			<label class="control-label">所属人：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" class="input-xlarge " onkeydown="if(event.keyCode==13){setDefaults();return false;}"/>
				<span class="help-inline"><font color="red">输入完后请务必按回车自动查询相关信息</font> </span>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">所属单位：</label>
			<div class="controls">
				<table id="unitTable" class="table table-striped table-bordered table-condensed">
					<thead>
					<tr>
						<th class="hide"></th>
						<th>单位</th>
						<th width="10">&nbsp;
								<%--							<input  class="btn btn-primary"  type="button" value="导入" onclick="what()"/>--%>
								<%--<input  class="btn btn-primary"  type="button" value="导出" onclick="exportChild('${affairPoliceHome.id}')"/>
                            --%>
						</th>
					</tr>
					</thead>
					<tbody id="affairNewsUnitList">
					</tbody>
					<shiro:hasPermission name="affair:affairNews:edit">
						<tfoot>
						<tr>
							<td colspan="46">
								<a href="javascript:;"
								   onclick="addRow('#affairNewsUnitList', affairNewsUnitRowIdx, affairNewsUnitTpl);affairNewsUnitRowIdx = affairNewsUnitRowIdx + 1;"
								   class="btn">新增</a>
							</td>
						</tr>
						</tfoot>
					</shiro:hasPermission>
				</table>
				<script type="text/template" id="affairNewsUnitTpl">
					<tr id="affairNewsUnitList{{idx}}">
						<td class="hide">
							<input id="affairNewsUnitList{{idx}}_id" name="affairNewsUnitList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
							<input id="affairNewsUnitList{{idx}}_delFlag" name="affairNewsUnitList[{{idx}}].delFlag" type="hidden" value="0"/>
							<input id="affairNewsUnitList{{idx}}_newsId" name="affairNewsUnitList[{{idx}}].newsId" type="hidden" value="{{row.newsId}}"/>
						</td>
						<td>
								<%--11.6问题反馈 管理账号应该能选择全部单位，现在只能选择宣教部门账号（三个公安处的共同问题）kevin.jia--%>
							<c:choose>
							<c:when test="${ 'd30e324c8f73492d9b74103374fbc689' eq fns:getUser().id || 'e3ac8381fb3247e0b64fd6e3c48bddc1' eq fns:getUser().id || '66937439b2124f328d1521968fab06db' eq fns:getUser().id || 'd154234ecb35470e84fb95e53726866b' eq fns:getUser().id}">
								//<!--<sys:treeselect id="affairNewsUnitList{{idx}}_newsUnit" name="affairNewsUnitList[{{idx}}].newsUnitId"  value="{{row.newsUnitId}}" labelName="affairNewsUnitList[{{idx}}].newsUnit" labelValue="{{row.newsUnit}}"
													  title="单位" url="/sys/office/treeData?type=2" allowClear="true" notAllowSelectParent="false" dataMsgRequired="必填信息" isAll="true"/>
								//-->
							</c:when>
									<c:otherwise>
										//<!--<sys:treeselect id="affairNewsUnitList{{idx}}_newsUnit" name="affairNewsUnitList[{{idx}}].newsUnitId"  value="{{row.newsUnitId}}" labelName="affairNewsUnitList[{{idx}}].newsUnit" labelValue="{{row.newsUnit}}"
															  title="单位" url="/sys/office/treeData?type=2" allowClear="true" notAllowSelectParent="false" dataMsgRequired="必填信息" />
										//-->
									</c:otherwise>
							</c:choose>
							<%--//<!--<sys:treeselect id="affairNewsUnitList{{idx}}_newsUnit" name="affairNewsUnitList[{{idx}}].newsUnitId"  value="{{row.newsUnitId}}" labelName="affairNewsUnitList[{{idx}}].newsUnit" labelValue="{{row.newsUnit}}"
										title="单位" url="/sys/office/treeData?type=2" allowClear="true" notAllowSelectParent="false" dataMsgRequired="必填信息" />
										//-->--%>
						</td>


						<td class="text-center" width="10">
							{{#delBtn}}<span class="close" onclick="delRow(this, '#affairNewsUnitList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
						</td>
					</tr>
				</script>
				<script type="text/javascript">
					var affairNewsUnitRowIdx = 0,
							affairNewsUnitTpl = $("#affairNewsUnitTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g, "");
					function initUnitData(data){
						$('#affairNewsUnitList').html('');
						for (var i = 0; i < data.length; i++) {
							addRow('#affairNewsUnitList', affairNewsUnitRowIdx, affairNewsUnitTpl, data[i]);
							affairNewsUnitRowIdx = affairNewsUnitRowIdx + 1;
						}
					}
					$(document).ready(function () {
						var data = ${fns:toJson(affairNews.affairNewsUnitList)};
						initUnitData(data);
					});
				</script>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">所属人：</label>
			<div class="controls">
				<table id="contentTable" class="table table-striped table-bordered table-condensed">
					<thead>
					<tr>
						<th class="hide"></th>
						<th>姓名</th>
						<th>身份证号</th>
						<th width="10">&nbsp;
								<%--							<input  class="btn btn-primary"  type="button" value="导入" onclick="what()"/>--%>
								<%--<input  class="btn btn-primary"  type="button" value="导出" onclick="exportChild('${affairPoliceHome.id}')"/>
                            --%>
						</th>
					</tr>
					</thead>
					<tbody id="affairNewsNameList">
					</tbody>
					<shiro:hasPermission name="affair:affairNews:edit">
						<tfoot>
						<tr>
							<td colspan="46">
								<a href="javascript:;"
								   onclick="addRow('#affairNewsNameList', affairNesNameRowIdx, affairNewsNameTpl);affairNesNameRowIdx = affairNesNameRowIdx + 1;"
								   class="btn">新增</a>
							</td>
						</tr>
						</tfoot>
					</shiro:hasPermission>
				</table>
				<script type="text/template" id="affairNewsNameTpl">
					<tr id="affairNewsNameList{{idx}}">
						<td class="hide">
							<input id="affairNewsNameList{{idx}}_id" name="affairNewsNameList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
							<input id="affairNewsNameList{{idx}}_delFlag" name="affairNewsNameList[{{idx}}].delFlag" type="hidden" value="0"/>
							<input id="affairNewsNameList{{idx}}_newsId" name="affairNewsNameList[{{idx}}].newsId" type="hidden" value="{{row.newsId}}"/>
						</td>
						<td>
							<input id="affairNewsNameList{{idx}}_newsName" name="affairNewsNameList[{{idx}}].newsName" type="text" value="{{row.newsName}}" class="input-small "/>
						</td>
						<td>
							<input id="affairNewsNameList{{idx}}_newsIdNumber" name="affairNewsNameList[{{idx}}].newsIdNumber" type="text" value="{{row.newsIdNumber}}" class="input-small required"/>
						</td>

						<td class="text-center" width="10">
							{{#delBtn}}<span class="close" onclick="delRow(this, '#affairNewsNameList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
						</td>
					</tr>
				</script>
				<script type="text/javascript">
					var affairNesNameRowIdx = 0,
							affairNewsNameTpl = $("#affairNewsNameTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g, "");
					function initData(data){
						$('#affairNewsNameList').html('');
						for (var i = 0; i < data.length; i++) {
							addRow('#affairNewsNameList', affairNesNameRowIdx, affairNewsNameTpl, data[i]);
							affairNesNameRowIdx = affairNesNameRowIdx + 1;
						}
					}
					$(document).ready(function () {
						var data = ${fns:toJson(affairNews.affairNewsNameList)};
						initData(data);
					});
				</script>
			</div>
		</div>





























	<%--	<div class="control-group">
			<label class="control-label">所属人身份证号：</label>
			<div class="controls">
				<form:input path="idNumber" htmlEscape="false" class="input-xlarge"/>

			</div>
		</div>--%>

		<div class="control-group">
			<label class="control-label">内容：</label>
			<div class="controls">
<%--				<form:textarea path="content" htmlEscape="false" rows="4" class="input-xxlarge "/>--%>
					<form:textarea id="content" htmlEscape="true" path="content" rows="4" maxlength="150" class="input-xxlarge"/>
					<sys:ckeditor replace="content" uploadPath="/cms/article" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">附件：</label>
			<div class="controls">
				<form:hidden id="filePath" path="filePath" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:webuploader input="filePath" type="files" uploadPath="affair/affairNews" selectMultiple="true"/>
			</div>
		</div>

		<div class="form-actions">
			<shiro:hasPermission name="affair:affairNews:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.$.jBox.close();"/>
		</div>
	</form:form>
	<script>
		if("sucess"=="${saveResult}"){
			parent.$.jBox.tip("保存成功");
			parent.$.jBox.close();
		}
	</script>
</body>
</html>