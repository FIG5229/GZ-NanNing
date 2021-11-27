<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>其他数据管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			//这是打印功能的JS
			$("#print").click(function(){
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
		//添加function,用于打开form添加页面弹窗
		function openForm(url) {
			top.$.jBox.open("iframe:" + url, "其他数据", 1000, 600, {
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/exam/examOtherData/"}
			});
		}

	</script>
</head>
<body>
	<ul class="nav nav-tabs">
<%--		<li class="active"><a href="${ctx}/exam/examOtherData/">其他数据列表</a></li>--%>
		<%--<shiro:hasPermission name="exam:examOtherData:edit"><li><a href="${ctx}/exam/examOtherData/form">其他数据添加</a></li></shiro:hasPermission>--%>
	<c:choose>
		<c:when test="${mType==null}">
			<li><a href="${ctx}/exam/examCheck/">检查情况</a></li>
			<li><a href="${ctx}/exam/examCheckChild/">问题整改</a></li>
			<%--<shiro:hasPermission name="exam:examJcInfo:view">--%>
				<li ><a href="${ctx}/exam/examAutoEvaluation/">推送管理</a></li>
			<%--</shiro:hasPermission>--%>
			<li class="active"><a href="${ctx}/exam/examOtherData/">其他数据</a></li>
		</c:when>
		<c:otherwise>
			<li><a href="${ctx}/exam/examCheck?mType=1">检查情况</a></li>
			<shiro:hasPermission name="exam:examCheck:edit">
				<li ><a href="${ctx}/exam/examCheck/">检查情况管理</a></li>
			</shiro:hasPermission>
			<li><a href="${ctx}/exam/examCheck1/">问题整改</a></li>
			<shiro:hasPermission name="exam:examCheck:edit">
				<li ><a href="${ctx}/exam/examJcInfo/">推送管理</a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="exam:examJcInfo:view">
				<li ><a href="${ctx}/exam/examJcInfo/">推送管理</a></li>
			</shiro:hasPermission>
			<li class="active"><a href="${ctx}/exam/examOtherData/">其他数据</a></li>
		</c:otherwise>
	</c:choose>
	</ul>
	<form:form id="searchForm" modelAttribute="examOtherData" action="${ctx}/exam/examOtherData/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
		<%--	<li><label>检查时间：</label>
				<input name="beginCheckDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${examOtherData.beginCheckDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/> -
				<input name="endCheckDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${examOtherData.endCheckDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
			</li>--%>
			<li><label>审核单位：</label>
				<sys:treeselect id="checkUnit" name="checkUnitId" value="${examOtherData.checkUnitId}" labelName="checkUnit" labelValue="${examOtherData.checkUnit}"
								title="审核单位" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
<%--			<li><label>检查人：</label>--%>
<%--				<sys:treeselect id="checkPerson" name="checkPersonId" value="${examOtherData.checkPersonId}" labelName="checkPerson" labelValue="${examOtherData.checkPerson}"--%>
<%--								title="检查人" url="/sys/office/treeData?type=3" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>--%>
<%--			</li>--%>
			<li><label>责任单位：</label>
				<sys:treeselect id="dutyUnit" name="dutyUnitId" value="${examOtherData.dutyUnitId}" labelName="dutyUnit" labelValue="${examOtherData.dutyUnit}"
								title="责任单位" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<li><label>使用模板：</label>
				<form:select path="useModel" class="input-medium" cssStyle="width: 400px;">
					<form:option value="" label=""/>
					<form:options items="${templateFile}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<c:if test="${mType==null}">
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/exam/examOtherData/form')"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/exam/examOtherData/deleteByIds','checkAll','myCheckBox')"/></li>
			</c:if>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
			<c:choose>
				<c:when test="${mType==null}">
					<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/exam/examOtherData'"/></li>
				</c:when>
				<c:otherwise>
					<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/exam/examOtherData?mType=1'"/></li>
				</c:otherwise>
			</c:choose>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
		<tr>
			<th><input style='margin-left:12px' type='checkbox'id='checkAll'onclick='chooseAll(this,"myCheckBox")'/>全选</th>
			<th>序号</th>
<%--			<th>检查时间</th>--%>
<%--			<th>检查单位</th>--%>
<%--			<th>检查人</th>--%>
			<th>时间</th>
			<th>责任单位</th>
			<th>责任领导</th>
			<th>责任人</th>
			<th>责任单位扣分</th>
			<th>责任领导扣分</th>
			<th>责任人扣分</th>
			<c:if test="${mType==null}">
				<shiro:hasPermission name="exam:examOtherData:edit"><th id="handleTh">操作</th></shiro:hasPermission>
			</c:if>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="examOtherData" varStatus="status">
			<c:choose>
				<c:when test="${not empty examOtherData.examOtherDataList}">
					<c:forEach items="${examOtherData.examOtherDataList}" var="child" varStatus="childStatus">
						<c:choose>
							<c:when test="${childStatus.first}">
								<tr>
									<td rowspan="${examOtherData.examOtherDataList.size()}"><input style='margin-left:12px'
																								type='checkbox'
																								name="myCheckBox"
																								value="${examOtherData.id}"/>
									</td>
									<td rowspan="${examOtherData.examOtherDataList.size()}">
											${(page.pageNo-1)*page.pageSize+status.index+1}
									</td>
								<%--	<td rowspan="${examOtherData.examOtherDataList.size()}">
										<fmt:formatDate value="${examOtherData.checkDate}" pattern="yyyy-MM-dd"/>
									</td>
									<td rowspan="${examOtherData.examOtherDataList.size()}">
										<a onclick="openDetailDialog('${examOtherData.id}')">
												${examOtherData.checkUnit}
										</a>
									</td>
									<td rowspan="${examOtherData.examOtherDataList.size()}">
											${examOtherData.checkPerson}
									</td>--%>
									<td>
										<fmt:formatDate value="${child.time}" pattern="yyyy-MM-dd"/>
									</td>
									<td>
											${child.dutyUnit}
									</td>
									<td>
											${child.dutyLeader}
									</td>
									<td>
											${child.dutyPerson}
									</td>
									<td>
											${child.dutyUnitScore}
									</td>
									<td>
											${child.dutyLeaderScore}
									</td>
									<td>
											${child.dutyPersonScore}
									</td>
									<td rowspan="${examOtherData.examOtherDataList.size()}" class="handleTd">
										<shiro:hasPermission name="exam:examOtherData:edit">
											<c:if test="${mType==null&&examOtherData.createBy.id == fns:getUser().id}">
												<a href="javascript:void(0)"
												   onclick="openForm('${ctx}/exam/examOtherData/form?id=${examOtherData.id}')">修改</a>
												<a href="${ctx}/exam/examOtherData/delete?id=${examOtherData.id}"
												   onclick="return confirmx('确认要删除该信息吗？', this.href)">删除</a>
											</c:if>
										</shiro:hasPermission>
									</td>
								</tr>
							</c:when>
							<c:otherwise>
								<c:if test="${! childStatus.first}">
									<tr>
										<td>
												${child.dutyUnit}
										</td>
										<td>
												${child.dutyLeader}
										</td>
										<td>
												${child.dutyPerson}
										</td>
										<td>
												${child.dutyUnitScore}
										</td>
										<td>
												${child.dutyLeaderScore}
										</td>
										<td>
												${child.dutyPersonScore}
										</td>
									</tr>
								</c:if>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr>
						<td><input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${examOtherData.id}"/>
						</td>
						<td>
								${(page.pageNo-1)*page.pageSize+status.index+1}
						</td>
						<td>
							<fmt:formatDate value="${examOtherData.checkDate}" pattern="yyyy-MM-dd"/>
						</td>
						<td>
							<a href="javascript:void(0)" onclick="openDetailDialog('${examOtherData.id}')">
									${examOtherData.checkUnit}
							</a>
						</td>
						<td>
								${examOtherData.checkPerson}
						</td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td class="handleTd">
							<shiro:hasPermission name="exam:examCheck:edit">
								<c:if test="${mType==null&&examOtherData.createBy.id == fns:getUser().id}">
									<a href="javascript:void(0)"
									   onclick="openForm('${ctx}/exam/examOtherData/form?id=${examOtherData.id}')">修改</a>
									<a href="${ctx}/exam/examOtherData/delete?id=${examOtherData.id}"
									   onclick="return confirmx('确认要删除该信息吗？', this.href)">删除</a>
								</c:if>
							</shiro:hasPermission>
						</td>
					</tr>
				</c:otherwise>
			</c:choose>
		</c:forEach>
		</tbody>
	</table>

	<%--<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th id="checkTh"><input style='margin-left:12px' type='checkbox' id='checkAll' onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				&lt;%&ndash;<th>检查时间</th>&ndash;%&gt;
				<th>审核单位</th>
				&lt;%&ndash;<th>检查人</th>&ndash;%&gt;
				<th>责任单位</th>
				<th>加扣分情况</th>
				<th>责任领导</th>
				<th>责任人</th>
				<th>责任单位加扣分</th>
				<th>责任领导加扣分</th>
				<th>责任人加扣分</th>
				<th id="handleTh">操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="examOtherData">
			<tr>
				<td class="checkTd"><input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${examOtherData.id}"/>
				</td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				&lt;%&ndash;<td>
					<fmt:formatDate value="${examOtherData.checkDate}" pattern="yyyy-MM-dd"/>
				</td>&ndash;%&gt;
				<td>
					${examOtherData.checkUnit}
				</td>
				&lt;%&ndash;<td>
					${examOtherData.checkPerson}
				</td>&ndash;%&gt;
				<td>
					${examOtherData.dutyUnit}
				</td>
				<td>
					${examOtherData.scortSituation}
				</td>
				<td>
					${examOtherData.dutyLeader}
				</td>
				<td>
					${examOtherData.dutyPerson}
				</td>
				<td>
					${examOtherData.dutyUnitScore}
				</td>
				<td>
					${examOtherData.dutyLeaderScore}
				</td>
				<td>
					${examOtherData.dutyPersonScore}
				</td>
				<td class="handleTd">
				<shiro:hasPermission name="exam:examOtherData:edit">
    				<a href="javascript:void(0)"
					   onclick="openForm('${ctx}/exam/examOtherData/form?id=${examOtherData.id}')">修改</a>
					<a href="${ctx}/exam/examOtherData/delete?id=${examOtherData.id}" onclick="return confirmx('确认要删除该其他数据吗？', this.href)">删除</a>
				</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>--%>
	<div class="pagination">${page}</div>
</body>
</html>