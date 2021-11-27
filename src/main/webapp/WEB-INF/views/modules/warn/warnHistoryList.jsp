<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>预警历史记录管理</title>
	<meta name="decorator" content="default"/>
	<style>
		.redText{
			color: red;
		}
		.yellowText{
			color: #ffb118;
		}
		.blueText{
			color: #2aabd2;
		}
		.demo {
			display: inline-block;
			*display: inline;
			*zoom: 1;
			width: 200px;
			height: 20px;
			line-height: 20px;
			font-size: 12px;
			overflow: hidden;
			-ms-text-overflow: ellipsis;
			text-overflow: ellipsis;
			white-space: nowrap;}
		.demo:hover {height: auto;white-space: normal;}
	</style>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		function openFormDetail(id) {
			var url = "iframe:${ctx}/warn/warnHistory/formDetail?id="+id;
			top.$.jBox.open(url, "预警信息详情",800,520,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		}
		//生成警衔晋升名单
		function createPersonList() {
			top.$.jBox.open("iframe:${ctx}/personnel/personnelPoliceRank/warnCreatePersonList", "生成名单",900,600,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/warn/warning/">预警信息列表</a></li>
		<shiro:hasPermission name="warn:warning:edit"><li><a href="${ctx}/warn/warning/form">预警信息添加</a></li></shiro:hasPermission>
		<li class="active"><a href="${ctx}/warn/warnHistory/">预警历史记录</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="warnHistory" action="${ctx}/warn/warnHistory/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>预警名称：</label>
				<form:input path="warnName" htmlEscape="false" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>预警名称</th>
				<th>接收用户</th>
				<th>开始时间</th>
				<th>预警内容</th>
				<th>紧急程度</th>
				<th>重复周期</th>
				<th>上次提醒时间</th>
				<th>重复提醒(分钟)</th>
				<th>持续时间(天)</th>
				<%--<th>是否不再提醒</th>
				<th>是否已完成</th>--%>
				<th>创建人</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="warnHistory">
			<c:choose>
				<c:when test="${warnHistory.alertDegree eq '1'}">
					<tr class = "redText">
				</c:when>
				<c:when test="${warnHistory.alertDegree eq '2'}">
					<tr class = "yellowText">
				</c:when>
				<c:when test="${warnHistory.alertDegree eq '3'}">
					<tr class = "blueText">
				</c:when>
				<c:otherwise>
					<tr>
				</c:otherwise>
			</c:choose>
				<td>
					${warnHistory.warnName}
				</td>
				<td>
					${warnHistory.receivePerName}
				</td>
				<td>
					<c:if test="${warnHistory.repeatCycle == '0'}">
						${warnHistory.hour}:${warnHistory.minute}
					</c:if>
					<c:if test="${warnHistory.repeatCycle == '1'}">
						${fns:getDictLabel(warnHistory.week,'warn_week','')}&nbsp;&nbsp;${warnHistory.hour}:${warnHistory.minute}
					</c:if>
					<c:if test="${warnHistory.repeatCycle == '2'}">
						${warnHistory.day}日&nbsp; ${warnHistory.hour}:${warnHistory.minute}
					</c:if>
					<c:if test="${warnHistory.repeatCycle == '3'}">
						${warnHistory.month}月${warnHistory.day}日${warnHistory.hour}:${warnHistory.minute}
					</c:if>
					<c:if test="${warnHistory.repeatCycle == '4'}">
						<fmt:formatDate value="${warnHistory.date}" pattern="yyyy-MM-dd HH:mm"/>
					</c:if>
				</td>
				<td>
					<c:choose>
						<c:when test="${fn:contains(warnHistory.alertContent,'createPersonList')}">
							<c:set var="alertContent" value="${fn:replace(warnHistory.alertContent,'createPersonList', '')}" />
							${alertContent}
							<input type="button" onclick="createPersonList()" value="生成名单">
						</c:when>
						<c:otherwise>
							<span class="demo"> ${warnHistory.alertContent}</span>
						</c:otherwise>
					</c:choose>
					<%--<span class="demo"> ${warnHistory.alertContent}</span>--%>
					<%--${warnHistory.alertContent}--%>
				</td>
				<td>
					${fns:getDictLabel(warnHistory.alertDegree, 'warn_degree', '')}
				</td>
				<td>
					${fns:getDictLabel(warnHistory.repeatCycle, 'warn_repeat_cycle', '')}
				</td>
				<td>
					<fmt:formatDate value="${warnHistory.lastRemindTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${warnHistory.remind}
				</td>
				<td>
					${warnHistory.continueDay}
				</td>
				<td>${fns:getUserById(warnHistory.warnCreateBy).name}</td>
				<td>
					<a href="javaScript:void(0);" onclick="openFormDetail('${warnHistory.id}')">查看</a>
					<a href="${ctx}/warn/warnHistory/delete?id=${warnHistory.id}" onclick="return confirmx('确认要删除该预警历史记录吗？', this.href)">删除</a>
				</td>
			<%--<td></td>
            <td></td>--%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>