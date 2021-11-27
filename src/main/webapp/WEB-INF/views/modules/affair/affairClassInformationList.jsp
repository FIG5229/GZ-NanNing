<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>课程信息</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript" src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {

			var lo="look";
			var lo1="look111";
			var lk="${look}";
			if(lk==lo){
				hidebutton();
			}else if(lk==lo1){
				showbutton();
			}

			var basic1="basicLook";
			var basic2="basicUpdate";
			var basic3="${basic}"
			if(basic3!=basic1){
				var basicLook= document.getElementById("y1");
				basicLook.style.display="none";
			}else if(basic3!=basic2){
				var basicLook= document.getElementById("y2");
				basicLook.style.display="none";
			}

			$("#btnPrint").click(function(){
				$('#handleTh').css('display', 'none');
				$('.handleTd').css('display', 'none');
				$('#checkTh').css('display', 'none');
				$('.checkTd').css('display', 'none');
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
						$('#checkTh').css('display', 'table-cell');
						$('.checkTd').css('display', 'table-cell');
					}
				});
			});

			$("#btnExport").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairClassInformation/export?id=${id}");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairClassManage/idList?classId=${id}&look=${look}");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairClassInformation/export?flag=true&id=${id}");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairClassManage/idList?classId=${id}&look=${look}");
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
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairClassInformation", "导入",800,520,{
					itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){
						self.location.href="${ctx}/affair/affairClassManage/idList?classId=${id}&look=${look}"}});
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		function openAddForm(url) {
			top.$.jBox.open("iframe:"+url, "课程信息",1200,600,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
					//${ctx}/affair/affairClassInformation/form?classId=${id}
				},closed:function (){self.location.href="${ctx}/affair/affairClassManage/idList?classId=${id}&look=${look}"}
			});
		}
		function openEditDialog(id) {
			top.$.jBox.open("iframe:${ctx}/affair/affairClassInformation/form?classId=${id}&id="+id,"课程信息编辑",1200,600,{
				buttons:{"关闭":true},
				loaded:function () {
					$(".jbox-content",top.document).css("overflow-y","hidden");
				},closed:function () {self.location.href="${ctx}/affair/affairClassManage/idList?classId=${id}&look=${look}"}
			});
		}
		function openDetailDialog(id) {
			top.$.jBox.open("iframe:${ctx}/affair/affairClassInformation/formDetail?id="+id,"课程信息详情",1200,600,{
				buttons:{"关闭":true},
				loaded:function () {
					$(".jbox-content",top.document).css("overflow-y","hidden");
				},closed:function () {self.location.href="${ctx}/affair/affairClassManage/idList?classId=${id}&look=${look}"

				}
			});
		}
		if("success"=="${saveResult}"){
			parent.$.jBox.close();
		}
		function openDetailDialogtwo(id) {
			top.$.jBox.open("iframe:${ctx}/affair/affairClassManage/formDetail?id="+id,"培训班详情",1200,600,{
				buttons:{"关闭":true},
				loaded:function () {
					$(".jbox-content",top.document).css("overflow-y","hidden");
				},closed:function () {
					self.location.href="/affair/affairClassManage/idListRenYuan?id="+id
					//onclick="parent.$.jBox.close();"

				}
			});
		}
		function keChengXinXi(id) {
			top.$.jBox.open("iframe:${ctx}/affair/affairClassManage/idList?id="+id,"课程信息",1200,600,{
				buttons:{"关闭":true},
				loaded:function () {
					$(".jbox-content",top.document).css("overflow-y","hidden");
				},closed:function () {self.location.href="${ctx}/affair/affairClassManage"
				}
			});
		}
		function renYuanXinXi(id) {
			top.$.jBox.open("iframe:${ctx}/affair/affairClassManage/idListRenYuan?id="+id,"人员信息",1200,600,{
				buttons:{"关闭":true},
				loaded:function () {
					$(".jbox-content",top.document).css("overflow-y","hidden");
				},closed:function () {self.location.href="${ctx}/affair/affairClassManage"
				}
			});
		}




		function hidebutton() {
			var elementById = document.getElementById("tianjia");
			var elementById1 = document.getElementById("btnImport");
			var elementById2 = document.getElementById("del");
			elementById.style.display="none";
			elementById1.style.display="none";
			elementById2.style.display="none";
		}
		function showbutton(){
			var elementById = document.getElementById("tianjia");
			var elementById1 = document.getElementById("btnImport");
			var elementById2 = document.getElementById("del");
			elementById.style.display="block";
			elementById1.style.display="block";
			elementById2.style.display="block";

		}


	</script>
</head>
<body>

<ul  class="nav nav-tabs">
	<li id="y1"><a href="${ctx}/affair/affairClassManage/formDetail?id=${id}&basic=${basic}">基本信息</a></li>
	<li id="y2"><a href="${ctx}/affair/affairClassManage/form?id=${id}&basic=${basic}">基本信息</a></li>
	<li class="active"><a href="${ctx}/affair/affairClassManage/idList?classId=${id}&look=${look}">课程信息</a></li>
	<li><a href="${ctx}/affair/affairClassManage/idListRenYuan?id=${id}&lookPers=${lookPers}">人员信息</a></li>

</ul>

	<form:form id="searchForm" modelAttribute="affairClassInformation" action="${ctx}/affair/affairClassManage/idList?classId=${id}&look=${look}" method="post" class="breadcrumb form-search">
		<%--<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>--%>
		<input id="fileName" name="fileName" type="hidden" value="培训班课程信息表.xlsx"/>
		<ul class="ul-form">

			<li><label>培训班名称：</label>
				<form:input path="className" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>课程编号：</label>
				<form:input path="number" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>课程名称：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>授课方式：</label>
				<form:select path="way" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('way_classify')}" itemLabel="label" itemValue="value"
								  htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>单位：</label>
				<sys:treeselect id="unit" name="unitId" value="${affairClassInformation.unitId}" labelName="unit" labelValue="${affairClassInformation.unit}"
								title="单位" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="false"/>
			</li>
			<ul class="ul-form2">
				<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
				<shiro:hasPermission name="affair:affairClassInformation:edit">

					<li class="btns"><input id="tianjia"  class="btn btn-primary"  type="button" value="添加"
											 onclick="openAddForm('${ctx}/affair/affairClassInformation/form?classId=${id}&look=${look}')"/></li>
					<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
					<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除"
											onclick="deleteByIds('${ctx}/affair/affairClassInformation/deleteByIds','checkAll','myCheckBox')"/></li>
				</shiro:hasPermission>
				<li class="btns"><input id="btnExport" class="btn btn-primary" type="button" value="导出"/></li>
				<li class="btns"><input id="btnPrint" class="btn btn-primary" type="button" value="打印"/></li>
				<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairClassManage/idList?classId=${id}&look=${look}'"/></li>
				<li class="clearfix"></li>
			</ul>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th id="checkTh"><input style='margin-left:12px' type='checkbox' id='checkAll' onclick='chooseAll(this,"myCheckBox")'/>全选</th>
<%--
				<th>序号</th>
--%>
				<th>培训班名称</th>
				<th>课程编号</th>
				<th>课程名称</th>
				<th>授课方式</th>
				<th>开课时间</th>
				<th>学分</th>
				<th>教官</th>
				<shiro:hasPermission name="affair:affairClassInformation:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page}" var="affairClassInformation" varStatus="status">
			<tr>
				<td class="checkTd">
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairClassInformation.id}"/>
				</td>

				<td>
					${affairClassInformation.className}
				</td>
				<td>
					${affairClassInformation.number}
				</td>
				<td>
					${affairClassInformation.name}
				</td>
				<td>
					${fns:getDictLabel(affairClassInformation.way, 'way_classify','')}
				</td>
				<td>
						${affairClassInformation.learnTime}
				</td>
				<td>
						${affairClassInformation.score}
				</td>
				<td>
						${affairClassInformation.teacher}
				</td>
				<td class="handleTd">
					<a href="javascript:;" onclick="openDetailDialog('${affairClassInformation.id}')">查看</a>
					<shiro:hasPermission name="affair:affairClassInformation:edit">
						<a href="javascript:;" onclick="openEditDialog('${affairClassInformation.id}')">修改</a>
						<a href="${ctx}/affair/affairClassInformation/delete?id=${affairClassInformation.id}&classId=${id}&look=${look}"
						   onclick="return confirmx('确认要删除课程课程信息吗？', this.href)">删除</a>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
<%--
	<div class="pagination">${page}</div>
--%>
</body>
</html>