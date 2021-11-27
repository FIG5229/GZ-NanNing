<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>创建工作管理</title>
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
					afterPrint:function (){
						$('#handleTh').css('display', 'table-cell');
						$('.handleTd').css('display', 'table-cell');
					}
				});
			});

			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairCreateWork/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action", "${ctx}/affair/affairCreateWork/list");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairCreateWork/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action", "${ctx}/affair/affairCreateWork/list");
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
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairCreateWork", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText: "导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",
					closed: function () {
						self.location.href = "${ctx}/affair/affairCreateWork/list"
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

		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairCreateWork/formDetail?id="+id;
			top.$.jBox.open(url, "创建工作详情",800,600,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
        }
        //添加，修改弹窗
		function openDialog(id) {
			var url="iframe:${ctx}/affair/affairCreateWork/form?id="+id;
			if (id == null || id == undefined) {
				var url="iframe:${ctx}/affair/affairCreateWork/form";
			}
			top.$.jBox.open(url, "创建工作",800,600,{
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
			<li><a href="${ctx}/affair/affairSpiritualReview/">复查情况报告</a></li>
		</shiro:hasPermission>
		<shiro:hasPermission name="affair:affairCreateWork:view">

<%--改为台账形式--%>
			<%--<c:choose>
				<c:when test="${userId=='66937439b2124f328d1521968fab06db' || userId == '8a6819768aef40968e8f289842613276'} ">
					<li><a href="${ctx}/affair/affairCreateWork/sign">创建工作</a></li>
				</c:when>
				<c:when test="${userId == 'e3ac8381fb3247e0b64fd6e3c48bddc1' || userId=='d30e324c8f73492d9b74103374fbc689'|| userId=='d154234ecb35470e84fb95e53726866b'}">
					<li class="active"><a href="${ctx}/affair/affairCreateWork/list">创建工作上报</a></li>
					<li><a href="${ctx}/affair/affairCreateWork/sign">创建工作审核</a></li>
				</c:when>
				<c:otherwise>
					<li class="active"><a href="${ctx}/affair/affairCreateWork/list">创建工作</a></li>
					&lt;%&ndash;其他账号进行上报&ndash;%&gt;
				</c:otherwise>
			</c:choose>--%>
			<li class="active"><a href="${ctx}/affair/affairCreateWork/list">创建工作</a></li>
		</shiro:hasPermission>
		<shiro:hasPermission name="affair:affairEvaluationCriteria:view">
			<li><a href="${ctx}/affair/affairEvaluationCriteria/">测评标准</a></li>
		</shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="affairCreateWork" action="${ctx}/affair/affairCreateWork/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="7.2创建工作 .xlsx"/>

		<ul class="ul-form">
			<li><label>测评项目：</label>
				<form:input path="title" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>层级：</label>
				<form:select path="level" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('evaluation_level')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>年度：</label>
				<input name="beginYear" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="${affairCreateWork.beginYear}"
					onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false});"/> -
				<input name="endYear" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					 value="${affairCreateWork.endYear}"
					onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false});"/>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairCreateWork:edit">
				<li class="btns">
					<input  class="btn btn-primary"  type="button" value="添加"
							onclick="openDialog()"/>
				</li>
				<li class="btns">
					<input id="del" class="btn btn-primary" type="button" value="删除"
						   onclick="deleteByIds('${ctx}/affair/affairCreateWork/deleteByIds','checkAll','myCheckBox')"/>
				</li>
<%--
				<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
--%>
			</shiro:hasPermission>

			<li class="btns">
				<input id="export" class="btn btn-primary" type="button" value="导出"/>
			</li>
			<li class="btns">
				<input id="print" class="btn btn-primary" type="button" value="打印"/>
			</li>
			<li class="btns">
				<input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairCreateWork/list'"/>
			</li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th id="checkTh"><input style='margin-left:12px' type='checkbox' id='checkAll' onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>年度</th>
				<th>层级</th>
				<th>测评项目</th>
				<th>测评标准</th>
				<th>单位</th>
				<th>测评方法</th>
				<th>测评分值</th>
				<th>测评内容</th>
<%--				<th>状态</th>--%>
				<th>开展工作情况</th>
				<shiro:hasPermission name="affair:affairCreateWork:edit"><th id="handleTh">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairCreateWork" varStatus="status">
			<tr>
				<td class="checkTd">
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairCreateWork.id}"/>
				</td>
				<td>
					${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
					${affairCreateWork.year}
				</td>
				<td>
					${fns:getDictLabel(affairCreateWork.level, 'evaluation_level', '')}
				</td>
				<td>
					${affairCreateWork.title}
				</td>
				<td>
					${affairCreateWork.standard}
				</td>
				<td>
					${affairCreateWork.unit}
				</td>
				<td>
					${fns:getDictLabel(affairCreateWork.method, 'evaluation_method', '')}
				</td>
				<td>
					${affairCreateWork.score}
				</td>
				<td>
					${affairCreateWork.content}
				</td>
				<%--<td>
					${fns:getDictLabel(affairCreateWork.status, 'declare_status', '')}
				</td>--%>
				<td>
					${affairCreateWork.workingConditions}
				</td>
				<td class="handleTd">
					<shiro:hasPermission name="affair:affairCreateWork:edit">
						<a href="javascript:" onclick="openDetailDialog('${affairCreateWork.id}')">查看</a>
					<%--只有未提交和审核未通过状态才能修改删除--%>
<%--						<a href="${ctx}/affair/affairCreateWork/report?id=${affairCreateWork.id}" onclick="return confirmx('确认要上报该创建工作吗？', this.href)">上报</a>--%>
<%--					<c:if test="${affairCreateWork.status == '1' || affairCreateWork.status == '4'}">--%>
						<a href="javascript:" onclick="openDialog('${affairCreateWork.id}')">修改</a>
						<a href="${ctx}/affair/affairCreateWork/delete?id=${affairCreateWork.id}" onclick="return confirmx('确认要删除该创建工作吗？', this.href)">删除</a>
<%--					</c:if>--%>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>