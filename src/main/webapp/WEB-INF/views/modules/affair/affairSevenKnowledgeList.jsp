<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>七知档案管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript" src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#print").click(function () {
				$('#handleTh').css('display', 'none');
				$('.handleTd').css('display', 'none');
				$("#contentTable").printThis({
					debug: false,
					importCSS: false,
					importStyle: false,
					printContainer: true,
					loadCSS: ["${ctxStatic}/bootstrap/2.3.1/css_cerulean/bootstrap.min.css", "${ctxStatic}/bootstrap/2.3.1/awesome/font-awesome.min.css", "${ctxStatic}/common/jeesite.css"],
					pageTitle: "打印",
					removeInline: false,
					printDelay: 333,
					header: null,
					formValues: false,
					afterPrint: function () {
						$('#handleTh').css('display', 'table-cell');
						$('.handleTd').css('display', 'table-cell');
					}
				});
			});
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairSevenKnowledge/exportBatch");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairSevenKnowledge/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairSevenKnowledge/exportBatch?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairSevenKnowledge/");
							}
							if (v == 'cancel') {
								$.jBox.tip('已取消');
							}
							return true;
						};
						$.jBox.confirm("您是否要导出数据？", "数据导出确认", submit, { buttons: { '导出全部数据': 'all', '导出当前页面数据': 'part','取消':'cancel'} });
					}
			);

			$("#btnImport").click(function(){
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairSevenKnowledge", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairSevenKnowledge"}});
			});
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
        function openDetailDialog(id) {
<%--			${ctx}/affair/affairSevenKnowledge/formDetail?id=--%>
			var url = "iframe:${ctx}/affair/affairSevenKnowledge/formDetail?id="+id;
			top.$.jBox.open(url, "七知档案",1000,600,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});

		}
		//添加修改弹窗
		function openAddEditDialog(id) {
			var url = "iframe:${ctx}/affair/affairSevenKnowledge/form?id="+id;
			if (id == null || id == undefined){
				url = "iframe:${ctx}/affair/affairSevenKnowledge/form";
			}
			top.$.jBox.open(url, "七知档案",1000,600,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		};

		//导入
		function openImportForm(idNumber,name) {
			var submit = function (v, h, f) {
				if (v == 'confirm') {
					top.$.jBox.open("iframe:${ctx}/file/template/affairSevenKnowledge/view?id=affair_affairSevenKnowledge&idNumber="+idNumber, name+"七知档案导入",800,520,{title:name+"七知档案导入", buttons:{"关闭":true},
						bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",
						closed:function (){
							self.location.href="${ctx}/affair/affairSevenKnowledge/"
						}});
				}
				if (v == 'cancel') {
					$.jBox.tip('已取消');
				}
				return true;
			};
			$.jBox.confirm("您是否要更新"+name+"的七知档案?", "数据导入确认", submit, { buttons: { '确认': 'confirm', '取消':'cancel'} });



		}

	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/affair/affairSevenKnowledge/">七知档案</a></li>
<%--		<shiro:hasPermission name="affair:affairSevenKnowledge:edit"><li><a href="${ctx}/affair/affairSevenKnowledge/form">七知档案添加</a></li></shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="affairSevenKnowledge" action="${ctx}/affair/affairSevenKnowledge/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="3.5七知档案.xlsx"/>
		<ul class="ul-form">
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>性别：</label>
				<form:select path="sex" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>出生日期：</label>
				<input name="beginBirthday" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairSevenKnowledge.beginBirthday}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> - 
				<input name="endBirthday" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairSevenKnowledge.endBirthday}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>政治面貌：</label>
				<form:select path="politicsFace" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('zzmm')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>参加工作时间：</label>
				<input name="beginWorkTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairSevenKnowledge.beginWorkTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> - 
				<input name="endWorkTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairSevenKnowledge.endWorkTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>从警时间：</label>
				<input name="beginFromPoliceTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairSevenKnowledge.beginFromPoliceTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> - 
				<input name="endFromPoliceTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairSevenKnowledge.endFromPoliceTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
				<%--11.6问题反馈 管理账号应该能选择全部单位，现在只能选择宣教部门账号（三个公安处的共同问题）kevin.jia--%>
			<c:choose>
				<c:when test="${ 'd30e324c8f73492d9b74103374fbc689' eq fns:getUser().id || 'e3ac8381fb3247e0b64fd6e3c48bddc1' eq fns:getUser().id || '66937439b2124f328d1521968fab06db' eq fns:getUser().id || 'd154234ecb35470e84fb95e53726866b' eq fns:getUser().id}">
					<li><label>工作单位：</label>
						<sys:treeselect id="unit" name="unitId" value="${affairSevenKnowledge.unitId}" labelName="unit" labelValue="${affairSevenKnowledge.unit}"
										title="单位" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="false" isAll="true"/>
					</li>
				</c:when>
				<c:otherwise>
					<li><label>工作单位：</label>
						<sys:treeselect id="unit" name="unitId" value="${affairSevenKnowledge.unitId}" labelName="unit" labelValue="${affairSevenKnowledge.unit}"
										title="单位" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="false"/>
					</li>
				</c:otherwise>
			</c:choose>
			<%--<li><label>工作单位：</label>
				<sys:treeselect id="unit" name="unitId" value="${affairSevenKnowledge.unitId}" labelName="unit" labelValue="${affairSevenKnowledge.unit}"
								title="单位" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="false"/>
			</li>--%>
			<li><label>职务：</label>
				<form:input path="job" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>民族：</label>
				<form:select path="nation" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('nation')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>年度：</label>
				<input name="year" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="${affairSevenKnowledge.year}"
					   onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false});"/>
			</li>
			<li><label>月度：</label>
				<input name="month" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="${affairSevenKnowledge.month}"
					   onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false});"/>
			</li>
			<li><label>评定类别：</label>
				<form:select path="evaluate" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('seven_evaluate_rating')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairSevenKnowledge:edit">
				<li class="btns">
					<input  class="btn btn-primary"  type="button" value="添加" onclick="openAddEditDialog()"/>
				</li>
				<li class="btns">
					<input id="del" class="btn btn-primary" type="button" value="删除"
						   onclick="deleteByIds('${ctx}/affair/affairSevenKnowledge/deleteByIds','checkAll','myCheckBox')"/>
				</li>
<%--
				<a href="javascript:void(0)" onclick="openImportForm('${affairSevenKnowledge.idNumber}','${affairSevenKnowledge.name}')">导入七知档案</a>
--%>
<%--				<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>--%>
			</shiro:hasPermission>

			<li class="btns">
				<input id="export" class="btn btn-primary" type="button" value="导出"/>
			</li>
			<li class="btns">
				<input id="print" class="btn btn-primary" type="button" value="打印"/>
			</li>
			<li class="btns">
				<input  class="btn btn-primary" type="button" value="重置"
						onclick="window.location.href='${ctx}/affair/affairSevenKnowledge'"/>
			</li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th id="checkTh"><input style='margin-left:12px' type='checkbox' id='checkAll' onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>工作单位</th>
				<th>姓名</th>
				<th>性别</th>
				<th>民族</th>
				<th>政治面貌</th>
				<th>出生日期</th>
				<th>参加工作时间</th>
				<th>从警时间</th>
				<th>职务</th>
				<th>综合评定</th>
				<shiro:hasPermission name="affair:affairSevenKnowledge:edit"><th id="handleTh">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairSevenKnowledge" varStatus="status">
			<tr
					<c:choose>
						<c:when test="${affairSevenKnowledge.evaluate == '3'}">
							style="color: #4e8bff"
						</c:when>
						<c:when test="${affairSevenKnowledge.evaluate == '2'}">
							style="color: #d5bb38"
						</c:when>
						<c:when test="${affairSevenKnowledge.evaluate == '1'}">
							style="color: #d53732"
						</c:when>
						<c:otherwise></c:otherwise>
					</c:choose>
			>

				<td class="checkTd">
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairSevenKnowledge.id}"/>
				</td>
				<td>
					${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
					${affairSevenKnowledge.unit}
				</td>
				<td>
					${affairSevenKnowledge.name}
				</td>
				<td>
						${fns:getDictLabel(affairSevenKnowledge.sex, 'sex', '')}
				</td>
				<td>
						${fns:getDictLabel(affairSevenKnowledge.nation, 'nation', '')}
				</td>
				<td>
					${fns:getDictLabel(affairSevenKnowledge.politicsFace, 'political_status', '')}
				</td>
				<td>
					<fmt:formatDate value="${affairSevenKnowledge.birthday}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<fmt:formatDate value="${affairSevenKnowledge.workTime}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<fmt:formatDate value="${affairSevenKnowledge.fromPoliceTime}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
						${affairSevenKnowledge.job}
				</td>
				<td>
						${fns:getDictLabel(affairSevenKnowledge.evaluate, "seven_evaluate_rating", "")}
				</td>
				<td class="handleTd">
						<a href="javascript:;" onclick="openDetailDialog('${affairSevenKnowledge.id}')">查看</a>
					<shiro:hasPermission name="affair:affairSevenKnowledge:edit">
						<a href="javascript:;" onclick="openAddEditDialog('${affairSevenKnowledge.id}')">修改</a>
						<a href="${ctx}/affair/affairSevenKnowledge/delete?id=${affairSevenKnowledge.id}"
						   onclick="return confirmx('确认要删除该七知档案吗？', this.href)">删除</a>
					</shiro:hasPermission>
						<a href="${ctx}/affair/affairSevenKnowledge/export?id=${affairSevenKnowledge.id}&fileName=3.5七知档案.xlsx"
						   onclick="return confirmx('确认要导出${affairSevenKnowledge.name}的七知档案吗？', this.href)">导出</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>