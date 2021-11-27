<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>党章党规及党建知识管理</title>
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
        };
		//批量发布
		function publishByIds() {
			var ids = [];
			$("input:checkbox[name=myCheckBox]:checked").each(function () {
				console.log($(this).val())
				ids.push($(this).val());
			});
			if (ids.length > 0) {
				$.ajax({
					type:"post",
					url:"${ctx}/affair/affairKnowledge/publishByIds",
					data:{ids:ids},
					dataType:"json",
					success:function(data){
						$.jBox.tip(data.message);
						resetCheckBox("checkAll","myCheckBox");
						location.reload();
					}
				})
			} else {
				$.jBox.tip('请先选择要发布的内容');
			}
		};
		//批量取消发布
		function cancelByIds() {
			var ids = [];
			$("input:checkbox[name=myCheckBox]:checked").each(function () {
				console.log($(this).val())
				ids.push($(this).val());
			});
			if (ids.length > 0) {
				$.ajax({
					type:"post",
					url:"${ctx}/affair/affairKnowledge/cancelByIds",
					data:{ids:ids},
					dataType:"json",
					success:function(data){
						$.jBox.tip(data.message);
						resetCheckBox("checkAll","myCheckBox");
						location.reload();
					}
				})
			} else {
				$.jBox.tip('请先选择要取消发布的内容');
			}
		};
		//添加修改弹窗
		function openAddEditDialog(id) {
			var url = "iframe:${ctx}/affair/affairKnowledge/form?id="+id;
			if (id == null || id == undefined){
				url = "iframe:${ctx}/affair/affairKnowledge/form";
			}
			top.$.jBox.open(url, "党章党规及党建知识",1000,700,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){
					self.location.href="${ctx}/affair/affairKnowledge/manageList";
				}
			});
		};
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairKnowledge/formDetail?id="+id;
			top.$.jBox.open(url, "党章党规及党建知识",1000,700,{
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
		<li><a href="${ctx}/affair/affairKnowledge/">查询</a></li>
		<%--<shiro:hasPermission name="affair:affairKnowledge:edit"><li><a href="${ctx}/affair/affairKnowledge/form">党章党规及党建知识添加</a></li></shiro:hasPermission>--%>
		<shiro:hasPermission name="affair:affairKnowledge:manage"><li class="active"><a href="${ctx}/affair/affairKnowledge/manageList">管理</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="affairKnowledge" action="${ctx}/affair/affairKnowledge/manageList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>标题：</label>
				<form:input path="title" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>类型：</label>
				<form:select path="type" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_knowledge_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>发布日期：</label>
				<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairKnowledge.startDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				 --
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairKnowledge.endDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>状态：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_publish_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairKnowledge:manage">
				<li class="btns"><input id="btn" class="btn btn-primary" type="button" onclick="openAddEditDialog()" value="添加"/></li>
				<li class="btns"><input id="sign" class="btn btn-primary" type="button" value="发布" onclick="publishByIds()"/></li>
				<li class="btns"><input id="cancel" class="btn btn-primary" type="button" value="取消发布" onclick="cancelByIds()"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairKnowledge/deleteByIds','checkAll','myCheckBox')"/></li>
			</shiro:hasPermission>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印" /></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairKnowledge/manageList'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>
                    <input style='margin-left:12px' type='checkbox'id='checkAll'onclick='chooseAll(this,"myCheckBox")'/>
				</th>
				<th>序号</th>
				<th>标题</th>
				<th>类型</th>
				<th>发布部门</th>
				<th>发布人</th>
				<th>发布日期</th>
				<th>状态</th>
				<shiro:hasPermission name="affair:affairKnowledge:manage"><th id="handleTh">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairKnowledge" varStatus="status">
			<tr>
				<td>
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairKnowledge.id}"/>
				</td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>

						${affairKnowledge.title}

				</td>
				<td>
					${fns:getDictLabel(affairKnowledge.type, 'affair_knowledge_type', '')}
				</td>
				<td>
					${affairKnowledge.publishDep}
				</td>
				<td>
					${affairKnowledge.publisher}
				</td>
				<td>
					<%--<fmt:formatDate value="${affairKnowledge.publishDate}" pattern="yyyy-MM-dd"/>--%>
					<fmt:formatDate value="${affairKnowledge.updateDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${fns:getDictLabel(affairKnowledge.status, 'affair_publish_status', '')}
				</td>
				<shiro:hasPermission name="affair:affairKnowledge:manage"><td class="handleTd">
					<a href="javascript:void(0)" onclick="openDetailDialog('${affairKnowledge.id}')">查看</a>
                    <c:if test="${affairKnowledge.createBy.id == fns:getUser().id}">
                        <%-- 一旦发布不可再修改--%>
                        <c:if test="${affairKnowledge.status != '2'}">
                            <a href="javascript:void(0)" onclick="openAddEditDialog('${affairKnowledge.id}')">修改</a>
                        </c:if>
                        <a href="${ctx}/affair/affairKnowledge/delete?id=${affairKnowledge.id}"
                           onclick="return confirmx('确认要删除该党章党规及党建知识吗？', this.href)">删除</a>
                    </c:if>
                </td>
                </shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>