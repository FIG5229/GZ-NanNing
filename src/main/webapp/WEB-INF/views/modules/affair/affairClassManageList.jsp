<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>培训班管理管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript" src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnPrint").click(function(){
				$('#handleTh').css('display', 'none');
				$('.handleTd').css('display', 'none');
				$('#checkTh').css('display', 'none');
				$('.checkTd').css('display', 'none');
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
						$('#checkTh').css('display', 'table-cell');
						$('.checkTd').css('display', 'table-cell');
						$('#headTitle').css('display', 'none');
					}
				});
			});

			$("#btnExport").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairClassManage/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairClassManage/list?treeId");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairClassManage/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairClassManage/list");
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
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairClass", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairClassManage/list"}});
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		function openAddForm(url) {
			top.$.jBox.open("iframe:"+url, "培训班",1200,600,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairClassManage"}
			});
		}

		function openEditDialog(id) {
			top.$.jBox.open("iframe:${ctx}/affair/affairClassManage/form?id="+id+"&basic=${basic2}","培训班编辑",1200,600,{
				buttons:{"关闭":true},
				loaded:function () {
					$(".jbox-content",top.document).css("overflow-y","hidden");
				},closed:function () {self.location.href="${ctx}/affair/affairClassManage"}
			});
		}
		function openDetailDialog(id) {
			top.$.jBox.open("iframe:${ctx}/affair/affairClassManage/formDetail?id="+id+"&basic=${basic1}","培训班详情",1200,600,{
				buttons:{"关闭":true},
				loaded:function () {
					$(".jbox-content",top.document).css("overflow-y","hidden");
				},closed:function () {self.location.href="${ctx}/affair/affairClassManage"
				}
			});
		}

		if("success"=="${saveResult}"){
			parent.$.jBox.close();
		}
		function keChengXinXi(id) {
			top.$.jBox.open("iframe:${ctx}/affair/affairClassManage/idList?classId="+id,"课程信息",1200,600,{
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
		function openDetailDialogtwo(id) {
			top.$.jBox.open("iframe:${ctx}/affair/affairClassManage/formDetail?id="+id,"培训班详情",1200,600,{
				buttons:{"关闭":true},
				loaded:function () {
					$(".jbox-content",top.document).css("overflow-y","hidden");
				},closed:function () {
					//self.location.href="<%--${ctx}--%>/affair/affairClassManage"
					onclick="parent.$.jBox.close();"
				}
			});
		}
		function openForm(url) {
			top.$.jBox.open("iframe:"+url, "课程管理",1000,600,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairClassManage"}
			});
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/affair/affairClassManage/">培训班管理</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="affairClassManage" action="${ctx}/affair/affairClassManage/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="培训班详情表.xlsx"/>
		<ul class="ul-form">
			<li><label>培训名称：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
<%--			<li><label>培训年度：</label>--%>
<%--				<form:input path="year" htmlEscape="false" class="input-medium"/>--%>
<%--			</li>--%>
			<li>
				<label>培训年度：</label>
				<input name="year" path="year" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="${affairClassManage.year}"
					   onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false});"/>
			</li>
			<li><label>单位：</label>
				<sys:treeselect id="unit" name="unitId" value="${affairClassManage.unitId}" labelName="unit" labelValue="${affairClassManage.unit}"
								title="单位" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="false"/>
			</li>
			<li><label>培训日期：</label>
				<input name="beganDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairClassManage.beganDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				至
				<input name="resultDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairClassManage.resultDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<ul class="ul-form2">
				<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
				<shiro:hasPermission name="affair:affairClassManage:edit">
					<li class="btns"><input  class="btn btn-primary"  type="button" value="添加"
											 onclick="openAddForm('${ctx}/affair/affairClassManage/formTwo')"/></li>
					<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
					<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除"
											onclick="deleteByIds('${ctx}/affair/affairClassManage/deleteByIds','checkAll','myCheckBox')"/></li>
				</shiro:hasPermission>
				<li class="btns"><input id="btnExport" class="btn btn-primary" type="button" value="导出"/></li>
				<li class="btns"><input id="btnPrint" class="btn btn-primary" type="button" value="打印"/></li>
				<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairClassManage'"/></li>
				<li class="clearfix"></li>
			</ul>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
		<div id="contentTable">
			<table id="headTitle"  class="table table-striped table-bordered table-condensed" style="display: none;">
				<tr>
					<td style="text-align: center; font-size: 36px;padding-top: 10px;" >南宁铁路公安处培训班管理报表</td>
				</tr>
			</table>
			<table  class="table table-striped table-bordered table-condensed">

			<thead>
			<tr>
				<th id="checkTh"><input style='margin-left:12px' type='checkbox' id='checkAll' onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>培训名称</th>
				<th>单位</th>
				<th>培训日期</th>
				<th>标题</th>
				<th>培训班类型</th>
				<th>状态</th>
<%--				<th>建班状态</th>--%>
<%--				<th>结项状态</th>--%>
				<th>创建人</th>
				<th>创建时间</th>
				<th>创建人单位</th>
				<th>主办部门</th>
				<th>更新时间</th>
				<shiro:hasPermission name="affair:affairClassManage:edit"><th class="handleTd">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairClassManage" varStatus="status">
			<tr>
				<td class="checkTd">
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairClassManage.id}"/>
				</td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
					${affairClassManage.name}
				</td>
				<td>
					${affairClassManage.unit}
				</td>
				<td>
					<fmt:formatDate value="${affairClassManage.beganDate}" pattern="yyyy-MM-dd"/> ~ <fmt:formatDate value="${affairClassManage.resultDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${affairClassManage.title}
				</td>
				<td>
					${fns:getDictLabel(affairClassManage.type, 'affair_train_type', '')}
				</td>
				<td>

					<c:choose>
						<c:when test="${'1' == affairClassManage.openStatus}">
							<c:if test="${affairClassManage.createBy.id == fns:getUser().id}">
								<a href="${ctx}/affair/affairClassManage/updateStatus?id=${affairClassManage.id}&status=0" onclick="return confirmx('确认要取消开班吗？', this.href)">已开班</a>
							</c:if>
						</c:when>
						<c:otherwise>
							<c:if test="${affairClassManage.createBy.id == fns:getUser().id}">
								<a href="${ctx}/affair/affairClassManage/updateStatus?id=${affairClassManage.id}&status=1" onclick="return confirmx('确认要开班吗？', this.href)">未开班</a>
							</c:if>
						</c:otherwise>
					</c:choose>
					<c:choose>
						<c:when test="${'1' == affairClassManage.classStatus}">
							<c:if test="${affairClassManage.createBy.id == fns:getUser().id}">
								<a href="${ctx}/affair/affairClassManage/updateClassStatus?id=${affairClassManage.id}&status=0" onclick="return confirmx('确认要取消建班吗？', this.href)">已建班</a>
							</c:if>
						</c:when>
						<c:otherwise>
							<c:if test="${affairClassManage.createBy.id == fns:getUser().id}">
								<a href="${ctx}/affair/affairClassManage/updateClassStatus?id=${affairClassManage.id}&status=1" onclick="return confirmx('确认要建班吗？', this.href)">未建班</a>
							</c:if>
						</c:otherwise>
					</c:choose>
					<c:choose>
						<c:when test="${'1' == affairClassManage.pospStatus}">
							<c:if test="${affairClassManage.createBy.id == fns:getUser().id}">
								<a href="${ctx}/affair/affairClassManage/updateResultStatus?id=${affairClassManage.id}&status=0" onclick="return confirmx('确认要取消结项吗？', this.href)">已结项</a>
							</c:if>
						</c:when>
						<c:otherwise>
							<c:if test="${affairClassManage.createBy.id == fns:getUser().id}">
								<a href="${ctx}/affair/affairClassManage/updateResultStatus?id=${affairClassManage.id}&status=1" onclick="return confirmx('确认要结项吗？', this.href)">未结项</a>
							</c:if>
						</c:otherwise>
					</c:choose>
					<c:choose>
						<c:when test="${'1' == affairClassManage.status}">
							<c:if test="${affairClassManage.createBy.id == fns:getUser().id}">
								<a href="${ctx}/affair/affairClassManage/status?id=${affairClassManage.id}&status=2" onclick="return confirmx('确认要实施吗？', this.href)">未实施</a>
							</c:if>
						</c:when>
						<c:otherwise>
							<c:if test="${affairClassManage.createBy.id == fns:getUser().id}">
								<a href="${ctx}/affair/affairClassManage/status?id=${affairClassManage.id}&status=1" onclick="return confirmx('确认要取消实施吗？', this.href)">已实施</a>
							</c:if>
						</c:otherwise>
					</c:choose>
				</td>
				<td>
						${fns:getUserById(affairClassManage.createBy).getName()}
				</td>
				<td>
					<fmt:formatDate value="${affairClassManage.createDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
						${fns:getUserById(affairClassManage.createBy).getOffice()}
				</td>
				<td>
					${affairClassManage.sponsorUnit}
				</td>
				<td>
					<fmt:formatDate value="${affairClassManage.updateDate}" pattern="yyyy-MM-dd"/>
				</td>

				<td class="handleTd">
					<a href="javascript:;" onclick="openDetailDialog('${affairClassManage.id}')">查看</a>

					<shiro:hasPermission name="affair:affairClassManage:edit">
						<a href="javascript:;" onclick="openEditDialog('${affairClassManage.id}')">修改</a>
						<a href="${ctx}/affair/affairClassManage/delete?id=${affairClassManage.id}"
						   onclick="return confirmx('确认要删除吗？', this.href)">删除</a>
					</shiro:hasPermission>
<%--
					<a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairClassInformation?courseId=${affairClassManage.courseId}')">课程信息</a>
--%>
					<%--<a href="javascript:;" onclick="keChengXinXi('${affairClassManage.id}')">课程信息</a>
					<a href="javascript:;" onclick="renYuanXinXi('${affairClassManage.id}')">人员信息</a>--%>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
		</div>
	<div class="pagination">${page}</div>
</body>
</html>