<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>内部教官管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {

			$("[data-toggle='popover']").popover();
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
					afterPrint:function (){
						$('#handleTh').css('display', 'table-cell');
						$('.handleTd').css('display', 'table-cell');
						$('#headTitle').css('display', 'none');

					}
				});
			});
			//导出功能的JS
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairInteriorInstructorLibrary/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairInteriorInstructorLibrary/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairInteriorInstructorLibrary/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairInteriorInstructorLibrary/");
							}
							if (v == 'cancel') {
								$.jBox.tip('已取消');
							}
							return true;
						};
						$.jBox.confirm("您是否要导出数据？", "数据导出确认", submit, { buttons: { '导出全部数据': 'all', '导出当前页面数据': 'part','取消':'cancel'} });
					}
			);
			//导入功能的JS
			$("#btnImport").click(function(){
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairInteriorInstructorLibrary", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairInteriorInstructorLibrary"}});
			});
		});

		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairInteriorInstructorLibrary/formDetail?id="+id;
			top.$.jBox.open(url, "教官",1000,400,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		};


		//添加，修改弹窗
		function openDialog(url) {
			top.$.jBox.open("iframe:"+url, "教官",1200,600,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairInteriorInstructorLibrary"}
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
	<shiro:hasPermission name="affair:affairInstructorLibrary:view">
		<li>
			<a href="${ctx}/affair/affairInstructorLibrary/">外部教官管理</a>
		</li>
	</shiro:hasPermission>
	<shiro:hasPermission name="affair:affairInteriorInstructorLibrary:view">
		<li class="active">
			<a href="${ctx}/affair/affairInteriorInstructorLibrary/">内部教官管理</a>
		</li>
	</shiro:hasPermission>
</ul>
	<form:form id="searchForm" modelAttribute="affairInteriorInstructorLibrary" action="${ctx}/affair/affairInteriorInstructorLibrary/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="内部教官表.xlsx"/>
		<ul class="ul-form">
			<li><label>身份证号：</label>
				<form:input path="idNumber" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>教官姓名：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			<li>
				<label>教官类别：</label>
				<form:select id="instructorCategory" path="instructorCategory" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('Instructor_category')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li>
				<label>教官级别：</label>
				<form:select id="instructorLevel" path="instructorLevel" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('instructor_level')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairInteriorInstructorLibrary:edit">
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openDialog('${ctx}/affair/affairInteriorInstructorLibrary/form')"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairInteriorInstructorLibrary/deleteByIds','checkAll','myCheckBox')"/></li>
				<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
			</shiro:hasPermission>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印" /></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairInteriorInstructorLibrary'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
<div id="contentTable">
	<table id="headTitle"  class="table table-striped table-bordered table-condensed" style="display: none;">
		<tr>
			<td style="text-align: center; font-size: 36px;padding-top: 10px;" >南宁铁路公安处内部教官管理报表</td>
		</tr>
	</table>
	<table  class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input style='margin-left:12px' type='checkbox' id='checkAll' onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>身份证号</th>
				<th>姓名</th>
				<th>单位</th>
				<th>特长</th>
				<th>状态</th>
				<th>创建者</th>
				<shiro:hasPermission name="affair:affairInteriorInstructorLibrary:edit"><th class="handleTd">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairInteriorInstructorLibrary" varStatus="status">
			<tr>
				<td>
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairInteriorInstructorLibrary.id}"/>
				</td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
						${affairInteriorInstructorLibrary.idNumber}
				</td>
				<td>
						${affairInteriorInstructorLibrary.name}
				</td>
				<td>
						${affairInteriorInstructorLibrary.unit}
				</td>
				<td>
						${affairInteriorInstructorLibrary.speciality}
				</td>
				<td>
						${affairInteriorInstructorLibrary.perpleState}
				</td>
				<td>
						${fns:getUserById(affairInteriorInstructorLibrary.createBy).getName()}
				</td>
				<td class="handleTd">
					<a href="javascript:void(0)" onclick="openDetailDialog('${affairInteriorInstructorLibrary.id}')">查看</a>
					<shiro:hasPermission name="affair:affairInteriorInstructorLibrary:edit">
						<a href="javascript:void(0)" onclick="openDialog('${ctx}/affair/affairInteriorInstructorLibrary/form?id=${affairInteriorInstructorLibrary.id}')">修改</a>
						<a href="${ctx}/affair/affairInteriorInstructorLibrary/delete?id=${affairInteriorInstructorLibrary.id}" onclick="return confirmx('确认要删除该内部教官吗？', this.href)">删除</a>
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