<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>查看报名详细管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript" src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {

			var lo1="lookPers";
			var lo2="lookPers111";
			var lk1="${lookPers}";
			if(lk1==lo1){
				hidebutton();
			}else if(lk1==lo2){
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


			$("#export").click(
					function () {
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action", "${ctx}/affair/affairPersonnelMessage/export?id=${id}");
								$("#searchForm").submit();
								$("#searchForm").attr("action", "${ctx}/affair/affairClassManage/idListRenYuan?id=${id}&lookPers=lookPers");
							}
							if (v == 'part') {
								$("#searchForm").attr("action", "${ctx}/affair/affairPersonnelMessage/export?flag=true&id=${id}")
								$("#searchForm").submit();
								$("#searchForm").attr("action", "${ctx}/affair/affairClassManage/idListRenYuan?id=${id}&lookPers=lookPers");
							}
							if (v == 'cancel') {
								$.jBox.tip('已取消');
							}
							return true;
						};
						$.jBox.confirm("您是否要导出数据？", "数据导出确认", submit, {
							buttons: {
								'导出全部数据': 'all',
								'导出当前页面数据': 'part',
								'取消': 'cancel'
							}
						});
					}
			);
			$("#btnImport").click(function () {
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairPersonnelMessage", "导入", 800, 520, {
					itle: "导入数据", buttons: {"关闭": true},
					bottomText: "导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！", closed: function () {
						self.location.href = "${ctx}/affair/affairClassManage/idListRenYuan?id=${affairPersonnelMessage.id}&lookPers=lookPers"
					}
				});
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        };
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairPersonnelMessage/formDetail?id=" + id;
			top.$.jBox.open(url, "人员信息报名详情", 1200, 600, {
				buttons: {"关闭": true},
				loaded: function (h) {
					$(".jbox-content", top.document).css("overflow-y", "hidden");
				}
			});
		};
		//添加，修改弹窗
		function openDialog(url) {
			top.$.jBox.open("iframe:"+url, "人员信息",1200,600,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairClassManage/idListRenYuan?id=${affairPersonnelMessage.id}&lookPers=${lookPers}"}
			});
		}
		function openDetailDialogtwo(id) {
			top.$.jBox.open("iframe:${ctx}/affair/affairClassManage/formDetail?id="+id,"培训班详情",1200,600,{
				buttons:{"关闭":true},
				loaded:function () {
					$(".jbox-content",top.document).css("overflow-y","hidden");
				},closed:function () {
					self.location.href="${ctx}/affair/affairClassManage/idList?id="+id+"&lookPers="+${lookPers}

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

	<li><a href="${ctx}/affair/affairClassManage/idList?classId=${id}&look=${look}">课程信息</a></li>
	<li  class="active"><a href="${ctx}/affair/affairClassManage/idListRenYuan?id=${id}&lookPers=${lookPers}">人员信息</a></li>


</ul>
	<form:form id="searchForm" modelAttribute="affairPersonnelMessage" action="${ctx}/affair/affairClassManage/idListRenYuan?id=${affairPersonnelMessage.id}&lookPers=${lookPers}" method="post" class="breadcrumb form-search">
		<%--<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>--%>
		<input id="fileName" name="fileName" type="hidden" value="人员信息.xlsx"/>
		<ul class="ul-form">
			<li><label>用户姓名：</label>
				<form:input path="username" htmlEscape="false" class="input-medium"/>
			</li>

			<li><label>出生日期：</label>
				<input name="birthDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairPersonnelMessage.birthDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>机构：</label>
				<form:input path="organization" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>职务：</label>
				<form:input path="post" htmlEscape="false" class="input-medium"/>
			</li>
			<li class="clearfix"></li>

			<ul class="ul-form2">

				<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>

				<shiro:hasPermission name="affair:affairPersonnelMessage:edit">
					<li class="btns"><input id="tianjia" class="btn btn-primary" type="button" value="添加"
											onclick="openDialog('${ctx}/affair/affairPersonnelMessage/form?personnelId=${id}&lookPers=${lookPers}')"/></li>

					<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除"
											onclick="deleteByIds('${ctx}/affair/affairPersonnelMessage/deleteByIds','checkAll','myCheckBox')"/>
					</li>

					<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
				</shiro:hasPermission>

				<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
				<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairClassManage/idListRenYuan?id=${id}&lookPers=${lookPers}'"/></li>
				<li class="clearfix">x</li>
			</ul>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th id="checkTh"><input style='margin-left:12px' type='checkbox' id='checkAll'
										onclick='chooseAll(this,"myCheckBox")'/></th>
				<th>用户姓名</th>
				<th>身份证号</th>
				<th>性别</th>
				<th>机构</th>
				<th>职务</th>
<%--<th>操作</th>--%>
				<shiro:hasPermission name="affair:affairPersonnelMessage:edit"><th>操作</th></shiro:hasPermission>

			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page}" var="affairPersonnelMessage" varStatus="status">
			<tr>
				<td class="checkTd">
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairPersonnelMessage.id}"/>
				</td>
				<td>
					${affairPersonnelMessage.username}
				</td>
				<td>
					${affairPersonnelMessage.idNumber}
				</td>
				<td>
					${fns:getDictLabel(affairPersonnelMessage.gender, 'sex','')}
				</td>
				<td>
					${affairPersonnelMessage.organization}
				</td>
				<td>
					${affairPersonnelMessage.post}
				</td>
				<td>
					<a onclick="openDetailDialog('${affairPersonnelMessage.id}')" style="cursor: pointer">查看报名详细信息</a>
					<shiro:hasPermission name="affair:affairPersonnelMessage:edit">
						<a href="javascript:void(0)"
						   onclick="openDialog('${ctx}/affair/affairPersonnelMessage/form?personnelId=${id}&id=${affairPersonnelMessage.id}')">修改</a>
						<a href="${ctx}/affair/affairPersonnelMessage/delete?id=${affairPersonnelMessage.id}&personnelId=${id}&lookPers=${lookPers}" onclick="return confirmx('确认要删除该人员信息吗？', this.href)">删除</a>
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