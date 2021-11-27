<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>学习教育管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairStudyEducation/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairStudyEducation/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairStudyEducation/export?flag=true")
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairStudyEducation/");
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
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairStudyEducation", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairStudyEducation"}});
			});
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
					afterPrint:function(){
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
		//添加function,用于打开form添加页面弹窗,需要传一个url
		function openForm(url) {
			top.$.jBox.open("iframe:"+url, "学习教育",800,700,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairStudyEducation"}
			});
		}
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairStudyEducation/formDetail?id="+id;
			top.$.jBox.open(url, "学习教育详情",800,500,{
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
		<li class="active"><a href="${ctx}/affair/affairStudyEducation/">学习教育</a></li>
		<%--<shiro:hasPermission name="affair:affairStudyEducation:edit"><li><a href="${ctx}/affair/affairStudyEducation/form">学习教育添加</a></li></shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="affairStudyEducation" action="${ctx}/affair/affairStudyEducation/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="学习教育表.xlsx"/>
		<ul class="ul-form">
			<li><label>学习时间：</label>
				<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairStudyEducation.startDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				至
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairStudyEducation.endDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>团组织名称：</label>
				<sys:treeselect id="organization" name="organizationId" value="${affairStudyEducation.organizationId}" labelName="organization" labelValue="${affairStudyEducation.organization}"
					title="团组织名称" url="/affair/affairTwBase/treeData" cssClass="input-small" allowClear="true" />
			</li>
			<li><label>主题名称：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>类型：</label>
				<form:select path="type" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_xuexiluru')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>主持人：</label>
				<form:input path="host" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>记录人：</label>
				<form:input path="recorder" htmlEscape="false" class="input-medium"/>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairStudyEducation:edit">
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/affair/affairStudyEducation/form')"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairStudyEducation/deleteByIds','checkAll','myCheckBox')"/></li>
				<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
			</shiro:hasPermission>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairStudyEducation'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input style='margin-left:12px' type='checkbox'id='checkAll'onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>年度</th>
				<th>会议名称</th>
				<th>团组织名称</th>
				<th>会议类型</th>
				<th>主持人</th>
				<th>记录人</th>
				<th>学习时间</th>
				<th id="handleTh">操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairStudyEducation" varStatus="status">
			<tr>
				<td><input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairStudyEducation.id}"/></td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
						${affairStudyEducation.niandu}
				</td>
				<td>
				${affairStudyEducation.name}
				</td>
				<td>
						${affairStudyEducation.organization}
				</td>
				<td>
						${fns:getDictLabel(affairStudyEducation.type, 'affair_xuexiluru', '')}
				</td>
				<td>
						${affairStudyEducation.host}
				</td>
				<td>
						${affairStudyEducation.recorder}
				</td>
				<td>
					<fmt:formatDate value="${affairStudyEducation.date}" pattern="yyyy-MM-dd"/>
				</td>
				<td class="handleTd">
					<shiro:hasPermission name="affair:affairStudyEducation:edit">
						<a href="javascript:void(0)" onclick="openDetailDialog('${affairStudyEducation.id}')">查看</a>
						<%--<c:if test="${affairStudyEducation.createBy.id == fns:getUser().id}">--%>
							<a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairStudyEducation/form?id=${affairStudyEducation.id}')">修改</a>
							<a href="${ctx}/affair/affairStudyEducation/delete?id=${affairStudyEducation.id}" onclick="return confirmx('确认要删除该学习教育吗？', this.href)">删除</a>
						<%--</c:if>--%>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>