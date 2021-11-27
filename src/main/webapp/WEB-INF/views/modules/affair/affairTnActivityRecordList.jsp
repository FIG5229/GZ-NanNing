<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>团内活动记录管理</title>
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
								$("#searchForm").attr("action","${ctx}/affair/affairTnActivityRecord/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairTnActivityRecord/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairTnActivityRecord/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairTnActivityRecord/");
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
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairTnActivityRecord", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairTnActivityRecord"}});
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
			top.$.jBox.open("iframe:"+url, "活动记录",1200,600,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				 },closed:function (){self.location.href="${ctx}/affair/affairTnActivityRecord"}
			});
		}
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairTnActivityRecord/formDetail?id="+id;
			top.$.jBox.open(url, "\n" +
					"活动记录",800,500,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		};
		//详情弹窗
		function openPropellingDialog(id) {
			var url = "iframe:${ctx}/affair/affairTnActivityRecord/propelling?id="+id;
			top.$.jBox.open(url, "\n" + "数据推送",500,300,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}, closed:function (){
					self.location.href="${ctx}/affair/affairTnActivityRecord/list/?repage";
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
			top.$.jBox.open("iframe:${ctx}/affair/affairTnActivityRecord/shenHeDialog?id="+id,"活动记录审核",800,420,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},
				closed:function (){
					self.location.href="${ctx}/affair/affairTnActivityRecord/list/?repage";
				}
			});
		};
		$('#notPass').popover();
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/affair/affairTnActivityRecord/">活动记录</a></li>
<%--		<shiro:hasPermission name="affair:affairTnActivityRecord:edit"><li><a href="${ctx}/affair/affairTnActivityRecord/form">团内活动记录添加</a></li></shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="affairTnActivityRecord" action="${ctx}/affair/affairTnActivityRecord/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="团内活动记录表.xlsx"/>
		<ul class="ul-form">
			<li><label>标题：</label>
				<form:input path="title" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>团组织：</label>
				<sys:treeselect id="partyBranch" name="partyBranchId" value="${affairTnActivityRecord.partyBranchId}" labelName="partyBranch" labelValue="${affairTnActivityRecord.partyBranch}"
					title="团组织" url="/affair/affairTwBase/treeData" cssClass="input-small" allowClear="true" notAllowSelectParent="false"/>			</li>
			<li><label>时间：</label>
				<input name="beginDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairTnActivityRecord.beginDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> -
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairTnActivityRecord.endDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairTnActivityRecord:edit">
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/affair/affairTnActivityRecord/form?id=${affairTnActivityRecord.id}')"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairTnActivityRecord/deleteByIds','checkAll','myCheckBox')"/></li>
				<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
<%--				<li class="btns"><input class="btn btn-primary" type="button" value="推送主页"/></li>--%>
			</shiro:hasPermission>
			<shiro:hasPermission name="affair:affairTnActivityRecord:edit">
<%--				<li class="btns"><input class="btn btn-primary" type="button" value="取消推送"/></li>--%>
			</shiro:hasPermission>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairTnActivityRecord'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input style='margin-left:12px' type='checkbox' id='checkAll' onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>标题</th>
				<th>团组织</th>
				<th>时间</th>
				<th>审核状态</th>
				<th>上报时间</th>
				<th>处审核时间</th>
				<th>局审核时间</th>
				<th id="handleTh">操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairTnActivityRecord" varStatus="status">
			<tr>
				<td>
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairTnActivityRecord.id}"/>
				</td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
					${affairTnActivityRecord.title}
				</td>
				<td>
					${affairTnActivityRecord.partyBranch}
				</td>
				<td>
					<fmt:formatDate value="${affairTnActivityRecord.date}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<c:choose>
						<c:when test="${affairTnActivityRecord.checkType == '0'}">
							<a id="notPass" data-container="body" data-toggle="popover" data-placement="right"
							   data-content="${affairTnActivityRecord.shOpinion}"  style="cursor: pointer;color: red">退回整改</a>
						</c:when>
						<c:otherwise>
							${fns:getDictLabel(affairTnActivityRecord.checkType, 'check_type', '')}
						</c:otherwise>
					</c:choose>
				</td>
				<td><fmt:formatDate value="${affairTnActivityRecord.jcSbTime}" pattern="yyyy-MM-dd HH:mm"/></td>
				<td><fmt:formatDate value="${affairTnActivityRecord.chuShTime}" pattern="yyyy-MM-dd HH:mm"/></td>
				<td><fmt:formatDate value="${affairTnActivityRecord.juShTime}" pattern="yyyy-MM-dd HH:mm"/></td>
				<td class="handleTd">
					<a href="javascript:void(0)" onclick="openDetailDialog('${affairTnActivityRecord.id}')">查看</a>
						<%-- 33 南宁处团委  268 北海处团委  148 柳州处团委  9  公安局团委--%>
					<c:if test="${fns:getUser().office.id eq '9' || fns:getUser().office.id eq '33' || fns:getUser().office.id eq '268' || fns:getUser().office.id eq '148' || fns:getUser().id eq '1'}">
						<a href="javascript:void(0)" onclick="openPropellingDialog('${affairTnActivityRecord.id}')">推送</a>
					</c:if>
					<shiro:hasPermission name="affair:affairTnActivityRecord:edit">
						<c:if test="${affairTnActivityRecord.createBy.id == fns:getUser().id&&('1'.equals(affairTnActivityRecord.checkType)||'0'.equals(affairTnActivityRecord.checkType))}">
							<a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairTnActivityRecord/form?id=${affairTnActivityRecord.id}')">修改</a>
						</c:if>
						<shiro:hasPermission name="affair:affairTnActivityRecord:manage">
							<a href="${ctx}/affair/affairTnActivityRecord/delete?id=${affairTnActivityRecord.id}" onclick="return confirmx('确认要删除该活动记录吗？', this.href)">删除</a>
							<a href="javascript:void(0)" onclick="openDialog('${affairTnActivityRecord.id}')">审核</a>
						</shiro:hasPermission>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	<form:form id="searchForm2" modelAttribute="affairTnActivityRecord" action="${ctx}/affair/affairTnActivityRecord/submitByIds" method="post" class="breadcrumb form-search">
		<ul class="ul-form" style="text-align: right">
			<font color="red">请选择审核单位：</font>
			<input type="hidden" name="ids" id="idsValue"/>
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
			<input  class="btn btn-primary" type="button" value="提交送审" onclick="submitByIds()"/>
		</ul>
	</form:form>
</body>
</html>