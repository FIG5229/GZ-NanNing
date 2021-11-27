<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>职务层次信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			//导出功能的JS
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/personnel/personnelJob/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/personnel/personnelJob/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/personnel/personnelJob/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/personnel/personnelJob/");
							}
							if (v == 'cancel') {
								$.jBox.tip('已取消');
							}
							return true;
						};
						$.jBox.confirm("您是否要导出数据？", "数据导出确认", submit, { buttons: { '导出全部数据': 'all', '导出当前页面数据': 'part','取消':'cancel'} });
					}
			);

//这是打印功能的JS
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

		});
		function excelImp(url){
			top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=personnel_personnelJob", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
				bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href=url}});
		}
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		function openForm(url,rUrl) {
			top.$.jBox.open("iframe:"+url, "职务层次信息管理",800,520,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href=rUrl}
			});
		}
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/personnel/personnelJob/formDetail?id="+id;
			top.$.jBox.open(url, "职务层次信息",800,500,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
        }
    </script>
</head>
<body>
			<form:form id="searchForm" modelAttribute="personnelJob" action="${ctx}/personnel/personnelJob/chartsPersonnelJobList" method="post" class="breadcrumb form-search">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
				<input id="idNumber" name="idNumber" type="hidden" value="${personnelJob.idNumber}"/>
				<input id="mType" name="mType" type="hidden" value="${mType}"/>
				<input id="fileName" name="fileName" type="hidden" value="职务层次信息集.xlsx"/>

				<input id="label" name="label" type="hidden" value="${personnelJob.label}"/>
				<input id="unitId" name="unitId" type="hidden" value="${personnelJob.unitId}"/>
			<%--	<ul class="ul-form">
					<li><label>职务层次：</label>
						<form:select path="jobLevel" class="input-medium">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('personnel_zwcc')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</li>
					<li><label class="width120">批准机关名称：</label>
						<form:input path="approvalOrgName" htmlEscape="false" class="input-medium"/>
					</li>
					<li><label>批准日期：</label>
						<input name="beginDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
							   value="<fmt:formatDate value="${personnelJob.beginDate}" pattern="yyyy-MM-dd"/>"
							   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
						至
						<input name="finishDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
							   value="<fmt:formatDate value="${personnelJob.finishDate}" pattern="yyyy-MM-dd"/>"
							   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
					</li>
					<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
					<shiro:hasPermission name="personnel:personnelJob:edit">
						<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/personnel/personnelJob/form?mType=1','${ctx}/personnel/personnelJob?mType=1')"/></li>
						<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/personnel/personnelJob/deleteByIds','checkAll','myCheckBox')"/></li>
						<li class="btns"><input  class="btn btn-primary" type="button" value="导入" onclick="excelImp('${ctx}/personnel/personnelJob?mType=1')"/></li>
					</shiro:hasPermission>
					<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
					<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印" /></li>
						<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/personnel/personnelJob?mType=1&idNumber=${personnelJob.idNumber}'"/></li>
					<li class="clearfix"></li>
				</ul>--%>
			</form:form>


	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr><c:if test="${mType==null || mType eq '1'}">
				<th><input style='margin-left:12px' type='checkbox'id='checkAll'onclick='chooseAll(this,"myCheckBox")'/>全选</th>
			</c:if>
				<th>序号</th>
				<th>姓名</th>
				<th>身份证号</th>
				<th>职务层次</th>
				<th>状态</th>
				<th>变动原因</th>
				<th>批准文号</th>
				<th>批准机关名称</th>
				<th>批准日期</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="personnelJob" varStatus="status">
			<tr>
				<c:if test="${mType==null || mType eq '1'}">
				<td><input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${personnelJob.id}"/></td>
				</c:if>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
						${personnelJob.personName}
				</td>
				<td>
						${personnelJob.idNumber}
				</td>
				<td>
					${fns:getDictLabel(personnelJob.jobLevel, 'personnel_zwcc', '')}
				</td>
				<td>
					${fns:getDictLabel(personnelJob.status, 'personnel_zwtype', '')}
				</td>
				<td>
					${personnelJob.reason}
				</td>
				<td>
					${personnelJob.fileNo}
				</td>
				<td>
					${personnelJob.approvalOrgName}
				</td>
				<td>
					<fmt:formatDate value="${personnelJob.approvalDate}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>