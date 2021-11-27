<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>死亡干部档案登记花名册管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairArchiveRegister/export");
								$("#searchForm").submit();
							}
							if (v == 'part') {
								dataExport('contentTable');
							}
							if (v == 'cancel') {
								$.jBox.tip('已取消');
							}
							return true;
						};
						$.jBox.confirm("您是否要导出数据？", "数据导出确认", submit, { buttons: { '导出全部数据': 'all', '导出当前页面数据': 'part','取消':'cancel'} });
					}
			);
			/*$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairDeath/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairDeath/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairDeath/export?flag=true")
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairDeath/");
							}
							if (v == 'cancel') {
								$.jBox.tip('已取消');
							}
							return true;
						};
						$.jBox.confirm("您是否要导出数据？", "数据导出确认", submit, { buttons: { '导出全部数据': 'all', '导出当前页面数据': 'part','取消':'cancel'} });
					}
			);*/
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
		function openForm(url) {
			top.$.jBox.open("iframe:"+url, "死亡干部档案登记花名册",800,520,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairDeath"}
			});
		}
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairDeath/formDetail?id="+id;
			top.$.jBox.open(url, "死亡干部档案登记花名册",800,500,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		}
		//批量导出弹窗
		function openAccountExport(url) {
			top.$.jBox.open("iframe:"+url, "批量导出",800,520,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairDeath"}
			});
		}
	</script>
</head>
<body>
<form:form id="searchForm" modelAttribute="affairDeath" action="${ctx}/affair/affairDeath/" method="post" class="breadcrumb form-search">
	<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
	<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	<input id="fileName" name="fileName" type="hidden" value="干部档案台账.xlsx"/>
	<%--<ul class="ul-form">
		<li><label>档案号：</label>
			<form:input path="archiveNo" htmlEscape="false" class="input-medium"/>
		</li>
		<li><label>死亡时间：</label>
			<input name="beginOutDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
				   value="<fmt:formatDate value="${affairDeath.beginOutDate}" pattern="yyyy-MM-dd"/>"
				   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> -
			<input name="endOutDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
				   value="<fmt:formatDate value="${affairDeath.endOutDate}" pattern="yyyy-MM-dd"/>"
				   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
		</li>
		<li><label>档案管理单位：</label>
			<form:select path="fileUnit" class="input-medium">
				<form:option value="" label=""/>
				<form:options items="${fns:getDictList('personnel_daunit')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</form:select>
		</li>
	</ul>--%>
<%--	<ul class="ul-form2">
&lt;%&ndash;		<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>&ndash;%&gt;
		<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
			&lt;%&ndash;			<li class="btns"><input  class="btn btn-primary" type="button" value="导出" onclick="openAccountExport('${ctx}/affair/affairLedgerInto/accountExport')"/></li>&ndash;%&gt;
		<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
&lt;%&ndash;		<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairDeath'"/></li>&ndash;%&gt;
		<li class="clearfix"></li>
	</ul>--%>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
	<thead>
	<tr>
		<th>序号</th>
		<th>姓名</th>
		<th>单位</th>
		<th>职务</th>
		<th>级别</th>
		<th>正本数量</th>
		<th>副本数量</th>
		<th>档案号</th>
		<th>死亡时间</th>
		<th>备注</th>
	</tr>
	</thead>
	<tbody>
	<c:forEach items="${page.list}" var="affairDeath" varStatus="status">
		<tr>
			<td>${(page.pageNo-1)*page.pageSize+status.index+1}</td>
			<td>
					${affairDeath.name}
			</td>
			<td>
					${affairDeath.unit}
			</td>
			<td>
<%--					${fns:getDictLabel(affairDeath.job, 'affair_job', '')}--%>
					${affairDeath.job}
			</td>
			<td>
<%--					${fns:getDictLabel(affairDeath.level, 'affair_job_level', '')}--%>
				${affairDeath.level}
			</td>
			<td>
					${affairDeath.zNum}
			</td>
			<td>
					${affairDeath.fNum}
			</td>
			<td>
					${affairDeath.archiveNo}
			</td>
			<td>
				<fmt:formatDate value="${affairDeath.outDate}" pattern="yyyy-MM-dd"/>
			</td>
			<td>
					${affairDeath.remark}
			</td>
		</tr>
	</c:forEach>
	</tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>
	<%--<ul class="nav nav-tabs">
		<li  ><a href="${ctx}/affair/affairLedgerInto/">转入</a></li>
		<li ><a href="${ctx}/affair/affairLedgerOut/">转出</a></li>
&lt;%&ndash;		<li ><a href="${ctx}/affair/affairConsult/">查阅</a></li>&ndash;%&gt;
&lt;%&ndash;		<li ><a href="${ctx}/affair/affairBorrow/">借阅</a></li>&ndash;%&gt;
&lt;%&ndash;		<li ><a href="${ctx}/affair/affairArchiveApproval/">查(借)阅审批列表</a></li>&ndash;%&gt;
		<li ><a href="${ctx}/affair/affairArchiveRegister/">在职</a></li>
		<li ><a href="${ctx}/affair/affairRetire/">离退</a></li>
		<li class="active" ><a href="${ctx}/affair/affairDeath/">死亡</a></li>
&lt;%&ndash;		<li ><a href="${ctx}/affair/affairMaterial/">收集</a></li>&ndash;%&gt;
&lt;%&ndash;		<li ><a href="${ctx}/affair/affairTemHum/">温湿度</a></li>&ndash;%&gt;
&lt;%&ndash;		<li ><a href="${ctx}/affair/affairCheck/">核对</a></li>&ndash;%&gt;
&lt;%&ndash;		<li><a href="${ctx}/affair/affairSecurityCheck/">安全检查</a></li>&ndash;%&gt;
&lt;%&ndash;		<li ><a href="${ctx}/affair/affairDestroyMeterial/">销毁清册</a></li>&ndash;%&gt;
&lt;%&ndash;		<li ><a href="${ctx}/affair/affairCopy/">复印档案登记</a></li>&ndash;%&gt;
		&lt;%&ndash;<shiro:hasPermission name="affair:affairDeath:edit"><li><a href="${ctx}/affair/affairDeath/form">死亡干部档案登记花名册添加</a></li></shiro:hasPermission>&ndash;%&gt;
	</ul>
	<form:form id="searchForm" modelAttribute="affairDeath" action="${ctx}/affair/affairDeath/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="死亡.xlsx"/>
		<ul class="ul-form">
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>身份证号：</label>
				<form:input path="idNumber" htmlEscape="false" class="input-medium"/>
			</li>
			&lt;%&ndash;<li><label>单位：</label>
				<sys:treeselect id="unit" name="unitId" value="${affairDeath.unitId}" labelName="" labelValue="${affairDeath.unit	}"
					title="部门" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<li><label>职务：</label>
				<form:select path="job" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_job')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>级别：</label>
				<form:select path="level" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_job_level')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>&ndash;%&gt;
			<li><label>档案号：</label>
				<form:input path="archiveNo" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>死亡时间：</label>
				<input name="beginOutDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairDeath.beginOutDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> -
				<input name="endOutDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairDeath.endOutDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			&lt;%&ndash;<shiro:hasPermission name="affair:affairDeath:edit">
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/affair/affairDeath/form')"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairDeath/deleteByIds','checkAll','myCheckBox')"/></li>
				<li class="btns"><input  class="btn btn-primary" type="button" value="导入" /></li>
			</shiro:hasPermission>&ndash;%&gt;
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
&lt;%&ndash;			<li class="btns"><input  class="btn btn-primary" type="button" value="导出" onclick="openAccountExport('${ctx}/affair/affairLedgerInto/accountExport')"/></li>&ndash;%&gt;
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairDeath'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input style='margin-left:12px' type='checkbox'id='checkAll'onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>姓名</th>
				<th>身份证号</th>
				<th>单位</th>
				<th>职务</th>
				<th>级别</th>
				<th>正本数量</th>
				<th>副本数量</th>
				<th>档案号</th>
				<th>死亡时间</th>
				<th>备注</th>
&lt;%&ndash;				<shiro:hasPermission name="affair:affairDeath:edit"><th id="handleTh">操作</th></shiro:hasPermission>&ndash;%&gt;
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairDeath" varStatus="status">
			<tr>
				<td><input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairDeath.id}"/></td>
				<td>${(page.pageNo-1)*page.pageSize+status.index+1}</td>
				<td>&lt;%&ndash;<a onclick="openDetailDialog('${affairDeath.id}')">&ndash;%&gt;
					${affairDeath.name}
				</a></td>
				<td>${affairDeath.idNumber}</td>
				<td>
					${affairDeath.unit}
				</td>
				<td>
					${fns:getDictLabel(affairDeath.job, 'affair_job', '')}
				</td>
				<td>
					${fns:getDictLabel(affairDeath.level, 'affair_job_level', '')}
				</td>
				<td>
					${affairDeath.zNum}
				</td>
				<td>
					${affairDeath.fNum}
				</td>
				<td>
					${affairDeath.archiveNo}
				</td>
				<td>
					<fmt:formatDate value="${affairDeath.outDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${affairDeath.remark}
				</td>
				&lt;%&ndash;<shiro:hasPermission name="affair:affairDeath:edit"><td class="handleTd">
					<c:if test="${affairDeath.createBy.id == fns:getUser().id}">
						<a href="javascript:void(0)"
						   onclick="openForm('${ctx}/affair/affairDeath/form?id=${affairDeath.id}')">修改</a>
						<a href="${ctx}/affair/affairDeath/delete?id=${affairDeath.id}"
						   onclick="return confirmx('确认要删除该死亡干部档案登记花名册吗？', this.href)">删除</a>
					</c:if>
				</td></shiro:hasPermission>&ndash;%&gt;
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>--%>