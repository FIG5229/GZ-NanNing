<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>报名人员管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairApplyPersonnel/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairApplyPersonnel/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairApplyPersonnel/export?flag=true")
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairApplyPersonnel/");
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
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairApplyPersonnel", "导入",800,520,
						{itle:"导入数据", buttons:{"关闭":true},
							bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",
							closed:function (){self.location.href="${ctx}/affair/affairApplyPersonnel"}});
			});

		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }

		//添加，修改弹窗
		function openForm(url) {
			top.$.jBox.open("iframe:"+url, "报名管理",1200,600,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairApplyPersonnel"}
			});
		}

		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairApplyPersonnel/formDetail?id="+id;
			top.$.jBox.open(url, "",1000,400,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		};
		if("sucess"=="${saveResult}"){
			parent.$.jBox.close();
		}

	</script>
</head>
<body>
	<form:form id="searchForm" modelAttribute="affairApplyPersonnel" action="${ctx}/affair/affairApplyPersonnel" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="报名人员管理表.xlsx"/>

		<ul class="ul-form">
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>报名状态：</label>
				<form:input path="applyState" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>班级id：</label>
				<input name="classId" type="text" readonly maxlength="30" class="input-medium"
					   value="${affairApplyPersonnel.classId}"/>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
				<shiro:hasPermission name="affair:affairApplyPersonnel:edit">
					<li class="btns">
						<input id="btnImport" class="btn btn-primary" type="submit" value="导入"/>
					</li>
				</shiro:hasPermission>

			<li class="btns">
				<input id="export" class="btn btn-primary" type="button" value="导出"/>
			</li>
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
				<th>报名状态</th>
				<th>审批人</th>
				<th>审批意见</th>
				<th>手机号</th>
				<th>到达车次</th>
				<th>到达车站</th>
				<th>公免号</th>
<%--
				<shiro:hasPermission name="affair:affairApplyPersonnel:edit"><th>操作</th></shiro:hasPermission>
--%>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${AffairApplyPersonnel}" var="affairApplyPersonnel" varStatus="status">
			<tr>
				<td>
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairApplyPersonnel.id}"/>
				</td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
					${affairApplyPersonnel.name}
				</td>
				<td>
					${affairApplyPersonnel.unit}
				</td>
				<td>
					${affairApplyPersonnel.job}
				</td>
				<td>
					${affairApplyPersonnel.applyState}
				</td>
				<td>
					${affairApplyPersonnel.approver}
				</td>
				<td>
					${affairApplyPersonnel.approvalOpinion}
				</td>
				<td>
					${affairApplyPersonnel.phone}
				</td>
				<td>
					${affairApplyPersonnel.trip}
				</td>
				<td>
					${affairApplyPersonnel.station}
				</td>
				<td>
					${affairApplyPersonnel.adFree}
				</td>
				<%--<td>
					<a onclick="openDetailDialog('${affairApplyPersonnel.id}')">查看</a>
					<shiro:hasPermission name="affair:affairApplyPersonnel:edit">
						<a href="${ctx}/affair/affairApplyPersonnel/form?id=${affairApplyPersonnel.id}">修改</a>
						<a href="${ctx}/affair/affairApplyPersonnel/delete?id=${affairApplyPersonnel.id}" onclick="return confirmx('确认要删除该报名人员吗？', this.href)">删除</a>
					</shiro:hasPermission>
				</td>--%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>