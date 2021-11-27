<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>推优入党管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("[data-toggle='popover']").popover();
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairPushParty/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairPushParty/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairPushParty/export?flag=true")
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairPushParty/");
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
			$("#btnImport").click(function(){
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairPushParty", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairPushParty"}});
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		function openForm(url) {
			top.$.jBox.open("iframe:"+url, "推优入党",900,600,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairPushParty"}
			});
		}
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairPushParty/formDetail?id="+id;
			top.$.jBox.open(url, "推优入党详情",800,500,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		};
		//提交JS
		function submitByIds() {
			if(null == $("#oneCheckId").val() || "" ==  $("#oneCheckId").val()){
				$.jBox.tip('请选择审核单位');
				return false;
			}
			var ids = [];
			$("input:checkbox[name=myCheckBox]:checked").each(function () {
				console.log($(this).val())
				ids.push($(this).val());
			});
			if (ids.length > 0) {
				var idsStr = ids.join(",");
				$("#idsValue").val(idsStr);
				$("#searchForm2").submit();
			} else {
				$.jBox.tip('请先选择要提交的内容');
			}
		};
		function openDialog(id) {
			top.$.jBox.open("iframe:${ctx}/affair/affairPushParty/shenHeDialog?id="+id,"推优入党",800,420,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},
				closed:function (){
					self.location.href="${ctx}/affair/affairPushParty/list";
				}
			});
		};
		$('#notPass').popover();
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/affair/affairPushParty/">推优入党</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="affairPushParty" action="${ctx}/affair/affairPushParty/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="推优入党表.xlsx"/>
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
			<li><label>民族：</label>
				<form:select path="nationality" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('nation')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>文化程度：</label>
				<form:select path="eduLevel" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_xueli')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>推优类型：</label>
				<form:select path="recommendType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('recommend_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<%--<li><label>介绍人：</label>
				<form:input path="introducer" htmlEscape="false" class="input-medium"/>
			</li>--%>
			<li><label>入团时间：</label>
				<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairPushParty.startDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				至
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairPushParty.endDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>文化程度：</label>
				<form:select path="recommendType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('recommend_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>推优时间：</label>
				<input name="startRecommendDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairPushParty.startDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				至
				<input name="endRecommendDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairPushParty.endDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairPushParty:edit">
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/affair/affairPushParty/form')"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairPushParty/deleteByIds','checkAll','myCheckBox')"/></li>
				<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入" /></li>
			</shiro:hasPermission>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出" /></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairPushParty'"/></li>
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
				<th>性别</th>
				<th>民族</th>
				<th>文化程度</th>
				<%--<th>介绍人</th>--%>
				<th>所属团组织</th>
				<th>入团时间</th>
				<th>推优类型</th>
				<th>推优时间</th>
				<th>审核状态</th>
				<th id="handleTh">操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairPushParty" varStatus="status">
			<tr>
				<td><input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairPushParty.id}"/></td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
					${affairPushParty.name}
				</td>
				<td>
					${fns:getDictLabel(affairPushParty.sex, 'sex', '')}
				</td>
				<td>
						${fns:getDictLabel(affairPushParty.nationality, 'nation', '')}
				</td>
				<td>
					${fns:getDictLabel(affairPushParty.eduLevel, 'affair_xueli', '')}
				</td>
				<%--<td>
					${affairPushParty.introducer}
				</td>--%>
				<td>
					${affairPushParty.partyBranch}
				</td>
				<td>
					<fmt:formatDate value="${affairPushParty.joinDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
						${fns:getDictLabel(affairPushParty.recommendType, 'recommend_type', '')}
				</td>
				<td>
					<fmt:formatDate value="${affairPushParty.recommendDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<c:choose>
						<c:when test="${affairPushParty.checkType == '0'}">
							<a id="notPass" data-container="body" data-toggle="popover" data-placement="right"
							   data-content="${affairPushParty.tuanweiOpinion}" style="cursor: pointer;color: red">审核未通过</a>
						</c:when>
						<c:otherwise>
							${fns:getDictLabel(affairPushParty.checkType, 'check_type', '')}
						</c:otherwise>
					</c:choose>
				</td>
				<td class="handleTd">
					<a href="javascript:void(0)" onclick="openDetailDialog('${affairPushParty.id}')">查看</a>
					<shiro:hasPermission name="affair:affairPushParty:edit">
					<c:if test="${affairPushParty.createBy.id == fns:getUser().id &&('0'.equals(affairPushParty.checkType)||'1'.equals(affairPushParty.checkType))}">
						<a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairPushParty/form?id=${affairPushParty.id}')">修改</a>
					</c:if>
					</shiro:hasPermission>
					<shiro:hasPermission name="affair:affairPushParty:manage">
						<a href="${ctx}/affair/affairPushParty/delete?id=${affairPushParty.id}" onclick="return confirmx('确认要删除该推优入党吗？', this.href)">删除</a>
						<a href="javascript:void(0)" onclick="openDialog('${affairPushParty.id}')">审核</a>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	<form:form id="searchForm2" modelAttribute="affairPushParty" action="${ctx}/affair/affairPushParty/submitByIds" method="post" class="breadcrumb form-search">
		<ul class="ul-form" style="text-align: right">
			<font color="red">请选择审核单位：</font>
			<input type="hidden" name="ids" id="idsValue"/>
			<%-- 34 南宁处  95  柳州处  156 北海处  1 南宁局--%>
			<%-- 33 南宁处团委  268 北海处团委  148 柳州处团委  9  公安局团委--%>
			<form:select id="oneCheckId" path="oneCheckId"  class="input-xlarge required">
				<c:choose>
					<c:when test="${(fns:getUser().office.id ne '34' && fns:getUser().company.id eq '34' && fns:getUser().office.id ne '33')}">
						<form:option value="28f59642a1e74d0588f0d515fe462775" label="南宁处团委"/>
						<%--<form:option value="ff7f9fe2597b40429ded58f8b76a2f65" label="南宁局团委"/> 10月28--%>
					</c:when>
					<c:when test="${(fns:getUser().office.id ne '156' && fns:getUser().company.id eq '156' && fns:getUser().office.id ne '268')}">
						<form:option value="78d0e07ed2e14ca0b6c73e14c11f4d55" label="北海处团委"/>
						<%--<form:option value="ff7f9fe2597b40429ded58f8b76a2f65" label="南宁局团委"/> 10月28--%>
					</c:when>
					<c:when test="${(fns:getUser().office.id ne '95' && fns:getUser().company.id eq '95' && fns:getUser().office.id ne '148')}">
						<form:option value="11d94fe57ede47a9bae4bffa36af487c" label="柳州处团委"/>
						<%--<form:option value="ff7f9fe2597b40429ded58f8b76a2f65" label="南宁局团委"/> 10月28--%>
					</c:when>
					<c:when test="${(fns:getUser().office.id eq '1' || fns:getUser().company.id eq '1' || fns:getUser().office.id eq '148'|| fns:getUser().office.id eq '268'|| fns:getUser().office.id eq '33' || fns:getUser().office.id eq '95' || fns:getUser().office.id eq '156' || fns:getUser().office.id eq '34')}">
						<form:option value="ff7f9fe2597b40429ded58f8b76a2f65" label="南宁局团委"/>
					</c:when>
					<c:otherwise>
						<form:options items="${fns:getDictList('user_tuanwei')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</c:otherwise>
				</c:choose>
				<%-- <form:option value="" label=""/>
                 <form:options items="${fns:getDictList('user_tuanwei')}" itemLabel="label" itemValue="value" htmlEscape="false"/>--%>
			</form:select>
		<%--	<sys:treeselect id="oneCheckMan" name="oneCheckId" value="${affairPushParty.oneCheckId}" labelName="oneCheckMan" labelValue="${affairPushParty.oneCheckMan}"
							title="单位" url="/sys/office/treeData?type=3" cssClass="required" allowClear="true" isAll="true" notAllowSelectParent="true" dataMsgRequired="必填信息"/>
	--%>		<input  class="btn btn-primary" type="button" value="提交送审" onclick="submitByIds()"/>
		</ul>
	</form:form>
</body>
</html>