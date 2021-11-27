<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>监察发现情况录入管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {

			$("[data-toggle='popover']").popover();
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
					afterPrint:function(){
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
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/exam/examCheck1/formDetail?id="+id;
			top.$.jBox.open(url, "问题整改报送",800,500,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		};
		function openForm(url) {
			top.$.jBox.open("iframe:"+url, "问题整改报送",800,520,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/exam/examCheck1"}
			});
		}
	</script>
</head>${ctx}/exam/examCheck1/manageList
<body>
	<ul class="nav nav-tabs">
		<%--问题整改页面--%>
		<%--<li class="active"><a href="${ctx}/exam/examCheck1/">查询</a></li>
		<shiro:hasPermission name="exam:examCheck1:manage"><li><a href="${ctx}/exam/examCheck1/manageList">管理</a></li></shiro:hasPermission>--%>
			<c:choose>
				<c:when test="${mType==null}">
					<li><a href="${ctx}/exam/examCheck/">检查情况</a></li>
					<li class="active"><a href="${ctx}/exam/examCheck1/">问题整改</a></li>
<%--					<shiro:hasPermission name="exam:examJcInfo:view">--%>
						<li ><a href="${ctx}/exam/examAutoEvaluation/">推送管理</a></li>
<%--					</shiro:hasPermission>--%>
					<li><a href="${ctx}/exam/examJcInfo/">其他数据</a></li>
				</c:when>
				<c:otherwise>
					<li><a href="${ctx}/exam/examCheck?mType=1">检查情况</a></li>
					<shiro:hasPermission name="exam:examCheck:edit">
						<li ><a href="${ctx}/exam/examCheck/">检查情况管理</a></li>
					</shiro:hasPermission>
					<li class="active"><a href="${ctx}/exam/examCheck1/">问题整改</a></li>
					<shiro:hasPermission name="exam:examCheck:edit">
						<li ><a href="${ctx}/exam/examJcInfo/">推送管理</a></li>
					</shiro:hasPermission>
					<shiro:hasPermission name="exam:examJcInfo:view">
						<li ><a href="${ctx}/exam/examJcInfo/">推送管理</a></li>
					</shiro:hasPermission>
					<li><a href="${ctx}/exam/examOtherData/">其他数据</a></li>
				</c:otherwise>
			</c:choose>

	</ul>
	<form:form id="searchForm" modelAttribute="examCheck1" action="${ctx}/exam/examCheck1" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>检查时间：</label>
				<input name="beginCheckDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${examCheck1.beginCheckDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> -
				<input name="endCheckDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${examCheck1.endCheckDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>检查单位：</label>
				<sys:treeselect id="checkUnit" name="checkUnitId" value="${examCheck1.checkUnitId}" labelName="checkUnit" labelValue="${examCheck1.checkUnit}"
					title="部门" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<li><label>检查人：</label>
				<sys:treeselect id="checkPerson" name="checkPersonId" value="${examCheck1.checkPersonId}" labelName="checkPerson" labelValue="${examCheck1.checkPerson}"
					title="检查人" url="/sys/office/treeData?type=3" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<li><label>责任单位：</label>
				<sys:treeselect id="dutyUnit" name="dutyUnitId" value="${examCheck1.dutyUnitId}" labelName="dutyUnit" labelValue="${examCheck1.dutyUnit}"
					title="部门" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
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
			<shiro:hasPermission name="exam:examCheck1:edit">
				<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
				<%--				<li class="btns"><input  class="btn btn-primary"  type="button" value="报送" onclick="openForm('${ctx}/exam/examCheck1/report?id=${examCheck1.id}')"/></li>--%>
			</shiro:hasPermission>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/exam/examCheck1/manageList'"/></li>

			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th id="handleTh"><input style='margin-left:12px' type='checkbox'id='checkAll'onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>使用模板</th>
				<th>责任项</th>
				<th>检查时间</th>
				<th>检查单位</th>
				<th>检查人</th>
				<th>责任单位</th>
				<th>整改情况</th>
				<th>整改情况说明</th>
				<th>审核状态</th>
				<shiro:hasPermission name="exam:examCheck1:edit"><th id="handleTh">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="examCheck1" varStatus="status">
			<tr>
				<td>
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${examCheck1.id}"/>
				</td>
				<td>${(page.pageNo-1)*page.pageSize+status.index+1}</td>
				<td>
					${examCheck1.useModelName}
				</td>
				<td>
					${examCheck1.chooseOptions}
				</td>
				<td>
					<fmt:formatDate value="${examCheck1.checkDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td><a onclick="openDetailDialog('${examCheck1.id}')">
					${examCheck1.checkUnit}
				</a></td>
				<td>
					${examCheck1.checkPerson}
				</td>
				<td>
					${examCheck1.dutyUnit}
				</td>
				<td>
						${fns:getDictLabel(examCheck1.reviewType, 'exam_rectify', '')}
				</td>
				<td>
						${examCheck1.explan}
				</td>
				<td>
					<c:if test="${examCheck1.status != ''}"><%--2：已经提交过了--%>
						<c:choose>
							<c:when test="${examCheck1.status == '2'}">
								<a id="notPass" data-container="body" data-toggle="popover" data-placement="right"
								   data-content="${examCheck1.opinion}"  style="cursor: pointer;color: red">不通过</a>
							</c:when>
							<c:otherwise>
								${fns:getDictLabel(examCheck1.status, 'affair_query_shenhe', '')}
							</c:otherwise>
						</c:choose>
					</c:if>
				</td>
				<shiro:hasPermission name="exam:examCheck1:edit"><td class="handleTd">
						<a href="javascript:void(0)" onclick="openForm('${ctx}/exam/examCheck1/form?id=${examCheck1.id}')">报送</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>