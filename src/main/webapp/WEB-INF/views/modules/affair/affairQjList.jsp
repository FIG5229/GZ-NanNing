<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>请假信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("[data-toggle='popover']").popover();
			$("#btnImport").click(function(){
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairQj", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairQj"}});
			});
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairQj/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairQj/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairQj/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairQj/");
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
			/*$("#pageNo").val(n);
			$("#pageSize").val(s);*/
			$("#searchForm").submit();
        	return false;
        }
		function openForm(url) {
			top.$.jBox.open("iframe:"+url, "请假申请单",800,520,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairQj"}
			});
		}
		/*//销假单页面弹窗
		function openFormCancel(url) {
			top.$.jBox.open("iframe:"+url, "销假单",800,520,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairQj"}
			});
		}
		//审核居处弹窗
		function openDialog(myCheckBox) {
			var id = getIds(myCheckBox);
			if (id.length == 1) {
			top.$.jBox.open("iframe:${ctx}/affair/affairQj/leaderShenHeDialog?id="+id, "请假信息审核(局处)",800,420,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){
					self.location.href="${ctx}/affair/affairQj/";
				}
			});
			}else {
				$.jBox.tip('请先选择要审核的内容且只能单条审核');
			}
		}
		//审核部门弹窗
		function openDialog1(myCheckBox) {
			var id = getIds(myCheckBox);
			if (id.length == 1) {
				top.$.jBox.open("iframe:${ctx}/affair/affairQj/depShenHeDialog?id="+id, "请假信息审核(部门)",800,420,{
					buttons:{"关闭":true},
					loaded:function(){
						$(".jbox-content", top.document).css("overflow-y","hidden");
					},closed:function (){
						self.location.href="${ctx}/affair/affairQj/";
					}
				});
			}else {
				$.jBox.tip('请先选择要审核的内容且只能单条审核');
			}
		}
		//审核人事弹窗
		function openDialog2(myCheckBox) {
			var id = getIds(myCheckBox);
			if (id.length == 1) {
				top.$.jBox.open("iframe:${ctx}/affair/affairQj/hrShenHeDialog?id="+id, "请假信息审核(人事)",800,420,{
					buttons:{"关闭":true},
					loaded:function(){
						$(".jbox-content", top.document).css("overflow-y","hidden");
					},closed:function (){
						self.location.href="${ctx}/affair/affairQj/";
					}
				});
			}else {
				$.jBox.tip('请先选择要审核的内容且只能单条审核');
			}
		}*/

		$('#notPass').popover();
	</script>
</head>
<body>
<%--<c:choose>
	<c:when test="${mType eq '1'}">
		<ul class="nav nav-tabs">
			<li ><a href="${ctx}/affair/affairQj">请假信息列表</a></li>
			<shiro:hasPermission name="affair:affairQj:hrManage"><li class="active"><a href="${ctx}/affair/affairQj?mType=1">人事审核</a></li></shiro:hasPermission>
			<shiro:hasPermission name="affair:affairQj:depManage"><li ><a href="${ctx}/affair/affairQj?mType=2">部门审核</a></li></shiro:hasPermission>
			<shiro:hasPermission name="affair:affairQj:officeManage"><li ><a href="${ctx}/affair/affairQj?mType=3">局处审核</a></li></shiro:hasPermission>
			<li ><a href="${ctx}/affair/affairQjSum/statistic">请假信息统计</a></li>
		</ul>
		<form:form id="searchForm" modelAttribute="affairQj" action="${ctx}/affair/affairQj/" method="post" class="breadcrumb form-search">
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
			<input id="mType" name="mType" type="hidden" value="${mType}"/>
			<ul class="ul-form">
				<li><label>姓名：</label>
					<form:input path="name" htmlEscape="false" class="input-medium"/>
				</li>
				<li><label>部门：</label>
					<sys:treeselect id="department" name="departmentId" value="${affairQj.departmentId}" labelName="department" labelValue="${affairQj.department}"
									title="部门" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
				</li>
				<li><label>休假种类：</label>
					<form:select path="type" class="input-medium">
						<form:option value="" label=""/>
						<form:options items="${fns:getDictList('personnel_xjtype')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
				</li>
			</ul>
			<ul class="ul-form2">
				<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
				<shiro:hasPermission name="affair:affairQj:hrManage">
					<li class="btns"><input  class="btn btn-primary" type="button" value="人事审核" onclick="openDialog2('myCheckBox')"/></li>
				</shiro:hasPermission>
				<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairQj?mType=1'"/></li>
				<li class="clearfix"></li>
			</ul>
		</form:form>
	</c:when>
	<c:when test="${mType eq '2'}">
		<ul class="nav nav-tabs">
			<li ><a href="${ctx}/affair/affairQj">请假信息列表</a></li>
			<shiro:hasPermission name="affair:affairQj:hrManage"><li ><a href="${ctx}/affair/affairQj?mType=1">人事审核</a></li></shiro:hasPermission>
			<shiro:hasPermission name="affair:affairQj:depManage"><li class="active"><a href="${ctx}/affair/affairQj?mType=2">部门审核</a></li></shiro:hasPermission>
			<shiro:hasPermission name="affair:affairQj:officeManage"><li ><a href="${ctx}/affair/affairQj?mType=3">局处审核</a></li></shiro:hasPermission>
			<li ><a href="${ctx}/affair/affairQjSum/statistic">请假信息统计</a></li>
		</ul>
		<form:form id="searchForm" modelAttribute="affairQj" action="${ctx}/affair/affairQj/" method="post" class="breadcrumb form-search">
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
			<input id="mType" name="mType" type="hidden" value="${mType}"/>
			<ul class="ul-form">
				<li><label>姓名：</label>
					<form:input path="name" htmlEscape="false" class="input-medium"/>
				</li>
				<li><label>部门：</label>
					<sys:treeselect id="department" name="departmentId" value="${affairQj.departmentId}" labelName="department" labelValue="${affairQj.department}"
									title="部门" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
				</li>
				<li><label>休假种类：</label>
					<form:select path="type" class="input-medium">
						<form:option value="" label=""/>
						<form:options items="${fns:getDictList('personnel_xjtype')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
				</li>
			</ul>
			<ul class="ul-form2">
				<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
				<shiro:hasPermission name="affair:affairQj:officeManage">
					<li class="btns"><input  class="btn btn-primary" type="button" value="部门审核" onclick="openDialog1('myCheckBox')"/></li>
				</shiro:hasPermission>
				<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairQj?mType=2'"/></li>
				<li class="clearfix"></li>
			</ul>
		</form:form>
	</c:when>
	<c:when test="${mType eq '3'}">
		<ul class="nav nav-tabs">
			<li ><a href="${ctx}/affair/affairQj">请假信息列表</a></li>
			<shiro:hasPermission name="affair:affairQj:hrManage"><li ><a href="${ctx}/affair/affairQj?mType=1">人事审核</a></li></shiro:hasPermission>
			<shiro:hasPermission name="affair:affairQj:depManage"><li ><a href="${ctx}/affair/affairQj?mType=2">部门审核</a></li></shiro:hasPermission>
			<shiro:hasPermission name="affair:affairQj:officeManage"><li class="active"><a href="${ctx}/affair/affairQj?mType=3">局处审核</a></li></shiro:hasPermission>
			<li ><a href="${ctx}/affair/affairQjSum/statistic">请假信息统计</a></li>
		</ul>
		<form:form id="searchForm" modelAttribute="affairQj" action="${ctx}/affair/affairQj/" method="post" class="breadcrumb form-search">
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
			<input id="mType" name="mType" type="hidden" value="${mType}"/>
			<ul class="ul-form">
				<li><label>姓名：</label>
					<form:input path="name" htmlEscape="false" class="input-medium"/>
				</li>
				<li><label>部门：</label>
					<sys:treeselect id="department" name="departmentId" value="${affairQj.departmentId}" labelName="department" labelValue="${affairQj.department}"
									title="部门" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
				</li>
				<li><label>休假种类：</label>
					<form:select path="type" class="input-medium">
						<form:option value="" label=""/>
						<form:options items="${fns:getDictList('personnel_xjtype')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
				</li>
			</ul>
			<ul class="ul-form2">
				<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
				<shiro:hasPermission name="affair:affairQj:depManage">
					<li class="btns"><input  class="btn btn-primary" type="button" value="局处领导审核" onclick="openDialog('myCheckBox')"/></li>
				</shiro:hasPermission>
				<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairQj?mType=3'"/></li>
				<li class="clearfix"></li>
			</ul>
		</form:form>
	</c:when>--%>
<%--	<c:otherwise>--%>
		<ul class="nav nav-tabs">
			<li class="active"><a href="${ctx}/affair/affairQj">请假信息</a></li>
		<%--	<shiro:hasPermission name="affair:affairQj:hrManage"><li ><a href="${ctx}/affair/affairQj?mType=1">人事审核</a></li></shiro:hasPermission>
			<shiro:hasPermission name="affair:affairQj:depManage"><li ><a href="${ctx}/affair/affairQj?mType=2">部门审核</a></li></shiro:hasPermission>
			<shiro:hasPermission name="affair:affairQj:officeManage"><li ><a href="${ctx}/affair/affairQj?mType=3">局处审核</a></li></shiro:hasPermission>
	--%>
			<li><a href="${ctx}/affair/affairQjCount/count">请假信息汇总</a></li>
			<li ><a href="${ctx}/affair/affairQjSum/statistic">请假信息统计</a></li>
		</ul>
		<form:form id="searchForm" modelAttribute="affairQj" action="${ctx}/affair/affairQj/" method="post" class="breadcrumb form-search">
			<%--<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>--%>
			<input id="fileName" name="fileName" type="hidden" value="请销假记录表.xlsx"/>
			<ul class="ul-form">
				<li><label>年份：</label>
					<form:input path="year" htmlEscape="false" class="input-medium"/>
				</li>
				<li><label>月份：</label>
					<form:input path="month" htmlEscape="false" class="input-medium"/>
				</li>
				<li><label>姓名：</label>
					<form:input path="name" htmlEscape="false" class="input-medium"/>
				</li>
				<li><label>部门：</label>
					<sys:treeselect id="unit" name="unitId" value="${affairQj.unitId}" labelName="unit" labelValue="${affairQj.unit}"
									title="部门" url="/affair/affairLaborOffice/treeData" cssClass="input-small" allowClear="true" notAllowSelectParent="false"/>
				</li>
				<li><label>休假种类：</label>
					<form:select path="type" class="input-medium">
						<form:option value="" label=""/>
						<form:options items="${fns:getDictList('personnel_xjtype')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
				</li>
				<li><label>请假时间：</label>
					<input name="startQjDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
						   value="<fmt:formatDate value="${affairQj.startQjDate}" pattern="yyyy-MM-dd"/>"
						   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
					至
					<input name="endQjDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
						   value="<fmt:formatDate value="${affairQj.endQjDate}" pattern="yyyy-MM-dd"/>"
						   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				</li>
			</ul>
			<ul class="ul-form2">
				<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
				<shiro:hasPermission name="affair:affairQj:edit">

					<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/affair/affairQj/form')"/></li>
					<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairQj/deleteByIds','checkAll','myCheckBox')"/></li>
					<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
				</shiro:hasPermission>
				<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
					<%--	<shiro:hasPermission name="affair:affairQj:hrManage">
                            <li class="btns"><input  class="btn btn-primary" type="button" value="人事审核" onclick="openDialog2('myCheckBox')"/></li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="affair:affairQj:officeManage">
                            <li class="btns"><input  class="btn btn-primary" type="button" value="部门审核" onclick="openDialog1('myCheckBox')"/></li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="affair:affairQj:depManage">
                            <li class="btns"><input  class="btn btn-primary" type="button" value="局处领导审核" onclick="openDialog('myCheckBox')"/></li>
                        </shiro:hasPermission>
                --%>
				<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairQj'"/></li>
				<li class="clearfix"></li>
			</ul>
		</form:form>
<%--	</c:otherwise>
</c:choose>--%>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input style='margin-left:12px' type='checkbox'id='checkAll'onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th style="text-align: center">序号</th>
				<th style="text-align: center">月份</th>
				<th style="text-align: center">单位</th>
				<th style="text-align: center">姓名</th>
				<%--<th>部门</th>--%>
				<th style="text-align: center">假别</th>
				<th style="text-align: center">休假开始时间</th>
				<th style="text-align: center">休假结束时间</th>
<%--				<th>累计病事假达5个月</th>--%>
				<th style="text-align: center">请假天数（除去法定节假日时间）</th>
				<%--<th>审核状态</th>--%>
				<%--<c:if test="${mType==null}">--%>
					<shiro:hasPermission name="affair:affairQj:edit"><th style="text-align: center">操作</th></shiro:hasPermission>
				<%--</c:if>--%>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairQj" varStatus="status">
			<tr><td><input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairQj.id}"/></td>
				<td style="text-align: center">${(page.pageNo-1)*page.pageSize+status.index+1}</td>
				<td style="text-align: center">
						${affairQj.month}
				</td>
				<td style="text-align: center">
						${affairQj.unit}
				</td>
				<td style="text-align: center">
					${affairQj.name}
				</td>
				<%--<td>
					${affairQj.department}
				</td>--%>
				<td style="text-align: center">
						${fns:getDictLabel(affairQj.type, 'personnel_xjtype', '')}
				</td>
				<td style="text-align: center">
					<fmt:formatDate value="${affairQj.startTime}" pattern="yyyy-MM-dd "/>
				</td>
				<td style="text-align: center">
					<fmt:formatDate value="${affairQj.endTime}" pattern="yyyy-MM-dd "/>
				</td>
			<%--	<td>
						${affairQj.ljLeave}
				</td>--%>
				<td style="text-align: center">
					${affairQj.qjDay}
				</td>
				<%--<td>
					<c:choose>
						<c:when test="${affairQj.status == '9'}">
							<a id="notPass" data-container="body" data-toggle="popover" data-placement="right"
							   data-content="${affairQj.leaderOpinion}"  style="cursor: pointer;color: red">局处领导审核未通过</a>
						</c:when>
						<c:when test="${affairQj.status == '6'}">
							<a id="notPass" data-container="body" data-toggle="popover" data-placement="right"
							   data-content="${affairQj.depOpinion}"  style="cursor: pointer;color: red">部门审核未通过</a>
						</c:when>
						<c:when test="${affairQj.status == '3'}">
							<a id="notPass" data-container="body" data-toggle="popover" data-placement="right"
							   data-content="${affairQj.hrOpinion}"  style="cursor: pointer;color: red">人事审核未通过</a>
						</c:when>
						<c:otherwise>
							${fns:getDictLabel(affairQj.status, 'qj_review_status', '')}
						</c:otherwise>
					</c:choose>
				</td>--%>
				<%--<c:if test="${mType==null}">--%>
					<shiro:hasPermission name="affair:affairQj:edit"><td>
						<c:if test="${affairQj.createBy.id == fns:getUser().id}">
							<a href="javascript:void(0)"
							   onclick="openForm('${ctx}/affair/affairQj/form?id=${affairQj.id}')">修改</a>
							<%--					<a href="javascript:void(0)" onclick="openFormCancel('${ctx}/affair/affairQj/formCancel?id=${affairQj.id}')">销假</a>
                                --%>                <a href="${ctx}/affair/affairQj/delete?id=${affairQj.id}"
								   onclick="return confirmx('确认要删除该请假信息吗？', this.href)">删除</a>
						</c:if>
					</td></shiro:hasPermission>
				<%--</c:if>--%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<%--<div class="pagination">${page}</div>--%>
</body>
</html>