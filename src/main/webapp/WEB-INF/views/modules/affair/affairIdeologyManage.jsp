<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>意识形态管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
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
								$("#searchForm").attr("action","${ctx}/affair/affairIdeology/export?hasAuth=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairIdeology/manage");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairIdeology/export?flag=true&hasAuth=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairIdeology/manage");
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
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairIdeology", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairIdeology/manage"}});
			});

		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
    //    ${ctx}/affair/affairIdeology/form?id=
		function openAddDialog() {
			top.$.jBox.open("iframe:${ctx}/affair/affairIdeology/form", "意识形态添加",800,600,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairIdeology/manage"}
			});
		}


		function openEditDialog(id) {
			top.$.jBox.open("iframe:${ctx}/affair/affairIdeology/form?id="+id,"意识形态编辑",800,600,{
				buttons:{"关闭":true},
				loaded:function () {
					$(".jbox-content",top.document).css("overflow-y","hidden");
				},closed:function () {self.location.href="${ctx}/affair/affairIdeology/manage"}
			});
		}
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairIdeology/formDetail?id="+id;
			top.$.jBox.open(url, "意识形态详情",900,600,{
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
		<%--宣教的管理账号进行查看 签收--%>

		 <c:choose>
              <%-- <c:when test="${userId == '66937439b2124f328d1521968fab06db' || userId == '8a6819768aef40968e8f289842613276' ||
               userId == 'd154234ecb35470e84fb95e53726866b' || userId=='d30e324c8f73492d9b74103374fbc689'|| userId=='e3ac8381fb3247e0b64fd6e3c48bddc1'}" >
					   <li class="active"><a href="${ctx}/affair/affairIdeology/manage">意识形态</a></li>
               </c:when>--%>
			 <c:when test="${userId == '66937439b2124f328d1521968fab06db' || userId == '8a6819768aef40968e8f289842613276'}">
				 <li><a href="${ctx}/affair/affairIdeology/juList">局机关</a></li>
				 <li class="active"><a href="${ctx}/affair/affairIdeology/manage">公安处党委</a></li>
			 </c:when>
			 <%--账号改为业务账号--%>
			 <c:when test="${userId == 'e3ac8381fb3247e0b64fd6e3c48bddc1' || userId=='d30e324c8f73492d9b74103374fbc689'|| userId=='d154234ecb35470e84fb95e53726866b'}">
				 <li class="active"><a href="${ctx}/affair/affairIdeology/manage">基层党支部</a></li>
				 <li><a href="${ctx}/affair/affairIdeology/list">公安处党委</a></li>
			 </c:when>
			 <c:otherwise>
				 <li><a href="${ctx}/affair/affairIdeology/list">基层党支部</a></li>
				 <%--其他账号进行上报--%>
			 </c:otherwise>
           </c:choose>


<%--		<li class="active"><a href="${ctx}/affair/affairIdeology/list">意识形态列表</a></li>--%>
<%--		<li class="active"><a href="${ctx}/affair/affairIdeology/manage">意识形态列表</a></li>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="affairIdeology" action="${ctx}/affair/affairIdeology/manage" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="意识形态.xlsx"/>
		<ul class="ul-form">
			<%--<li><label>单位：</label>
				<sys:treeselect id="unit" name="unitId" value="${affairIdeology.unitId}" labelName="unit" labelValue="${affairIdeology.unit}"
								title="单位" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="false"/>
			</li>
			<li class="control-group">
				<label>报送状态：</label>
				<form:select path="reportStatus" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('thought_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="control-group">
				<label>报告类型：</label>
				<form:select path="reportType" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('report_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>报送时间：</label>
				<input name="beginReportTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairIdeology.beginReportTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> - 
				<input name="endReportTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairIdeology.endReportTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>--%>
				<li><label>时间：</label>
					<input name="year" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
						   value="${affairIdeology.year}"
						   onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false});"/>
				</li>
				<li class="control-group">
					<label>报告类型：</label>
					<form:select path="reportType" class="input-xlarge required">
						<form:option value="" label=""/>
						<form:options items="${fns:getDictList('report_tale')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
				</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input id="btnExport" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input id="btnPrint" class="btn btn-primary" type="button" value="打印"/></li>
			<c:if test="${userId == '66937439b2124f328d1521968fab06db' || userId == '8a6819768aef40968e8f289842613276' ||
			userId == 'a4e40825e3b34c37895ee2ed168f24a0' || userId=='94ccb0bdb21441f997c72268b4d882db' || userId=='82f8e9ba1cd7446b99c084d5b4b5c038'}">
						<li class="btns"><input id="btnAdd" class="btn btn-primary" type="button" value="添加"
												onclick="openAddDialog()"/></li>
						<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入" /></li>
			</c:if>
			<li class="btns"><input id="btnDel" class="btn btn-primary" type="button" value="删除"
									onclick="deleteByIds('${ctx}/affair/affairIdeology/deleteByIds','checkAll','myCheckBox')"/></li>
			<li class="btns"><input id="btnRese" class="btn btn-primary" type="button" value="重置"
									onclick="window.location.href='${ctx}/affair/affairIdeology/manage'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th id="checkTh"><input style='margin-left:12px' type='checkbox' id='checkAll' onclick='chooseAll(this,"myCheckBox")'/></th>
				<th>序号</th>
				<th>单位</th>
				<th>报告类型</th>
<%--				<th>时间</th>--%>
				<th>报送内容</th>
				<th>报送状态</th>
				<th>报送时间</th>
				<th>签收时间</th>
				<shiro:hasPermission name="affair:affairIdeology:edit"><th id="handleTh">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairIdeology" varStatus="status">
			<tr>
				<td class="checkTd">
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairIdeology.id}"/>
				</td>
				<td>
					${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
					${affairIdeology.unit}
				</td>
				<td>
					${fns:getDictLabel(affairIdeology.reportType, 'report_tale', '')}
				</td>
<%--				<td>--%>
<%--					<fmt:formatDate value="${affairIdeology.time}" pattern="yyyy-MM-dd"/>--%>
<%--				</td>--%>
				<td>
					${affairIdeology.content}
				</td>
				<td>
					${fns:getDictLabel(affairIdeology.reportStatus, 'thought_status', '')}
				</td>
				<td>
					<fmt:formatDate value="${affairIdeology.reportTime}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<fmt:formatDate value="${affairIdeology.signInTime}" pattern="yyyy-MM-dd"/>
				</td>
				<shiro:hasPermission name="affair:affairIdeology:edit"><td class="handleTd">
    				<a href="javascript:" onclick="openDetailDialog('${affairIdeology.id}')">查看</a>

					<c:if test="${userId == '66937439b2124f328d1521968fab06db' || userId == '8a6819768aef40968e8f289842613276' ||
					userId == 'd154234ecb35470e84fb95e53726866b' || userId=='d30e324c8f73492d9b74103374fbc689' || userId=='e3ac8381fb3247e0b64fd6e3c48bddc1'}">
						<a href="javascript:" onclick="openEditDialog('${affairIdeology.id}')">修改</a>
						<a href="${ctx}/affair/affairIdeology/delete?id=${affairIdeology.id}" onclick="return confirmx('确认要删除该意识形态吗？', this.href)">删除</a>
					</c:if>
					<c:if test="${affairIdeology.reportStatus != '1' && affairIdeology.createBy != fns:getUser().id}">
						<a href="${ctx}/affair/affairIdeology/sign?id=${affairIdeology.id}&reportStatus=3"
						   onclick="return confirmx('确认要签收该意识形态吗？', this.href)">签收</a>
						<a href="${ctx}/affair/affairIdeology/sign?id=${affairIdeology.id}&reportStatus=4"
						   onclick="return confirmx('确认要退回该意识形态吗？', this.href)">退回</a>
					</c:if>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
<%--	<div class="pagination">${page}</div>--%>
</body>
</html>