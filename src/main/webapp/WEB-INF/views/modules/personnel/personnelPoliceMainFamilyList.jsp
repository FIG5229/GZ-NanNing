<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>民警家庭管理</title>
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

		//添加修改弹窗
		function openAddEditDialog(id) {
			var url = "iframe:${ctx}/personnel/personnelPoliceMainFamily/updateInfo?id="+id;
			if (id == null || id == undefined){
				url = "iframe:${ctx}/personnel/personnelPoliceMainFamily/form?isAdd=add";
			}
			top.$.jBox.open(url, "民警家庭数据",1000,600,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){
					self.location.href="${ctx}/personnel/personnelPoliceMainFamily/list";
				}
			});
		}
		function openDialog(id) {
			top.$.jBox.open("iframe:${ctx}/personnel/personnelPoliceMainFamily/shenHeDialog?id="+id,"民警家庭",800,420,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},
				closed:function (){
					self.location.href="${ctx}/personnel/personnelPoliceMainFamily/list/?repage";
				}
			});
        }
        function submitByIds() {
			if(null == $("#unitCheckId").val() || "" ==  $("#unitCheckId").val()){
				$.jBox.tip('请选择审核单位');
				return false;
			}
			var ids = [];
			$("input:checkbox[name=myCheckBox]:checked").each(function () {
				console.log($(this).val());
				ids.push($(this).val());
			});
			if (ids.length > 0) {
				var idsStr = ids.join(",");
				$("#idsValue").val(idsStr);
				$("#searchForm2").submit();
			} else {
				$.jBox.tip('请先选择要提交的内容');
			}
        }

		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/personnel/personnelPoliceMainFamily/formDetail?id="+id;
			top.$.jBox.open(url, "民警家庭",1000,600,{
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
		<li class="active"><a href="${ctx}/personnel/personnelPoliceMainFamily/">民警家庭</a></li>
<%--		<shiro:hasPermission name="personnel:personnelPoliceMainFamily:edit"><li><a href="${ctx}/personnel/personnelPoliceMainFamily/form">民警家庭添加</a></li></shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="personnelPoliceMainFamily" action="${ctx}/personnel/personnelPoliceMainFamily/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
<%--			<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/personnel/personnelPoliceMainFamily/form')"/></li>--%>
			<shiro:hasPermission name="personnel:personnelPoliceMainFamily:edit">
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openAddEditDialog()"/></li>
			</shiro:hasPermission>
			<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除"
									onclick="deleteByIds('${ctx}/personnel/personnelPoliceMainFamily/deleteByIds','checkAll','myCheckBox')"/></li>
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
				<th>单位</th>
				<th>身份证号</th>
				<th>警号</th>
				<th>审核状态</th>
				<shiro:hasPermission name="personnel:personnelPoliceMainFamily:view"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="personnelPoliceMainFamily" varStatus="status">
			<tr>
				<td><input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${personnelPoliceMainFamily.id}"/></td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
					${personnelPoliceMainFamily.name}
				</td>
				<td>
					${personnelPoliceMainFamily.unit}
				</td>
				<td>
					${personnelPoliceMainFamily.idNumber}
				</td>
				<td>
					${personnelPoliceMainFamily.policeNum}
				</td>
				<td>
						${fns:getDictLabel(personnelPoliceMainFamily.checkType, 'personnel_police', '')}
				</td>
				<td>
					<a href="javascript:;" onclick="openDetailDialog('${personnelPoliceMainFamily.id}')">查看</a>
				<shiro:hasPermission name="personnel:personnelPoliceMainFamily:edit">
					<c:if test="${personnelPoliceMainFamily.checkType == '1'}">
						<a href="javascript:void(0)" onclick="openAddEditDialog('${personnelPoliceMainFamily.id}')">修改</a>
						<a href="${ctx}/personnel/personnelPoliceMainFamily/delete?id=${personnelPoliceMainFamily.id}" onclick="return confirmx('确认要删除该民警家庭吗？', this.href)">删除</a>
					</c:if>

				</shiro:hasPermission>

				<c:if test="${personnelPoliceMainFamily.unitCheckId eq fns:getUser().id}">
					<c:if test="${'2' eq personnelPoliceMainFamily.checkType}">
						<a onclick="openDialog('${personnelPoliceMainFamily.id}')">审核</a>
					</c:if>
				</c:if>

				<c:if test="${(personnelPoliceMainFamily.juChuCheckId eq fns:getUser().id) && (fns:getUser().id eq 'bfdf74f010c9466dba12c1589ecab7f3' || fns:getUser().id eq '0921d686251848d5911e8a753cd50090' || fns:getUser().id eq 'a417f6a0d4b948398413d82448b77b86' || fns:getUser().id eq 'c5869e138911485cb80b172567e64789')}">
					<c:if test="${'3' eq personnelPoliceMainFamily.checkType|| '6' eq personnelPoliceMainFamily.checkType}">
						<a onclick="openDialog('${personnelPoliceMainFamily.id}')">审核</a>
					</c:if>
				</c:if>
				<c:choose>
					<%--民警提交--%>
					<c:when test="${(personnelPoliceMainFamily.checkType eq '2') && (fns:getUser().no eq personnelPoliceMainFamily.submitId or fns:getUser().id eq personnelPoliceMainFamily.createBy.id)}">
						<a href="${ctx}/personnel/personnelPoliceMainFamily/revocation?id=${personnelPoliceMainFamily.id}&flag=1" onclick="return confirmx('确认要撤销该民警家庭吗？', this.href)">撤销至上一步</a>
					</c:when>
					<%--民警单位--%>
					<c:when test="${(personnelPoliceMainFamily.checkType eq '3' or personnelPoliceMainFamily.checkType eq '6') && (fns:getUser().id eq personnelPoliceMainFamily.unitCheckId)}">
						<a href="${ctx}/personnel/personnelPoliceMainFamily/revocation?id=${personnelPoliceMainFamily.id}&flag=2" onclick="return confirmx('确认要撤销该民警家庭吗？', this.href)">撤销至上一步</a>
					</c:when>
					<%--组干--%>
					<c:when test="${(personnelPoliceMainFamily.checkType eq '4') && (fns:getUser().id eq 'bfdf74f010c9466dba12c1589ecab7f3' || fns:getUser().id eq '0921d686251848d5911e8a753cd50090' || fns:getUser().id eq 'a417f6a0d4b948398413d82448b77b86' || fns:getUser().id eq 'c5869e138911485cb80b172567e64789')}">
						<a href="${ctx}/personnel/personnelPoliceMainFamily/revocation?id=${personnelPoliceMainFamily.id}&flag=3" onclick="return confirmx('确认要撤销该民警家庭吗？', this.href)">撤销至上一步</a>
					</c:when>
					<%--特殊情况--0为未审核--%>
					<c:when test="${personnelPoliceMainFamily.checkType == '0'}">

					</c:when>
					<c:otherwise>

					</c:otherwise>
				</c:choose>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	<form:form id="searchForm2" modelAttribute="personnelPoliceMainFamily" action="${ctx}/personnel/personnelPoliceMainFamily/submitByIds" method="post" class="breadcrumb form-search">
		<ul class="ul-form" style="text-align: right">
			<font color="red">请选择评定单位：</font>
			<input type="hidden" name="ids" id="idsValue"/>
			<form:select id="unitCheckId" path="unitCheckId"  class="input-xlarge required">
				<c:choose>
					<c:when test="${userUnit != ''}">
						<form:option value="${userUnitId}" label="${name}"/>
					</c:when>
				</c:choose>
			</form:select>
			<input  class="btn btn-primary" type="button" value="提交送审" onclick="submitByIds()"/>

		</ul>
	</form:form>
</body>
</html>