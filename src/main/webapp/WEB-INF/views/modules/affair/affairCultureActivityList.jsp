<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>警营文化活动管理</title>
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
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		function openDialog(url) {
			top.$.jBox.open("iframe:"+url, "警营文化活动",800,520,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairCultureActivity"}
			});
		}
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairCultureActivity/formDetail?id="+id;
			top.$.jBox.open(url, "警营文化活动详情",800,500,{
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
		<li><a href="${ctx}/affair/affairWenyi/">文艺作品</a></li>
		<li class="active"><a href="${ctx}/affair/affairCultureActivity/">文化活动</a></li>
		<li><a href="${ctx}/affair/affairWenhua/">文化人才</a></li>
		<li><a href="${ctx}/affair/affairActivityMien/">活动风采</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="affairCultureActivity" action="${ctx}/affair/affairCultureActivity/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>活动名称：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>活动时间：</label>
				<input name="beginDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairCultureActivity.beginDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				--
				<input name="finishDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairCultureActivity.finishDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>活动地点：</label>
				<form:input path="place" htmlEscape="false" class="input-medium"/>
			</li>
				<%--11.6问题反馈 管理账号应该能选择全部单位，现在只能选择宣教部门账号（三个公安处的共同问题）kevin.jia--%>
			<c:choose>
				<c:when test="${ 'd30e324c8f73492d9b74103374fbc689' eq fns:getUser().id || 'e3ac8381fb3247e0b64fd6e3c48bddc1' eq fns:getUser().id || '66937439b2124f328d1521968fab06db' eq fns:getUser().id || 'd154234ecb35470e84fb95e53726866b' eq fns:getUser().id}">
					<li><label class="width140">组织单位/举办单位：</label>
						<sys:treeselect id="unit" name="unitId" value="${affairCultureActivity.unitId}" labelName="unit" labelValue="${affairCultureActivity.unit}"
										title="组织单位/举办单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="false" isAll="true"/>
					</li>
				</c:when>
				<c:otherwise>
					<li><label class="width140">组织单位/举办单位：</label>
						<sys:treeselect id="unit" name="unitId" value="${affairCultureActivity.unitId}" labelName="unit" labelValue="${affairCultureActivity.unit}"
										title="组织单位/举办单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="false"/>
					</li>
				</c:otherwise>
			</c:choose>
			<%--<li><label class="width140">组织单位/举办单位：</label>
				<sys:treeselect id="unit" name="unitId" value="${affairCultureActivity.unitId}" labelName="unitName" labelValue="${affairCultureActivity.unit}"
					title="组织单位/举办单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="false"/>

			</li>--%>
			<li><label>参加人员：</label>
				<form:input path="joinPerson" htmlEscape="false" class="input-medium"/>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairCultureActivity:edit">
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openDialog('${ctx}/affair/affairCultureActivity/form?id=${affairCultureActivity.id}')"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairCultureActivity/deleteByIds','checkAll','myCheckBox')"/></li>
			</shiro:hasPermission>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairCultureActivity'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input style='margin-left:12px' type='checkbox' id='checkAll' onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>活动名称</th>
				<th>组织单位/举办单位</th>
				<th>活动时间</th>
				<th>活动地点</th>
				<shiro:hasPermission name="affair:affairCultureActivity:edit"><th id="handleTh">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairCultureActivity" varStatus="status">
			<tr>
				<td>
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairCultureActivity.id}"/>
				</td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
					${affairCultureActivity.name}
				</td>
				<td>
					${affairCultureActivity.unit}
				</td>
				<td>
					<fmt:formatDate value="${affairCultureActivity.startDate}" pattern="yyyy-MM-dd HH:mm"/> 至 <fmt:formatDate value="${affairCultureActivity.endDate}" pattern="yyyy-MM-dd HH:mm"/>
				</td>
				<td>
					${affairCultureActivity.place}
				</td>
				<td class="handleTd">
					<a onclick="openDetailDialog('${affairCultureActivity.id}')">查看</a>
				<shiro:hasPermission name="affair:affairCultureActivity:edit">
						<a href="javascript:void(0)" onclick="openDialog('${ctx}/affair/affairCultureActivity/form?id=${affairCultureActivity.id}')">修改</a>
						<a href="${ctx}/affair/affairCultureActivity/delete?id=${affairCultureActivity.id}" onclick="return confirmx('确认要删除该警营文化活动吗？', this.href)">删除</a>
			</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>