<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>练兵比武管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {


			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairTraining/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairTraining/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairTraining/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairTraining/");
							}
							if (v == 'cancel') {
								$.jBox.tip('已取消');
							}
							return true;
						};
						$.jBox.confirm("您是否要导出数据？", "数据导出确认", submit, { buttons: { '导出全部数据': 'all', '导出当前页面数据': 'part','取消':'cancel'} });
					}
			);
			$("#print").click(function(){
				$('#handleTh').css('display', 'none');
				$('.handleTd').css('display', 'none');
				$('#headTitle').addClass("table table-striped table-bordered table-condensed");
				$('#headTitle').removeAttr('style');
				$("#contentTable").printThis({
					debug: false,
					importCSS: false,
					importStyle: false,
					printContainer: true,
					loadCSS: ["${ctxStatic}/bootstrap/2.3.1/css_cerulean/bootstrap.min.css","${ctxStatic}/bootstrap/2.3.1/awesome/font-awesome.min.css","${ctxStatic}/common/jeesite.css"],
					pageTitle: "",
					removeInline: false,
					printDelay: 333,
					header: "",
					formValues: false,
					afterPrint:function(){
						$('#handleTh').css('display', 'table-cell');
						$('.handleTd').css('display', 'table-cell');
						$('#headTitle').css('display', 'none');
					}
				});
			});
			$("#btnImport").click(function(){
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairTraining", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairTraining/list?unitId=${unitId}&unit=${unit}&pageNo=${page.pageNo}&pageSize=${page.pageSize}";}});
			});
		});


		function openForm(url) {
			top.$.jBox.open("iframe:"+url, "",1000,600,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairTraining/list?unitId=${unitId}&unit=${unit}&pageNo=${page.pageNo}&pageSize=${page.pageSize}";
				}
			});
		}

		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairTraining/formDetail?id="+id;
			top.$.jBox.open(url, "",1000,600,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		}
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<%--<ul class="nav nav-tabs">
		<shiro:hasPermission name="affair:affairTraining:view"><li class="active"><a href="${ctx}/affair/affairTraining/list">练兵比武</a></li></shiro:hasPermission>
			&lt;%&ndash;<shiro:hasPermission name="affair:affairTrainingManage:view"><li><a href="${ctx}/affair/affairTrainingManage/list">成绩管理员练兵比武</a></li></shiro:hasPermission>&ndash;%&gt;
			<shiro:hasPermission name="affair:affairJobTraining:view"><li><a href="${ctx}/affair/affairJobTraining/">岗位练兵</a></li></shiro:hasPermission>
			<shiro:hasPermission name="affair:affairTrainCombat:view"><li><a href="${ctx}/affair/affairTrainCombat/list">实弹训练</a></li></shiro:hasPermission>
			<shiro:hasPermission name="affair:affairSwapExercise:view"><li><a href="${ctx}/affair/affairSwapExercise/">交流锻炼</a></li></shiro:hasPermission>
			<shiro:hasPermission name="affair:affairTrainOutsource:view"><li><a href="${ctx}/affair/affairTrainOutsource/">委外培训</a></li></shiro:hasPermission>
			<shiro:hasPermission name="affair:affairSendTeacher:view"><li><a href="${ctx}/affair/affairSendTeacher/">送教上门</a></li></shiro:hasPermission>
	</ul>--%>
	<form:form id="searchForm" modelAttribute="affairTraining" action="${ctx}/affair/affairTraining/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="练兵比武表.xlsx"/>
		<ul class="ul-form">
			<li><label>考核比武名称：</label>
				<form:input path="title" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>类型：</label>
				<form:input path="type" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>层次：</label>
				<form:input path="level" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>主办部门：</label>
				<sys:treeselect id="unitId" name="unitId" value="${affairTraining.unitId}" labelName="unit" labelValue="${affairTraining.unit}"
								title="所属机构" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<li>
				<label>比武考核日期：</label>
				<input name="beginTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairTraining.beginTime}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> -
				<input name="endTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairTraining.endTime}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>

			<li><label>域：</label>
				<form:input path="createBy" htmlEscape="false" class="input-medium"/>
			</li>
			<%--<li><label>创建人：</label>
				<form:input path="createBy" htmlEscape="false" class="input-medium"/>
			</li>--%>
			<%--<li><label>创建时间：</label>
				<form:input path="createOrgId" htmlEscape="false" class="input-medium"/>
			</li>--%>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/affair/affairTraining/form')"/></li>
			<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairTraining/deleteByIds','checkAll','myCheckBox')"/></li>
			<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairTraining/list?organizationId=${organizationId}&organization=${organization}'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>

	<div id="contentTable">
		<table id="headTitle"  class="table table-striped table-bordered table-condensed" style="display: none;">
			<tr>
				<td style="text-align: center; font-size: 36px;padding-top: 10px;" >南宁铁路公安处练兵比武报表</td>
			</tr>
		</table>
		<table  class="table table-striped table-bordered table-condensed">

		<thead>
			<tr>
				<th><input style='margin-left:12px' type='checkbox'id='checkAll'onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>考核比武名称</th>
				<th>类型</th>
				<th>层次</th>
				<th>主办部门</th>
				<th>考核开始日期</th>
				<th>考核结束日期</th>
				<th>描述</th>
				<th>创建单位</th>
				<th>状态</th>
				<shiro:hasPermission name="affair:affairTraining:edit"><th class="handleTd">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairTraininginfo" varStatus="status">
			<tr>
				<td>
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairTraininginfo.id}"/>
				</td>
				<td>${(page.pageNo-1)*page.pageSize+status.index+1}</td>
				<td>
					${affairTraininginfo.title}
				</td>
				<td>
					${affairTraininginfo.type}
				</td>
				<td>
					${affairTraininginfo.level}
				</td>
				<td>
					${affairTraininginfo.unit}
				</td>
				<td>
					<fmt:formatDate value="${affairTraininginfo.beginTime}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<fmt:formatDate value="${affairTraininginfo.endTime}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${affairTraininginfo.description}
				</td>
				<td>
						${fns:getUserById(affairTraininginfo.createBy).getOffice()}
				</td>
				<td>
					${affairTraininginfo.state}
				</td>
				<td class="handleTd">
					<a href="javascript:void(0)" onclick="openDetailDialog('${affairTraininginfo.id}')">查看</a>
					<shiro:hasPermission name="affair:affairTraining:edit">
						<a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairTraining/form?id=${affairTraininginfo.id}')">修改</a>
						<a href="${ctx}/affair/affairTraining/delete?id=${affairTraininginfo.id}&unitId=${affairTraining.unitId}" onclick="return confirmx('确认要删除该练兵比武吗？', this.href)">删除</a>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	</div>
	<div class="pagination">${page}</div>
</body>
</html>