<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>文艺人才库管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairWentiTalentNext/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairWentiTalentNext/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairWentiTalentNext/export?flag=true")
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairWentiTalentNext/");
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
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairWentiTalentNext", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairWentiTalentNext"}});
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
			return false;
		}
		//添加function,用于打开form添加页面弹窗,需要传一个url
		function openForm(url) {
			top.$.jBox.open("iframe:"+url, "文艺人才库",800,520,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairWentiTalentNext"}
			});
		}
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairWentiTalentNext/formDetail?id="+id;
			top.$.jBox.open(url, '文艺人才库',800,500,{
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
	<!--
	<li ><a href="${ctx}/affair/affairWentiTalent/">体育人才库</a></li>-->
	<li class="active"><a href="${ctx}/affair/affairWentiTalentNext/">文艺人才库</a></li>
</ul>
<form:form id="searchForm" modelAttribute="affairWentiTalentNext" action="${ctx}/affair/affairWentiTalentNext/" method="post" class="breadcrumb form-search">
	<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
	<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	<input id="fileName" name="fileName" type="hidden" value="文艺人才库表.xlsx"/>
	<ul class="ul-form">
		<li><label>警号：</label>
			<form:input path="policeNo" htmlEscape="false" class="input-medium"/>
		</li>
		<li><label>单位：</label>
			<sys:treeselect id="unit" name="unitId" value="${affairWentiTalentNext.unitId}" labelName="unit" labelValue="${affairWentiTalentNext.unit}"
				title="单位名称" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
		</li>
		<li><label>姓名：</label>
			<form:input path="name" htmlEscape="false" class="input-medium"/>
		</li>
	</ul>
	<ul class="ul-form2">
		<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
		<shiro:hasPermission name="affair:affairWentiTalentNext:edit">
			<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/affair/affairWentiTalentNext/form')"/></li>
			<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairWentiTalentNext/deleteByIds','checkAll','myCheckBox')"/></li>
			<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
		</shiro:hasPermission>
		<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
		<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
		<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairWentiTalentNext'"/></li>
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
		<th>性别</th>
		<th>身份证号</th>
		<th>警号</th>
		<th>出生年月</th>
		<th>毕业院校</th>
		<th>何种艺术特长</th>
		<th>取得成绩</th>
		<th id="handleTh">操作</th>
	</tr>
	</thead>
	<tbody>
	<c:forEach items="${page.list}" var="affairWentiTalentNext" varStatus="status">
		<tr>
			<td><input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairWentiTalentNext.id}"/></td>
			<td>
					${(page.pageNo-1)*page.pageSize+status.index+1}
			</td>
			<td>
					${affairWentiTalentNext.name}
			</td>
			<td>
				${fns:getDictLabel(affairWentiTalentNext.sex, 'sex', '')}
			</td>
			<td>
				${affairWentiTalentNext.idNumber}
			</td>
			<td>
				${affairWentiTalentNext.policeNo}
			</td>
			<td>
				<fmt:formatDate value="${affairWentiTalentNext.birthday}" pattern="yyyy-MM-dd"/>
			</td>
			<td>
					${affairWentiTalentNext.school}
			</td>
			<td>
					${affairWentiTalentNext.skill}
			</td>
			<td>
					${affairWentiTalentNext.achievement}
			</td>
			<td class="handleTd">
					<%--<c:if test="${affairWentiTalentNext.createBy.id == fns:getUser().id}">--%>
				<a onclick="openDetailDialog('${affairWentiTalentNext.id}')">查看</a>
					<shiro:hasPermission name="affair:affairWentiTalentNext:edit">
					<a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairWentiTalentNext/form?id=${affairWentiTalentNext.id}')">修改</a>
					<a href="${ctx}/affair/affairWentiTalentNext/delete?id=${affairWentiTalentNext.id}" onclick="return confirmx('确认要删除该文艺人才库吗？', this.href)">删除</a>
						<%--</c:if>--%>
					</shiro:hasPermission>
			</td>
		</tr>
	</c:forEach>
	</tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>