<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>领导廉政干部档案管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {

			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairLdgblzFile/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairLdgblzFile/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairLdgblzFile/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairLdgblzFile/");
							}
							if (v == 'cancel') {
								$.jBox.tip('已取消');
							}
							return true;
						};
						$.jBox.confirm("您是否要导出数据？", "数据导出确认", submit, { buttons: { '导出全部数据': 'all', '导出当前页面数据': 'part','取消':'cancel'} });
					}
			);
			//打印功能
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
			//导入功能
			$("#btnImport").click(function(){
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairLdgblzFile", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairLdgblzFile"}});
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		//添加修改弹窗
		function openAddEditDialog(id) {
			var url = "iframe:${ctx}/affair/affairLdgblzFile/form?id="+id;
			if (id == null || id == undefined){
				url = "iframe:${ctx}/affair/affairLdgblzFile/form";
			}
			top.$.jBox.open(url, "领导干部廉政档案",1000,570,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){
					self.location.href="${ctx}/affair/affairLdgblzFile/";
				}
			});
		}
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairLdgblzFile/formDetail?id="+id;
			top.$.jBox.open(url, "领导干部廉政档案",1500,600,{
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
		<li ><a href="${ctx}/affair/affairRqlzIntegrity/">廉政鉴定</a></li>
		<li ><a href="${ctx}/affair/affairLianzhengSupervise/">廉政监督</a></li>
		<li class="active"><a href="${ctx}/affair/affairLdgblzFile/">领导干部廉政档案</a></li>
<%--		<li ><a href="http://10.3.240.8/" target="_blank">领导干部考廉系统</a></li>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="affairLdgblzFile" action="${ctx}/affair/affairLdgblzFile/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="领导廉政干部档案表.xlsx"/>
		<ul class="ul-form">
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>身份证号：</label>
				<form:input path="idNumber" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>性别：</label>
				<form:select path="sex" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>出生年月：</label>
				<input name="birthdayStart" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairLdgblzFile.birthdayStart}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>至
				<input name="birthdayEnd" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairLdgblzFile.birthdayEnd}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>入党时间：</label>
				<input name="rdDateStart" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairLdgblzFile.rdDateStart}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>至
				<input name="rdDateEnd" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairLdgblzFile.rdDateEnd}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
        </ul>
        <ul class="ul-form2">
            <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairLdgblzFile:edit">
				<li class="btns"><input id="btn" class="btn btn-primary" type="button" onclick="openAddEditDialog()" value="添加"/></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="affair:affairLdgblzFile:delete">
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairLdgblzFile/deleteByIds','checkAll','myCheckBox')"/></li>
			</shiro:hasPermission>
            <shiro:hasPermission name="affair:affairLdgblzFile:edit">
                <li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
            </shiro:hasPermission>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
            <li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印" /></li>
            <li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairLdgblzFile/'"/></li>
            <li class="clearfix"></li>
        </ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input style='margin-left:12px' type='checkbox' id='checkAll' onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>身份证号</th>
				<th>姓名</th>
				<th>性别</th>
				<th>工作单位</th>
				<th>职务</th>
				<th>职级</th>
				<th>出生年月</th>
				<th>入党时间</th>
				<th>学历</th>
				<th>籍贯</th>
				<shiro:hasPermission name="affair:affairLdgblzFile:edit"><th id="handleTh">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairLdgblzFile" varStatus="status">
			<tr>
				<td>
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairLdgblzFile.id}"/>
				</td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>

							${affairLdgblzFile.idNumber}

				</td>
				<td>
					${affairLdgblzFile.name}
				<td>
					${fns:getDictLabel(affairLdgblzFile.sex, 'sex', '')}
				</td>

				<td>
						${affairLdgblzFile.unit}
				</td>
				<td>
					${affairLdgblzFile.workUnitJob}
				</td>
				<td>
					${affairLdgblzFile.level}
				</td>
				<td>
					<fmt:formatDate value="${affairLdgblzFile.birthday}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<fmt:formatDate value="${affairLdgblzFile.rdDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${affairLdgblzFile.education}
				</td>
				<td>
					${affairLdgblzFile.nativePlace}
				</td>
				<td class="handleTd">
					<a href="javascript:void(0)" onclick="openDetailDialog('${affairLdgblzFile.id}')">查看</a>
				<shiro:hasPermission name="affair:affairLdgblzFile:edit">
						<a href="javascript:void(0)" onclick="openAddEditDialog('${affairLdgblzFile.id}')">修改</a>
				</shiro:hasPermission>
					<shiro:hasPermission name="affair:affairLdgblzFile:delete">
						<a href="${ctx}/affair/affairLdgblzFile/delete?id=${affairLdgblzFile.id}" onclick="return confirmx('确认要删除该领导廉政干部档案吗？', this.href)">删除</a>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>