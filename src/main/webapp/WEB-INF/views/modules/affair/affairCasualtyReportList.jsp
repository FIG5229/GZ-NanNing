<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>抚恤申报管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#print").click(function(){
				$('#handleTh').css('display', 'none');
				$('.handleTd').css('display', 'none');
				$("#contentTable").printThis({
					debug: false,
					importCSS: false,
					importStyle: false,
					printContainer: true,
					loadCSS: ["${ctxStatic}/bootstrap/2.3.1/css_cerulean/bootstrap.min.css","${ctxStatic}/bootstrap/2.3.1/awesome/font-awesome.min.css","${ctxStatic}/common/jeesite.css"],
					pageTitle: "打印",
					removeInline: false,
					printDelay: 333,
					header: null,
					formValues: false,
					afterPrint:function (){
						$('#handleTh').css('display', 'table-cell');
						$('.handleTd').css('display', 'table-cell');
					}
				});
			});
			//导出功能的JS
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairCasualtyReport/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairCasualtyReport/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairCasualtyReport/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairCasualtyReport/");
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
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairCasualtyReport", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairCasualtyReport"}});
			});
			$("[data-toggle='popover']").popover();
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		function openForm(url) {
			top.$.jBox.open("iframe:"+url, "抚恤申报管理",800,520,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairCasualtyReport"}
			});
		}
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairCasualtyReport/formDetail?id="+id;
			top.$.jBox.open(url, "抚恤申报详情",800,500,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		};
		function submitByIds() {
			var ids = [];
			$("input:checkbox[name=myCheckBox]:checked").each(function () {
				console.log($(this).val())
				ids.push($(this).val());
			});
			if (ids.length > 0) {
				$.ajax({
					type:"post",
					url:"${ctx}/affair/affairCasualtyReport/submitByIds",
					data:{ids:ids},
					dataType:"json",
					success:function(data){
						$.jBox.tip(data.message);
						resetCheckBox("checkAll","myCheckBox");
						location.reload();
					}
				})
			} else {
				$.jBox.tip('请先选择要提交的内容');
			}
		};
		$('#notPass').popover();
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/affair/affairCasualtyReport/">抚恤申报</a></li>
		<shiro:hasPermission name="affair:affairCasualtyReport:manage"><li >
			<a href="${ctx}/affair/affairCasualtyReport/manageList">抚恤审核</a></li></shiro:hasPermission>
		<shiro:hasPermission name="personnel:personnelPensionRecord:view">
			<li ><a href="${ctx}/personnel/personnelPensionRecord?mType=3">抚恤发放</a></li>
		<li ><a href="${ctx}/affair/affairCasualtyReport/statistic">伤亡信息查询</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="affairCasualtyReport" action="${ctx}/affair/affairCasualtyReport/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="抚恤申报.xlsx"/>
		<ul class="ul-form">
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>种类与性质：</label>
				<form:select path="type" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_casualty_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>伤亡时间：</label>
				<input name="beginCasualtyDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairCasualtyReport.beginCasualtyDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> -
				<input name="endCasualtyDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairCasualtyReport.endCasualtyDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>部门名称：</label>
				<sys:treeselect id="affirmDep" name="affirmDepId" value="${affairCasualtyReport.affirmDepId}" labelName="affirmDep" labelValue="${affairCasualtyReport.affirmDep}"
					title="部门名称" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<li><label>认定部门：</label>
				<sys:treeselect id="depName" name="depNameId" value="${affairCasualtyReport.depNameId}" labelName="depName" labelValue="${affairCasualtyReport.depName}"
								title="认定部门" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<li><label>审核状态：</label>
				<form:select path="shStatus" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_query_shenhe')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairCasualtyReport:edit">
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/affair/affairCasualtyReport/form')"/></li>
				<li class="btns"><input class="btn btn-primary" type="button" value="提交" onclick="submitByIds()" /></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairCasualtyReport/deleteByIds','checkAll','myCheckBox')"/></li>
				<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
			</shiro:hasPermission>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印" /></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairCasualtyReport'"/></li>
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
				<th>种类与性质</th>
				<th>部门与警种</th>
				<th>伤亡时间</th>
				<th>部门名称</th>
				<th>认定部门</th>
				<th>认定时间</th>
				<th>复核部门</th>
				<th>牺牲/病故证明书编号</th>
				<th>负伤程度</th>
				<th>审核状态</th>
				<shiro:hasPermission name="affair:affairCasualtyReport:edit"><th id="handleTh">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairCasualtyReport" varStatus="status">
			<tr>
				<td><input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairCasualtyReport.id}"/></td>
				<td>${(page.pageNo-1)*page.pageSize+status.index+1}</td>
				<td><a onclick="openDetailDialog('${affairCasualtyReport.id}')">
				${affairCasualtyReport.name}
				</a></td>
				<td>
					${fns:getDictLabel(affairCasualtyReport.type, 'affair_casualty_type', '')}
				</td>
				<td>
					${affairCasualtyReport.depPolice}
				</td>
				<td>
					<fmt:formatDate value="${affairCasualtyReport.casualtyDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${affairCasualtyReport.affirmDep}
				</td>
				<td>
					${affairCasualtyReport.depName}
				</td>
				<td>
					<fmt:formatDate value="${affairCasualtyReport.affirmDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${affairCasualtyReport.checkDep}
				</td>
				<td>
					${affairCasualtyReport.certificateNo1}
				</td>
				<td>
					${affairCasualtyReport.injuryDegree}
				</td>
				<td>
					<c:choose>
						<c:when test="${affairCasualtyReport.shStatus == '2'}">
							<a id="notPass" data-container="body" data-toggle="popover" data-placement="right"
							   data-content="${affairCasualtyReport.opinion}"  style="cursor: pointer;color: red">未通过</a>
						</c:when>
						<c:otherwise>
							${fns:getDictLabel(affairCasualtyReport.shStatus, 'affair_query_shenhe', '')}
						</c:otherwise>
					</c:choose>
				</td>
				<shiro:hasPermission name="affair:affairCasualtyReport:edit"><td class="handleTd">
					<c:if test="${affairCasualtyReport.createBy.id == fns:getUser().id}">
						<a href="javascript:void(0)"
						   onclick="openForm('${ctx}/affair/affairCasualtyReport/form?id=${affairCasualtyReport.id}')">修改</a>
						<a href="${ctx}/affair/affairCasualtyReport/delete?id=${affairCasualtyReport.id}"
						   onclick="return confirmx('确认要删除该抚恤申报吗？', this.href)">删除</a>
					</c:if>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>