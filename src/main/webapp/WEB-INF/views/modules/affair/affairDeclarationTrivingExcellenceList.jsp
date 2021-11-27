<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>创先争优申报管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnPrint").click(function(){
				$('#handleTh').css('display', 'none');
				$('.handleTd').css('display', 'none');
				$('#checkTh').css('display', 'none');
				$('.checkTd').css('display', 'none');
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
						$('#checkTh').css('display', 'table-cell');
						$('.checkTd').css('display', 'table-cell');
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

		function openAddDialog() {
			top.$.jBox.open("iframe:${ctx}/affair/affairDeclarationTrivingExcellence/form", "创先争优申报",800,600,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairDeclarationTrivingExcellence"}
			});
		}

		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairDeclarationTrivingExcellence/formDetail?id="+id;
			top.$.jBox.open(url, "创先争优申报详情",1000,600,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		};


		//编辑弹窗
		function openEditDialog(id) {
			var url = "iframe:${ctx}/affair/affairDeclarationTrivingExcellence/form?id="+id;
			top.$.jBox.open(url, "创先争优申报修改",1000,600,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairDeclarationTrivingExcellence/"}
			});
		};

		function submitByIds() {
			if(null == $("#checkId").val() || "" ==  $("#checkId").val()){
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
		function openExamineDialog(id) {
			top.$.jBox.open("iframe:${ctx}/affair/affairDeclarationTrivingExcellence/examineView?id="+id,"工作申报审核",800,420,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},
				closed:function (){
					self.location.href="${ctx}/affair/affairDeclarationTrivingExcellence/list/?repage";
				}
			});
		};

	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/affair/affairDeclarationTrivingExcellence/">创先争优申报</a></li>
		<li><a href="${ctx}/affair/affairDifficultHelp/">困难帮扶申报</a></li>
		<li><a href="${ctx}/affair/affairDeclarationOther/">其他申报</a></li>
	</ul>
	<ul class="nav nav-tabs">
		<c:choose>
			<c:when test="${type == 1}">
				<li class="active"><a href="${ctx}/affair/affairDeclarationQiyi/?type=1">先进党组织</a></li>
				<li><a href="${ctx}/affair/affairDeclarationQiyi/?type=2">优秀党员</a></li>
				<li><a href="${ctx}/affair/affairDeclarationQiyi/?type=3">党务工作者</a></li>
				<li><a href="${ctx}/affair/affairDeclarationQiyi/?type=4">党内品牌</a></li>
			</c:when>
			<c:when test="${type == 2}">
				<li><a href="${ctx}/affair/affairDeclarationQiyi/?type=1">先进党组织</a></li>
				<li class="active"><a href="${ctx}/affair/affairDeclarationQiyi/?type=2">优秀党员</a></li>
				<li><a href="${ctx}/affair/affairDeclarationQiyi/?type=3">党务工作者</a></li>
				<li><a href="${ctx}/affair/affairDeclarationQiyi/?type=4">党内品牌</a></li>
			</c:when>
			<c:when test="${type == 3}">
				<li><a href="${ctx}/affair/affairDeclarationQiyi/?type=1">先进党组织</a></li>
				<li><a href="${ctx}/affair/affairDeclarationQiyi/?type=2">优秀党员</a></li>
				<li class="active"><a href="${ctx}/affair/affairDeclarationQiyi/?type=3">党务工作者</a></li>
				<li><a href="${ctx}/affair/affairDeclarationQiyi/?type=4">党内品牌</a></li>
			</c:when>
			<c:when test="${type == 4}">
				<li><a href="${ctx}/affair/affairDeclarationQiyi/?type=1">先进党组织</a></li>
				<li><a href="${ctx}/affair/affairDeclarationQiyi/?type=2">优秀党员</a></li>
				<li><a href="${ctx}/affair/affairDeclarationQiyi/?type=3">党务工作者</a></li>
				<li class="active"><a href="${ctx}/affair/affairDeclarationQiyi/?type=4">党内品牌</a></li>
			</c:when>
			<c:otherwise>
			</c:otherwise>
		</c:choose>
	</ul>
	<form:form id="searchForm" modelAttribute="affairDeclarationTrivingExcellence" action="${ctx}/affair/affairDeclarationTrivingExcellence/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>申报标题：</label>
				<form:input path="title" htmlEscape="false" class="input-medium"/>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairDeclarationTrivingExcellence:edit">
			<li class="btns"><input id="btnAdd" class="btn btn-primary" type="button" value="添加"
									onclick="openAddDialog()"/></li>
			<li class="btns"><input id="btnDel" class="btn btn-primary" type="button" value="删除"
									onclick="deleteByIds('${ctx}/affair/affairDeclarationTrivingExcellence/deleteByIds','checkAll','myCheckBox')"/></li>
			</shiro:hasPermission>
<%--			<li class="btns"><input id="btnExamine" class="btn btn-primary" type="button" value="审核"/></li>--%>
			<li class="btns"><input id="btnPrint" class="btn btn-primary" type="button" value="打印"/></li>
			<li class="btns"><input id="btnReset" class="btn btn-primary" type="button" value="重置"
									onclick="window.location.href='${ctx}/affair/affairDeclarationTrivingExcellence/'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th id="checkTh">
					<input style='margin-left:12px' type='checkbox'id='checkAll'onclick='chooseAll(this,"myCheckBox")'/>
				</th>
				<th>序号</th>
				<th>申报标题</th>
				<th>上传时间</th>
				<th>附件</th>
				<th>审核状态</th>
				<shiro:hasPermission name="affair:affairDeclarationTrivingExcellence:edit"><th id="handleTh">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairDeclarationTrivingExcellence" varStatus="status">
			<tr>
				<td class="checkTd">
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairDeclarationTrivingExcellence.id}"/>
				</td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
					${affairDeclarationTrivingExcellence.title}
				</a></td>
				<td>
					<fmt:formatDate value="${affairDeclarationTrivingExcellence.time}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${affairDeclarationTrivingExcellence.filePath}
				</td>
				<td>
					${fns:getDictLabel(affairDeclarationTrivingExcellence.checkType, 'declare_status', '')}
				</td>
				<td class="handleTd">
    				<a href="javascript:;" onclick="openDetailDialog('${affairDeclarationTrivingExcellence.id}')">查看</a>
				<shiro:hasPermission name="affair:affairDeclarationTrivingExcellence:edit">
					<c:if test="${affairDeclarationTrivingExcellence.createBy.id == fns:getUser().id&&('1'.equals(affairDeclarationTrivingExcellence.checkType))}">
						<a href="javascript:;" onclick="openEditDialog('${affairDeclarationTrivingExcellence.id}')">修改</a>
					</c:if>
						<a href="${ctx}/affair/affairDeclarationTrivingExcellence/delete?id=${affairDeclarationTrivingExcellence.id}" onclick="return confirmx('确认要删除该困难帮扶申报吗？', this.href)">删除</a>
				</shiro:hasPermission>
					<shiro:hasPermission name="affair:affairDeclarationTrivingExcellence:manage">
						<c:if test="${affairDeclarationTrivingExcellence.checkId == fns:getUser().id}">
							<a href="javascript:;" onclick="openExamineDialog('${affairDeclarationTrivingExcellence.id}')">审核</a>
						</c:if>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	<shiro:hasPermission name="affair:affairDeclarationTrivingExcellence:edit">
		<form:form id="searchForm2" modelAttribute="affairDeclarationTrivingExcellence" action="${ctx}/affair/affairDeclarationTrivingExcellence/submitByIds" method="post" class="breadcrumb form-search">
			<ul class="ul-form" style="text-align: right">
				<font color="red">请选择审核单位：</font>
				<input type="hidden" name="ids" id="idsValue"/>
				<form:select id="checkId" path="checkId"  class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('job_declaration')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<input  class="btn btn-primary" type="button" value="提交送审" onclick="submitByIds()"/>
			</ul>
		</form:form>
	</shiro:hasPermission>
</body>
</html>