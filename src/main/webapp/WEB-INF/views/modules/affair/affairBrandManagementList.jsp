<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>品牌创建管理</title>
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
					afterPrint:function(){
						$('#handleTh').css('display', 'table-cell');
						$('.handleTd').css('display', 'table-cell');
					}
				});
			});
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairBrandManagement/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairBrandManagement/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairBrandManagement/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairBrandManagement/");
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
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairBrandManagement", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairBrandManagement"}});
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		function openForm(url) {
			top.$.jBox.open("iframe:"+url, "品牌创建",800,520,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairBrandManagement"}
			});
		}
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairBrandManagement/formDetail?id="+id;
			top.$.jBox.open(url, "品牌创建详情",800,500,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		};
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
			top.$.jBox.open("iframe:${ctx}/affair/affairBrandManagement/shenHeDialog?id="+id,"青年品牌",800,420,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},
				closed:function (){
					self.location.href="${ctx}/affair/affairBrandManagement/list/?repage";
				}
			});
		};
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/affair/affairBrandManagement/">青年品牌</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="affairBrandManagement" action="${ctx}/affair/affairBrandManagement/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="品牌创建.xlsx"/>
		<ul class="ul-form">
			<li><label>品牌类型：</label>
				<form:select path="brandType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_pinpai')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>所在团组织：</label>
				<sys:treeselect id="unit" name="unitId" value="${affairBrandManagement.unitId}" labelName="unit"
								labelValue="${affairBrandManagement.unit}"
								title="所在团组织" url="/affair/affairTwBase/treeData" cssClass="input-small" allowClear="true"
								notAllowSelectParent="false"/>
			</li>
			<li><label>人员组成：</label>
				<form:input path="personnel" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>创建时间：</label>
				<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairBrandManagement.startDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				至
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairBrandManagement.endDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairBrandManagement:edit">
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/affair/affairBrandManagement/form?id=${affairBrandManagement.id}')"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairBrandManagement/deleteByIds','checkAll','myCheckBox')"/></li>
				<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
			</shiro:hasPermission>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairBrandManagement'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input style='margin-left:12px' type='checkbox' id='checkAll' onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>创建时间</th>
				<th>品牌类型</th>
				<th>所在团组织</th>
				<th>人员组成</th>
				<th>基本情况</th>
				<th>状态</th>
				<th id="handleTh">操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairBrandManagement" varStatus="status">
			<tr>
				<td>
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairBrandManagement.id}"/>
				</td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
					<fmt:formatDate value="${affairBrandManagement.createTime}" pattern="yyyy-MM-dd"/>
				</td>
				<td>

							${fns:getDictLabel(affairBrandManagement.brandType, 'affair_pinpai', '')}

				</td>
				<td>
						${affairBrandManagement.unit}
				</td>
				<td>
					${affairBrandManagement.personnel}
				</td>
				<td>
					${affairBrandManagement.basicSituation}
				</td>
				<td>
						${fns:getDictLabel(affairBrandManagement.checkType, 'education_comment', '')}
				</td>
				<td class="handleTd">
					<a href="javascript:void(0)" onclick="openDetailDialog('${affairBrandManagement.id}')">查看</a>
					<shiro:hasPermission name="affair:affairBrandManagement:edit">
						<%--<c:if test="${affairBrandManagement.createBy.id == fns:getUser().id}">--%>
						<a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairBrandManagement/form?id=${affairBrandManagement.id}')">修改</a>
						<a href="${ctx}/affair/affairBrandManagement/delete?id=${affairBrandManagement.id}" onclick="return confirmx('确认要删除该品牌创建吗？', this.href)">删除</a>
						<%--</c:if>--%>
					</shiro:hasPermission>
					<shiro:hasPermission name="affair:affairBrandManagement:manage">
						<a href="javascript:void(0)" onclick="openDialog('${affairBrandManagement.id}')">审核</a>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	<form:form id="searchForm2" modelAttribute="affairBrandManagement" action="${ctx}/affair/affairBrandManagement/submitByIds" method="post" class="breadcrumb form-search">
		<ul class="ul-form" style="text-align: right">
			<font color="red">请选择评定单位：</font>
			<input type="hidden" name="ids" id="idsValue"/>
			<form:select id="oneCheckId" path="oneCheckId"  class="input-xlarge required">
				<c:choose>
					<c:when test="${(fns:getUser().office.id eq '34' || fns:getUser().company.id eq '34') && fns:getUser().office.id ne '33'}">
						<form:option value="28f59642a1e74d0588f0d515fe462775" label="南宁处团委"/>
						<%--<form:option value="ff7f9fe2597b40429ded58f8b76a2f65" label="南宁局团委"/> 11月9--%>
					</c:when>
					<c:when test="${(fns:getUser().office.id eq '156' || fns:getUser().company.id eq '156') && fns:getUser().office.id ne '268'}">
						<form:option value="78d0e07ed2e14ca0b6c73e14c11f4d55" label="北海处团委"/>
						<%--<form:option value="ff7f9fe2597b40429ded58f8b76a2f65" label="南宁局团委"/> 11月9--%>
					</c:when>
					<c:when test="${(fns:getUser().office.id eq '95' || fns:getUser().company.id eq '95') && fns:getUser().office.id ne '148'}">
						<form:option value="11d94fe57ede47a9bae4bffa36af487c" label="柳州处团委"/>
						<%--<form:option value="ff7f9fe2597b40429ded58f8b76a2f65" label="南宁局团委"/> 11月9--%>
					</c:when>
					<c:when test="${(fns:getUser().office.id eq '1' || fns:getUser().company.id eq '1' || fns:getUser().office.id eq '148'|| fns:getUser().office.id eq '268'|| fns:getUser().office.id eq '33') && fns:getUser().office.id ne '95' && fns:getUser().office.id ne '156' && fns:getUser().office.id ne '34'}">
						<form:option value="ff7f9fe2597b40429ded58f8b76a2f65" label="南宁局团委"/>
					</c:when>
					<c:otherwise>
						<form:options items="${fns:getDictList('user_tuanwei')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</c:otherwise>
				</c:choose>
			</form:select>
			<input  class="btn btn-primary" type="button" value="提交送审" onclick="submitByIds()"/>
		</ul>
	</form:form>
</body>
</html>