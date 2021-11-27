<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>测评标准管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairEvaluationCriteria/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairEvaluationCriteria/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairEvaluationCriteria/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairEvaluationCriteria/");
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
					afterPrint:function(){
						$('#handleTh').css('display', 'table-cell');
						$('.handleTd').css('display', 'table-cell');
						$('#checkTh').css('display', 'table-cell');
						$('.checkTd').css('display', 'table-cell');
					}
				});
			});
			$("#btnImport").click(function(){
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairEvaluationCriteria", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairEvaluationCriteria"}});
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
			var url = "iframe:${ctx}/affair/affairEvaluationCriteria/formDetail?id="+id;
			top.$.jBox.open(url, "测评标准详情",800,600,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		}
		//添加，修改弹窗
		function openDialog(url) {
			top.$.jBox.open("iframe:"+url, "测评标准",800,600,{
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
			<li class="active">
				<a href="${ctx}/affair/affairEvaluationCriteria/">测评标准</a>
			</li>
		</shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="affairEvaluationCriteria" action="${ctx}/affair/affairEvaluationCriteria/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="7.3测评标准.xlsx"/>
	<ul class="ul-form">
			<li><label>测评项目名：</label>
				<form:input path="title" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>层级：</label>
<%--				<form:input path="level" htmlEscape="false" class="input-medium"/>--%>
				<form:select path="level" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('evaluation_level')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>年度：</label>
<%--				<form:input path="year" htmlEscape="false" class="input-medium"/>--%>
				<input name="year" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					   value="${affairEvaluationCriteria.year}"
					   onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false});"/>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairEvaluationCriteria:edit">
				<li class="btns">
					<input  class="btn btn-primary"  type="button" value="添加"
							onclick="openDialog('${ctx}/affair/affairEvaluationCriteria/form')"/>
				</li>
				<li class="btns">
					<input id="del" class="btn btn-primary" type="button" value="删除"
						   onclick="deleteByIds('${ctx}/affair/affairEvaluationCriteria/deleteByIds','checkAll','myCheckBox')"/>
				</li>
				<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
			</shiro:hasPermission>

			<li class="btns">
				<input id="export" class="btn btn-primary" type="button" value="导出"/>
			</li>
			<li class="btns">
				<input id="print" class="btn btn-primary" type="button" value="打印"/>
			</li>
			<li class="btns">
				<input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairEvaluationCriteria'"/>
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
				<th>测评项目</th>
				<th>层级</th>
				<th>年度</th>
				<th>测评标准</th>
				<th>测评方法</th>
				<th>分数值</th>
<%--				<th>考核目标单位</th>--%>
				<th id="handleTh">操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairEvaluationCriteria" varStatus="status">
			<tr>
				<td class="checkTd">
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairEvaluationCriteria.id}"/>
				</td>
				<td>
					${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
					${affairEvaluationCriteria.title}
				</td>
				<td>
					${fns:getDictLabel(affairEvaluationCriteria.level, 'evaluation_level', '')}
				</td>
				<td>
					${affairEvaluationCriteria.year}
				</td>
				<td>
					${affairEvaluationCriteria.standard}
				</td>
				<td>
					${fns:getDictLabel(affairEvaluationCriteria.method, 'evaluation_method', '')}
				</td>
				<td>
					${affairEvaluationCriteria.score}
				</td>
				<%--<td>
					${affairEvaluationCriteria.unit}
				</td>--%>
				<td class="handleTd">
						<a href="javascript:"
						   onclick="openDetailDialog('${affairEvaluationCriteria.id}')">查看</a>
					<shiro:hasPermission name="affair:affairEvaluationCriteria:edit">
						<a href="javascript:"
						   onclick="openDialog('${ctx}/affair/affairEvaluationCriteria/form?id=${affairEvaluationCriteria.id}')">修改</a>
						<a href="${ctx}/affair/affairEvaluationCriteria/delete?id=${affairEvaluationCriteria.id}"
						   onclick="return confirmx('确认要删除该测评标准吗？', this.href)">删除</a>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>