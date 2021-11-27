<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>监察发现情况录入管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {

			//这是打印功能的JS
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
        }
		//添加function,用于打开form添加页面弹窗
		function openForm(url) {
            top.$.jBox.open("iframe:" + url, "检查情况管理", 1200, 600, {
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/exam/examCheck/"}
			});
		}
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/exam/examCheck/formDetail?id="+id;
            top.$.jBox.open(url, "检查情况详情", 1500, 600, {
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
		<%--监察情况页面--%>
		<c:choose>
			<c:when test="${mType==null}">
				<li class="active"><a href="${ctx}/exam/examCheck/">检查情况</a></li>
				<li><a href="${ctx}/exam/examCheckChild/">问题整改</a></li>
<%--				<shiro:hasPermission name="exam:examAutoEvaluation:view">--%>
				<li ><a href="${ctx}/exam/examAutoEvaluation/">推送管理</a></li>
<%--				</shiro:hasPermission>--%>
                <li><a href="${ctx}/exam/examOtherData/">其他数据</a></li>
			</c:when>
			<c:otherwise>
				<li class="active"><a href="${ctx}/exam/examCheck?mType=1">检查情况</a></li>
				<shiro:hasPermission name="exam:examCheck:edit">
				<li ><a href="${ctx}/exam/examCheck/">检查情况管理</a></li>
				</shiro:hasPermission>
                <li><a href="${ctx}/exam/examCheckChild/">问题整改</a></li>
                <shiro:hasPermission name="exam:examCheck1:manage">
                    <li><a href="${ctx}/exam/examCheck1/manageList">问题整改管理</a></li>
                </shiro:hasPermission>
                <shiro:hasPermission name="exam:examAutoEvaluation:view">
                    <li ><a href="${ctx}/exam/examAutoEvaluation/">推送管理</a></li>
                </shiro:hasPermission>
                <li><a href="${ctx}/exam/examOtherData/">其他数据</a></li>
			</c:otherwise>
		</c:choose>
	</ul>
	<form:form id="searchForm" modelAttribute="examCheck" action="${ctx}/exam/examCheck/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="mType" name="mType" type="hidden" value="${mType}"/>
		<ul class="ul-form">
			<li><label>检查时间：</label>
				<input name="beginCheckDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${examCheck.beginCheckDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> -
				<input name="endCheckDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${examCheck.endCheckDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>检查单位：</label>
				<sys:treeselect id="checkUnit" name="checkUnitId" value="${examCheck.checkUnitId}" labelName="checkUnit" labelValue="${examCheck.checkUnit}"
					title="检查单位" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<li><label>检查人：</label>
				<sys:treeselect id="checkPerson" name="checkPersonId" value="${examCheck.checkPersonId}" labelName="checkPerson" labelValue="${examCheck.checkPerson}"
					title="检查人" url="/sys/office/treeData?type=3" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<li><label>责任单位：</label>
				<sys:treeselect id="dutyUnit" name="dutyUnitId" value="${examCheck.dutyUnitId}" labelName="dutyUnit" labelValue="${examCheck.dutyUnit}"
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
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/exam/examCheck/form')"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/exam/examCheck/deleteByIds','checkAll','myCheckBox')"/></li>
			</c:if>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
			<c:choose>
				<c:when test="${mType==null}">
					<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/exam/examCheck'"/></li>
				</c:when>
				<c:otherwise>
					<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/exam/examCheck?mType=1'"/></li>
				</c:otherwise>
			</c:choose>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input style='margin-left:12px' type='checkbox' id='checkAll' onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>检查时间</th>
				<th>检查单位</th>
				<th>检查人</th>
				<th>责任单位</th>
				<th>责任单位扣分</th>
				<th>责任领导</th>
				<th>责任领导扣分</th>
				<th>责任人</th>
				<th>责任人扣分</th>
				<th>详细情况</th>
				<c:if test="${mType==null}">
					<th id="handleTh">
				操作
					</th>
				</c:if>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="examCheck" varStatus="status">
			<c:choose>
				<c:when test="${not empty examCheck.examCheckChildList}">
					<c:forEach items="${examCheck.examCheckChildList}" var="child" varStatus="childStatus">
						<c:choose>
							<c:when test="${childStatus.first}">
								<tr>
                                    <td rowspan="${examCheck.examCheckChildList.size()}"><input style='margin-left:12px'
                                                                                                type='checkbox'
                                                                                                name="myCheckBox"
                                                                                                value="${examCheck.id}"/>
                                    </td>
									<td rowspan="${examCheck.examCheckChildList.size()}">
											${(page.pageNo-1)*page.pageSize+status.index+1}
									</td>
									<td rowspan="${examCheck.examCheckChildList.size()}">
										<fmt:formatDate value="${examCheck.checkDate}" pattern="yyyy-MM-dd"/>
									</td>
									<td rowspan="${examCheck.examCheckChildList.size()}">
										<a href="javascript:void(0)" onclick="openDetailDialog('${examCheck.id}')">
                                                ${examCheck.checkUnit}
										</a>
									</td>
									<td rowspan="${examCheck.examCheckChildList.size()}">
                                            ${examCheck.checkPerson}
									</td>
									<td>
                                            ${child.dutyUnit}
									</td>
									<td>
											${child.dutyUnitScore}
									</td>
									<td>
											${child.dutyLeader}
									</td>
									<td>
											${child.dutyLeaderScore}
									</td>
									<td>
											${child.dutyPerson}
									</td>
									<td>
											${child.dutyPersonScore}
									</td>
									<td>${child.scortSituation}</td>
                                            <td rowspan="${examCheck.examCheckChildList.size()}" class="handleTd">
									<shiro:hasPermission name="exam:examCheck:edit">
                                        <c:if test="${(mType==null&&examCheck.createBy.id == fns:getUser().id)|| (not empty isJxb && isJxb eq 'isJxb')}">
                                                <a href="javascript:void(0)"
                                                   onclick="openForm('${ctx}/exam/examCheck/form?id=${examCheck.id}')">修改</a>
                                                <a href="${ctx}/exam/examCheck/delete?id=${examCheck.id}"
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
                        <td><input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${examCheck.id}"/>
                        </td>
						<td>
								${(page.pageNo-1)*page.pageSize+status.index+1}
						</td>
						<td>
							<fmt:formatDate value="${examCheck.checkDate}" pattern="yyyy-MM-dd"/>
						</td>
						<td>
							<a href="javascript:void(0)" onclick="openDetailDialog('${examCheck.id}')">
                                    ${examCheck.checkUnit}
							</a>
						</td>
						<td>
                                ${examCheck.checkPerson}
						</td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td class="handleTd">
						<shiro:hasPermission name="exam:examCheck:edit">
                            <c:if test="${(mType==null&&examCheck.createBy.id == fns:getUser().id)|| (not empty isJxb && isJxb eq 'isJxb')}">
                                    <a href="javascript:void(0)"
                                       onclick="openForm('${ctx}/exam/examCheck/form?id=${examCheck.id}')">修改</a>
                                    <a href="${ctx}/exam/examCheck/delete?id=${examCheck.id}"
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
	<div class="pagination">${page}</div>
</body>
</html>