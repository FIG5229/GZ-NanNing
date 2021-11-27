<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>教育整顿经验做法管理</title>
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
			//默认显示某个输入框
			var reason = "${reason}";
			if(reason != null && reason != '' && typeof reason != undefined){
				$("#reason").val(${reason}).trigger("change");
			}
			$("#reason").change(function(){
				$("#nd").val("")
				$("#sd").val("")
				$("#ed").val("")
				$("#qt").val("")
				year();
				console.log($("#reason").val())
			});
			function year() {
				if ($("#reason").val() == '1') {
					$("#rd").attr("style","display:none")
					$("#yr").attr("style","display:block")
					$("#oy").attr("style","display:none")
				}else if($("#reason").val() == '2') {
					$("#rd").attr("style","display:block")
					$("#yr").attr("style","display:none")
					$("#oy").attr("style","display:none")
				}else if($("#reason").val() == '3'){
					$("#rd").attr("style","display:none")
					$("#yr").attr("style","display:none")
					$("#oy").attr("style","display:block")
				}else {
					$("#rd").attr("style","display:none")
					$("#yr").attr("style","display:block")
					$("#oy").attr("style","display:none")
				}
			}
			year();

		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		//添加function,用于打开form添加页面弹窗,需要传一个url
		function openForm(url) {
			top.$.jBox.open("iframe:"+url, "信息简报/经验交流",1200,600,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairEmpiricalPractice"}
			});
		}
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairEmpiricalPractice/formDetail?id="+id;
			top.$.jBox.open(url, "信息简报/经验交流",1200,600,{
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
		<li ><a href="${ctx}/affair/affairLzxxjyActivities/">廉政教育</a></li>
		<shiro:hasPermission name="affair:affairLzxxjyActivities:manage">
			<li><a href="${ctx}/affair/affairLzxxjyActivities/manageList">廉政教育审核</a></li>
		</shiro:hasPermission>
		<li><a href="${ctx}/affair/affairTeamDiscipline/">纪律作风教育整顿</a></li>
		<shiro:hasPermission name="affair:affairTeamDiscipline:manage">
			<li><a href="${ctx}/affair/affairTeamDiscipline/manageList">纪律作风教育整顿管理</a></li>
		</shiro:hasPermission>
		<li class="active"><a href="${ctx}/affair/affairEmpiricalPractice/">信息简报/经验交流</a></li>
		<%--<shiro:hasPermission name="affair:affairEmpiricalPractice:edit"><li><a href="${ctx}/affair/affairEmpiricalPractice/form">教育整顿经验做法添加</a></li></shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="affairEmpiricalPractice" action="${ctx}/affair/affairEmpiricalPractice/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li>
				<label>数据范围：</label>
				<select id="reason" style="width: 140px;"  name="reason">
					<option value="2">全部</option>
					<option value="3">其他年份</option>
				</select>
			</li>
			<li id="yr"><label>年度：</label>
				<input id="nd" name="startYear" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairEmpiricalPractice.startYear}" pattern="yyyy"/>"
					   onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false});"/>
			</li>
			<li id="rd"><label>发布日期：</label>
				<input id="sd" name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairEmpiricalPractice.startDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				至
				<input id="ed" name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairEmpiricalPractice.endDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li id="oy"><label>其他年份：</label>
				<input id="qt" name="otherYear" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="${affairEmpiricalPractice.otherYear}"
					   onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false});"/>
			</li>
			<li><label>文件名称：</label>
				<form:input path="title" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>发布单位：</label>
				<sys:treeselect id="unit" name="unitId" value="${affairEmpiricalPractice.unitId}" labelName="unit" labelValue="${affairEmpiricalPractice.unit}"
								title="单位" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="false"/>
			</li>

			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairEmpiricalPractice:edit">
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/affair/affairEmpiricalPractice/form')"/></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="affair:affairEmpiricalPractice:delete">
			<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairEmpiricalPractice/deleteByIds','checkAll','myCheckBox')"/></li>
			</shiro:hasPermission>

			<%--<shiro:hasPermission name="affair:affairEmpiricalPractice:edit">
				<li class="btns"><input  class="btn btn-primary" type="button" value="发布"/></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="affair:affairEmpiricalPractice:edit">
				<li class="btns"><input  class="btn btn-primary" type="button" value="取消发布"/></li>
			</shiro:hasPermission>--%>

				<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairEmpiricalPractice'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input style='margin-left:12px' type='checkbox'id='checkAll'onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>文件名称</th>
				<th>发布单位</th>
				<th>发布时间</th>
				<shiro:hasPermission name="affair:affairEmpiricalPractice:edit"><th id="handleTh">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairEmpiricalPractice" varStatus="status">
			<c:choose>
				<%--<!-- 局机关部门，除了局纪检监察 -->--%>
				<c:when test="${ '1' == fns:getUser().company.id && '6' != fns:getUser().office.id}">
					<%--<!-- 三个处添加的信息看不到 -->--%>
					<c:choose>
						<c:when test="${ '19e70728419d4051bd4f9f496fbf0d7c' == affairEmpiricalPractice.createBy.id || '49e960f9fe6c4f7786ae894ffac51c7d' == affairEmpiricalPractice.createBy.id || 'f278b35db9ca4f5d8418cc44acec36de' == affairEmpiricalPractice.createBy.id}">

						</c:when>
						<%--<!-- 除了三个处添加的信息能够看到 -->--%>
						<c:otherwise>
							<tr>
								<td><input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairEmpiricalPractice.id}"/></td>
								<td>
										${(page.pageNo-1)*page.pageSize+status.index+1}
								</td>
								<td>
										${affairEmpiricalPractice.title}
								</td>
								<td>
										${affairEmpiricalPractice.unit}
								</td>
								<td>
									<fmt:formatDate value="${affairEmpiricalPractice.releaseDate}" pattern="yyyy-MM-dd"/>
								</td>
								<td class="handleTd">
									<a onclick="openDetailDialog('${affairEmpiricalPractice.id}')">查看</a>
									<c:choose>
										<c:when test="${  '31' == fns:getUser().office.id || '146'== fns:getUser().office.id || '266'== fns:getUser().office.id }">
											<c:choose>
												<c:when test="${'276d8cdc184748c8a5ff014221fb135a' == affairEmpiricalPractice.createBy.id|| 'd5ec905f77714c6f8a216e5cbd14ff67'  == affairEmpiricalPractice.createBy.id}">}">

												</c:when>
												<c:otherwise>
													<shiro:hasPermission name="affair:affairEmpiricalPractice:edit">
														<a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairEmpiricalPractice/form?id=${affairEmpiricalPractice.id}')">修改</a>
													</shiro:hasPermission>
													<shiro:hasPermission name="affair:affairEmpiricalPractice:delete">
														<a href="${ctx}/affair/affairEmpiricalPractice/delete?id=${affairEmpiricalPractice.id}" onclick="return confirmx('确认要删除该廉政学习教育活动吗？', this.href)">删除</a>
													</shiro:hasPermission>
												</c:otherwise>
											</c:choose>
										</c:when>
										<c:otherwise>
											<shiro:hasPermission name="affair:affairEmpiricalPractice:edit">
												<a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairEmpiricalPractice/form?id=${affairEmpiricalPractice.id}')">修改</a>
											</shiro:hasPermission>
											<shiro:hasPermission name="affair:affairEmpiricalPractice:delete">
												<a href="${ctx}/affair/affairEmpiricalPractice/delete?id=${affairEmpiricalPractice.id}" onclick="return confirmx('确认要删除该廉政学习教育活动吗？', this.href)">删除</a>
											</shiro:hasPermission>
										</c:otherwise>
									</c:choose>
								</td>
							</tr>
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>
					<tr>
						<td><input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairEmpiricalPractice.id}"/></td>
						<td>
								${(page.pageNo-1)*page.pageSize+status.index+1}
						</td>
						<td>
								${affairEmpiricalPractice.title}
						</td>
						<td>
								${affairEmpiricalPractice.unit}
						</td>
						<td>
							<fmt:formatDate value="${affairEmpiricalPractice.releaseDate}" pattern="yyyy-MM-dd"/>
						</td>
						<td class="handleTd">
							<a href="javascript:void(0)" onclick="openDetailDialog('${affairEmpiricalPractice.id}')">查看</a>
							<c:choose>
								<c:when test="${  '31' == fns:getUser().office.id || '146'== fns:getUser().office.id || '266'== fns:getUser().office.id }">
									<c:choose>
										<c:when test="${'276d8cdc184748c8a5ff014221fb135a' == affairEmpiricalPractice.createBy.id || 'd5ec905f77714c6f8a216e5cbd14ff67'  == affairEmpiricalPractice.createBy.id}">

										</c:when>
										<c:otherwise>
											<shiro:hasPermission name="affair:affairEmpiricalPractice:edit">
												<a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairEmpiricalPractice/form?id=${affairEmpiricalPractice.id}')">修改</a>
											</shiro:hasPermission>
											<shiro:hasPermission name="affair:affairEmpiricalPractice:delete">
												<a href="${ctx}/affair/affairEmpiricalPractice/delete?id=${affairEmpiricalPractice.id}" onclick="return confirmx('确认要删除该廉政学习教育活动吗？', this.href)">删除</a>
											</shiro:hasPermission>
										</c:otherwise>
									</c:choose>
								</c:when>
								<c:otherwise>
									<shiro:hasPermission name="affair:affairEmpiricalPractice:edit">
										<a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairEmpiricalPractice/form?id=${affairEmpiricalPractice.id}')">修改</a>
									</shiro:hasPermission>
									<shiro:hasPermission name="affair:affairEmpiricalPractice:delete">
										<a href="${ctx}/affair/affairEmpiricalPractice/delete?id=${affairEmpiricalPractice.id}" onclick="return confirmx('确认要删除该廉政学习教育活动吗？', this.href)">删除</a>
									</shiro:hasPermission>
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
				</c:otherwise>
			</c:choose>
		<%--	<tr>
				<td><input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairEmpiricalPractice.id}"/></td>
				<td>
					${status.index+1}
				</td>
				<td>
					${affairEmpiricalPractice.title}
				</td>
				<td>
					${affairEmpiricalPractice.unit}
				</td>
				<td>
					<fmt:formatDate value="${affairEmpiricalPractice.releaseDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td class="handleTd">
					<c:choose>
						<c:when test="${  '31' == fns:getUser().office.id || '146'== fns:getUser().office.id || '266'== fns:getUser().office.id }">
							<c:choose>
								<c:when test="${'276d8cdc184748c8a5ff014221fb135a' == affairEmpiricalPractice.createBy.id}">

								</c:when>
								<c:otherwise>
									<shiro:hasPermission name="affair:affairEmpiricalPractice:edit">
										<a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairEmpiricalPractice/form?id=${affairEmpiricalPractice.id}')">修改</a>
									</shiro:hasPermission>
									<shiro:hasPermission name="affair:affairEmpiricalPractice:delete">
										<a href="${ctx}/affair/affairEmpiricalPractice/delete?id=${affairEmpiricalPractice.id}" onclick="return confirmx('确认要删除该廉政学习教育活动吗？', this.href)">删除</a>
									</shiro:hasPermission>
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
							<shiro:hasPermission name="affair:affairEmpiricalPractice:edit">
								<a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairEmpiricalPractice/form?id=${affairEmpiricalPractice.id}')">修改</a>
							</shiro:hasPermission>
							<shiro:hasPermission name="affair:affairEmpiricalPractice:delete">
								<a href="${ctx}/affair/affairEmpiricalPractice/delete?id=${affairEmpiricalPractice.id}" onclick="return confirmx('确认要删除该廉政学习教育活动吗？', this.href)">删除</a>
							</shiro:hasPermission>
						</c:otherwise>
					</c:choose>
			</td>
			</tr>--%>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>