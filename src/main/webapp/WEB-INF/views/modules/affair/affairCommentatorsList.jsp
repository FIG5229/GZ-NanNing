<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>网评员管理管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairCommentators/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairCommentators/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairCommentators/export?flag=true")
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairCommentators/");
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
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairCommentators", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairCommentators"}});
			});

		});

		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairCommentators/formDetail?id="+id;
			top.$.jBox.open(url, "网评员管理详情",1000,400,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		};


		//添加，修改弹窗
		function openDialog(url) {
			top.$.jBox.open("iframe:"+url, "网评员管理",1200,600,{
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
		<shiro:hasPermission name="affair:affairCorrespondent:view">
			<li>
				<a href="${ctx}/affair/affairCorrespondent/">通讯员管理</a>
			</li>
		</shiro:hasPermission>
		<shiro:hasPermission name="affair:affairCommentators:view">
			<li class="active">
				<a href="${ctx}/affair/affairCommentators/">网评员管理</a>
			</li>
		</shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="affairCommentators" action="${ctx}/affair/affairCommentators/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="6.4网评员.xlsx"/>

		<ul class="ul-form">
			<li>
				<label>姓名：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
				<%--11.6问题反馈 管理账号应该能选择全部单位，现在只能选择宣教部门账号（三个公安处的共同问题）kevin.jia--%>
			<c:choose>
				<c:when test="${ 'd30e324c8f73492d9b74103374fbc689' eq fns:getUser().id || 'e3ac8381fb3247e0b64fd6e3c48bddc1' eq fns:getUser().id || '66937439b2124f328d1521968fab06db' eq fns:getUser().id || 'd154234ecb35470e84fb95e53726866b' eq fns:getUser().id}">
					<li>
						<label>单位：</label>
						<sys:treeselect id="unit" name="unitId" value="${affairCommentators.unitId}" labelName="unit" labelValue="${affairCommentators.unit}"
										title="单位" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="false" isAll="true"/>
					</li>
				</c:when>
				<c:otherwise>
					<li>
						<label>单位：</label>
						<sys:treeselect id="unit" name="unitId" value="${affairCommentators.unitId}" labelName="unit" labelValue="${affairCommentators.unit}"
										title="单位" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="false"/>
					</li>
				</c:otherwise>
			</c:choose>
			<%--<li>
				<label>单位：</label>
				<sys:treeselect id="unit" name="unitId" value="${affairCommentators.unitId}" labelName="unit" labelValue="${affairCommentators.unit}"
								title="单位" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="false"/>
			</li>--%>
			<li>
				<label>职务：</label>
				<form:input path="duty" htmlEscape="false" class="input-medium"/>
			</li>
			<li>
				<label>网评员账号：</label>
				<form:input path="account" htmlEscape="false" class="input-medium"/>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
					<shiro:hasPermission name="affair:affairCommentators:edit">
						<li class="btns">
						<li class="btns">
							<input  class="btn btn-primary"  type="button" value="添加" onclick="openDialog('${ctx}/affair/affairCommentators/form?id=${affairCommentators.id}')"/>
						</li>
					</shiro:hasPermission>
					<shiro:hasPermission name="affair:affairCommentators:edit">
						<li class="btns">
							<input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairCommentators/deleteByIds','checkAll','myCheckBox')"/>
						</li>
					</shiro:hasPermission>
					<shiro:hasPermission name="affair:affairCommentators:edit">
						<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
					</shiro:hasPermission>

			<li class="btns">
				<input id="export" class="btn btn-primary" type="button" value="导出"/>
			</li>
			<li class="btns">
				<input id="print" class="btn btn-primary" type="button" value="打印"/>
			</li>
			<li class="btns">
				<input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairCommentators'"/>
			</li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input style='margin-left:12px' type='checkbox' id='checkAll' onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>姓名</th>
				<th>单位</th>
				<th>职务</th>
<%--
				<th>网评员账号</th>
--%>
				<shiro:hasPermission name="affair:affairCommentators:edit">
					<th class="handleTd">操作</th>
				</shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairCommentators" varStatus="status">
			<tr>
				<td>
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairCommentators.id}"/>
				</td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
					${affairCommentators.name}
				</td>
				<td>
						${affairCommentators.unit}
				</td>
				<td>
						${affairCommentators.duty}
				</td>
				<%--<td>
						${affairCommentators.account}
				</td>--%>
				<td class="handleTd">
					<shiro:hasPermission name="affair:affairCommentators:edit">
						<a href="javascript:void(0)" onclick="openDetailDialog('${affairCommentators.id}')">查看</a>
						<a href="javascript:void(0)" onclick="openDialog('${ctx}/affair/affairCommentators/form?id=${affairCommentators.id}')">修改</a>
						<a href="${ctx}/affair/affairCommentators/delete?id=${affairCommentators.id}" onclick="return confirmx('确认要删除该网评员管理吗？', this.href)">删除</a>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>