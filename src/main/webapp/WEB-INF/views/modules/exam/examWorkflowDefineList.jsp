<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>考评流程定义管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		//流程环节弹出框
		function openSegmentDialog(id) {
			var url = "iframe:${ctx}/exam/examWorkflowDefine/segmentDialog?id="+id;
			top.$.jBox.open(url, "任务分配",1000,500,{
				buttons:{"关闭":true},
				persistent: true,  //设置点击窗口外不关闭的参数
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){
				}
			});
		};
        //添加修改弹窗
        function openAddEditDialog(id) {
            var url = "iframe:${ctx}/exam/examWorkflowDefine/form?id="+id;
            if (id == null || id == undefined){
                url = "iframe:${ctx}/exam/examWorkflowDefine/form";
            }
            top.$.jBox.open(url, "考评流程",1500,700,{
                buttons:{"关闭":true},
                loaded:function(h){
                    $(".jbox-content", top.document).css("overflow-y","hidden");
                },closed:function (){
					$("#searchForm").submit();
                   /*self.location.href="${ctx}/exam/examWorkflowDefine/";*/
                }
            });
        };
        //详情弹窗
        function openDetailDialog(id) {
            var url = "iframe:${ctx}/exam/examWorkflowDefine/formDetail?id="+id;
            top.$.jBox.open(url, "考评流程",1000,700,{
                buttons:{"关闭":true},
                loaded:function(h){
                    $(".jbox-content", top.document).css("overflow-y","hidden");
                }
            });
        };
	</script>
</head>
<body>
	<%--<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/exam/examWorkflowDefine/">考评流程管理</a></li>
		&lt;%&ndash;<shiro:hasPermission name="exam:examWorkflowDefine:edit"><li><a href="${ctx}/exam/examWorkflowDefine/form">考评流程定义添加</a></li></shiro:hasPermission>&ndash;%&gt;
	</ul>--%>
	<form:form id="searchForm" modelAttribute="examWorkflowDefine" action="${ctx}/exam/examWorkflowDefine/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>创建时间：</label>
				<input name="createStartDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${examWorkflowDefine.createStartDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				 --
				<input name="createEndDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${examWorkflowDefine.createEndDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>流程名称：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>考评类别：</label>
				<form:select path="examType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('kp_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label class="width120">被考评对象类别：</label>
				<form:select path="examObjectType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('exam_object_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>是否启用：</label>
				<form:select path="isUse" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label class="control-label">创建人：</label>
				<sys:treeselect id="create" name="createBy.id" value="${examWorkflowDefine.createBy.id}" labelName="createBy.name" labelValue="${examWorkflowDefine.createBy.name}"
								title="创建人" url="/sys/office/treeData?type=3"  allowClear="true" notAllowSelectParent="true" isAll="false"/>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="exam:examWorkflowDefine:edit">
				<li class="btns"><input id="btn" class="btn btn-primary" type="button" onclick="openAddEditDialog()" value="添加"/></li>
<%--				<li class="btns"><input id="btnQUery" class="btn btn-primary" type="button" onclick="window.location.href='${ctx}/exam/examWorkflowDefine/queryPoliceDatas'" value="查询民警考核对象"/></li>--%>
				<li><li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/exam/examWorkflowDefine/deleteByIds','checkAll','myCheckBox')"/></li>
			</shiro:hasPermission>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/exam/examWorkflowDefine/'"/></li>
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
				<th>流程名称</th>
				<th>考评类别</th>
				<th>考评周期</th>
				<th>被考评对象类别</th>
				<th>是否启用</th>
				<th>创建时间</th>
				<shiro:hasPermission name="exam:examWorkflowDefine:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="examWorkflowDefine" varStatus="status">
			<tr>
				<td>
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${examWorkflowDefine.id}"/>
				</td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
					<%--<a href="${ctx}/exam/examWorkflowDefine/form?id=${examWorkflowDefine.id}">
					${examWorkflowDefine.name}
					</a>--%>
					${examWorkflowDefine.name}
				</td>
				<td>
					${fns:getDictLabel(examWorkflowDefine.examType, 'kp_type', '')}
				</td>
				<td>
					${fns:getDictLabel(examWorkflowDefine.examCycle, 'exam_cycle', '')}
				</td>
				<td>
					${fns:getDictLabel(examWorkflowDefine.examObjectType, 'exam_object_type', '')}
				</td>
				<td>
					${fns:getDictLabel(examWorkflowDefine.isUse, 'yes_no', '')}
				</td>
				<td>
					<fmt:formatDate value="${examWorkflowDefine.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="exam:examWorkflowDefine:edit"><td>
					<%--临时数据配置调整--%>
                    <c:if test="${examWorkflowDefine.createBy.id == fns:getUser().id}">
                        <a href="#" onclick="openAddEditDialog('${examWorkflowDefine.id}')">修改</a>
                        <a href="#" onclick="openSegmentDialog('${examWorkflowDefine.id}')">任务分配</a>
                        <c:choose>
                            <c:when test="${'1' == examWorkflowDefine.isUse}">
                                <a href="${ctx}/exam/examWorkflowDefine/diasble?id=${examWorkflowDefine.id}"
                                   onclick="return confirmx('确认要禁用该考评流程吗？', this.href)">禁用</a>
                            </c:when>
                            <c:otherwise>
                                <a href="${ctx}/exam/examWorkflowDefine/usable?id=${examWorkflowDefine.id}"
                                   onclick="return confirmx('确认要启用该考评流程吗？', this.href)">启用</a>
                            </c:otherwise>
                        </c:choose>
                        <a href="${ctx}/exam/examWorkflowDefine/delete?id=${examWorkflowDefine.id}"
                           onclick="return confirmx('确认要删除该考评流程吗？', this.href)">删除</a>
					</c:if>

<%--						<a href="#" onclick="openAddEditDialog('${examWorkflowDefine.id}')">修改</a>--%>
<%--						<a href="#" onclick="openSegmentDialog('${examWorkflowDefine.id}')">任务分配</a>--%>
<%--						<c:choose>--%>
<%--							<c:when test="${'1' == examWorkflowDefine.isUse}">--%>
<%--								<a href="${ctx}/exam/examWorkflowDefine/diasble?id=${examWorkflowDefine.id}"--%>
<%--								   onclick="return confirmx('确认要禁用该考评流程吗？', this.href)">禁用</a>--%>
<%--							</c:when>--%>
<%--							<c:otherwise>--%>
<%--								<a href="${ctx}/exam/examWorkflowDefine/usable?id=${examWorkflowDefine.id}"--%>
<%--								   onclick="return confirmx('确认要启用该考评流程吗？', this.href)">启用</a>--%>
<%--							</c:otherwise>--%>
<%--						</c:choose>--%>
<%--						<a href="${ctx}/exam/examWorkflowDefine/delete?id=${examWorkflowDefine.id}"--%>
<%--						   onclick="return confirmx('确认要删除该考评流程吗？', this.href)">删除</a>--%>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>