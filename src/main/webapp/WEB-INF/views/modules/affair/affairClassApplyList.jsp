<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>班级报名</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript" src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {

		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
			return false;
		}
		//打开报名窗口
		function openApplyDialog(id) {
			top.$.jBox.open("iframe:${ctx}/affair/affairClassApply/form?classId="+id, "班级报名",1200,600,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairClassApply"}
			});
		}

		/*function openDetailDialog(id) {
			top.$.jBox.open("iframe:${ctx}/affair/affairClassManage/formDetail?id="+id,"培训班详情",1200,600,{
				buttons:{"关闭":true},
				loaded:function () {
					$(".jbox-content",top.document).css("overflow-y","hidden");
				},closed:function () {self.location.href="${ctx}/affair/affairClassApply"
				}
			});
		}*/

		if("success"=="${saveResult}"){
			parent.$.jBox.close();
		}
	</script>
</head>
<body>
<%--<ul class="nav nav-tabs">
	<li class="active"><a href="${ctx}/affair/affairClassApply/">班级报名</a></li>
</ul>--%>
<form:form id="searchForm" modelAttribute="affairClassManage" action="${ctx}/affair/affairClassApply/" method="post" class="breadcrumb form-search">
	<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
	<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	<%--<input id="fileName" name="fileName" type="hidden" value="培训班详情表.xlsx"/>--%>
	<ul class="ul-form">
		<li><label>培训名称：</label>
			<form:input path="name" htmlEscape="false" class="input-medium"/>
		</li>
		<li>
			<label>培训年度：</label>
			<input name="year" path="year" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
				   value="${affairClassManage.year}"
				   onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false});"/>
		</li>
		<li><label>单位：</label>
			<sys:treeselect id="unit" name="unitId" value="${affairClassManage.unitId}" labelName="unit" labelValue="${affairClassManage.unit}"
							title="单位" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="false"/>
		</li>
		<li><label>培训日期：</label>
			<input name="beganDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
				   value="<fmt:formatDate value="${affairClassManage.beganDate}" pattern="yyyy-MM-dd"/>"
				   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			至
			<input name="resultDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
				   value="<fmt:formatDate value="${affairClassManage.resultDate}" pattern="yyyy-MM-dd"/>"
				   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
		</li>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairClassApply'"/></li>
			<li class="clearfix"></li>
		</ul>
	</ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
	<thead>
	<tr>
		<%--<th id="checkTh"><input style='margin-left:12px' type='checkbox' id='checkAll' onclick='chooseAll(this,"myCheckBox")'/>全选</th>--%>
		<th>序号</th>
		<th>培训名称</th>
		<th>单位</th>
		<th>培训日期</th>
		<th>标题</th>
		<th>培训班类型</th>
		<th>状态</th>
		<th>创建人</th>
		<th>创建时间</th>
		<th>创建人单位</th>
		<th>主办部门</th>
		<th>操作</th>
	</tr>
	</thead>
	<tbody>
	<c:forEach items="${page.list}" var="affairClassManage" varStatus="status">
		<tr>
			<%--<td class="checkTd">
				<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairClassManage.id}"/>
			</td>--%>
			<td>
					${(page.pageNo-1)*page.pageSize+status.index+1}
			</td>
			<td>
					${affairClassManage.name}
			</td>
			<td>
					${affairClassManage.unit}
			</td>
			<td>
				<fmt:formatDate value="${affairClassManage.beganDate}" pattern="yyyy-MM-dd"/> ~ <fmt:formatDate value="${affairClassManage.resultDate}" pattern="yyyy-MM-dd"/>
			</td>
			<td>
					${affairClassManage.title}
			</td>
			<td>
					${fns:getDictLabel(affairClassManage.type, 'affair_train_type', '')}
			</td>
			<td>

				<c:choose>
					<c:when test="${'1' == affairClassManage.openStatus}">
					 	已开班
					</c:when>
					<c:otherwise>
						未开班
					</c:otherwise>
				</c:choose>
				<c:choose>
					<c:when test="${'1' == affairClassManage.classStatus}">
						已建班
					</c:when>
					<c:otherwise>
						未建班
					</c:otherwise>
				</c:choose>
				<c:choose>
					<c:when test="${'1' == affairClassManage.pospStatus}">
						已结项
					</c:when>
					<c:otherwise>
						 未结项
					</c:otherwise>
				</c:choose>
				<c:choose>
					<c:when test="${'1' == affairClassManage.status}">
						已实施
					</c:when>
					<c:otherwise>
						未实施
					</c:otherwise>
				</c:choose>
			</td>
			<td>
					${affairClassManage.creator}
			</td>
			<td>
				<fmt:formatDate value="${affairClassManage.createTime}" pattern="yyyy-MM-dd"/>
			</td>
			<td>
					${affairClassManage.creatorUnit}
			</td>
			<td>
					${affairClassManage.sponsorUnit}
			</td>
			<%--<td>
				<fmt:formatDate value="${affairClassManage.updateDate}" pattern="yyyy-MM-dd"/>
			</td>--%>
			<td class="handleTd">
				<%--<a href="javascript:;" onclick="openDetailDialog('${affairClassManage.id}')">查看</a>--%>
				<a href="javascript:;" onclick="openApplyDialog('${affairClassManage.id}')">报名</a>
			</td>
		</tr>
	</c:forEach>
	</tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>