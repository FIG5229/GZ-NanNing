<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>学历信息管理</title>
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
		});


		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }

		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/personnel/personnelXueli/formDetail?id="+id;
			top.$.jBox.open(url, "学历信息",800,500,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
        }
    </script>
</head>
<body>
	<form:form id="searchForm" modelAttribute="personnelXueli" action="${ctx}/personnel/personnelXueli/maxEducationList" method="post"
			   class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="mType" name="mType" type="hidden" value="${mType}"/>
		<input id="idNumber" name="idNumber" type="hidden" value="${personnelXueli.idNumber}"/>
		<input id="fileName" name="fileName" type="hidden" value="学历信息集.xlsx"/>

		<input id="label" name="label" type="hidden" value="${personnelXueli.label}"/>
		<input id="unitId" name="unitId" type="hidden" value="${personnelXueli.unitId}"/>
		<%--<ul class="ul-form">
			<li><label>学历名称：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label class="width140">学校(单位)名称：</label>
				<form:input path="schoolName" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>入学日期：</label>
				<input name="beginDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${personnelXueli.beginDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				至
				<input name="finishDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${personnelXueli.finishDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>记录状态：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('personnel_jltype')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label class="width120">学习完成情况：</label>
				<form:select path="completeSituation" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('personnel_xuexi')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
			<li class="btns"><input class="btn btn-primary" type="button" value="重置"
									onclick="window.location.href='${ctx}/personnel/personnelXueli/fullTimeEducationList?mType=1&?idNumber=${personnelXueli.idNumber}'"/>
			</li>
			<li class="clearfix"></li>
		</ul>--%>
	</form:form>


	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<c:if test="${mType==null || mType eq '1'}">
				<th><input style='margin-left:12px' type='checkbox'id='checkAll'onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				</c:if>
				<th>序号</th>
				<th>姓名</th>
				<th>单位</th>
				<th>学历名称</th>
				<th>学校(单位)名称</th>
				<th>入学日期</th>
				<th>毕(肄)业日期</th>
				<th>学制(年)</th>
				<th>所学专业名称</th>
				<th>从学类别</th>
				<th>最高学历说明</th>
				<th>记录状态</th>
				<th>学习完成情况</th>
<%--					<shiro:hasPermission name="personnel:personnelXueli:edit"><th id="handleTh">操作</th></shiro:hasPermission>--%>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="personnelXueli" varStatus="status">
			<tr>
				<c:if test="${mType==null || mType eq '1'}">
					<td><input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${personnelXueli.id}"/></td></c:if>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
						${personnelXueli.personName}
				</td>
				<td>
						${personnelXueli.unit}
				</td>
				<td><a onclick="openDetailDialog('${personnelXueli.id}')">
					${personnelXueli.name}
				</a></td>
				<td>
					${personnelXueli.schoolName}
				</td>
				<td>
					<fmt:formatDate value="${personnelXueli.startDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<fmt:formatDate value="${personnelXueli.endDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${personnelXueli.year}
				</td>
				<td>
					${personnelXueli.majorCode}
				</td>
				<td>
					${fns:getDictLabel(personnelXueli.type1, 'personnel_cxtype', '')}
				</td>
				<td>
					${personnelXueli.explain}
				</td>
				<td>
					${fns:getDictLabel(personnelXueli.status, 'personnel_jltype', '')}
				</td>
				<td>
					${fns:getDictLabel(personnelXueli.completeSituation, 'personnel_xuexi', '')}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>