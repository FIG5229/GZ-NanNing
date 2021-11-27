<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>考勤信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			//导出功能的JS
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairWorkAttendance/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairWorkAttendance/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairWorkAttendance/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairWorkAttendance/");
							}
							if (v == 'cancel') {
								$.jBox.tip('已取消');
							}
							return true;
						};
						$.jBox.confirm("您是否要导出数据？", "数据导出确认", submit, { buttons: { '导出全部数据': 'all', '导出当前页面数据': 'part','取消':'cancel'} });
					}
			);
			//导入功能的JS
			$("#btnImport").click(function(){
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairWorkAttendance", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairWorkAttendance"}});
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		function openForm(url) {
			top.$.jBox.open("iframe:"+url, "考勤信息",800,520,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairWorkAttendance"}
			});
		}
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairWorkAttendance/formDetail?id="+id;
			top.$.jBox.open(url, "",800,500,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		};

	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/affair/affairWorkAttendance/">考勤信息</a></li>
		<%--<shiro:hasPermission name="affair:affairWorkAttendance:edit"><li><a href="${ctx}/affair/affairWorkAttendance/form">考勤信息添加</a></li></shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="affairWorkAttendance" action="${ctx}/affair/affairWorkAttendance/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="考勤信息.xlsx"/>
		<ul class="ul-form">
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>单位：</label>
				<sys:treeselect id="unit" name="unitId" value="${affairWorkAttendance.unitId}" labelName="unit" labelValue="${affairWorkAttendance.unit}"
					title="部门" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<li><label>日期：</label>
				<input name="beginDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairWorkAttendance.beginDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> -
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairWorkAttendance.endDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairWorkAttendance:edit">
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/affair/affairWorkAttendance/form')"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairWorkAttendance/deleteByIds','checkAll','myCheckBox')"/></li>
				<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
			</shiro:hasPermission>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairWorkAttendance'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input style='margin-left:12px' type='checkbox'id='checkAll'onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>姓名</th>
				<th>警种</th>
				<th>当月工时</th>
				<th>缺勤</th>
				<th>工伤</th>
				<th>年休</th>
				<th>出差</th>
				<th>执勤</th>
				<th>加班</th>
				<th>零星加班</th>
				<th>单位</th>
				<th>日期</th>
				<shiro:hasPermission name="affair:affairWorkAttendance:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairWorkAttendance" varStatus="status">
			<tr><td><input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairWorkAttendance.id}"/></td>
				<td>${(page.pageNo-1)*page.pageSize+status.index+1}</td>
				<td><a onclick="openDetailDialog('${affairWorkAttendance.id}')">
				${affairWorkAttendance.name}
				</a></td>
				<td>
					${fns:getDictLabel(affairWorkAttendance.policeType, 'affair_kaoqin_jingzhong', '')}
				</td>
				<td>
					${affairWorkAttendance.hour}
				</td>
				<td>
					${affairWorkAttendance.absence}
				</td>
				<td>
					${affairWorkAttendance.jobInjury}
				</td>
				<td>
					${affairWorkAttendance.annualRest}
				</td>
				<td>
					${affairWorkAttendance.evection}
				</td>
				<td>
					${affairWorkAttendance.onDuty}
				</td>
				<td>
					${affairWorkAttendance.overtime}
				</td>
				<td>
					${affairWorkAttendance.lxOvertime}
				</td>
				<td>
					${affairWorkAttendance.unit}
				</td>
				<td>
					<fmt:formatDate value="${affairWorkAttendance.date}" pattern="yyyy-MM-dd"/>
				</td>
				<shiro:hasPermission name="affair:affairWorkAttendance:edit"><td>
					<a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairWorkAttendance/form?id=${affairWorkAttendance.id}')">修改</a>
					<a href="${ctx}/affair/affairWorkAttendance/delete?id=${affairWorkAttendance.id}" onclick="return confirmx('确认要删除该考勤信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>