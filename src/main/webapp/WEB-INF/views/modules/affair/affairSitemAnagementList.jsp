<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>场地管理管理</title>
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
		//添加，修改弹窗
		function openDialog(url) {
			top.$.jBox.open("iframe:"+url, "场地管理",800,520,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairSitemAnagement"}
			});
		}
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairSitemAnagement/formDetail?id="+id;
			top.$.jBox.open(url, "场地管理详情",800,500,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		};
		//使用记录弹窗
		function openuSageRecord(url) {
			top.$.jBox.open("iframe:"+url, "使用记录",800,500,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairSitemAnagement"}
			});
		}

	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/affair/affairSitemAnagement/">场地管理</a></li>
<%--		<shiro:hasPermission name="affair:affairSitemAnagement:edit"><li><a href="${ctx}/affair/affairSitemAnagement/form">场地管理添加</a></li></shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="affairSitemAnagement" action="${ctx}/affair/affairSitemAnagement/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="场地管理.xlsx"/>
		<ul class="ul-form">

			<li><label>省：</label>
				<form:select id="province" path="province" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_sheng')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>市：</label>
				<form:select id="city" path="city" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_shi')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>场地名称：</label>
				<form:input path="siteName" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>地址：</label>
				<form:input path="site" htmlEscape="false" class="input-medium"/>
			</li>
				<%--<li class="btns"><input id="btnSubmit1" class="btn btn-primary" type="submit" value="查询"/></li>--%>
			<li class="clearfix"></li>

			<ul class="ul-form2">
				<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
				<shiro:hasPermission name="affair:affairSitemAnagement:edit">
					<li class="btns"><input  class="btn btn-primary"  type="button" value="添加"
											 onclick="openDialog('${ctx}/affair/affairSitemAnagement/form')"/></li>

					<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairSitemAnagement/deleteByIds','checkAll','myCheckBox')"/></li>
				</shiro:hasPermission>
				<li class="btns"><input  class="btn btn-primary"  type="button" value="使用记录" onclick="openuSageRecord('${ctx}/affair/affairSitemAnagement/findAllaffairUsageRecordlist')"/></li>
				<li class="clearfix">x</li>
			</ul>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th id="checkTh"><input style='margin-left:12px' type='checkbox' id='checkAll' onclick='chooseAll(this,"myCheckBox")'/></th>
				<th>序号</th>
				<th>省</th>
				<th>市</th>
				<th>场地名称</th>
				<th>最大承受能力</th>
				<th>联系电话</th>
				<th>场地床位个数</th>
				<th>食堂就餐人数</th>
				<th>联系人</th>
				<th>创建者</th>
				<th>创建时间</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="affair:affairSitemAnagement:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairSitemAnagement" varStatus="status">
			<tr>
				<td  class="checkTd">
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairSitemAnagement.id}"/>
				</td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<%--<td><a href="${ctx}/affair/affairSitemAnagement/form?id=${affairSitemAnagement.id}">
					${fns:getDictLabel(affairSitemAnagement.province, '', '')}
				</a></td>--%>
				<td>
<%--						${affairSitemAnagement.province}--%>
					<%--
						获取字典对象    第一个单引号中的内容是在添加字典管理的时候里面的类型
					--%>
					${fns:getDictLabel(affairSitemAnagement.province, 'affair_sheng','')}
				</td>
				<td>
<%--					    ${affairSitemAnagement.city}--%>
					<%--
						获取字典对象    第一个单引号中的内容是在添加字典管理的时候里面的类型
					--%>
					${fns:getDictLabel(affairSitemAnagement.city, 'affair_shi','')}
				</td>
				<td>
					${affairSitemAnagement.siteName}
				</td>
				<td>
					${affairSitemAnagement.maxChengxunCapacity}
				</td>
				<td>
					${affairSitemAnagement.phone}
				</td>
				<td>
					${affairSitemAnagement.siteBedNum}
				</td>
				<td>
					${affairSitemAnagement.siteCanteenRepastNum}
				</td>
				<td>
					${affairSitemAnagement.linkman}
				</td>
				<td>
					${fns:getUserById(affairSitemAnagement.createBy).getName()}
				</td>
				<td>
					<fmt:formatDate value="${affairSitemAnagement.createDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<fmt:formatDate value="${affairSitemAnagement.updateDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${affairSitemAnagement.remarks}
				</td>
				<td>
					<a onclick="openDetailDialog('${affairSitemAnagement.id}')" style="cursor: pointer">查看</a>
					<shiro:hasPermission name="affair:affairSitemAnagement:edit">

<%--					<a href="${ctx}/affair/affairSitemAnagement/form?id=${affairSitemAnagement.id}">修改</a>--%>
					<a href="javascript:void(0)"  onclick="openDialog('${ctx}/affair/affairSitemAnagement/form?id=${affairSitemAnagement.id}')">修改</a>

					<a href="${ctx}/affair/affairSitemAnagement/delete?id=${affairSitemAnagement.id}" onclick="return confirmx('确认要删除该场地管理吗？', this.href)">删除</a>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>

		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>