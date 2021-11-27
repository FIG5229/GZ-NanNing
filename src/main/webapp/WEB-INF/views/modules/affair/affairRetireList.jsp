<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>离退干部档案登记花名册管理</title>
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
								$("#searchForm").attr("action","${ctx}/affair/affairRetire/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairRetire/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairRetire/export?flag=true")
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairRetire/");
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
			$("#btnImport").click(function(){
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairRetire", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairRetire"}});
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		function openForm(url) {
			top.$.jBox.open("iframe:"+url, "离退干部档案登记花名册",800,520,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairRetire"}
			});
		}
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairRetire/formDetail?id="+id;
			top.$.jBox.open(url, "离退干部档案登记花名册",800,500,{
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
				},closed:function (){self.location.href="${ctx}/affair/affairRetire"}
			});
		}
	</script>
</head>
<body>
<form:form id="searchForm" modelAttribute="affairRetire" action="${ctx}/affair/affairRetire/" method="post" class="breadcrumb form-search">
	<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
	<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	<input id="fileName" name="fileName" type="hidden" value="干部档案台账.xlsx"/>
	<%--<ul class="ul-form">
		<li><label>原任级别：</label>
			<form:select path="level" class="input-medium">
				<form:option value="" label=""/>
				<form:options items="${fns:getDictList('affair_yrjb')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</form:select>
		</li>
		<li><label>档案号：</label>
			<form:input path="archiveNo" htmlEscape="false" class="input-medium"/>
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
&lt;%&ndash;		<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairRetire'"/></li>&ndash;%&gt;
		<li class="clearfix"></li>
	</ul>--%>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
	<thead>
	<tr>
		<th>序号</th>
		<th>姓名</th>
		<th>原任单位</th>
		<th>原任职务</th>
		<th>原任级别</th>
		<th>正本数量</th>
		<th>副本数量</th>
		<th>档案号</th>
		<th>离丶退休时间</th>
		<%--				<shiro:hasPermission name="affair:affairRetire:edit"><th id="handleTh">操作</th></shiro:hasPermission>--%>
	</tr>
	</thead>
	<tbody>
	<c:forEach items="${page.list}" var="affairRetire" varStatus="status">
		<tr>
			<td>${(page.pageNo-1)*page.pageSize+status.index+1}</td>
			<td>
					${affairRetire.name}
			</td>
			<td>
					${affairRetire.unit}
			</td>
			<td>
<%--					${fns:getDictLabel(affairRetire.job, 'affair_yrzw', '')}--%>
				${affairRetire.job}
			</td>
			<td>
<%--					${fns:getDictLabel(affairRetire.level, 'affair_yrjb', '')}--%>
				${affairRetire.level}
			</td>
			<td>
					${affairRetire.zNum}
			</td>
			<td>
					${affairRetire.fNum}
			</td>
			<td>
					${affairRetire.archiveNo}
			</td>
			<td>
				<fmt:formatDate value="${affairRetire.outDate}" pattern="yyyy-MM-dd"/>
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
		<li class="active"><a href="${ctx}/affair/affairRetire/">离退</a></li>
		<li ><a href="${ctx}/affair/affairDeath/">死亡</a></li>
&lt;%&ndash;		<li ><a href="${ctx}/affair/affairMaterial/">收集</a></li>&ndash;%&gt;
&lt;%&ndash;		<li ><a href="${ctx}/affair/affairTemHum/">温湿度</a></li>&ndash;%&gt;
&lt;%&ndash;		<li ><a href="${ctx}/affair/affairCheck/">核对</a></li>&ndash;%&gt;
&lt;%&ndash;		<li><a href="${ctx}/affair/affairSecurityCheck/">安全检查</a></li>&ndash;%&gt;
&lt;%&ndash;		<li ><a href="${ctx}/affair/affairDestroyMeterial/">销毁清册</a></li>&ndash;%&gt;
&lt;%&ndash;		<li ><a href="${ctx}/affair/affairCopy/">复印档案登记</a></li>&ndash;%&gt;
	&lt;%&ndash;	<shiro:hasPermission name="affair:affairRetire:edit"><li><a href="${ctx}/affair/affairRetire/form">离退干部档案登记花名册添加</a></li></shiro:hasPermission>&ndash;%&gt;
	</ul>
	<form:form id="searchForm" modelAttribute="affairRetire" action="${ctx}/affair/affairRetire/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="离退.xlsx"/>
		<ul class="ul-form">
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>身份证号：</label>
				<form:input path="idNumber" htmlEscape="false" class="input-medium"/>
			</li>
			&lt;%&ndash;<li><label>原任单位：</label>
				<sys:treeselect id="unit" name="unitId" value="${affairRetire.unitId}" labelName="unit" labelValue="${affairRetire.unit}"
					title="原任单位" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<li><label>原任职务：</label>
				<form:select path="job" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_yrzw')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>&ndash;%&gt;
			<li><label>原任级别：</label>
				<form:select path="level" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_yrjb')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>档案号：</label>
				<form:input path="archiveNo" htmlEscape="false" class="input-medium"/>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
		&lt;%&ndash;	<shiro:hasPermission name="affair:affairRetire:edit">
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/affair/affairRetire/form')"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairRetire/deleteByIds','checkAll','myCheckBox')"/></li>
				<li class="btns"><input  class="btn btn-primary" type="button" value="导入" /></li>
			</shiro:hasPermission>&ndash;%&gt;
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
&lt;%&ndash;			<li class="btns"><input  class="btn btn-primary" type="button" value="导出" onclick="openAccountExport('${ctx}/affair/affairLedgerInto/accountExport')"/></li>&ndash;%&gt;
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairRetire'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr><th><input style='margin-left:12px' type='checkbox'id='checkAll'onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>姓名</th>
				<th>原任单位</th>
				<th>原任职务</th>
				<th>原任级别</th>
				<th>正本数量</th>
				<th>副本数量</th>
				<th>档案号</th>
				<th>离丶退休时间</th>
&lt;%&ndash;				<shiro:hasPermission name="affair:affairRetire:edit"><th id="handleTh">操作</th></shiro:hasPermission>&ndash;%&gt;
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairRetire" varStatus="status">
			<tr>
				<td><input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairRetire.id}"/></td>
				<td>${(page.pageNo-1)*page.pageSize+status.index+1}</td>
				<td>&lt;%&ndash;<a onclick="openDetailDialog('${affairRetire.id}')">&ndash;%&gt;
				${affairRetire.name}
				</a></td>
				<td>
					${affairRetire.unit}
				</td>
				<td>
					${fns:getDictLabel(affairRetire.job, 'affair_yrzw', '')}
				</td>
				<td>
					${fns:getDictLabel(affairRetire.level, 'affair_yrjb', '')}
				</td>
				<td>
					${affairRetire.zNum}
				</td>
				<td>
					${affairRetire.fNum}
				</td>
				<td>
					${affairRetire.archiveNo}
				</td>
				<td>
					<fmt:formatDate value="${affairRetire.outDate}" pattern="yyyy-MM-dd"/>
				</td>
			&lt;%&ndash;	<shiro:hasPermission name="affair:affairRetire:edit"><td class="handleTd">
					<c:if test="${affairRetire.createBy.id == fns:getUser().id}">
						<a href="javascript:void(0)"
						   onclick="openForm('${ctx}/affair/affairRetire/form?id=${affairRetire.id}')">修改</a>
						<a href="${ctx}/affair/affairRetire/delete?id=${affairRetire.id}"
						   onclick="return confirmx('确认要删除该离退干部档案登记花名册吗？', this.href)">删除</a>
					</c:if>
				</td></shiro:hasPermission>&ndash;%&gt;
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
<font color="red">注：1.干部离、退休后，其干部档案从在职干部档案花名册中注销，在离、退休干部档案花名册中登记。<br>

	2.每个姓氏的后面留出适量的空格，以备填写转入人员  </font>
</body>
</html>--%>