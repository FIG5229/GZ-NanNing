<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>查(借)阅审批管理</title>
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
		//添加function,用于打开form添加页面弹窗,需要传一个url
		function openForm(url) {
			top.$.jBox.open("iframe:"+url, "查(借)阅审批管理",1400,800,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairArchiveApproval"}
			});
		}
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		//批量导出弹窗
		function openAccountExport(url) {
			top.$.jBox.open("iframe:"+url, "批量导出",800,520,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairArchiveApproval"}
			});
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li ><a href="${ctx}/affair/affairLedgerInto/">转入</a></li>
		<li ><a href="${ctx}/affair/affairLedgerOut/">转出</a></li>
		<li ><a href="${ctx}/affair/affairConsult/">查阅</a></li>
		<li ><a href="${ctx}/affair/affairBorrow/">借阅</a></li>
		<li class="active"><a href="${ctx}/affair/affairArchiveApproval/">查(借)阅审批列表</a></li>
		<li ><a href="${ctx}/affair/affairArchiveRegister/">在职</a></li>
		<li ><a href="${ctx}/affair/affairRetire/">离退</a></li>
		<li ><a href="${ctx}/affair/affairDeath/">死亡</a></li>
		<li ><a href="${ctx}/affair/affairMaterial/">收集</a></li>
		<li ><a href="${ctx}/affair/affairTemHum/">温湿度</a></li>
		<li ><a href="${ctx}/affair/affairCheck/">核对</a></li>
		<li><a href="${ctx}/affair/affairSecurityCheck/">安全检查</a></li>
		<li ><a href="${ctx}/affair/affairDestroyMeterial/">销毁清册</a></li>
		<li ><a href="${ctx}/affair/affairCopy/">复印档案登记</a></li>

	</ul>
	<form:form id="searchForm" modelAttribute="affairArchiveApproval" action="${ctx}/affair/affairArchiveApproval/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>查档事由：</label>
				<form:input path="reason" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>查档内容：</label>
				<form:input path="content" htmlEscape="false" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairArchiveApproval:edit">
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/affair/affairArchiveApproval/form')"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairArchiveApproval/deleteByIds','checkAll','myCheckBox')"/></li>
				<li class="btns"><input  class="btn btn-primary" type="button" value="导入" /></li>
			</shiro:hasPermission>
			<li class="btns"><input  class="btn btn-primary" type="button" value="导出" onclick="openAccountExport('${ctx}/affair/affairLedgerInto/accountExport')"/></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairArchiveApproval'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input style='margin-left:12px' type='checkbox'id='checkAll'onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>查档事由</th>
				<th>查档内容</th>
				<th>被查档人姓名</th>
				<th>查档人单位</th>
				<th>查档人政治面貌</th>
				<shiro:hasPermission name="affair:affairArchiveApproval:edit"><th id="handleTh">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairArchiveApproval" varStatus="status">
			<c:choose>
				<c:when test="${not empty affairArchiveApproval.affairCdPersonList || not empty affairArchiveApproval.affairCdObjectList}">
					<c:choose>
						<c:when test="${affairArchiveApproval.affairCdObjectList.size() >= affairArchiveApproval.affairCdPersonList.size()}">
							<c:forEach items="${affairArchiveApproval.affairCdObjectList}" var="cdObject" varStatus="childStatus">
								<c:choose>
									<c:when test="${childStatus.first}">
										<tr>
											<td rowspan="${affairArchiveApproval.affairCdObjectList.size()}">
												<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairArchiveApproval.id}"/>
											</td>
											<td rowspan="${affairArchiveApproval.affairCdObjectList.size()}">
													${(page.pageNo-1)*page.pageSize+status.index+1}
											</td>
											<td rowspan="${affairArchiveApproval.affairCdObjectList.size()}">
													${affairArchiveApproval.reason}
											</td>
											<td rowspan="${affairArchiveApproval.affairCdObjectList.size()}">
													${affairArchiveApproval.content}
											</td>
											<td>
													${cdObject.name}
											</td>
											<c:choose>
												<c:when test="${childStatus.index <= affairArchiveApproval.affairCdPersonList.size()}">
													<td>${affairArchiveApproval.affairCdPersonList[childStatus.index].unit}</td>
													<td>${fns:getDictLabel(affairArchiveApproval.affairCdPersonList[childStatus.index].face, 'zzmm', '')}</td>
												</c:when>
												<c:otherwise>
													<td></td>
													<td></td>
												</c:otherwise>
											</c:choose>
											<shiro:hasPermission name="affair:affairArchiveApproval:edit"><td rowspan="${affairArchiveApproval.affairCdObjectList.size()}" class="handleTd">
												<a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairArchiveApproval/form?id=${affairArchiveApproval.id}')">修改</a>
												<a href="${ctx}/affair/affairArchiveApproval/delete?id=${affairArchiveApproval.id}" onclick="return confirmx('确认要删除该条信息吗？', this.href)">删除</a>
											</td></shiro:hasPermission>
										</tr>
									</c:when>
									<c:otherwise>
										<c:if test="${! childStatus.first}">
											<tr>
												<td>
														${cdObject.name}
												</td>
												<c:choose>
													<c:when test="${childStatus.index <= affairArchiveApproval.affairCdPersonList.size()}">
														<td>${affairArchiveApproval.affairCdPersonList[childStatus.index].unit}</td>
														<td>${fns:getDictLabel(affairArchiveApproval.affairCdPersonList[childStatus.index].face, 'zzmm', '')}</td>
													</c:when>
													<c:otherwise>
														<td></td>
														<td></td>
													</c:otherwise>
												</c:choose>
											</tr>
										</c:if>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<c:forEach items="${affairArchiveApproval.affairCdPersonList}" var="cdPerson" varStatus="childStatus">
								<c:choose>
									<c:when test="${childStatus.first}">
										<tr>
											<td rowspan="${affairArchiveApproval.affairCdPersonList.size()}">
												<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairArchiveApproval.id}"/>
											</td>
											<td rowspan="${affairArchiveApproval.affairCdPersonList.size()}">
													${(page.pageNo-1)*page.pageSize+status.index+1}
											</td>
											<td rowspan="${affairArchiveApproval.affairCdPersonList.size()}">
													${affairArchiveApproval.reason}
											</td>
											<td rowspan="${affairArchiveApproval.affairCdPersonList.size()}">
													${affairArchiveApproval.content}
											</td>
											<c:choose>
												<c:when test="${childStatus.index <= affairArchiveApproval.affairCdPersonList.size()}">
													<td>${affairArchiveApproval.affairCdObjectList[childStatus.index].name}</td>
												</c:when>
												<c:otherwise>
												<td></td>
												</c:otherwise>
											</c:choose>
											<td>
													${cdPerson.unit}
											</td>
											<td>
													${fns:getDictLabel(cdPerson.face, 'zzmm', '')}
											</td>
											<shiro:hasPermission name="affair:affairArchiveApproval:edit"><td rowspan="${affairArchiveApproval.affairCdPersonList.size()}" class="handleTd">
												<a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairArchiveApproval/form?id=${affairArchiveApproval.id}')">修改</a>
												<a href="${ctx}/affair/affairArchiveApproval/delete?id=${affairArchiveApproval.id}" onclick="return confirmx('确认要删除该条信息吗？', this.href)">删除</a>
											</td></shiro:hasPermission>
										</tr>
									</c:when>
									<c:otherwise>
										<c:if test="${! childStatus.first}">
											<tr>
												<c:choose>
													<c:when test="${childStatus.index <= affairArchiveApproval.affairCdPersonList.size()}">
														<td>${affairArchiveApproval.affairCdObjectList[childStatus.index].name}</td>
													</c:when>
													<c:otherwise>
														<td></td>
													</c:otherwise>
												</c:choose>
												<td>
														${cdPerson.unit}
												</td>
												<td>
														${fns:getDictLabel(cdPerson.face, 'zzmm', '')}
												</td>
											</tr>
										</c:if>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>
					<tr>
						<td><input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairArchiveApproval.id}"/></td>
						<td>
								${(page.pageNo-1)*page.pageSize+status.index+1}
						</td>
						<td>
								${affairArchiveApproval.reason}
						</td>
						<td>
								${affairArchiveApproval.content}
						</td>
						<td></td>
						<td></td>
						<td></td>
						<shiro:hasPermission name="affair:affairArchiveApproval:edit"><td class="handleTd">
							<c:if test="${affairArchiveApproval.createBy.id == fns:getUser().id}">
								<a href="javascript:void(0)"
								   onclick="openForm('${ctx}/affair/affairArchiveApproval/form?id=${affairArchiveApproval.id}')">修改</a>
								<a href="${ctx}/affair/affairArchiveApproval/delete?id=${affairArchiveApproval.id}"
								   onclick="return confirmx('确认要删除该条信息吗？', this.href)">删除</a>
							</c:if>
						</td></shiro:hasPermission>
					</tr>
				</c:otherwise>
			</c:choose>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>