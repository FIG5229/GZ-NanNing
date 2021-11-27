<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>健康体检管理</title>
	<meta name="decorator" content="default"/>
    <script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairHealthCheckup/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairHealthCheckup/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairHealthCheckup/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairHealthCheckup/");
							}
							if (v == 'cancel') {
								$.jBox.tip('已取消');
							}
							return true;
						};
						$.jBox.confirm("您是否要导出数据？", "数据导出确认", submit, { buttons: { '导出全部数据': 'all', '导出当前页面数据': 'part','取消':'cancel'} });
					}
			);

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
			$("#btnImport").click(function(){
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairHealthCheckup", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairHealthCheckup"}});
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
			return false;
		}
		function openDialog(url) {
			top.$.jBox.open("iframe:"+url, "健康体检",800,670,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairHealthCheckup"}
			});
		}
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairHealthCheckup/formDetail?id="+id;
			top.$.jBox.open(url, "健康体检",800,500,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		}
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/affair/affairHealthCheckup/">健康体检</a></li>
<%--
		<shiro:hasPermission name="affair:affairHealthCheckup:edit"><li><a href="${ctx}/affair/affairHealthCheckup/form">健康体检添加</a></li></shiro:hasPermission>
--%>
	</ul>
	<form:form id="searchForm" modelAttribute="affairHealthCheckup" action="${ctx}/affair/affairHealthCheckup/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="健康体检表.xlsx"/>
		<ul class="ul-form">
			<li><label>单位：</label>
				<sys:treeselect id="unit" name="unitId" value="${affairHealthCheckup.unitId}" labelName="unit" labelValue="${affairHealthCheckup.unit}"
								title="单位" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>体检时间：</label>
				<input name="beginTjDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairHealthCheckup.beginTjDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> -
				<input name="endTjDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairHealthCheckup.endTjDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>患病史：</label>
				<form:select path="type" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_health_checkup_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairHealthCheckup:edit">
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openDialog('${ctx}/affair/affairHealthCheckup/form?id=${affairHealthCheckup.id}')"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairHealthCheckup/deleteByIds','checkAll','myCheckBox')"/></li>
				<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
				<%--<li class="btns"><input id="" class="btn btn-primary" type="button" value="发布"/></li>--%>
			</shiro:hasPermission>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairHealthCheckup'"/></li>

			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input style='margin-left:12px' type='checkbox' id='checkAll' onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>单位</th>
				<th>姓名</th>
				<th>体检时间</th>
				<th>性别</th>
				<th>年龄</th>
				<th>患病史</th>
				<th>体检情况</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairHealthCheckup" varStatus="status">
			<tr>
				<td>
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairHealthCheckup.id}"/>
				</td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
					${affairHealthCheckup.unit}
				</td>
				<td>
					${affairHealthCheckup.name}
				</td>
				<td>
					<fmt:formatDate value="${affairHealthCheckup.tjDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${fns:getDictLabel(affairHealthCheckup.sex, 'sex', '')}
				</td>
				<td>
					${affairHealthCheckup.age}
				</td>
				<td>
					<c:forEach items="${affairHealthCheckup.type.split(',')}" var="arr" varStatus="status">
						<c:choose>
							<c:when test="${status.index==0}">${fns:getDictLabel(arr, 'affair_health_checkup_type', '')}</c:when>
							<c:otherwise>
								,${fns:getDictLabel(arr, 'affair_health_checkup_type', '')}
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</td>
				<td>
					${affairHealthCheckup.tjQingkuang}
				</td>
				<td>
					<a href="javascript:void(0)" onclick="openDetailDialog('${affairHealthCheckup.id}')">查看</a>
					<shiro:hasPermission name="affair:affairHealthCheckup:edit">
<%--						<c:if test="${affairHealthCheckup.createBy.id == fns:getUser().id}">--%>
						<a href="javascript:void(0)" onclick="openDialog('${ctx}/affair/affairHealthCheckup/form?id=${affairHealthCheckup.id}')">修改</a>
						<a href="${ctx}/affair/affairHealthCheckup/delete?id=${affairHealthCheckup.id}" onclick="return confirmx('确认要删除该内容吗？', this.href)">删除</a>
<%--						</c:if>--%>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>