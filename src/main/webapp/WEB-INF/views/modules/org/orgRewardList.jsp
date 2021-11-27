<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>单位奖励信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/org/orgReward/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/org/orgReward/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/org/orgReward/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/org/orgReward/");
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
			var url = "iframe:${ctx}/org/orgReward/form?id="+id;
			if (id == null || id == undefined){
				url = "iframe:${ctx}/org/orgReward/form?orgId="+orgId;
			}
			top.$.jBox.open(url, "单位奖励信息",900,500,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){
					self.location.href="${ctx}/org/orgReward/list?orgId="+orgId;
				}
			});
		};
		//详情弹窗
		/*function openDetailDialog(id) {
			var url = "iframe:${ctx}/org/orgReward/formDetail?id="+id;
			top.$.jBox.open(url, "单位奖励信息",800,700,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		};*/

		//查看历史记录
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/org/orgReward/formDetail?id="+id;
			top.$.jBox.open(url, "单位奖励信息",800,700,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		}


		//导入
		function excelImport(orgId) {
			if (orgId == null || orgId == undefined || orgId ==""){
				top.$.jBox.info("导入前请先确认机构基本信息已保存",'系统提示');
				return false;
			}
			top.$.jBox.open("iframe:${ctx}/org/upload/template/download/view?id=org_orgReward&orgId="+orgId, "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
				bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/org/orgReward?orgId="+orgId}});
		}
	</script>
</head>
<body>
	<%--<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/org/orgReward/">单位奖励信息列表</a></li>
		<shiro:hasPermission name="org:orgReward:edit"><li><a href="${ctx}/org/orgReward/form">单位奖励信息添加</a></li></shiro:hasPermission>
	</ul>--%>
	<form:form id="searchForm" modelAttribute="orgReward" action="${ctx}/org/orgReward/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="单位奖励信息集.xlsx"/>
		<%--机构id--%>
		<input id="orgId" name="orgId" type="hidden" value="${orgReward.orgId}"/>
		<ul class="ul-form">
			<%--<li><label>受奖单位代码：</label>
				<form:input path="winUnitCode" htmlEscape="false" class="input-medium"/>
			</li>--%>
			<li><label>奖励名称：</label>
				<form:input path="rewardName" htmlEscape="false" class="input-medium"/>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<%--<shiro:hasPermission name="org:orgReward:edit">
				<li class="btns"><input id="btn" class="btn btn-primary" type="button" onclick="openAddEditDialog(null,'${orgReward.orgId}')" value="添加"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/org/orgReward/deleteByIds','checkAll','myCheckBox')"/></li>
				<li class="btns"><input class="btn btn-primary" type="button" value="导入" onclick="excelImport('${orgReward.orgId}')"/></li>
			</shiro:hasPermission>--%>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/org/orgReward/'"/></li>
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
				<th>受奖单位名称</th>
				<th>受奖单位代码</th>
				<th>建制标志</th>
				<th>奖励名称代码</th>
				<th>荣誉称号级别</th>
				<th>单位警种</th>
				<th>奖励名称</th>
				<th>批准单位</th>
				<%--<shiro:hasPermission name="org:orgReward:edit"><th>操作</th></shiro:hasPermission>--%>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="orgReward" varStatus="status">
			<tr>
				<td>
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${orgReward.id}"/>
				</td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
					<%--<a onclick="openDetailDialog('${orgReward.id}')">--%>
						<a href="javascript:" onclick="openDetailDialog('${orgReward.id}')">
						${orgReward.winUnitName}
					</a>
				</td>
				<td>
					${orgReward.winUnitCode}
				</td>
				<td>
					${orgReward.sign}
				</td>
				<td>
					${orgReward.rewardNameCode}
				</td>
				<td>
					${orgReward.level}
				</td>
				<td>
					${orgReward.policeType}
				</td>
				<td>
					${orgReward.rewardName}
				</td>
				<td>
					${orgReward.approvalUnit}
				</td>
				<%--<shiro:hasPermission name="org:orgReward:edit"><td>
					&lt;%&ndash;<a onclick="openAddEditDialog('${orgReward.id}','${orgReward.orgId}')">修改</a>
					<a href="${ctx}/org/orgReward/delete?id=${orgReward.id}" onclick="return confirmx('确认要删除该单位奖励信息吗？', this.href)">删除</a>&ndash;%&gt;
				</td></shiro:hasPermission>--%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>