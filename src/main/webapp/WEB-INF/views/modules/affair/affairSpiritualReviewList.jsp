<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>复查情况报告管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {

			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairSpiritualReview/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairSpiritualReview/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairSpiritualReview/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairSpiritualReview/");
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
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairSpiritualReview", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairSpiritualReview"}});
			});

			$("[data-toggle='popover']").popover();
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

		function submitByIds() {
			if(null == $("#oneCheckId").val() || "" ==  $("#oneCheckId").val()){
				$.jBox.tip('请选择审核单位');
				return false;
			}
			var ids = [];
			$("input:checkbox[name=myCheckBox]:checked").each(function () {
				console.log($(this).val());
				ids.push($(this).val());
			});
			if (ids.length > 0) {
				var idsStr = ids.join(",");
				$("#idsValue").val(idsStr);
				$("#searchForm2").submit();
			} else {
				$.jBox.tip('请先选择要提交的内容');
			}
		}
		/*
		* 审核
		* */
		function openDialog(id) {
			top.$.jBox.open("iframe:${ctx}/affair/affairSpiritualReview/shenHeDialog?id="+id,"复查情况审核",800,420,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},
				closed:function (){
					self.location.href="${ctx}/affair/affairSpiritualReview/list/?repage";
				}
			});
		}
		//添加修改弹窗
		function openAddEditDialog(id) {
			var url = "iframe:${ctx}/affair/affairSpiritualReview/form?id="+id;
			if (id == null || id == undefined){
				url = "iframe:${ctx}/affair/affairSpiritualReview/form";
			}
			top.$.jBox.open(url, "复查情况录入",1000,650,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		}
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairSpiritualReview/formDetail?id="+id;
			top.$.jBox.open(url, "复查情况详情",1000,400,{
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
		<shiro:hasPermission name="affair:affairSpiritualCivilization:view">
			<li><a href="${ctx}/affair/affairSpiritualCivilization/">精神文明单位</a></li>
		</shiro:hasPermission>
		<shiro:hasPermission name="affair:affairSpiritualTable:view">
			<li><a href="${ctx}/affair/affairSpiritualTable/">复查表</a></li>
		</shiro:hasPermission>
		<shiro:hasPermission name="affair:affairSpiritualReview:view">
			<li class="active"><a href="${ctx}/affair/affairSpiritualReview/">复查情况报告</a></li>
		</shiro:hasPermission>
		<shiro:hasPermission name="affair:affairCreateWork:view">
			<%--<c:choose>
				<c:when test="${userId=='66937439b2124f328d1521968fab06db' || userId == '8a6819768aef40968e8f289842613276'} ">
					<li><a href="${ctx}/affair/affairCreateWork/sign">创建工作</a></li>
				</c:when>
				<c:when test="${userId == 'e3ac8381fb3247e0b64fd6e3c48bddc1' || userId=='d30e324c8f73492d9b74103374fbc689'|| userId=='d154234ecb35470e84fb95e53726866b'}">
				<li><a href="${ctx}/affair/affairCreateWork/list">创建工作上报</a></li>
					<li><a href="${ctx}/affair/affairCreateWork/sign">创建工作审核</a></li>
				</c:when>
				<c:otherwise>
					<li><a href="${ctx}/affair/affairCreateWork/list">创建工作</a></li>
					&lt;%&ndash;其他账号进行上报&ndash;%&gt;
				</c:otherwise>
			</c:choose>--%>
			<li><a href="${ctx}/affair/affairCreateWork/list">创建工作</a></li>
		</shiro:hasPermission>
		<shiro:hasPermission name="affair:affairEvaluationCriteria:view">
			<li><a href="${ctx}/affair/affairEvaluationCriteria/">测评标准</a></li>
		</shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="affairSpiritualReview" action="${ctx}/affair/affairSpiritualReview/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
        <input id="fileName" name="fileName" type="hidden" value="7.1复查情况表.xlsx"/>
		<ul class="ul-form">
			<li>
				<label>单位：</label>
				<sys:treeselect id="unit" name="unitId" value="${affairSpiritualReview.unitId}" labelName="unit" labelValue="${affairSpiritualReview.unit}"
								title="单位" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="false" isAll="true"/>
			</li>
			<li>
				<label>题目：</label>
				<form:input path="title" htmlEscape="false" class="input-medium"/>
			</li>
			<li>
				<label>报告人：</label>
				<form:input path="reporter" htmlEscape="false" class="input-medium"/>
			</li>
			<li>
				<label>报告状态：</label>
				<form:select id="state" path="state" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('review_state')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairSpiritualReview:edit">
				<li class="btns"><input id="btn" class="btn btn-primary" type="button" onclick="openAddEditDialog()" value="添加"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairSpiritualReview/deleteByIds','checkAll','myCheckBox')"/></li>
			</shiro:hasPermission>
				<shiro:hasPermission name="affair:affairSpiritualReview:edit">
                    <li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
                </shiro:hasPermission>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
			<li class="btns"><input class="btn btn-primary" type="button" value="重置"
									onclick="window.location.href='${ctx}/affair/affairSpiritualReview/list?treeId=${treeId}'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input style='margin-left:12px' type='checkbox' id='checkAll' onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>单位</th>
				<th>题目</th>
				<th>报告人</th>
				<th>报告时间</th>
				<th>报告状态</th>
				<shiro:hasPermission name="affair:affairSpiritualReview:edit"><th class="handleTd">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairSpiritualReview" varStatus="status">
			<tr>
				<td>
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairSpiritualReview.id}"/>
				</td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
					${affairSpiritualReview.unit}
				</td>
				<td>
					${affairSpiritualReview.title}
				</td>
				<td>
					${affairSpiritualReview.reporter}
				</td>
				<td>
					<fmt:formatDate value="${affairSpiritualReview.time}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
						${fns:getDictLabel(affairSpiritualReview.state, 'review_state', '')}
				</td>
				<td class="handleTd">
					<shiro:hasPermission name="affair:affairSpiritualReview:edit">
						<a href="javascript:void(0)" onclick="openDetailDialog('${affairSpiritualReview.id}')">查看</a>
						<a href="javascript:void(0)" onclick="openAddEditDialog('${affairSpiritualReview.id}')">修改</a>
						<a href="${ctx}/affair/affairSpiritualReview/delete?id=${affairSpiritualReview.id}" onclick="return confirmx('确认要删除该复查情况报告吗？', this.href)">删除</a>
						<%--11.22 把审核推送先隐藏掉--%>
						<%--<shiro:hasPermission name="affair:affairSpiritualReview:manage">
							<a onclick="openDialog('${affairSpiritualReview.id}')">审核</a>
						</shiro:hasPermission>--%>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	<%--11.22 把审核推送先隐藏掉--%>
	<%--<form:form id="searchForm2" modelAttribute="affairSpiritualReview" action="${ctx}/affair/affairSpiritualReview/submitByIds" method="post" class="breadcrumb form-search">
		<ul class="ul-form" style="text-align: right">
			<font color="red">请选择审核单位：</font>
			<input type="hidden" name="ids" id="idsValue"/>
			<form:select id="oneCheckId" path="oneCheckId"  class="input-xlarge required">
				<form:option value="" label=""/>
				<form:options items="${fns:getDictList('audit_unit')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</form:select>
			<input  class="btn btn-primary" type="button" value="提交送审" onclick="submitByIds()"/>
		</ul>
	</form:form>--%>
</body>
</html>