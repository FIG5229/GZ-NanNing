<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>复查表管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {

			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairSpiritualTable/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairSpiritualTable/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairSpiritualTable/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairSpiritualTable/");
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
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairSpiritualTable", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairSpiritualTable"}});
			});
			
		});

		//添加，修改弹窗
		function openDialog(url) {
			top.$.jBox.open("iframe:"+url, " ",1200,600,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		}

		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairSpiritualTable/formDetail?id="+id;
			top.$.jBox.open(url, "",900,500,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
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
		<shiro:hasPermission name="affair:affairSpiritualCivilization:view">
			<li><a href="${ctx}/affair/affairSpiritualCivilization/">精神文明单位</a></li>
		</shiro:hasPermission>
		<shiro:hasPermission name="affair:affairSpiritualTable:view">
			<li class="active"><a href="${ctx}/affair/affairSpiritualTable/">复查表</a></li>
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
			<li><a href="${ctx}/affair/affairEvaluationCriteria/">测评标准</a></li>
		</shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="affairSpiritualTable" action="${ctx}/affair/affairSpiritualTable/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="复查表.xlsx"/>
		<ul class="ul-form">
			<li><label>单位：</label>
				<sys:treeselect id="unit" name="unitId" value="${affairSpiritualTable.unitId}" labelName="unit" labelValue="${affairSpiritualTable.unit}"
								title="机构" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="false" isAll="true"/>
			</li>
			<li>
				<label>年度：</label>
				<input name="year" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="${affairSpiritualTable.year}"
					   onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false});"/>
			</li>
			<li><label>批准层级：</label>
				<form:input path="ratify" htmlEscape="false" class="input-medium"/>
			</li>
			<li>
				<label>批准时间：</label>
				<input name="beginTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairSpiritualTable.beginTime}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> -
				<input name="endTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairSpiritualTable.endTime}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairSpiritualTable:edit">
				<li class="btns">
					<input  class="btn btn-primary"  type="button" value="添加" onclick="openDialog('${ctx}/affair/affairSpiritualTable/form?id=${affairSpiritualTable.id}')"/>
				</li>
				<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除"
										onclick="deleteByIds('${ctx}/affair/affairSpiritualTable/deleteByIds','checkAll','myCheckBox')"/></li>
			</shiro:hasPermission>

			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairSpiritualTable'"/></li>
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
				<th>单位</th>
				<th>批准层级</th>
				<th>批准时间</th>
				<th>联系电话</th>
				<th>检查成绩</th>
				<th>抽查成绩</th>
				<shiro:hasPermission name="affair:affairSpiritualTable:edit"><th class="handleTd">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairSpiritualTable" varStatus="status">
			<tr>
				<td class="checkTd">
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairSpiritualTable.id}"/>
				</td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
					${affairSpiritualTable.year}
				</td>
				<td>
					${affairSpiritualTable.unit}
				</td>
				<td>
					${affairSpiritualTable.ratify}
				</td>
				<td>
					<fmt:formatDate value="${affairSpiritualTable.time}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${affairSpiritualTable.phone}
				</td>
				<td>
					${affairSpiritualTable.number}
				</td>
				<td>
					${affairSpiritualTable.randomNumber}
				</td>
				<td class="handleTd">
					<a href="javascript:void(0)" onclick="openDetailDialog('${affairSpiritualTable.id}')">查看</a>
					<shiro:hasPermission name="affair:affairSpiritualTable:edit">
						<a href="javascript:void(0)" onclick="openDialog('${ctx}/affair/affairSpiritualTable/form?id=${affairSpiritualTable.id}')">修改</a>

						<a href="${ctx}/affair/affairSpiritualTable/delete?id=${affairSpiritualTable.id}" onclick="return confirmx('确认要删除该复查表吗？', this.href)">删除</a>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>