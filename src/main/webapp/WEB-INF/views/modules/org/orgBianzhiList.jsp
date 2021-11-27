<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>单位编制管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#print").click(function(){
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
					formValues: false
				});
			});
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/org/orgBianzhi/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/org/orgBianzhi/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/org/orgBianzhi/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/org/orgBianzhi/");
							}
							if (v == 'cancel') {
								$.jBox.tip('已取消');
							}
							return true;
						};
						$.jBox.confirm("您是否要导出数据？", "数据导出确认", submit, { buttons: { '导出全部数据': 'all', '导出当前页面数据': 'part','取消':'cancel'} });
					}
			);
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        };
		//添加修改弹窗
		function openAddEditDialog(id,orgId) {
			if (orgId == null || orgId == undefined || orgId ==""){
				top.$.jBox.info("添加前请先确认机构基本信息已保存",'系统提示');
				return false;
			}
			var url = "iframe:${ctx}/org/orgBianzhi/form?id="+id;
			if (id == null || id == undefined){
				url = "iframe:${ctx}/org/orgBianzhi/form?orgId="+orgId;
			}
			top.$.jBox.open(url, "单位编制信息",900,500,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){
					self.location.href="${ctx}/org/orgBianzhi/list?orgId="+orgId;
				}
			});
		};
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/org/orgBianzhi/formDetail?id="+id;
			top.$.jBox.open(url, "单位编制信息",700,600,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		};
		if("success"=="${saveResult}"){
			parent.$.jBox.close();
		}
		//导入
		function excelImport(orgId) {
			if (orgId == null || orgId == undefined || orgId ==""){
				top.$.jBox.info("导入前请先确认机构基本信息已保存",'系统提示');
				return false;
			}
			top.$.jBox.open("iframe:${ctx}/org/upload/template/download/view?id=org_orgBianzhi&orgId="+orgId, "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
				bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/org/orgBianzhi?orgId="+orgId}});
		}
	</script>
</head>
<body>
	<%--<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/org/orgBianzhi/">单位编制列表</a></li>
		<shiro:hasPermission name="org:orgBianzhi:edit"><li><a href="${ctx}/org/orgBianzhi/form">单位编制添加</a></li></shiro:hasPermission>
	</ul>--%>
	<form:form id="searchForm" modelAttribute="orgBianzhi" action="${ctx}/org/orgBianzhi/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="单位编制信息集.xlsx"/>
		<%--机构id--%>
		<input id="orgId" name="orgId" type="hidden" value="${orgBianzhi.orgId}"/>
		<ul class="ul-form">
			<li><label>批准时间：</label>
				<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${orgBianzhi.startDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				 --
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${orgBianzhi.endDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="org:orgBianzhi:edit">
				<li class="btns"><input id="btn" class="btn btn-primary" type="button" onclick="openAddEditDialog(null,'${orgBianzhi.orgId}')" value="添加"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/org/orgBianzhi/deleteByIds','checkAll','myCheckBox')"/></li>
				<li class="btns"><input class="btn btn-primary" type="button" value="导入" onclick="excelImport('${orgBianzhi.orgId}')"/></li>
			</shiro:hasPermission>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/org/orgBianzhi/'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>
					<input style='margin-left:12px' type='checkbox'id='checkAll'onclick='chooseAll(this,"myCheckBox")'/>
				</th>
				<th>序号</th>
				<th>批准时间</th>
				<th>批准单位</th>
				<th>批准文号</th>
				<th>政法专项编</th>
				<th>参公事业编</th>
				<th>事业编</th>
				<th>其他编制数</th>
				<shiro:hasPermission name="org:orgBianzhi:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="orgBianzhi" varStatus="status">
			<tr>
				<td>
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairActivist.id}"/>
				</td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
					<a onclick="openDetailDialog('${orgBianzhi.id}')">
						<fmt:formatDate value="${orgBianzhi.date}" pattern="yyyy-MM-dd"/>
					</a>
				</td>
				<td>
					${orgBianzhi.unit}
				</td>
				<td>
					${orgBianzhi.fileNo}
				</td>
				<td>
					${orgBianzhi.zfzxb}
				</td>
				<td>
					${orgBianzhi.cgsyb}
				</td>
				<td>
					${orgBianzhi.syb}
				</td>
				<td>
					${orgBianzhi.otherNum}
				</td>
				<shiro:hasPermission name="org:orgBianzhi:edit"><td>
    				<a onclick="openAddEditDialog('${orgBianzhi.id}','${orgBianzhi.orgId}')">修改</a>
					<a href="${ctx}/org/orgBianzhi/delete?id=${orgBianzhi.id}" onclick="return confirmx('确认要删除该单位编制吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>