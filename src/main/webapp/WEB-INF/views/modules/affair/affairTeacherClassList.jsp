<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>班主任培训班管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairTeacherClass/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairTeacherClass/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairTeacherClass/export?flag=true")
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairTeacherClass/");
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
				$('#headTitle').addClass("table table-striped table-bordered table-condensed");
				$('#headTitle').removeAttr('style');

				$("#contentTable").printThis({
					debug: false,
					importCSS: false,
					importStyle: false,
					printContainer: true,
					loadCSS: ["${ctxStatic}/bootstrap/2.3.1/css_cerulean/bootstrap.min.css","${ctxStatic}/bootstrap/2.3.1/awesome/font-awesome.min.css","${ctxStatic}/common/jeesite.css"],
					pageTitle: "",
					removeInline: false,
					printDelay: 333,
					header: "",
					formValues: false,
					afterPrint:function(){
						$('#handleTh').css('display', 'table-cell');
						$('.handleTd').css('display', 'table-cell');
						$('#headTitle').css('display', 'none');

					}
				});
			});
			$("#btnImport").click(function(){
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairTeacherClass", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairTeacherClass"}});
			});
		});

		//添加 弹窗
		function openForm(url) {
			top.$.jBox.open("iframe:"+url, "班主任培训班",1200,600,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairTeacherClass"}
			});
		}

	  	//添加修改弹窗
		function openAddEditDialog(id) {
			var url = "iframe:${ctx}/affair/affairTeacherClass/form?id="+id;
			if (id == null || id == undefined){
				url = "iframe:${ctx}/affair/affairTeacherClass/form";
			}
			top.$.jBox.open(url, "",1000,650,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){
					self.location.href = "${ctx}/affair/affairTeacherClass/list";
				}
			});
		};

		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairTeacherClass/formDetail?id="+id;
			top.$.jBox.open(url, "培训班详情",1000,400,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		};

		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<form:form id="searchForm" modelAttribute="affairTeacherClass" action="${ctx}/affair/affairTeacherClass/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="班主任培训班表.xlsx"/>
		<ul class="ul-form">
			<li>
				<label>编号：</label>
				<form:input path="createOrgId" htmlEscape="false" class="input-medium"/>
			</li>
			<li>
				<label>培训班名称：</label>
				<form:input path="className" htmlEscape="false" class="input-medium"/>
			</li>
			<li>
				<label>培训时间：</label>
				<input name="beginReportTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairTeacherClass.trainBeginTime}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> -
				<input name="endReportTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairTeacherClass.trainEndTime}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairTeacherClass:edit">
				<li class="btns">
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/affair/affairTeacherClass/form?id=${affairTeacherClass.id}')"/></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="affair:affairTeacherClass:edit">
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairTeacherClass/deleteByIds','checkAll','myCheckBox')"/></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="affair:affairTeacherClass:edit">
				<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
			</shiro:hasPermission>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairTeacherClass'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<div id="contentTable">
		<table id="headTitle"  class="table table-striped table-bordered table-condensed" style="display: none;">
			<tr>
				<td style="text-align: center; font-size: 36px;padding-top: 10px;" >南宁铁路公安处班主任培训班管理报表</td>
			</tr>
		</table>
		<table  class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input style='margin-left:12px' type='checkbox' id='checkAll' onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>培训班名称</th>
				<th>培训层次</th>
				<th>渠道</th>
				<th>培训项目</th>
				<th>培训开始日期</th>
				<th>培训结束日期</th>
				<th>主办部门</th>
				<th>创建人</th>
				<th>培训班状态</th>
				<shiro:hasPermission name="affair:affairTeacherClass:edit"><th class="handleTd">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairTeacherClass" varStatus="status">
			<tr>
				<td>
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairTeacherClass.id}"/>
				</td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
						${affairTeacherClass.className}
				</td>
				<td>
						${affairTeacherClass.type}
				</td>
				<td>
						${affairTeacherClass.way}
				</td>
				<td>
						${affairTeacherClass.project}
				</td>
				<td>
					<fmt:formatDate value="${affairTeacherClass.trainBeginTime}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<fmt:formatDate value="${affairTeacherClass.trainEndTime}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
						${affairTeacherClass.department}
				</td>
				<td>
						${fns:getUserById(affairTeacherClass.createBy).getName()}
				</td>
				<td>
						${affairTeacherClass.state}
				</td>
				<td class="handleTd">
					<a onclick="openDetailDialog('${affairTeacherClass.id}')">查看</a>
					<shiro:hasPermission name="affair:affairTeacherClass:edit">
						<a onclick="openAddEditDialog('${affairTeacherClass.id}')">修改</a>
						<a href="${ctx}/affair/affairTeacherClass/delete?id=${affairTeacherClass.id}" onclick="return confirmx('确认要删除吗？', this.href)">删除</a>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	</div>
	<div class="pagination">${page}</div>
</body>
</html>