<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>妇幼关爱管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
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


			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairPolicewomanTalent/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairPolicewomanTalent/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairPolicewomanTalent/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairPolicewomanTalent/");
							}
							if (v == 'cancel') {
								$.jBox.tip('已取消');
							}
							return true;
						};
						$.jBox.confirm("您是否要导出数据？", "数据导出确认", submit, { buttons: { '导出全部数据': 'all', '导出当前页面数据': 'part','取消':'cancel'} });
					}
			);

			$("#btnImport").click(function(){
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairPolicewomanTalent", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairPolicewomanTalent"}});
			});

		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		let width =  $(window).width()-50;
		let height =  $(window).height()-100;
		//添加function,用于打开form添加页面弹窗,需要传一个url
		function openForm(url) {
			top.$.jBox.open("iframe:" + url, "女警风采信息", width, height, {
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairPolicewomanTalent"}
			});
		}
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairPolicewomanTalent/formDetail?id="+id;
			top.$.jBox.open(url, "\n" +
					"女警风采信息", width, height, {
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		};
		//推送详情弹窗
		function openPropellingDialog(id) {
			var url = "iframe:${ctx}/affair/affairPolicewomanTalent/propelling?id="+id;
			top.$.jBox.open(url, "\n" + "数据推送",500,300,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function(){
					self.location.href="${ctx}/affair/affairPolicewomanTalent/";
				}
			});
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<shiro:lacksPermission name="affair:affairPolicewomanWork:manage">
			<li><a href="${ctx}/affair/affairPolicewomanWork/">女警工作管理</a></li>
		</shiro:lacksPermission>

		<shiro:hasPermission name="affair:affairPolicewomanWork:manage">
			<%--有管理权限时 跳转到管理页 无权限则是查询页 达到合并tab效果--%>
			<input type="hidden" value="hasPermission" id="permission">
			<li><a href="${ctx}/affair/affairPolicewomanWork/manageList">女警工作管理</a></li>
		</shiro:hasPermission>
		<li class="active"><a href="${ctx}/affair/affairPolicewomanTalent/">女警风采信息</a></li>
		<li ><a href="${ctx}/affair/affairChildSubsidy/">幼儿补助信息</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="affairPolicewomanTalent" action="${ctx}/affair/affairPolicewomanTalent/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="女警风采信息表.xlsx"/>
		<ul class="ul-form">
			<li><label>单位：</label>
				<sys:treeselect id="unit" name="unitId" value="${affairPolicewomanTalent.unitId}" labelName="unit" labelValue="${affairPolicewomanTalent.unit}"
								title="单位" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>录入时间：</label>
				<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairPolicewomanTalent.startDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				至
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairPolicewomanTalent.endDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairPolicewomanTalent:edit">
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/affair/affairPolicewomanTalent/form?id=${affairPolicewomanTalent.id}')"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairPolicewomanTalent/deleteByIds','checkAll','myCheckBox')"/></li>
<%--
				<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
--%>
			</shiro:hasPermission>
<%--
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
--%>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairPolicewomanTalent'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input style='margin-left:12px' type='checkbox' id='checkAll' onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>标题</th>
				<th>单位</th>
				<th>姓名</th>
				<%--<th>出生年月</th>
				<th>毕业院校</th>
				<th>何种特长</th>
				<th>获奖情况</th>--%>
				<shiro:hasPermission name="affair:affairPolicewomanTalent:edit"><th id="handleTh">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairPolicewomanTalent" varStatus="status">
			<tr>
				<td>
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairPolicewomanTalent.id}"/>
				</td>	
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
						${affairPolicewomanTalent.job}
				</td>
				<td>
						${affairPolicewomanTalent.unit}
				</td>
				<td>
				${affairPolicewomanTalent.name}
				</td>

				<%--<td>
					<fmt:formatDate value="${affairPolicewomanTalent.birthday}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${affairPolicewomanTalent.school}
				</td>
				<td>
					${affairPolicewomanTalent.speciality}
				</td>
				<td>
					${affairPolicewomanTalent.situation}
				</td>--%>
				<td class="handleTd">
					<a href="javascript:void(0)" onclick="openDetailDialog('${affairPolicewomanTalent.id}')">查看</a>
					<shiro:hasPermission name="affair:affairPolicewomanTalent:edit">
						<a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairPolicewomanTalent/form?id=${affairPolicewomanTalent.id}')">修改</a>
						<a href="${ctx}/affair/affairPolicewomanTalent/delete?id=${affairPolicewomanTalent.id}"
						   onclick="return confirmx('确认要删除该女警风采信息吗？', this.href)">删除</a>
						<a href="javascript:void(0)" onclick="openPropellingDialog('${affairPolicewomanTalent.id}')">推送</a>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>